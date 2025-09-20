package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.ur_color.data.UserData
import com.example.ur_color.data.local.PrefCache
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.calculateAge
import kotlinx.serialization.Serializable

@Serializable
data class Profile(val user: String = "null") : Screen {

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen() {
    val user by PrefCache.user.collectAsState()

    val navController = LocalNavController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding()
            .fillMaxSize()
            .padding(16.dp)
    ) {
        user?.let { u ->
            // Верхняя часть с аватаром и данными
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Круглый аватар (по uri или placeholder)
                val avatarPainter = if (u.avatarUri != null) {
                    rememberAsyncImagePainter(u.avatarUri)
                } else {
                    rememberAsyncImagePainter(android.R.drawable.sym_def_app_icon)
                }

                Image(
                    painter = avatarPainter,
                    contentDescription = "Аватар профиля",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.width(16.dp))

                // Имя, возраст, знак зодиака
                Column {
                    Text(
                        text = "${u.firstName} ${u.middleName.orEmpty()} ${u.lastName}".trim(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    calculateAge(u.birthDate)?.let { age ->
                        Text("Возраст: $age", style = MaterialTheme.typography.bodyMedium)
                    }
                    Text("Знак: ${u.zodiacSign}", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Нижние кнопки-навигация
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Aura Details",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(AuraDetails().route()) }
                        .padding(8.dp)
                )
                Text(
                    "Personal Tests",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
//                            navController.navigate("personalTests")
                        }
                        .padding(8.dp)
                )
                Text(
                    "Diary",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
//                            navController.navigate("diary")
                        }
                        .padding(8.dp)
                )
            }
        } ?: run {
            Text("Нет данных пользователя", style = MaterialTheme.typography.bodyMedium)
        }
    }
}