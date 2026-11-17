package com.example.thirtydays.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirtydays.model.Tip
import com.example.thirtydays.model.TipsRepository
import com.example.thirtydays.ui.theme.ThirtyDaysTheme

@Composable
fun TipCard(tip: Tip, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (expanded) 8.dp else 2.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .padding(16.dp)
        ) {
            // Номер дня (мелкий акцентный текст)
            Text(
                text = "День ${tip.day}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            // Название совета (крупный заголовок)
            Text(
                text = tip.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
            // Картинка во всю ширину карточки
            Image(
                painter = painterResource(tip.imageRes),
                contentDescription = "Иллюстрация к совету дня ${tip.day}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            // Описание появляется по клику
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
            key = { tip -> tip.day }
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