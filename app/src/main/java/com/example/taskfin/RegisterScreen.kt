package com.example.taskfin

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.taskfin.components.CustomTextField
import com.example.taskfin.components.RegisterBottomSection
import com.example.taskfin.ui.theme.Inter
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onLoginClick: () -> Unit = {},
    onRegisterNowClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val googleAuthHelper = remember { GoogleAuthHelper(context) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isGoogleAccount by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val mainVerticalScrollState = rememberScrollState()

    var isAgreed by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    val webClientId = "836256864311-t7skmj5mes5sd98jtni2pbtnpm1qfmst.apps.googleusercontent.com"

    fun handleAppleSignIn(activity: Activity) {
        val provider = OAuthProvider.newBuilder("apple.com")
        provider.scopes = listOf("email", "name")

        auth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                if (user != null) {
                    fullName = user.displayName ?: "Pengguna Apple"
                    email = user.email ?: ""
                    isGoogleAccount = true

                    Toast.makeText(
                        context,
                        "Berhasil terhubung dengan Apple!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Gagal Masuk dengan Apple: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background_register),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(mainVerticalScrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(R.drawable.logo_register),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Buat Akun TaskFin",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = Inter,
                color = Color(0xFF3525CD)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Mulai langkah pintarmu mengelola\ntugas dan keuangan.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Inter,
                textAlign = TextAlign.Center,
                color = Color(0xFF221D79)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 600.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStart = 32.dp,
                            topEnd = 32.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {

                Text(
                    text = "NAMA LENGKAP",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF464555)
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomTextField(
                    value = fullName,
                    onValueChange = { if (!isGoogleAccount) fullName = it },
                    enabled = !isGoogleAccount,
                    placeholder = "Masukkan nama lengkap",
                    leadingIcon = painterResource(R.drawable.ic_person)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "EMAIL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF464555)
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { if (!isGoogleAccount) email = it },
                    enabled = !isGoogleAccount,
                    placeholder = "nama@gmail.com",
                    leadingIcon = painterResource(R.drawable.ic_email)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "KATA SANDI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF464555)
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "••••••••",
                    leadingIcon = painterResource(R.drawable.ic_lock_password),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                if (isPasswordVisible) R.drawable.ic_visibilityon else R.drawable.ic_visibilityoff
                            ),
                            contentDescription = "Toggle Password Visibility",
                            tint = Color(0xFFA09EB1),
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { isPasswordVisible = !isPasswordVisible }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "KONFIRMASI KATA SANDI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF464555)
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "••••••••",
                    leadingIcon = painterResource(R.drawable.ic_verified),
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                if (isConfirmPasswordVisible) R.drawable.ic_visibilityon else R.drawable.ic_visibilityoff
                            ),
                            contentDescription = "Toggle Confirm Password Visibility",
                            tint = Color(0xFFA09EB1),
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { isConfirmPasswordVisible = !isConfirmPasswordVisible }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                TermsAndPrivacyCheckbox(
                    isChecked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    onTermsClick = { showTermsDialog = true },
                    onPrivacyClick = { showPrivacyDialog = true }
                )

                Spacer(modifier = Modifier.height(22.dp))

                RegisterBottomSection(
                    onRegisterNowClick = {

                        if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
                        } else if (password != confirmPassword) {
                            Toast.makeText(context, "Konfirmasi kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                        } else if (!isAgreed) {
                            Toast.makeText(context, "Anda harus menyetujui Syarat & Ketentuan", Toast.LENGTH_SHORT).show()
                        } else {

                            viewModel.setRegisterData(fullName, email, password)

                            onRegisterNowClick()
                        }
                    },
                    onGoogleClick = {

                        coroutineScope.launch {
                            googleAuthHelper.signInWithGoogle(
                                webClientId = webClientId,
                                onSuccess = { googleEmail, googleName ->

                                    fullName = googleName
                                    email = googleEmail
                                    isGoogleAccount = true

                                    Toast.makeText(
                                        context,
                                        "Berhasil terhubung dengan Google!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onError = { errorMsg ->
                                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    },
                    onAppleClick = {
                        val activity = context as? Activity
                        if (activity != null) {
                            handleAppleSignIn(activity)
                        } else {
                            Toast.makeText(context, "Aktivitas tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onLoginClick = {
                        onLoginClick()
                    }
                )
            }
        }

        if (showTermsDialog) {
            PolicyDialog(
                title = "Syarat & Ketentuan",
                intro = "Selamat datang di TaskFin!\n\nDengan menggunakan aplikasi ini, Anda menyetujui syarat dan ketentuan berikut:",
                items = listOf(
                    PolicyItem(
                        number = "1",
                        title = "Penggunaan Layanan",
                        description = "Aplikasi ini ditujukan untuk mempermudah pencatatan tugas kuliah dan pengelolaan keuangan harian mahasiswa."
                    ),
                    PolicyItem(
                        number = "2",
                        title = "Akun Pengguna",
                        description = "Anda bertanggung jawab penuh untuk menjaga kerahasiaan kata sandi serta keamanan akun Anda."
                    ),
                    PolicyItem(
                        number = "3",
                        title = "Penggunaan yang Dilarang",
                        description = "Dilarang keras menyalahgunakan sistem, memasukkan data palsu, atau mencoba melakukan peretasan pada aplikasi ini."
                    ),
                    PolicyItem(
                        number = "4",
                        title = "Perubahan Layanan",
                        description = "Pengembang berhak mengubah, memperbarui, atau menghentikan fitur tertentu tanpa pemberitahuan sebelumnya."
                    ),
                    PolicyItem(
                        number = "5",
                        title = "Pembatasan Tanggung Jawab",
                        description = "TaskFin tidak bertanggung jawab atas kerugian finansial pribadi yang disebabkan oleh kelalaian atau kesalahan input data oleh pengguna."
                    )
                ),
                onDismiss = { showTermsDialog = false }
            )
        }

        if (showPrivacyDialog) {
            PolicyDialog(
                title = "Kebijakan Privasi",
                intro = "Kami menghargai privasi Anda. Dokumen ini menjelaskan bagaimana data Anda dikumpulkan dan dilindungi:",
                items = listOf(
                    PolicyItem(
                        number = "1",
                        title = "Informasi yang Kami Kumpulkan",
                        description = "Kami mengumpulkan informasi berupa Nama Lengkap, Alamat Email, serta data pencatatan tugas dan transaksi keuangan yang Anda masukkan secara sukarela."
                    ),
                    PolicyItem(
                        number = "2",
                        title = "Penggunaan Informasi",
                        description = "Data Anda digunakan semata-mata untuk mempersonalisasi tips keuangan, menyusun grafik analisis statistik, serta jadwal akademik Anda."
                    ),
                    PolicyItem(
                        number = "3",
                        title = "Keamanan Data",
                        description = "Kami menerapkan langkah-langkah keamanan teknis untuk melindungi data pribadi Anda dari akses tanpa izin."
                    ),
                    PolicyItem(
                        number = "4",
                        title = "Pembagian Data",
                        description = "Kami TIDAK AKAN PERNAH menjual, menyewakan, atau membagikan data pribadi Anda kepada pihak ketiga."
                    ),
                    PolicyItem(
                        number = "5",
                        title = "Hak Pengguna",
                        description = "Anda berhak memperbarui data diri Anda kapan saja melalui layar Profil atau memperbarui data akun."
                    )
                ),
                onDismiss = { showPrivacyDialog = false }
            )
        }
    }
}

@Composable
fun TermsAndPrivacyCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = (-12).dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF3525CD),
                uncheckedColor = Color(0xFF8C8A9E)
            )
        )

        val annotatedText = buildAnnotatedString {
            append("Saya menyetujui ")

            pushStringAnnotation(tag = "TERMS", annotation = "terms")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF3525CD),
                    fontWeight = FontWeight.Medium
                )
            ) {
                append("Syarat & Ketentuan")
            }
            pop()

            append(" serta ")

            pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF3525CD),
                    fontWeight = FontWeight.Medium
                )
            ) {
                append("Kebijakan Privasi")
            }
            pop()

            append(".")
        }

        ClickableText(
            text = annotatedText,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Inter,
                color = Color(0xFF464555),
                lineHeight = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                    .firstOrNull()?.let { onTermsClick() }

                annotatedText.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                    .firstOrNull()?.let { onPrivacyClick() }
            }
        )
    }
}

data class PolicyItem(
    val number: String,
    val title: String,
    val description: String
)

@Composable
fun PolicyDialog(
    title: String,
    intro: String,
    items: List<PolicyItem>,
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF3525CD)
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    if (intro.isNotEmpty()) {
                        Text(
                            text = intro,
                            fontSize = 13.sp,
                            fontFamily = Inter,
                            color = Color(0xFF464555),
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            verticalAlignment = Alignment.Top
                        ) {

                            Text(
                                text = "${item.number}. ",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Inter,
                                color = Color(0xFF464555),
                                lineHeight = 18.sp
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Inter,
                                    color = Color(0xFF464555),
                                    lineHeight = 18.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.description,
                                    fontSize = 13.sp,
                                    fontFamily = Inter,
                                    color = Color(0xFF464555),
                                    lineHeight = 18.sp,
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3525CD))
                ) {
                    Text(
                        text = "YA, SAYA MENGERTI",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Inter,
                        color = Color.White
                    )
                }
            }
        }
    }
}