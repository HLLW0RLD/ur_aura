package com.example.ur_color.data.remote

import com.example.ur_color.utils.logDebug
import com.example.ur_color.utils.logError
import com.example.ur_color.utils.logSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class DailyCardService {
    private val apiKey =
        "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"


    /**
     * Генерирует карту дня в стиле Таро
     * @param userName - имя пользователя (для персонализации)
     * @return текст с картой дня
     */
    suspend fun generateDailyCard(userName: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prompt = """
            Ты — виртуальный таролог. 
            Сгенерируй для пользователя "$userName" карту дня как в Таро.
            Опиши кратко: название карты, её значение и совет на день.
            Ответ должен быть в формате: "Карта: ... Значение: ... Совет: ..."
        """.trimIndent()

            val messages = JSONArray().apply {
                put(
                    JSONObject().apply {
                        put("role", "user")
                        put("content", prompt)
                    }
                )
            }

            val json = JSONObject().apply {
                put("model", "gpt-3.5-turbo") // или "gpt-3.5-turbo"
                put("messages", messages)
                put("max_tokens", 200)
            }

            val body = json.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaType())

            val request = Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer $apiKey")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                val respBody = response.body?.string()
                if (!response.isSuccessful) {
                    val errorMsg = respBody?.let {
                        try {
                            JSONObject(it).getJSONObject("error").getString("message")
                        } catch (_: Exception) { null }
                    } ?: "Unknown error"
                    return@withContext Result.failure(Exception("GPT request failed: $errorMsg"))
                }

                val respJson = JSONObject(respBody!!)
                val content = respJson
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                Result.success(content.trim())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val loggingInterceptor = Interceptor { chain ->
        val request = chain.request()



        request.body?.let { body ->
            logDebug("start ${request.method} ${request.url}\n" +
                    "Headers: ${request.headers}\n" +
                    "Request body: $body")
        }

        val response = chain.proceed(request)

        val bodyString = response.body?.string().orEmpty()
        if (response.isSuccessful){
            logSuccess(
                "end ${request.method} ${request.url}\n" +
                        "Response body: $bodyString"
            )
        } else {
            logError(
                "end ${request.method} ${request.url}\n" +
                        "code: ${response.code} ${response.message}\n" +
                        "Response body: $bodyString"
            )
        }

        response.newBuilder()
            .body(bodyString.toResponseBody("application/json".toMediaType()))
            .build()
    }
}