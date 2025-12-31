package com.example.newsapp.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.newsapp.app.designsystem.skeleton

@Composable
fun NewsCardSkeleton() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .skeleton()
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(8.dp))
                .skeleton()
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(8.dp))
                .skeleton()
        )
    }
}