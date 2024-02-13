package com.iessanalberto.dam2.proyectodi.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterScreenViewModel {

    private val _uiState = MutableStateFlow(RegisterScreenUiState())
    val uiState: StateFlow<RegisterScreenUiState> = _uiState.asStateFlow()

    fun onChanged(registroUsuario: String, registroCorreo: String, registroPassword: String, registroConfPasw: String){
        _uiState.update {
                currentState -> currentState.copy(registroUsuario = registroUsuario, registroCorreo = registroCorreo, registroPassword = registroPassword, registroConfPasw = registroConfPasw)
        }
    }
    fun registrarUsuario(): Int{
        // Verificar que la contraseña tenga al menos una mayúscula, una minúscula y un número
        val regexMayuscMinus = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+\$")

        //Campos en blanco
        if(_uiState.value.registroCorreo.isEmpty() || _uiState.value.registroPassword.isEmpty() || _uiState.value.registroConfPasw.isEmpty()){
            return 1
        }
        //Correo con una longitud incorrecta mayor a 30 carácteres
        else if ( _uiState.value.registroCorreo.length > 30 ){
            return 2
        }
        //Correo no contiene simbolo @
        else if(!_uiState.value.registroCorreo.contains("@")){
            return 3
        }
        //Contraseña con una longitud incorrecta menor a 9 carácteres o mayor a 30
        else if (_uiState.value.registroPassword.length < 9 || _uiState.value.registroPassword.length > 30) {
            return 4
        }
        //Contraseña sin ningun simbolo
        else if(!_uiState.value.registroPassword.contains("@") && !_uiState.value.registroPassword.contains("#") && !_uiState.value.registroPassword.contains("$") && !_uiState.value.registroPassword.contains("%") && !_uiState.value.registroPassword.contains("&")){
            return 5
        }
        //Contraseña con un formato invalido ya que no tiene como minimo una mayuscula, una minuscula y un númeroña
        else if(!_uiState.value.registroPassword.matches(regexMayuscMinus)){
            return 6
        }
        //Confirmación de contraseña no es igual a contraseña
        else if(!_uiState.value.registroPassword.equals(_uiState.value.registroConfPasw)) {
            return 7
        }
        //Usuario (Confirmaciónd e correo) no es igual a correo
        else if(!_uiState.value.registroCorreo.equals(_uiState.value.registroUsuario)){
            return 8
        }else{
            return 9
        }
    }

}