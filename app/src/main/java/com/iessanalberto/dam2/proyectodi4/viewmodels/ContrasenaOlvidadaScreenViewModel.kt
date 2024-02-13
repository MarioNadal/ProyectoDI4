package com.iessanalberto.dam2.proyectodi.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContrasenaOlvidadaScreenViewModel {
    //Cogeremos los datos del UiState para poder modificarlos desde aqui
    private val _uiState = MutableStateFlow(ContrasenaOlvidadaUiState())
    val uiState: StateFlow<ContrasenaOlvidadaUiState> = _uiState.asStateFlow()
    

    //Cada vez que queramos cambiar uno de los datos pondremos esta función
    fun onChanged(usernameUi: String){
        _uiState.update {
                currentState -> currentState.copy(username = usernameUi)
        }
    }

    fun contrasenaOlvidada(): Int{
        //Campos en blanco
        if(_uiState.value.username.isEmpty()){
            return 1
        }
        //Username sin @ o con una longitud incorrecta ya que tiene más de 30 carácteres
        else if (!_uiState.value.username.contains("@") || _uiState.value.username.length > 30){
            return 2
        }else{
            return 3
        }
    }
}