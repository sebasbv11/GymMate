package com.jsbv99.gymmate.ui.brand

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jsbv99.gymmate.ui.theme.GymGreen
import com.jsbv99.gymmate.ui.theme.GymNavy

@Composable
fun GymMateMark(
    modifier: Modifier = Modifier,
    size: Dp = 144.dp,
    backgroundColor: Color = GymNavy
) {
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawRoundRect(
                color = backgroundColor,
                cornerRadius = CornerRadius(this.size.minDimension * 0.18f)
            )
            val unit = this.size.minDimension / 100f
            drawCircle(Color.White, 7f * unit, Offset(55f * unit, 25f * unit))
            drawLine(Color.White, Offset(51f * unit, 37f * unit), Offset(43f * unit, 58f * unit), 11f * unit, StrokeCap.Round)
            drawLine(Color.White, Offset(48f * unit, 43f * unit), Offset(65f * unit, 47f * unit), 8f * unit, StrokeCap.Round)
            drawLine(Color.White, Offset(44f * unit, 56f * unit), Offset(30f * unit, 73f * unit), 9f * unit, StrokeCap.Round)
            drawLine(Color.White, Offset(45f * unit, 57f * unit), Offset(62f * unit, 67f * unit), 9f * unit, StrokeCap.Round)
            drawLine(Color.White, Offset(62f * unit, 67f * unit), Offset(75f * unit, 61f * unit), 8f * unit, StrokeCap.Round)
            drawLine(GymGreen, Offset(32f * unit, 40f * unit), Offset(45f * unit, 48f * unit), 5f * unit, StrokeCap.Round)
            drawRect(GymGreen, Offset(24f * unit, 33f * unit), Size(5f * unit, 17f * unit))
            drawRect(GymGreen, Offset(29f * unit, 36f * unit), Size(5f * unit, 17f * unit))
            drawArc(
                color = GymGreen,
                startAngle = 25f,
                sweepAngle = 125f,
                useCenter = false,
                topLeft = Offset(16f * unit, 43f * unit),
                size = Size(72f * unit, 42f * unit),
                style = Stroke(width = 4f * unit, cap = StrokeCap.Round)
            )
        }
    }
}
