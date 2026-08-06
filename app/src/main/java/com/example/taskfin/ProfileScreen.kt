package com.example.taskfin


import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.taskfin.components.CustomBottomBar
import com.example.taskfin.components.CustomTextField
import com.example.taskfin.components.TextFormLabel
import com.example.taskfin.ui.theme.Inter

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel,
    onSettingsClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val mainVerticalScrollState = rememberScrollState()

    val imageUri by viewModel.profileImageUri.collectAsState()
    val savedName by viewModel.fullName.collectAsState()
    val savedUniv by viewModel.university.collectAsState()
    val savedBDate by viewModel.birthDate.collectAsState()
    val savedGender by viewModel.gender.collectAsState()
    val savedAddr by viewModel.address.collectAsState()
    val savedPhone by viewModel.phoneNumber.collectAsState()
    val savedSem by viewModel.semester.collectAsState()
    val savedJur by viewModel.jurusan.collectAsState()

    val primaryColor = Color(0xFF3525CD)
    val borderColor = if (imageUri != null) primaryColor else Color(0xFFC7C4D8)

    var namaLengkap by remember(savedName) { mutableStateOf(savedName) }
    var universitas by remember(savedUniv) { mutableStateOf(savedUniv) }
    var tanggalLahir by remember(savedBDate) { mutableStateOf(savedBDate) }
    var jenisKelamin by remember(savedGender) { mutableStateOf(savedGender) }
    var alamat by remember(savedAddr) { mutableStateOf(savedAddr) }
    var nomorTelepon by remember(savedPhone) { mutableStateOf(savedPhone) }
    var jurusan by remember(savedJur) { mutableStateOf(savedJur) }

    var semesterExpanded by remember { mutableStateOf(false) }
    var selectedSemester by remember(savedSem) { mutableStateOf(savedSem) }
    val semesterOptions = (1..8).map { it.toString() }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.updateProfileImage(context, it) }
    }

    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F7FF))
        ) {

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
                            ) { onBackClick() }
                    )

                    Text(
                        text = "Profil Saya",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Pengaturan",
                        tint = primaryColor,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(22.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onSettingsClick() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(mainVerticalScrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier.size(130.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(
                                width = 4.dp,
                                color = borderColor,
                                shape = CircleShape
                            )
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUri != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(imageUri)
                                    .crossfade(true)
                                    .build(),
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
                    text = "Mahasiswa Aktif",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Inter,
                    color = Color(0xFF1B1B24)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Update data diri untuk akurasi statistik",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Inter,
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
                        CustomTextField(
                            value = namaLengkap,
                            onValueChange = { namaLengkap = it },
                            placeholder = "Masukkan nama lengkap",
                            leadingIcon = painterResource(id = R.drawable.ic_person)
                        )
                    }

                    Column {
                        TextFormLabel("UNIVERSITAS")
                        CustomTextField(
                            value = universitas,
                            onValueChange = { universitas = it },
                            placeholder = "Masukkan universitas",
                            leadingIcon = painterResource(id = R.drawable.ic_education)
                        )
                    }

                    Column {
                        TextFormLabel("TANGGAL LAHIR")
                        CustomTextField(
                            value = tanggalLahir,
                            onValueChange = { tanggalLahir = it },
                            placeholder = "mm/dd/yyyy",
                            leadingIcon = painterResource(id = R.drawable.ic_birthday)
                        )
                    }

                    Column {
                        TextFormLabel("JENIS KELAMIN")
                        CustomTextField(
                            value = jenisKelamin,
                            onValueChange = { jenisKelamin = it },
                            placeholder = "Masukkan jenis kelamin",
                            leadingIcon = painterResource(id = R.drawable.ic_gender)
                        )
                    }

                    Column {
                        TextFormLabel("ALAMAT")
                        CustomTextField(
                            value = alamat,
                            onValueChange = { alamat = it },
                            placeholder = "Masukkan alamat domisili",
                            leadingIcon = painterResource(id = R.drawable.ic_location)
                        )
                    }

                    Column {
                        TextFormLabel("NOMOR TELEPON")
                        CustomTextField(
                            value = nomorTelepon,
                            onValueChange = { nomorTelepon = it },
                            placeholder = "Masukkan nomor telepon",
                            leadingIcon = painterResource(id = R.drawable.ic_phone_number),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Column(modifier = Modifier.weight(0.4f)) {
                            TextFormLabel("SEMESTER")
                            Box {
                                OutlinedTextField(
                                    value = selectedSemester,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = { Text("Pilih", fontSize = 14.sp, color = Color(0xFFA09EB1)) },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            modifier = Modifier.clickable { semesterExpanded = !semesterExpanded }
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .clickable { semesterExpanded = !semesterExpanded },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color(0xFFFFFCFA),
                                        unfocusedContainerColor = Color(0xFFFFFCFA),
                                        focusedBorderColor = Color(0xFF6B7280),
                                        unfocusedBorderColor = Color(0xFF6B7280)
                                    )
                                )

                                DropdownMenu(
                                    expanded = semesterExpanded,
                                    onDismissRequest = { semesterExpanded = false }
                                ) {
                                    semesterOptions.forEach { sem ->
                                        DropdownMenuItem(
                                            text = { Text(text = sem) },
                                            onClick = {
                                                selectedSemester = sem
                                                semesterExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(0.6f)) {
                            TextFormLabel("JURUSAN")
                            CustomTextField(
                                value = jurusan,
                                onValueChange = { jurusan = it },
                                placeholder = "Masukkan jurusan",
                                leadingIcon = painterResource(id = R.drawable.ic_education)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE2F7ED))
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFF1E824C),
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Data ini membantu kami mempersonalisasi tips keuangan mahasiswa yang relevan dengan jadwal akademik Anda.",
                                fontSize = 12.sp,
                                color = Color(0xFF1E824C),
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.updateProfileData(
                                name = namaLengkap,
                                univ = universitas,
                                bDate = tanggalLahir,
                                gen = jenisKelamin,
                                addr = alamat,
                                phone = nomorTelepon,
                                sem = selectedSemester,
                                jur = jurusan
                            )
                            Toast.makeText(context, "Perubahan Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        Text(
                            text = "Simpan Perubahan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Inter,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}