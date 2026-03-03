package com.example.thirtydaysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirtydaysapp.model.SpanishTopic
import com.example.thirtydaysapp.model.SpanishTopicsRepository.spanishTopics
import com.example.thirtydaysapp.ui.theme.ThirtyDaysAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirtyDaysAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ThirtyDaysApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SpanishTopicItem(topic: SpanishTopic, modifier: Modifier = Modifier) {
    var showImageCredits by remember { mutableStateOf(false) }
    var showDescription by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Image(
                painter = painterResource(topic.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable {
                        showImageCredits = !showImageCredits
                    }
                ,
                contentScale = ContentScale.Crop
            )
            if (showImageCredits) {
                Text(
                    text = stringResource(topic.imageCreditsRes),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(
                            bottom = 4.dp,
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium),
                        )
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "Day " + topic.day.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
            Text(
                text = stringResource(topic.nameRes),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
            if (showDescription) {
                Text(
                    text = stringResource(topic.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(R.dimen.padding_small),
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        ),
                )
            }
            IconButton(
                onClick = { showDescription = !showDescription },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (showDescription) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = stringResource(R.string.expand_button_description),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun SpanishTopicsList(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        items(spanishTopics) {
            SpanishTopicItem(it)
        }
    }
}

@Composable
fun ThirtyDaysApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar ={
            ThirtyDaysAppBar()
        }
    ) {
        SpanishTopicsList(
            contentPadding = it,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDaysAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ThirtyDaysAppPreview() {
    ThirtyDaysAppTheme {
        ThirtyDaysApp()
    }
}