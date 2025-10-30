/**
 * @author Iván Gómez Salcedo
 * @author Sergio de Santiago Redondo
 */

package com.example.p1_tarjetapresentacion_ivan_sergio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.p1_tarjetapresentacion_ivan_sergio.ui.theme.P1_TarjetaPresentacion_Ivan_SergioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P1_TarjetaPresentacion_Ivan_SergioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Azucar(
                        Modifier,
                        "Molan",
                        "Sergio Iván",
                        "123405",
                        "soy_yo_tu_amigo",
                        "tuamigo@soyyo.com"
                    )
                }
            }
        }
    }
}

@Composable
fun Azucar(
    modifier: Modifier = Modifier,
    fullName: String = "Full Name",
    title: String = "Title",
    phoneNo: String = "+00 (00) 000 000",
    social: String = "socialmedia",
    email: String = "email@domail.com"
) {
    val img = painterResource(R.drawable.gatooooo)
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Row {
                Image(img,
                    contentDescription = null,
                    modifier = modifier.size(100.dp)
                )
            }
            Row {
                Text(
                    text = fullName,
                    modifier = modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 37.sp
                )
            }
            Row {
                Text(
                    text = title,
                    modifier = modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
        }
    }
    Box (
        modifier = Modifier.padding(bottom = 55.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier.fillMaxSize()
        ) {
            Column (horizontalAlignment = Alignment.Start){
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Phone number",
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = phoneNo)
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Social"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "@$social")
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = email)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AzucarPreview() {
    P1_TarjetaPresentacion_Ivan_SergioTheme {
        Azucar()
    }
}
