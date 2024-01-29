package com.example.examenmediciones.ui

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.examenmediciones.Aplicacion
import com.example.examenmediciones.data.Registro
import com.example.examenmediciones.data.RegistroDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListaRegistrosViewModel(private val registroDao: RegistroDao):ViewModel() {

    var registros by mutableStateOf(listOf<Registro>())

    fun insertarRegistro(registro: Registro): List<Registro>{
        viewModelScope.launch(Dispatchers.IO) {
            registroDao.insertar(registro)
        }
        return registros

    }


    fun obtenerRegistro(): List<Registro>{
viewModelScope.launch(Dispatchers.IO) {
       registros = registroDao.ObtenerTodos()
}
        return registros

    }
    fun eliminarRegistro(registro: Registro) {
        viewModelScope.launch(Dispatchers.IO) {
            registroDao.eliminar(registro)
        }
    }


companion object{
    val Factory:ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val  savedStateHandle = createSavedStateHandle()
           val aplicacion = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Aplicacion)
           ListaRegistrosViewModel(aplicacion.registroDao)


        }




    }



}

}