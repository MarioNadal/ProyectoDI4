package com.iessanalberto.dam2.proyectodi.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConfirmacionCambioContrasenaScreenViewModel {
    //Cogeremos los datos del UiState para poder modificarlos desde aqui
    private val _uiState = MutableStateFlow(ConfirmacionCambioContrasenaScreenUiState())
    val uiState: StateFlow<ConfirmacionCambioContrasenaScreenUiState> = _uiState.asStateFlow()

    //Cada vez que queramos cambiar uno de los datos pondremos esta función
    fun onChanged(passwordUi: String, confPasswordUi: String){
        _uiState.update {
                currentState -> currentState.copy(password = passwordUi, codigoverif = confPasswordUi)
        }
    }

    fun confirmacionContrasena(): Int{
        // Verificar que la contraseña tenga al menos una mayúscula, una minúscula y un número
        val regexMayuscMinus = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+\$")
        //Campos en blanco
        if(_uiState.value.password.isEmpty() || _uiState.value.codigoverif.isEmpty()){
            return 1
        }
        //Contraseña con una longitud incorrecta mayor a 30 o menor que 9
        else if (_uiState.value.password.length > 30 || _uiState.value.password.length < 9){
            return 2
        }
        //Contraseña sin ningun simbolo
        else if(!_uiState.value.password.contains("@") && !_uiState.value.password.contains("#") && !_uiState.value.password.contains("$") && !_uiState.value.password.contains("%") && !_uiState.value.password.contains("&")){
            return 3
        }
        //Contraseña con un formato invalido ya que no tiene como minimo una mayuscula, una minuscula y un número
        else if(!_uiState.value.password.matches(regexMayuscMinus)){
            return 4
        }else if(_uiState.value.codigoverif.length!=6){
            return 5
        }else{
            return 6
        }
    }
}