package com.example.taskfin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskfin.components.OnboardingPageIndicator
import com.example.taskfin.ui.theme.Inter

@Composable
fun onBoarding1(
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 125.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.image_onboarding1),
            contentDescription = null,
            modifier = Modifier
                .size(
                    height = 300.dp,
                    width = 300.dp
                ),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Catat dan kelola tugas\nkuliahmu",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 25.sp,
            fontFamily = Inter,
            color = Color(0xFF3525CD)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Atur prioritas tugas agar tidak ada\nyang terlewatkan dalam satu\naplikasi",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            fontFamily = Inter,
            color = Color(0xFF464555)
        )

        Spacer(modifier = Modifier.height(30.dp))

        OnboardingPageIndicator(
            pageCount = 2,
            currentPage = 0
        )

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF3525CD))
                .clickable { onContinueClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Lanjut",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Lewati",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Inter,
            modifier = Modifier
                .clickable { onSkipClick() },
            color = Color(0xFF464555)
        )
    }
}