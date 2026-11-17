package com.example.thirtydays.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thirtydays.model.Tip
import com.example.thirtydays.model.TipsRepository
import com.example.thirtydays.ui.theme.ThirtyDaysTheme

@Composable
fun DayBadge(day: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$day",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun TipCard(tip: Tip, modifier: Modifier = Modifier) {
    // Состояние: развернута карточка или нет
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        // Тень меняется в зависимости от состояния (как в кодлабе)
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (expanded) 8.dp else 2.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                // ВСЯ КАРТОЧКА СТАНОВИТСЯ КЛИКАБЕЛЬНОЙ
                .clickable { expanded = !expanded }
                // Плавная анимация изменения размера
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                DayBadge(day = tip.day)
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "День ${tip.day}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = tip.title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Описание показывается только если expanded == true
            if (expanded) {
                Text(
                    text = tip.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TipsList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(
            items = TipsRepository.tips,
            key = { tip -> tip.day } // Оптимизация скролла
        ) { tip ->
            TipCard(tip = tip)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipsListPreview() {
    ThirtyDaysTheme(darkTheme = false) {
        TipsList()
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun TipsListDarkPreview() {
    ThirtyDaysTheme(darkTheme = true) {
        TipsList()
    }
}