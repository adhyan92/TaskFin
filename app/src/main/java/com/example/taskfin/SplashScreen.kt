package com.example.taskfin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskfin.ui.theme.Inter

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToBoarding1: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.splash_screen),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onNavigateToBoarding1()
                },
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 300.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.logo_taskfin),
            contentDescription = "Logo TaskFin",
            modifier = Modifier
                .size(150.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "TaskFin",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = Inter,
            modifier = Modifier.offset(y = (-24).dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Atur Tugas dan Keuangan dengan\nMudah",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontFamily = Inter,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-8).dp)
            )
        }

        Spacer(modifier = Modifier.height(175.dp))

        Image(
            painter = painterResource(R.drawable.line),
            contentDescription = "Line Indicator",
            modifier = Modifier
                .width(200.dp)
                .height(4.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "ACADEMIC & FINANCE SYNC",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold,
                fontFamily = Inter,
                letterSpacing = 1.2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (20).dp)
            )
        }
    }
}