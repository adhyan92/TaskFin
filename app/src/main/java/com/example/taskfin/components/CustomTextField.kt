package com.example.taskfin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    strokeColor: Color = Color(0xFF6B7280),
    strokeWidth: Float = 1f,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    val backgroundColor = if (enabled) Color(0xFFFFFCFA) else Color(0xFFF1F1F5)
    val textColor = if (enabled) Color(0xFF2E2C3D) else Color(0xFF757575)
    val borderColor = if (enabled) strokeColor else Color(0xFFE0E0E0)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(
                width = strokeWidth.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .background(backgroundColor),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = leadingIcon,
                contentDescription = null,
                tint = if (enabled) Color(0xFF8C8A9E) else Color(0xFFA09EB1),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = textColor
                ),
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 14.sp,
                                color = Color(0xFFA09EB1)
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(10.dp))
                trailingIcon()
            }
        }
    }
}

@Composable
fun TextFormLabel(text: String, color: Color = Color(0xFF464555)) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = customTextFieldColors(),
        singleLine = true
    )
}

@Composable
fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = Color(0xFF3525CD),
    unfocusedBorderColor = Color(0xFFDCDCDC)
)

@Composable
fun GenderButton(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    activeColor: Color
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) activeColor else Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) activeColor else Color(0xFFDCDCDC),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}