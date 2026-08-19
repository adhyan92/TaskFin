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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.taskfin.components.CustomBottomBar
import com.example.taskfin.ui.theme.Inter

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel,
    onSettingsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
){

    val mainVerticalScrollState = rememberScrollState()
    val context = LocalContext.current
    val primaryColor = Color(0xFF3525CD)

    val surfaceColor = MaterialTheme.colorScheme.surface
    val profileImageUri = viewModel.profileImageUri.collectAsState().value

    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        },
        modifier = modifier
    ){ innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(mainVerticalScrollState)
                .padding(top = 60.dp)
                .padding(bottom = innerPadding.calculateBottomPadding()),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TaskFin",
                    fontSize = 32.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF3525CD),
                                Color(0xFF006C49)
                            )
                        )
                    ),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Inter,
                    letterSpacing = (-0.64).sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Pengaturan",
                        tint = primaryColor,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onSettingsClick() }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = if (profileImageUri != null) Color(0xFF3525CD) else Color(0xFFC7C4D8),
                                shape = CircleShape
                            )
                            .background(surfaceColor)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onProfileClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        if (profileImageUri != null) {
                            AsyncImage(
                                model = profileImageUri,
                                contentDescription = "Profile Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Foto Profil",
                                tint = Color.Black,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ){
                Text(
                    text = "SELAMAT PAGI, KATON",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    letterSpacing = 1.2.sp,
                    color = Color(0xFF464555)
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Atur harimu dengan tenang.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color(0xFF1B1B24)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.TopStart
            ) {

                Image(
                    painter = painterResource(R.drawable.card_tugas),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(start = 42.dp, top = 40.dp, end = 42.dp, bottom = 22.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(44.dp)
                                    .height(34.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_book),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Tugas Kuliah",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Inter,
                                color = Color.White
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(110.dp)
                                .height(23.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(Color(0xFFBA1A1A)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "3 DEADLINE DEKAT",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color.White
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Matematika Diskrit",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Inter,
                                    color = Color.White
                                )

                                Text(
                                    text = "Besok, 23:59 WIB",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = Inter,
                                    color = Color.White.copy(alpha = 0.75f)
                                )
                            }

                            Text(
                                text = "!",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = Inter,
                                color = Color(0xFF6CF8BB)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .clickable {
                                    Toast.makeText(
                                        context,
                                        "Feature Coming Soon",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add),
                                contentDescription = null,
                                tint = Color(0xFF3525CD),
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Tambah Tugas",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF3525CD)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                .clickable {
                                    Toast.makeText(
                                        context,
                                        "Feature Coming Soon",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_list),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Daftar Tugas",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.TopStart
            ) {

                Image(
                    painter = painterResource(R.drawable.card_keuangan),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(start = 42.dp, top = 40.dp, end = 42.dp, bottom = 22.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(44.dp)
                                    .height(34.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_payments),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Keuangan",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Inter,
                                color = Color.White
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "SISA SALDO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "Rp 1.250.000",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Inter,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Anggaran Makan",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = Inter,
                                    color = Color.White
                                )

                                Text(
                                    text = "75% Terpakai",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = Inter,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(Color.White.copy(alpha = 0.25f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.75f)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(100.dp))
                                        .background(Color(0xFF6CF8BB))
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .clickable {
                                    Toast.makeText(
                                        context,
                                        "Feature Coming Soon",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_receipt),
                                contentDescription = null,
                                tint = Color(0xFF1E6F4A),
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Laporan",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF1E6F4A)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF88F2BA))
                                .clickable {
                                    Toast.makeText(
                                        context,
                                        "Feature Coming Soon",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_input),
                                contentDescription = null,
                                tint = Color(0xFF135235),
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Input Baru",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF135235)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .padding(horizontal = 24.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFF3525CD))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Eksplorasi Fitur",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Inter,
                        color = Color(0xFF1B1B24)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val featureItems = listOf(
                        Triple("Sharing", R.drawable.ic_sharing, "Sharing"),
                        Triple("Sinkron", R.drawable.ic_sync, "Sinkron"),
                        Triple("Integrasi", R.drawable.ic_integrasi, "Integrasi"),
                        Triple("Analisis", R.drawable.ic_analisis, "Analisis")
                    )

                    featureItems.forEach { (title, iconRes, _) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    Toast.makeText(
                                        context,
                                        "Feature Coming Soon",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFF1EFFC))
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFFC7C4D8),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(iconRes),
                                    contentDescription = title,
                                    tint = Color(0xFF3525CD),
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = title,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF464555)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(18.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFF3525CD))
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Daftar Tugas Hari Ini",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Inter,
                            color = Color(0xFF1B1B24)
                        )
                    }

                    Text(
                        text = "Lihat Semua",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Inter,
                        color = Color(0xFF3525CD),
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(
                                    context,
                                    "Feature Coming Soon",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF1EFFC))
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFFC7C4D8),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Laporan Praktikum",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF1B1B24)
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Fisika Dasar II",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF464555)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {

                            Text(
                                text = "DEADLINE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF3525CD)
                            )
                            Text(
                                text = "17:00 WIB",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Inter,
                                color = Color(0xFF1B1B24)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF1EFFC))
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFFC7C4D8),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Kuis Aljabar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF1B1B24)
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Matematika Lanjut",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF464555)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {

                            Text(
                                text = "DEADLINE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF3525CD)
                            )
                            Text(
                                text = "20:00 WIB",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Inter,
                                color = Color(0xFF1B1B24)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFF1EFFC))
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFFC7C4D8),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Skor Produktivitas",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF3525CD)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Kamu lebih produktif 15% dari\nminggu lalu!",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Inter,
                                color = Color(0xFF464555)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .border(4.dp, Color(0xFF3525CD), CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "75%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Inter,
                                color = Color(0xFF3525CD)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}