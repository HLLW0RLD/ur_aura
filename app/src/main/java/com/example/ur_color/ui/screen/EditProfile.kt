package com.example.ur_color.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.EditProfileViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object EditProfile: Screen

@Composable
fun EditProfile(editProfile: EditProfile) {
    AppScaffold(
        showBottomBar = true,
        topBar = {
            val navController = LocalNavController.current
            val editProfileViewModel: EditProfileViewModel = koinViewModel()
            val context = LocalContext.current

            CustomAppBar(
                title = stringResource(R.string.profile_edit),
                isCentered = true,
                showOptions = true,
                showBack = true,
                // если во вьюмодели поля пользователя такие же или не пустые то сохраняем
                optionsIcon = painterResource(R.drawable.magic_sparkles),
                onOptionsClick = {
                    editProfileViewModel.update(context)
                },
                onBackClick = {
                    navController.popBack()
                },
                backgroundColor = AppColors.background,
            )
        }
    ) {
        EditProfileScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun EditProfileScreen(
    modifier : Modifier = Modifier,
    editProfileViewModel: EditProfileViewModel = koinViewModel()
) {

    val navController = LocalNavController.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        editProfileViewModel.init(context)
    }

    val user by editProfileViewModel.user.collectAsState()
    val avatar by editProfileViewModel.avatar.collectAsState()
    val about by editProfileViewModel.about.collectAsState()

    var aboutText = about

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                editProfileViewModel.setAvatar(it.toString())
            }
        }

    Column(

    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!avatar.isNullOrEmpty()) {
                AsyncImage(
                    model = avatar,
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.magic_sparkles),
                    contentDescription = "Аватар",
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            FloatingActionButton(
                onClick = {
                    // Логика выбора изображения
//                    editProfileViewModel.setAvatar("https://picsum.photos/seed/abstract02/600/600")
                    photoPickerLauncher.launch("image/*")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Icon(painter = painterResource(R.drawable.magic_sparkles), "Изменить аватар")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "О себе",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = aboutText ?: "",
                onValueChange = {
                    aboutText = it
                    editProfileViewModel.setAbout(aboutText ?: "")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                placeholder = { Text("Расскажите о себе...") },
                maxLines = 10,
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
        }
    }
}