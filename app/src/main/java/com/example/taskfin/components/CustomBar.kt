package com.example.taskfin.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.taskfin.R
import com.example.taskfin.ui.theme.Inter
import data.BottomNavItem

@Composable
fun CustomBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val items = listOf(
        BottomNavItem("Beranda", R.drawable.ic_home, "dashboard_screen"),
        BottomNavItem("Tugas", R.drawable.ic_task, "task_screen"),
        BottomNavItem("Keuangan", R.drawable.ic_finance, "finance_screen"),
        BottomNavItem("Statistik", R.drawable.ic_statistic, "statistic_screen"),
        BottomNavItem("Profil", R.drawable.ic_person, "profile_screen")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedRoute by remember { mutableStateOf(currentRoute ?: "dashboard_screen") }

    LaunchedEffect(currentRoute) {
        if (currentRoute == "dashboard_screen" || currentRoute == "profile_screen") {
            selectedRoute = currentRoute
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = selectedRoute == item.route

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .width(64.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            if (isSelected) Color(0xFF5B4FE9) else Color.Transparent
                        )
                        .clickable {
                            selectedRoute = item.route

                            if (item.route == "dashboard_screen" || item.route == "profile_screen") {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Feature Coming Soon",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        painter = painterResource(item.iconRes),
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color(0xFF464555),
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        fontFamily = Inter,
                        color = if (isSelected) Color.White else Color(0xFF464555),
                        lineHeight = 11.sp,
                        maxLines = 1,
                        style = LocalTextStyle.current.copy(
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                }
            }
        }
    }
}