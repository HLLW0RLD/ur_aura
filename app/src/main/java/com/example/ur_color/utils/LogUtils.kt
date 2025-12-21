package com.example.ur_color.utils

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.text.SimpleDateFormat
import java.util.Date

fun logDebug(
    msg: Any?,
    resolvedTag: String? = null
) {
    log(
        msg = msg,
        fixedLabel = LogType.DEBUG,
        logColor = tagColorMap[LogType.DEBUG] ?: LogColor.ORANGE,
        resolvedTag = resolvedTag ?: "TESTOVIY"
    )
}

fun logSuccess(
    msg: Any?,
    resolvedTag: String? = null
) {
    log(
        msg = msg,
        fixedLabel = LogType.SUCCESS,
        logColor = tagColorMap[LogType.SUCCESS] ?: LogColor.GREEN,
        resolvedTag = resolvedTag ?: "Success"
    )
}

fun logError(
    msg: Any?,
    resolvedTag: String? = null
) {
    log(
        msg = msg,
        fixedLabel = LogType.ERROR,
        logColor = tagColorMap[LogType.ERROR] ?: LogColor.RED,
        resolvedTag = resolvedTag ?: "Error"
    )
}

private fun log(
    msg: Any?,
    fixedLabel: String,
    logColor: String,
    resolvedTag: String? = null
) {
    val stackTrace = Throwable().stackTrace

    val caller = stackTrace.firstOrNull { element ->
        val isLoggerFile = element.fileName?.contains("LogUtils") == true
        val isLoggerFunction = element.methodName.contains("logInternal") ||
                element.methodName.contains("debugLog")
        val isInternal = element.className.contains("java.lang.Thread")
        !(isLoggerFile || isLoggerFunction || isInternal)
    }

    val fileLocation = if (caller != null) {
        val fileName = caller.fileName ?: caller.className.substringAfterLast('.')
        val line = caller.lineNumber
        "$fileName:$line"
    } else {
        "<unknown>"
    }

    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()

    val formattedMessage = try {
        when {
            msg == null -> "null"
            msg is String -> autoFormatString(msg)
            else -> gson.toJson(msg)
        }
    } catch (e: JsonSyntaxException) {
        "${LogColor.RED}[PARSING ERROR]${LogColor.RESET} ${e.message}\nOriginal: ${msg.toString()}"
    }

    val callingTime = SimpleDateFormat("HH:mm:ss").format(Date())
    val msg =  buildString {
        appendLine("---- START $fixedLabel ----------------------------------------------------------")
        appendLine("\n${formattedMessage}")
        appendLine("\n---- END | $fileLocation ($callingTime)--------------------------------")
    }

    val logTag = resolvedTag ?: when (fixedLabel) {
        LogType.DEBUG -> "DEBUG"
        LogType.SUCCESS -> "SUCCESS"
        LogType.ERROR -> "ERROR"
        else -> "INFO"
    }

    when (fixedLabel) {
        LogType.DEBUG -> Log.d(logTag, msg)
        LogType.ERROR -> Log.e(logTag, msg)
        LogType.SUCCESS -> Log.i(logTag, msg)
        else -> Log.i(logTag, msg)
    }
}

fun autoFormatString(input: String): String {
    return when {
        input.trim().startsWith("{") || input.trim().startsWith("[") -> {
            try {
                val json = com.google.gson.JsonParser.parseString(input)
                val gson = com.google.gson.GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create()
                gson.toJson(json)
            } catch (e: Exception) {
                input
            }
        }

        input.contains("(") && input.contains("=") -> {
            input.replace(",", ",\n    ")
                .replace("(", "(\n    ")
                .replace(")", "\n)")
        }

        input.startsWith("[") && input.contains(",") -> {
            input.replace(",", ",\n    ")
                .replace("[", "[\n    ")
                .replace("]", "\n]")
        }

        else -> input
    }
}

private val tagColorMap = mapOf(
    LogType.DEBUG to LogColor.ORANGE,
    LogType.SUCCESS to LogColor.GREEN,
    LogType.ERROR to LogColor.RED,
    LogType.API to LogColor.BLUE,
    LogType.DB to LogColor.MAGENTA
)

object LogType {
    const val DEBUG = "DEBUG"
    const val SUCCESS = "SUCCESS"
    const val ERROR = "ERROR"

    const val API = "API"
    const val DB  = "DB"
}

object LogColor {
    const val RESET = "\u001B[0m"
    const val RED = "\u001B[31m"
    const val GREEN = "\u001B[32m"
    const val ORANGE = "\u001B[38;5;208m"
    const val BLUE = "\u001B[34m"
    const val PURPLE = "\u001B[35m"
    const val MAGENTA = "\u001B[38;5;201m"
    const val CYAN = "\u001B[36m"
    const val TEAL = "\u001B[38;5;37m"
    const val YELLOW = "\u001B[33m"
}