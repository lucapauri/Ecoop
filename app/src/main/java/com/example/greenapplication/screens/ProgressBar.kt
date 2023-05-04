package com.example.greenapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomProgressBar(
    modifier: Modifier, width: Dp, backgroundColor: Color, foregroundColor: Brush, percent: Int,
    isShownText: Boolean
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .width(width)
    ) {
        Box(
            modifier = modifier
                .background(foregroundColor)
                .width(width * percent / 100)
        )
        //if (isShownText) Text("${percent} %", modifier = Modifier.align(alignment = Alignment.Center), fontSize = 12.sp, color = MaterialTheme.colors.surface)
    }
}

@Composable
fun IconBar(percentCO2: Int, percentHealth: Int) {

    //Spacer(Modifier.height(20.dp))
    Row(horizontalArrangement = Arrangement.Center) {
        Spacer(Modifier.height(20.dp))
        Column() {

            Row(
                modifier = Modifier.padding(end = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(imageVector = Icons.Default.Face, "", modifier = Modifier.size(30.dp),
                tint = Color.DarkGray)
                Spacer(Modifier.width(10.dp))
                CustomProgressBar(
                    Modifier
                        .clip(shape = RoundedCornerShape(100))
                        .height(15.dp),
                    width = 150.dp,
                    Color.LightGray,
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF5DB075),
                            Color(0xffFBE41A),
                            Color(0xffFD7D20)
                        )
                    ),
                    percentHealth,
                    true,
                )
            }


            Row(
                modifier = Modifier.padding(end = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(imageVector = Icons.Default.Warning, "", modifier = Modifier.size(30.dp),
                    tint = Color.DarkGray)
                Spacer(Modifier.width(10.dp))
                CustomProgressBar(
                    Modifier
                        .clip(shape = RoundedCornerShape(100))
                        .height(15.dp),
                    width = 150.dp,
                    Color.LightGray,
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF31C8DC),
                            Color(0xff3039DB),
                            Color(0xffB130DB)
                        )
                    ),
                    percentCO2,
                    true
                )
            }
            //Spacer(Modifier.height(20.dp))
        }

        Row(
            modifier = Modifier.padding(top = 15.dp, start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, "", modifier = Modifier.size(30.dp),
                tint = Color.DarkGray)
            Spacer(Modifier.width(10.dp))
            Text(text = "25")
        }

    }
}