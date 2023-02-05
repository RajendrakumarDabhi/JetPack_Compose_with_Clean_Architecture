package com.example.composecleanarchitectureweatherapp.presentation.views.mainscreen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecleanarchitectureweatherapp.R
import com.example.composecleanarchitectureweatherapp.domain.model.WeatherApiResponse
import com.example.composecleanarchitectureweatherapp.presentation.theme.ComposeCleanArchitectureWeatherAppTheme
import com.example.composecleanarchitectureweatherapp.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel by viewModels<MainViewModel>()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewmodel.loadWeatherData()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        setContent {
            ComposeCleanArchitectureWeatherAppTheme {
                Scaffold(
                    topBar = {
                        Topbar()
                    },
                    content = { Body(viewmodel.getScreenState().collectAsState().value) }
                )
            }
        }
    }
}

@Composable
fun Topbar() {
    TopAppBar(title = {
        Text(
            "TopAppBar",
            style = MaterialTheme.typography.h5.copy(color = androidx.compose.ui.graphics.Color.White)
        )
    }, backgroundColor = androidx.compose.ui.graphics.Color.Black)
}

@Composable
fun Body(value: WeatherState) {
    when (value) {
        is WeatherState.Success -> {
            WeatherCard(value.weatherApiResponse, Color.White, Modifier.padding(2.dp))
        }
        is WeatherState.Error -> {
            StateError(msg = value.msg)
        }
        is WeatherState.Loading -> {
            StateLoading()
        }
    }
}

@Composable
fun WeatherCard(
    weatherApiResponse: WeatherApiResponse,
    backgroundColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier
) {
    Card(
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Today at ${LocalDateTime.now().toLocalDate()}",
                style = MaterialTheme.typography.h6
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                painter = painterResource(id = R.drawable.ic_sun),
                contentDescription = "Weather"
            )
            Text(
                text = "${weatherApiResponse.temp} C",
                style = MaterialTheme.typography.h3,

            )
            Text(
                text = "Feels like ${weatherApiResponse.feels_like} C",
                style = MaterialTheme.typography.body1
            )
            Row() {
                Text(
                    text = "Wind: ${weatherApiResponse.wind_speed} MPH",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = modifier.width(20.dp))
                Image(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    painter = painterResource(id = R.drawable.ic_wind),
                    contentDescription = "Weather"
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                Text(
                    text = "Sun Raise: ${getFormetedTime(weatherApiResponse.sunrise.toLong())}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = modifier.width(8.dp))
                Text(
                    text = "Sun set ${getFormetedTime(weatherApiResponse.sunset.toLong())}",
                    style = MaterialTheme.typography.body1
                )
            }


        }
    }
}

fun getFormetedTime(time: Long): String {
    val date = Date(time)
    val dateFormat = SimpleDateFormat("HH:mm aa")
    return dateFormat.format(date)
}

@Composable
fun StateLoading() {
    Column(verticalArrangement = Arrangement.SpaceAround) {
        CircularProgressIndicator()
    }
}

@Composable
fun StateError(msg: String) {
    Column(verticalArrangement = Arrangement.SpaceAround) {
        Text(text = "Error", style = MaterialTheme.typography.h3)
        Text(text = msg, style = MaterialTheme.typography.h6)
    }
}

