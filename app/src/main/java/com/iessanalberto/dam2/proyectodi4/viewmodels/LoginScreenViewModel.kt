package com.iessanalberto.dam2.proyectodi.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel {
    //Cogeremos los datos del UiState para poder modificarlos desde aqui
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

    //Cada vez que queramos cambiar uno de los datos del login pondremos esta función
    fun onChanged(correoUi: String, passwordUi: String){
        _uiState.update {
                currentState -> currentState.copy(correo = correoUi, password = passwordUi)
        }
    }

    fun loginUsuario(): Int{
        // Verificar que la contraseña tenga al menos una mayúscula, una minúscula y un número
        val regexMayuscMinus = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+\$")
        //Campos en blanco
        if(_uiState.value.correo.isEmpty() || _uiState.value.password.isEmpty()){
            return 1
        }
        //Correo sin @ o con una longitud incorrecta ya que tiene más de 30 carácteres
        else if (!_uiState.value.correo.contains("@") || _uiState.value.correo.length > 30){
            return 2
        }
        //Contraseña con una longitud incorrecta mayor a 30 o menor que 9
        else if (_uiState.value.password.length > 30 || _uiState.value.password.length < 9){
            return 3
        }
        //Contraseña sin ningun simbolo
        else if(!_uiState.value.password.contains("@") && !_uiState.value.password.contains("#") && !_uiState.value.password.contains("$") && !_uiState.value.password.contains("%") && !_uiState.value.password.contains("&")){
            return 4
        }
        //Contraseña con un formato invalido ya que no tiene como minimo una mayuscula, una minuscula y un número
        else if(!_uiState.value.password.matches(regexMayuscMinus)){
            return 5
        }
        else{
            return 6
        }
    }
}