package com.example.taskfin

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.taskfin.components.customTextFieldColors
import com.example.taskfin.ui.theme.Inter

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onBackClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {}
){

    val userName = viewModel.fullName.collectAsState("Pengguna TaskFin").value
    val userEmail = viewModel.email.collectAsState("user@gmail.com").value
    val profileImageUri = viewModel.profileImageUri.collectAsState().value

    // Mengambil kata sandi dinamis milik user yang sedang aktif dari ViewModel
    val currentPassword = viewModel.password.collectAsState("").value

    val context = LocalContext.current
    val mainVerticalScrollState = rememberScrollState()
    val primaryColor = Color(0xFF3525CD)
    val backgroundColor = Color(0xFFF8F7FF)

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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ){
        Surface(
            color = Color.White,
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
                    text = if (isChangingPassword) "Ubah Kata Sandi" else "Pengaturan",
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
                        text = "Sesuaikan preferensi dan akunmu",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Inter,
                        color = Color(0xFF1B1B24)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Kelola pengalaman belanjamu dan keamanan\nfinansial dalam satu tempat.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Inter,
                        color = Color(0xFF464555)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "AKUN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF777587)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // KARTU PROFIL LENGKAP KEMBALI DI SINI
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Bagian Informasi Akun (Foto, Nama, Email)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color(0xFF3525CD), Color(0xFF4F46E5))
                                        ),
                                        size = Size(6.dp.toPx(), size.height)
                                    )
                                }
                                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(6.dp))

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
                                        .background(Color.White),
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
                                            tint = Color.Black,
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
                                    text = userName.ifBlank { "Pengguna TaskFin" },
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Inter,
                                    color = Color(0xFF1B1B24)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = userEmail.ifBlank { "user@gmail.com" },
                                    fontSize = 13.sp,
                                    fontFamily = Inter,
                                    color = Color(0xFF777587)
                                )
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF3F3F6), thickness = 1.dp)

                        // Menu Edit Profil
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color(0xFF3525CD), Color(0xFF4F46E5))
                                        ),
                                        size = Size(6.dp.toPx(), size.height)
                                    )
                                }
                                .clickable { onEditProfileClick() }
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_person),
                                    contentDescription = null,
                                    tint = Color(0xFF3525CD),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Edit Profil",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = Inter,
                                    color = Color(0xFF1B1B24)
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF9CA3AF)
                            )
                        }

                        HorizontalDivider(color = Color(0xFFF3F3F6), thickness = 1.dp)

                        // Menu Ubah Kata Sandi
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color(0xFF3525CD), Color(0xFF4F46E5))
                                        ),
                                        size = Size(6.dp.toPx(), size.height)
                                    )
                                }
                                .clickable { isChangingPassword = true }
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_password),
                                    contentDescription = null,
                                    tint = Color(0xFF3525CD),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Ubah Kata Sandi",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = Inter,
                                    color = Color(0xFF1B1B24)
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF9CA3AF)
                            )
                        }

                        HorizontalDivider(color = Color(0xFFF3F3F6), thickness = 1.dp)

                        // Menu Keluar dari Akun
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color(0xFF3525CD), Color(0xFF4F46E5))
                                        ),
                                        size = Size(6.dp.toPx(), size.height)
                                    )
                                }
                                .clickable {
                                    Toast.makeText(context, "Keluar dari Akun", Toast.LENGTH_SHORT).show()
                                }
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_logout),
                                    contentDescription = null,
                                    tint = Color(0xFFB91C1C),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Keluar dari Akun",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = Inter,
                                    color = Color(0xFFB91C1C)
                                )
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(12.dp))

                // Tampilan Form Ubah Sandi
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
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
                            Text(text = "SANDI LAMA", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color(0xFF777587))
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
                            Text(text = "KONFIRMASI SANDI LAMA", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color(0xFF777587))
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
                                HorizontalDivider(color = Color(0xFFE5E7EB), thickness = 1.dp)
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

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}