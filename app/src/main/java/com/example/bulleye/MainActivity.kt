// Paquete base del proyecto
package com.example.bullseye

// Imports necesarios para Jetpack Compose y lógica
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.random.Random

// Clase principal de la app, hereda de ComponentActivity (actividad base en Compose)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define el contenido de la UI usando Jetpack Compose
        setContent {
            // Puedes usar tu propio tema aquí, por ejemplo BullseyeTheme
            MaterialTheme {
                BullsEyeGame() // Llama al Composable principal del juego
            }
        }
    }
}

// Función principal del juego. Marca con @Composable para ser usada en la UI
@Composable
fun BullsEyeGame() {
    // Estado del número objetivo aleatorio entre 1 y 100
    var target by remember { mutableStateOf(Random.nextInt(1, 100)) }

    // Estado del valor del slider, inicia en 50f (por defecto centrado)
    var sliderValue by remember { mutableStateOf(50f) }

    // Estado para controlar si se muestra el AlertDialog
    var showDialog by remember { mutableStateOf(false) }

    // Cálculo de diferencia entre el número objetivo y el valor seleccionado
    val difference = abs(target - sliderValue.toInt())

    var resetPoint by remember { mutableStateOf(false) }

    // Cálculo del puntaje: mientras menor sea la diferencia, mayor será el puntaje
    var score = 100 - difference

    var puntajeAcumulado by remember { mutableStateOf(score)}

    var round by remember { mutableStateOf(0) }

    // Layout vertical centrado (Column)
    Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa toda la pantalla
                .padding(24.dp), // Margen alrededor
            horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontal
            verticalArrangement = Arrangement.Center // Centrado vertical
    ) {

        // Texto que muestra el objetivo
        Text(
                text = "🎯 Intenta acercarte a: $target",
                fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio vertical

        // Slider que va del 1 al 100
        Slider(
                value = sliderValue, // Valor actual del slider
                onValueChange = { sliderValue = it }, // Actualiza el valor al moverlo
                valueRange = 1f..100f, // Rango de valores permitidos

                // Otra forma de mostrar el slider con pasos (descomentar si se desea):
                // steps = 98, // Divide el slider en pasos fijos

                modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Muestra el valor actual del slider
        Text(
                text = "Valor seleccionado: ${sliderValue.toInt()}",
                fontSize = 18.sp
        )

        Text(
            text = "Número de Ronda: ${round}"
        )

        Text(
            text = "Puntaje Total: ${puntajeAcumulado}"
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Botón para calcular el puntaje
        Button(onClick = { showDialog = true }) {
            Text("HIT ME") // Texto del botón
        }



        // Si showDialog es verdadero, muestra el AlertDialog
        if (showDialog) {
            AlertDialog(
                    onDismissRequest = { showDialog = false }, // Se cierra si tocan fuera

                    // Botón de confirmación dentro del AlertDialog
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false // Oculta el diálogo
                            target = Random.nextInt(1, 100) // Reinicia el objetivo
                            sliderValue = 50f // Reinicia el slider
                            round += 1
                            puntajeAcumulado += score
                        }) {
                            Text("OK") // Texto del botón de diálogo
                        }
                    },

                    // Título del diálogo
                    title = { Text("Resultado") },

                    // Texto dentro del diálogo, muestra el puntaje calculado
                    text = { Text("Tu puntaje fue: $score") }
            )
        }

        Button(onClick = { resetPoint = true}) {
            Text("Reinicio")
        }

        if (resetPoint) {
            AlertDialog(
                onDismissRequest = { resetPoint = false },

                confirmButton = {
                    TextButton(onClick = {
                        resetPoint = false
                        sliderValue = 0f
                        score = 0
                        round = 0
                        puntajeAcumulado = 0
                    }) {
                        Text(text = "Reinicio de interfaz")
                    }
                })
    }

        /*if(showDialog){
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {  if(score <= 20){
                        puntajeAcumulado = 0
                    } }) {
                        Text(text = "Puntaje menor o igual a 20, puntos perdidos")
                    }

                }
            )
        }*/
}}
