package com.example.taskfin

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.taskfin.components.customTextFieldColors
import com.example.taskfin.ui.theme.Inter
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import android.content.Intent
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import com.example.taskfin.components.CustomBottomBar


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    navController: NavController,
    onBackClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {}
){

    val currentLocaleTag = AppCompatDelegate.getApplicationLocales().toLanguageTags()
    val activeLanguageName = when {
        currentLocaleTag.contains("es") -> "Spanyol"
        currentLocaleTag.contains("en") -> "Inggris"
        currentLocaleTag.contains("ja") -> "Jepang"
        currentLocaleTag.contains("zh") -> "China"
        else -> "Indonesia"
    }

    var selectedLanguage by remember { mutableStateOf(activeLanguageName) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    val userName = viewModel.fullName.collectAsState("Pengguna TaskFin").value
    val userEmail = viewModel.email.collectAsState("user@gmail.com").value
    val profileImageUri = viewModel.profileImageUri.collectAsState().value

    val currentPassword = viewModel.password.collectAsState("").value

    val context = LocalContext.current
    val mainVerticalScrollState = rememberScrollState()

    var isDarkMode by remember { mutableStateOf(false) }

    val primaryColor = Color(0xFF3525CD)
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF8F7FF)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E2C) else Color.White
    val textColor = if (isDarkMode) Color(0xFFE0E0E0) else Color(0xFF1B1B24)
    val subTextColor = if (isDarkMode) Color(0xFFA0A0AB) else Color(0xFF777587)
    val dividerColor = if (isDarkMode) Color(0xFF2A2A3D) else Color(0xFFF3F3F6)
    val cardBorderColor = if (isDarkMode) Color(0xFF2A2A3D) else Color(0xFFE5E7EB)

    var isChangingPassword by remember { mutableStateOf(false) }

    var oldPassword by remember { mutableStateOf("") }
    var confirmOldPassword by remember { mutableStateOf("") }
    var isOldPasswordVerified by remember { mutableStateOf(false) }

    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateProfileImage(context, uri)
        }
    }

    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        },
        modifier = modifier
    ){ innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(bottom = innerPadding.calculateBottomPadding())
        ){
            Surface(
                color = surfaceColor,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp, bottom = 16.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = primaryColor,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(22.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (isChangingPassword) {
                                    isChangingPassword = false
                                    isOldPasswordVerified = false
                                    oldPassword = ""
                                    confirmOldPassword = ""
                                    newPassword = ""
                                    confirmNewPassword = ""
                                } else {
                                    onBackClick()
                                }
                            }
                    )

                    Text(
                        text = if (isChangingPassword) stringResource(id = R.string.change_password) else stringResource(id = R.string.settings_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(mainVerticalScrollState)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ){

                Spacer(modifier = Modifier.height(28.dp))

                if (!isChangingPassword) {
                    Column(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = stringResource(id = R.string.adjust_preferences),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Inter,
                            color = textColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(id = R.string.manage_experience),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = Inter,
                            color = subTextColor
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(id = R.string.account_section),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Inter,
                        color = subTextColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, cardBorderColor, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = surfaceColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            val leftLineModifier = Modifier.drawBehind {
                                drawRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Color(0xFF3525CD), Color(0xFF4F46E5))
                                    ),
                                    topLeft = Offset(0f, 0f),
                                    size = Size(6.dp.toPx(), size.height)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .padding(start = 22.dp, end = 16.dp, top = 14.dp, bottom = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.size(56.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .border(
                                                width = 2.dp,
                                                color = if (profileImageUri != null) Color(0xFF3525CD) else Color(0xFFC7C4D8),
                                                shape = CircleShape
                                            )
                                            .background(surfaceColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (profileImageUri != null) {
                                            AsyncImage(
                                                model = profileImageUri,
                                                contentDescription = "Foto Profil",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = "Foto Profil",
                                                tint = if (isDarkMode) Color.White else Color.Black,
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .align(Alignment.BottomEnd)
                                            .background(Color(0xFF3525CD), CircleShape)
                                            .clickable {
                                                imagePickerLauncher.launch("image/*")
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_edit),
                                            contentDescription = "Ganti Foto",
                                            tint = Color.White,
                                            modifier = Modifier.size(10.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.taskfin_user),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = Inter,
                                        color = textColor
                                    )

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        text = stringResource(id = R.string.default_email),
                                        fontSize = 13.sp,
                                        fontFamily = Inter,
                                        color = subTextColor
                                    )
                                }
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .clickable { onEditProfileClick() }
                                    .padding(start = 22.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_person),
                                        contentDescription = null,
                                        tint = Color(0xFF3525CD),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = stringResource(id = R.string.edit_profile),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = Inter,
                                        color = textColor
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF)
                                )
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .clickable { isChangingPassword = true }
                                    .padding(start = 22.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_lock_password),
                                        contentDescription = null,
                                        tint = Color(0xFF3525CD),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = stringResource(id = R.string.change_password),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = Inter,
                                        color = textColor
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF)
                                )
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(start = 22.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_logout),
                                        contentDescription = null,
                                        tint = Color(0xFFB91C1C),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = stringResource(id = R.string.logout),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = Inter,
                                        color = Color(0xFFB91C1C)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(id = R.string.app_preference),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Inter,
                        color = subTextColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, cardBorderColor, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = surfaceColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            val leftLineModifier = Modifier.drawBehind {
                                drawRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Color(0xFF3525CD), Color(0xFF4F46E5))
                                    ),
                                    topLeft = Offset(0f, 0f),
                                    size = Size(6.dp.toPx(), size.height)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .padding(start = 22.dp, end = 16.dp, top = 14.dp, bottom = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_mode),
                                        contentDescription = null,
                                        tint = Color(0xFF3525CD),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = stringResource(id = R.string.dark_mode),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = Inter,
                                        color = textColor
                                    )
                                }
                                Switch(
                                    checked = isDarkMode,
                                    onCheckedChange = { isDarkMode = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF3525CD)
                                    )
                                )
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .clickable { showLanguageDialog = true }
                                    .padding(start = 22.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_language),
                                        contentDescription = null,
                                        tint = Color(0xFF3525CD),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = stringResource(id = R.string.language_choice),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = Inter,
                                        color = textColor
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = selectedLanguage,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = Inter,
                                        color = Color(0xFF3525CD)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Color(0xFF9CA3AF)
                                    )
                                }
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .padding(start = 22.dp, end = 16.dp, top = 14.dp, bottom = 6.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.notification),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Inter,
                                    color = subTextColor
                                )
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(start = 22.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.task_notification),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = Inter,
                                    color = textColor
                                )
                                Switch(
                                    checked = false,
                                    onCheckedChange = {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF3525CD)
                                    )
                                )
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(start = 22.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.financial_notification),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = Inter,
                                    color = textColor
                                )
                                Switch(
                                    checked = false,
                                    onCheckedChange = {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF3525CD)
                                    )
                                )
                            }

                            HorizontalDivider(color = dividerColor, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(leftLineModifier)
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(start = 22.dp, end = 16.dp, top = 12.dp, bottom = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.reminder),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = Inter,
                                    color = textColor
                                )
                                Switch(
                                    checked = false,
                                    onCheckedChange = {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF3525CD)
                                    )
                                )
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = surfaceColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Task", fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color(0xFF3525CD))
                                Text(text = "Fin", fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color(0xFF1E5631))
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(text = "SANDI LAMA", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = subTextColor)
                                Spacer(modifier = Modifier.height(6.dp))
                                OutlinedTextField(
                                    value = oldPassword,
                                    onValueChange = { oldPassword = it },
                                    placeholder = { Text("Masukkan sandi lama", color = Color.Gray, fontFamily = Inter) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = customTextFieldColors()
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(text = "KONFIRMASI SANDI LAMA", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = subTextColor)
                                Spacer(modifier = Modifier.height(6.dp))
                                OutlinedTextField(
                                    value = confirmOldPassword,
                                    onValueChange = { confirmOldPassword = it },
                                    placeholder = { Text("Konfirmasi sandi lama", color = Color.Gray, fontFamily = Inter) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = customTextFieldColors()
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (oldPassword.isBlank() || confirmOldPassword.isBlank()) {
                                        Toast.makeText(context, "Mohon isi sandi lama terlebih dahulu", Toast.LENGTH_SHORT).show()
                                    } else if (oldPassword == currentPassword && confirmOldPassword == currentPassword) {
                                        isOldPasswordVerified = true
                                        Toast.makeText(context, "Sandi lama benar", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Sandi lama salah atau tidak cocok", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                            ) {
                                Text(text = "Ubah Sandi", fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color.White)
                            }

                            AnimatedVisibility(visible = isOldPasswordVerified) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    HorizontalDivider(color = dividerColor, thickness = 1.dp)
                                    Spacer(modifier = Modifier.height(20.dp))

                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(text = "SANDI BARU", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = primaryColor)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        OutlinedTextField(
                                            value = newPassword,
                                            onValueChange = { newPassword = it },
                                            placeholder = { Text("Masukkan sandi baru", color = Color.Gray, fontFamily = Inter) },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = customTextFieldColors()
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(text = "KONFIRMASI SANDI BARU", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = primaryColor)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        OutlinedTextField(
                                            value = confirmNewPassword,
                                            onValueChange = { confirmNewPassword = it },
                                            placeholder = { Text("Konfirmasi sandi baru", color = Color.Gray, fontFamily = Inter) },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = customTextFieldColors()
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Button(
                                        onClick = {
                                            if (newPassword.isBlank() || confirmNewPassword.isBlank()) {
                                                Toast.makeText(context, "Sandi baru tidak boleh kosong", Toast.LENGTH_SHORT).show()
                                            } else if (newPassword == confirmNewPassword) {
                                                viewModel.updatePassword(newPassword)
                                                Toast.makeText(context, "Kata sandi berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                                                isChangingPassword = false
                                                isOldPasswordVerified = false
                                                oldPassword = ""
                                                confirmOldPassword = ""
                                                newPassword = ""
                                                confirmNewPassword = ""
                                            } else {
                                                Toast.makeText(context, "Konfirmasi sandi baru tidak cocok", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(52.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                                    ) {
                                        Text(text = "Simpan Sandi Baru", fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.security_privacy),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = subTextColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .width(4.dp)
                                .height(130.dp)
                                .clip(RoundedCornerShape(topEnd = 60.dp, bottomEnd = 60.dp))
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF006C49),
                                            Color(0xFF4EDEA3)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_database),
                                        contentDescription = null,
                                        tint = Color(0xFF006C49),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.manage_personal_data),
                                        fontSize = 15.sp,
                                        fontFamily = Inter,
                                        fontWeight = FontWeight.Medium,
                                        color = textColor
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            HorizontalDivider(color = dividerColor.copy(alpha = 0.4f), thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_security),
                                        contentDescription = null,
                                        tint = Color(0xFF006C49),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.app_permissions),
                                        fontSize = 15.sp,
                                        fontFamily = Inter,
                                        fontWeight = FontWeight.Medium,
                                        color = textColor
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            HorizontalDivider(color = dividerColor.copy(alpha = 0.4f), thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_trash_account),
                                        contentDescription = null,
                                        tint = Color(0xFFC62828),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.delete_account),
                                        fontSize = 15.sp,
                                        fontFamily = Inter,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFC62828)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.about_app),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = subTextColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "TaskFin",
                                    fontSize = 16.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryColor
                                )

                                Box(modifier = Modifier.height(24.dp))
                            }

                            HorizontalDivider(color = dividerColor.copy(alpha = 0.4f), thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Kebijakan Privasi",
                                    fontSize = 15.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_open_new),
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            HorizontalDivider(color = dividerColor.copy(alpha = 0.4f), thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Syarat & Ketentuan",
                                    fontSize = 15.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_open_new),
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            HorizontalDivider(color = dividerColor.copy(alpha = 0.4f), thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Hubungi Dukungan",
                                    fontSize = 15.sp,
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_email),
                                    contentDescription = null,
                                    tint = primaryColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Simpan Perubahan",
                            fontSize = 16.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

    }

    if (showLanguageDialog) {

        val languageMap = mapOf(
            "Indonesia" to "id",
            "Inggris" to "en",
            "Jepang" to "ja",
            "China" to "zh",
            "Spanyol" to "es"
        )
        val languages = languageMap.keys.toList()

        Dialog(onDismissRequest = { showLanguageDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                color = surfaceColor,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.select_language),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Inter,
                        color = primaryColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        languages.forEach { lang ->
                            val isSelected = selectedLanguage == lang
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        selectedLanguage = lang
                                        showLanguageDialog = false

                                        val languageCode = languageMap[lang] ?: "id"
                                        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
                                        AppCompatDelegate.setApplicationLocales(appLocale)

                                        Toast.makeText(context, "Bahasa diubah ke $lang", Toast.LENGTH_SHORT).show()

                                        val intent = Intent(context, MainActivity::class.java).apply {
                                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)

                                        (context as? android.app.Activity)?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                                    },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) primaryColor.copy(alpha = 0.1f) else dividerColor.copy(alpha = 0.4f),
                                border = if (isSelected) BorderStroke(1.5.dp, primaryColor) else null
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp, horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = lang,
                                        fontSize = 15.sp,
                                        fontFamily = Inter,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) primaryColor else textColor
                                    )
                                    if (isSelected) {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .background(primaryColor, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}