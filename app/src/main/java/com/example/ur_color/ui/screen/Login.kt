package com.example.ur_color.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.UserData
import com.example.ur_color.data.calculateZodiac
import com.example.ur_color.data.local.PrefCache
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.formatDateInput
import com.example.ur_color.utils.generatePatternAura
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Login : Screen

@Composable
fun LoginScreen() {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf(TextFieldValue("")) }
    var birthTime by remember { mutableStateOf("") }
    var birthPlace by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Мужской") }

    var generatedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Фамилия") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = middleName,
            onValueChange = { middleName = it },
            label = { Text("Отчество (опционально)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = birthDate,
            onValueChange = { input -> birthDate = formatDateInput(birthDate, input) },
            label = { Text("Дата рождения (ДД/ММ/ГГГГ)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = birthTime,
            onValueChange = { birthTime = it },
            label = { Text("Время рождения (ЧЧ:ММ)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = birthPlace,
            onValueChange = { birthPlace = it },
            label = { Text("Место рождения") },
            modifier = Modifier.fillMaxWidth()
        )

        // Пол
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Мужской", "Женский").forEach { option ->
                Button(
                    onClick = { gender = option },
                    colors = ButtonDefaults.buttonColors(if (gender == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                ) {
                    Text(option)
                }
            }
        }

        Button(
            onClick = {
                if (firstName.isNotBlank() && lastName.isNotBlank() && birthDate.text.length == 10) {
                    val fullName = "$lastName $firstName ${middleName.ifBlank { "" }}".trim()

                    // 1) парсим день/месяц для знака зодиака
                    val parts = birthDate.text.split("/")
                    val day = parts.getOrNull(0)?.toIntOrNull() ?: 1
                    val month = parts.getOrNull(1)?.toIntOrNull() ?: 1
                    val zodiac = calculateZodiac(day, month)

                    // 2) генерируем битмап (нужен для сохранения)
                    val avatarBitmap: Bitmap = generatePatternAura(fullName, birthDate.text)

                    // 3) формируем модель
                    val user = UserData(
                        firstName = firstName,
                        lastName = lastName,
                        middleName = middleName.ifBlank { null },
                        birthDate = birthDate.text,
                        birthTime = birthTime,
                        birthPlace = birthPlace,
                        gender = gender,
                        zodiacSign = zodiac
                    )

                    // 4) сохраняем (suspend)
                    scope.launch {
                        PrefCache.saveUser(context, user, avatarBitmap)
                        navController.navigate(Main.route())
                    }
                }
            }
        ) {
            Text("войти")
        }
    }
}