package com.example.examenmediciones

import androidx.lifecycle.viewmodel.compose.viewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.util.LocalePreferences.FirstDayOfWeek.Days
import com.example.examenmediciones.data.Registro
import com.example.examenmediciones.ui.ListaRegistrosViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
AppRegistrosUi()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRegistrosUi(
    vmlistaregistros: ListaRegistrosViewModel = viewModel(factory = ListaRegistrosViewModel.Factory)
){
    var currentScreen by remember { mutableStateOf("lista") }

    LaunchedEffect(Unit){
        vmlistaregistros.obtenerRegistro()
    }

    when (currentScreen) {
        "lista" -> ListaRegistrosUi(vmlistaregistros, onNavigateToFormulario = { currentScreen = "formulario" })
        "formulario" -> FormularioRegistroUi(vmlistaregistros, onNavigateToLista = { currentScreen = "lista" })
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRegistrosUi(
    vmlistaregistros: ListaRegistrosViewModel,
    onNavigateToFormulario: () -> Unit
) {
    LaunchedEffect(Unit){
        vmlistaregistros.obtenerRegistro()

    }
    LazyColumn{
        items(vmlistaregistros.registros) { registro ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Muestra la imagen correspondiente según el tipo de registro
                val iconDrawable = when (registro.tiporegistro) {
                    "Agua" -> R.drawable.agua
                    "Luz" -> R.drawable.luz
                    "Gas" -> R.drawable.gas
                    else -> R.drawable.ic_launcher_background // Puedes tener un icono predeterminado para otros casos
                }

                Image(
                    painter = painterResource(id = iconDrawable),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Muestra la información del registro
                Column {
                    Text(text = "Tipo: ${registro.tiporegistro}")
                    Text(text = "Fecha: ${registro.fecha}")
                    Text(text = "Medidor: ${registro.medidor}")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    vmlistaregistros.eliminarRegistro(registro)
                        .also { vmlistaregistros.obtenerRegistro() }
                }) {
                    Text(R.string.btn_Elim.toString())
                }
            }
        }
    item{
    Button(onClick = onNavigateToFormulario) {
        Text(R.string.btn_Agre.toString())}
    }
}
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistroUi(
    vmlistaregistros: ListaRegistrosViewModel,
    onNavigateToLista: () -> Unit
) {
    var medidorText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedRadioButton by remember { mutableStateOf("") }

    // Formulario para agregar un nuevo registro
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = medidorText,
            onValueChange = { medidorText = it },
            label = { Text("Medidor") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date Picker Dialog
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Fecha: ${selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            RadioButton(
                selected = selectedRadioButton == "Agua",
                onClick = {
                    selectedRadioButton = "Agua"
                }
            )
            Text("Agua", modifier = Modifier.clickable {
                selectedRadioButton = "Agua"
            })

            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedRadioButton == "Luz",
                onClick = {
                    selectedRadioButton = "Luz"
                }
            )
            Text("Luz", modifier = Modifier.clickable {
                selectedRadioButton = "Luz"
            })

            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedRadioButton == "Gas",
                onClick = {
                    selectedRadioButton = "Gas"
                }
            )
            Text("Gas", modifier = Modifier.clickable {
                selectedRadioButton = "Gas"
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            vmlistaregistros.insertarRegistro(
                Registro(
                    null,
                    LocalDate.now(),
                    medidorText.toIntOrNull() ?: 0,
                    selectedRadioButton
                )
            ).also { vmlistaregistros.obtenerRegistro() }
        }) {
            Text(R.string.btn_Agre.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Agrega un botón para volver a la lista
        Button(onClick = onNavigateToLista) {
            Text(R.string.btn_Volver.toString())
        }
    }
}
/*
fun AppRegistrosUi(
    vmlistaregistros: ListaRegistrosViewModel = viewModel(factory = ListaRegistrosViewModel.Factory)

){
    var medidorText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedRadioButton by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit){
        vmlistaregistros.obtenerRegistro()

    }

    LazyColumn{
        items(vmlistaregistros.registros) { registro ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Muestra la imagen correspondiente según el tipo de registro
                val iconDrawable = when (registro.tiporegistro) {
                    "Agua" -> R.drawable.agua
                    "Luz" -> R.drawable.luz
                    "Gas" -> R.drawable.gas
                    else -> R.drawable.ic_launcher_background // Puedes tener un icono predeterminado para otros casos
                }

                Image(
                    painter = painterResource(id = iconDrawable),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Muestra la información del registro
                Column {
                    Text(text = "Tipo: ${registro.tiporegistro}")
                    Text(text = "Fecha: ${registro.fecha}")
                    Text(text = "Medidor: ${registro.medidor}")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    vmlistaregistros.eliminarRegistro(registro)
                        .also { vmlistaregistros.obtenerRegistro() }
                }) {
                    Text("Eliminar")
                }
            }
        }

        item {
            // Formulario para agregar un nuevo registro
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = medidorText,
                    onValueChange = { medidorText = it },
                    label = { Text("Medidor") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(16.dp))

                //Date Picker Dialog


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Fecha: ${selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}")


                }
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    RadioButton(
                        selected = selectedRadioButton == "Agua",
                        onClick = {
                            selectedRadioButton = "Agua"
                            // Handle the click, e.g., Pirates are the best
                        }
                    )
                    Text("Agua", modifier = Modifier.clickable {
                        selectedRadioButton = "Agua"

                    })

                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = selectedRadioButton == "Luz",
                        onClick = {
                            selectedRadioButton = "Luz"

                        }
                    )
                    Text("Luz", modifier = Modifier.clickable {
                        selectedRadioButton = "Luz"

                    })

                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = selectedRadioButton == "Gas",
                        onClick = {
                            selectedRadioButton = "Gas"

                        }
                    )
                    Text("Gas", modifier = Modifier.clickable {
                        selectedRadioButton = "Gas"

                    })
                }



                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    vmlistaregistros.insertarRegistro(
                        Registro(
                            null,
                            LocalDate.now(),
                            medidorText.toIntOrNull() ?: 0,
                            selectedRadioButton
                        )
                    ).also { vmlistaregistros.obtenerRegistro() }
                }) {
                    Text("Registrar Medición")
                }
            }


        }

    }

}*/

