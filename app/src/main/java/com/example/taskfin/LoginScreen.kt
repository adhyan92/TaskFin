package com.example.taskfin

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskfin.ui.theme.Inter
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onEnterClick: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleAuthHelper = remember { GoogleAuthHelper(context) }

    val webClientId = "836256864311-t7skmj5mes5sd98jtni2pbtnpm1qfmst.apps.googleusercontent.com"

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.login_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()

    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {

            Image(
                painter = painterResource(R.drawable.header_login),
                contentDescription = "Header Login",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentScale = ContentScale.Crop
            )


            Row(
                modifier = Modifier.padding(start = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_taskfin),
                    contentDescription = "Icon TaskFin",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "TaskFin",
                    fontSize = 32.sp,
                    color = Color(0xFF3525CD),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Inter,
                    letterSpacing = (-1.6).sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {

            Text(
                text = "Masuk ke TaskFin",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = Inter,
                color = Color(0xFF1B1B24)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Kelola tugas kuliah dan keuanganmu dalam satu tempat yang tenang.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Inter,
                color = Color(0xFF464555),
                lineHeight = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(28.dp),
                    clip = false,
                    ambientColor = Color.Black.copy(alpha = 0.08f),
                    spotColor = Color.Black.copy(alpha = 0.18f)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(vertical = 28.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {

                Text(
                    text = "EMAIL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF464555)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, Color(0xFFE3E2EA), RoundedCornerShape(14.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_email),
                            contentDescription = null,
                            tint = Color(0xFF464555),
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        BasicTextField(
                            value = email,
                            onValueChange = { email = it },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = Inter,
                                color = Color(0xFF2E2C3D)
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Box(
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (email.isEmpty()) {
                                        Text(
                                            text = "Masukkan email anda",
                                            fontSize = 14.sp,
                                            fontFamily = Inter,
                                            color = Color(0xFF2E2C3D)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "KATA SANDI",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Inter,
                        color = Color(0xFF464555)
                    )
                    Text(
                        text = "Lupa?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Inter,
                        color = Color(0xFF5E4AE3),
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            Toast.makeText(
                                context,
                                "Feature Coming Soon",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, Color(0xFFE3E2EA), RoundedCornerShape(14.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_password),
                            contentDescription = null,
                            tint = Color(0xFF464555),
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        BasicTextField(
                            value = password,
                            onValueChange = { password = it },
                            singleLine = true,
                            visualTransformation = if (isPasswordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = Inter,
                                color = Color(0xFF2E2C3D)
                            ),
                            modifier = Modifier.weight(1f),
                            decorationBox = { innerTextField ->
                                Box(
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (password.isEmpty()) {
                                        Text(
                                            text = "••••••••",
                                            fontSize = 14.sp,
                                            fontFamily = Inter,
                                            color = Color(0xFFA09EB1)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            painter = painterResource(
                                if (isPasswordVisible) R.drawable.ic_visibilityon else R.drawable.ic_visibilityoff
                            ),
                            contentDescription = "Toggle Password Visibility",
                            tint = Color(0xFF464555),
                            modifier = Modifier
                                .size(22.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    isPasswordVisible = !isPasswordVisible
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF5B4FE9), Color(0xFF3F2FD8))
                            )
                        )
                        .clickable { onEnterClick() },
                    contentAlignment = Alignment.Center
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = "Masuk",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Inter,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFE3E2EA),
                        thickness = 1.dp
                    )
                    Text(
                        text = "Atau",
                        fontSize = 13.sp,
                        fontFamily = Inter,
                        color = Color(0xFF9795A6),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFE3E2EA),
                        thickness = 1.dp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, Color(0xFFE3E2EA), RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .clickable {
                            coroutineScope.launch {
                                googleAuthHelper.signInWithGoogle(
                                    webClientId = webClientId,
                                    onSuccess = { googleEmail, googleName ->

                                        viewModel.setRegisterData(googleName, googleEmail)


                                        val isUserRegistered = viewModel.isEmailRegistered(googleEmail)

                                        if (isUserRegistered) {

                                            Toast.makeText(
                                                context,
                                                "Selamat datang kembali, $googleName!",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            onEnterClick()
                                        } else {

                                            Toast.makeText(
                                                context,
                                                "Akun belum terdaftar. Silakan lengkapi pendaftaran.",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            onRegisterClick()
                                        }
                                    },
                                    onError = { errorMsg ->
                                        Toast.makeText(
                                            context,
                                            errorMsg,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            painter = painterResource(R.drawable.ic_google),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Masuk dengan Google",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Inter,
                            color = Color(0xFF2E2C3D)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Belum punya akun?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Inter,
                color = Color(0xFF464555)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Daftar",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter,
                color = Color(0xFF3525CD),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onRegisterClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_verified),
                contentDescription = null,
                tint = Color(0xFF1B1B24).copy(alpha = 0.5f),
                modifier = Modifier.size(12.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Secure Student Portal",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter,
                letterSpacing = 0.6.sp,
                color = Color(0xFF1B1B24).copy(alpha = 0.5f)
            )
        }
    }
}