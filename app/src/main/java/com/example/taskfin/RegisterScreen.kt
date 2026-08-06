package com.example.taskfin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskfin.components.CustomTextField
import com.example.taskfin.components.RegisterBottomSection
import com.example.taskfin.ui.theme.Inter

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onLoginClick: () -> Unit = {},
    onRegisterNowClick: () -> Unit = {}
){

    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var confirmPassword by remember { mutableStateOf("") }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val mainVerticalScrollState = rememberScrollState()

    Box(
        modifier = modifier.fillMaxSize()
    ){
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
        ){
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
                    onValueChange = { fullName = it },
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
                    onValueChange = { email = it },
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
                    leadingIcon = painterResource(R.drawable.ic_password),

                    visualTransformation = if (isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },

                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                if (isPasswordVisible) R.drawable.ic_visibilityon else R.drawable.ic_visibilityoff
                            ),
                            contentDescription = "Toggle Password Visibility",
                            tint = Color(0xFF8C8A9E),
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    isPasswordVisible = !isPasswordVisible
                                }
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

                    visualTransformation = if (isConfirmPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },

                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                if (isConfirmPasswordVisible) R.drawable.ic_visibilityon else R.drawable.ic_visibilityoff
                            ),
                            contentDescription = "Toggle Confirm Password Visibility",
                            tint = Color(0xFF8C8A9E),
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    isConfirmPasswordVisible = !isConfirmPasswordVisible
                                }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(22.dp))

                RegisterBottomSection(
                    onRegisterNowClick = {
                        viewModel.setRegisterData(fullName, email)
                        onRegisterNowClick()
                    },
                    onLoginClick = { onLoginClick() }
                )
            }
        }
    }
}