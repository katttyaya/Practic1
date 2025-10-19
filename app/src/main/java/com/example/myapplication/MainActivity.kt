package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationApp()
        }
    }
}

@Composable
fun MyApplicationApp() {
    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Survey(Modifier.padding(innerPadding))
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SurveyPreview() {
    Survey(Modifier.padding(0.dp))
}

@Composable
fun Survey(modifier: Modifier) {
    var result by remember { mutableStateOf("") }
    var enabled by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var newsletterChecked by remember { mutableStateOf(false) }

    val genderOptions =
        listOf(stringResource(R.string.gender_male), stringResource(R.string.gender_female))
    var genderSelected by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.name_label), textAlign = TextAlign.Center)
                TextField(name, onValueChange = {
                    name = it
                    enabled = checkValidity(name, age, genderSelected)
                })
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.age_label), textAlign = TextAlign.Center)
                TextField(
                    age, onValueChange = {
                        if (it.all { char -> char.isDigit() }) age = it
                        enabled = checkValidity(name, age, genderSelected)
                    }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.gender_label), textAlign = TextAlign.Center)
                Row {
                    genderOptions.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                genderSelected = option
                                enabled = checkValidity(name, age, genderSelected)
                            }) {
                            RadioButton(
                                selected = (option == genderSelected), onClick = {
                                    genderSelected = option
                                    enabled = checkValidity(name, age, genderSelected)
                                })
                            Text(
                                text = option
                            )
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.newsletter_label), textAlign = TextAlign.Center)
                Checkbox(newsletterChecked, onCheckedChange = { newsletterChecked = it })
            }

            Button(
                onClick = {
                    result = "Имя: $name\nВозраст: $age\nПол: $genderSelected\nПодписка: ${
                        boolToString(newsletterChecked)
                    }"
                }, modifier = Modifier.fillMaxWidth(), enabled = enabled
            ) {
                Text(stringResource(R.string.send_button))
            }

            Text(result)
        }
    }
}

fun checkValidity(name: String, age: String, gender: String): Boolean {
    return name.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty()
}

fun boolToString(value: Boolean): String {
    return if (value) "да" else "нет"
}