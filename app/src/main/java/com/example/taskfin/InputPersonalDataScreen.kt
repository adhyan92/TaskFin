package com.example.taskfin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.taskfin.ui.theme.Inter
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import com.example.taskfin.components.CustomOutlinedTextField
import com.example.taskfin.components.GenderButton
import com.example.taskfin.components.TextFormLabel
import com.example.taskfin.components.customTextFieldColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPersonalData(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val mainVerticalScrollState = rememberScrollState()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    var namaLengkap by remember { mutableStateOf("") }

    var statusExpanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("") }
    val statusOptions = listOf("Mahasiswa", "Pelajar", "Pekerja")

    var universitas by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("") }

    var tanggalLahir by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }

    val primaryColor = Color(0xFF3525CD)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .padding(horizontal = 24.dp)
            .verticalScroll(mainVerticalScrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Kembali",
                tint = primaryColor,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(22.dp)
                    .clickable { onBackClick() }
            )

            Text(
                text = "Lengkapi Profilmu",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter,
                color = primaryColor
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier.size(130.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = Color(0xFFC7C4D8),
                        shape = CircleShape
                    )
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
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
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-4).dp, y = (-4).dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(primaryColor)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Ubah Foto",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "UNGGAH FOTO PROFIL",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Inter,
            letterSpacing = 0.6.sp,
            color = Color(0xFF464555)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Column {
                TextFormLabel("NAMA LENGKAP")
                CustomOutlinedTextField(
                    value = namaLengkap,
                    onValueChange = { namaLengkap = it },
                    placeholder = "Masukkan nama lengkap"
                )
            }

            Column {
                TextFormLabel("STATUS")
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = !statusExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedStatus,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Pilih status", color = Color.Gray, fontFamily = Inter) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = customTextFieldColors()
                    )

                    DropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false },
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        statusOptions.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = status, fontFamily = Inter) },
                                onClick = {
                                    selectedStatus = status
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = selectedStatus == "Mahasiswa") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF2EFFD))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {

                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .fillMaxHeight()
                                .background(
                                    color = primaryColor,
                                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Column {
                                TextFormLabel("UNIVERSITAS", color = primaryColor)
                                CustomOutlinedTextField(
                                    value = universitas,
                                    onValueChange = { universitas = it },
                                    placeholder = "Masukkan universitas anda"
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    TextFormLabel("SEMESTER", color = primaryColor)
                                    CustomOutlinedTextField(
                                        value = semester,
                                        onValueChange = { semester = it },
                                        placeholder = " "
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    TextFormLabel("JURUSAN", color = primaryColor)
                                    CustomOutlinedTextField(
                                        value = jurusan,
                                        onValueChange = { jurusan = it },
                                        placeholder = " "
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column {
                TextFormLabel("TANGGAL LAHIR")
                CustomOutlinedTextField(
                    value = tanggalLahir,
                    onValueChange = { tanggalLahir = it },
                    placeholder = "mm/dd/yyyy"
                )
            }

            Column {
                TextFormLabel("JENIS KELAMIN")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GenderButton(
                        text = "Laki-laki",
                        isSelected = jenisKelamin == "Laki-laki",
                        modifier = Modifier.weight(1f),
                        onClick = { jenisKelamin = "Laki-laki" },
                        activeColor = primaryColor
                    )
                    GenderButton(
                        text = "Perempuan",
                        isSelected = jenisKelamin == "Perempuan",
                        modifier = Modifier.weight(1f),
                        onClick = { jenisKelamin = "Perempuan" },
                        activeColor = primaryColor
                    )
                }
            }

            Column {
                TextFormLabel("ALAMAT")
                OutlinedTextField(
                    value = alamat,
                    onValueChange = { alamat = it },
                    placeholder = { Text("Masukkan alamat domisili", color = Color.Gray, fontFamily = Inter) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = customTextFieldColors(),
                    maxLines = 4
                )
            }

            Column {
                TextFormLabel("NOMOR TELEPON (OPSIONAL)")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                            .border(1.dp, Color(0xFFDCDCDC), shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "+62", fontWeight = FontWeight.Bold, color = Color.Gray, fontFamily = Inter)
                    }

                    OutlinedTextField(
                        value = nomorTelepon,
                        onValueChange = { nomorTelepon = it },
                        placeholder = { Text("8123456789", color = Color.Gray, fontFamily = Inter) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = customTextFieldColors()
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Handle simpan profil */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Simpan Profil",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Inter,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}