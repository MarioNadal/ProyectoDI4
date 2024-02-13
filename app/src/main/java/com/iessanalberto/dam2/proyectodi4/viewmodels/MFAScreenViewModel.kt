package com.iessanalberto.dam2.proyectodi.viewmodels

import android.widget.Toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MFAScreenViewModel {

    private val _uiState = MutableStateFlow(MFAScreenUiState())
    val uiState: StateFlow<MFAScreenUiState> = _uiState.asStateFlow()

    fun onChanged(mfaCodigo: String){
        _uiState.update {
                currentState -> currentState.copy(mfaCodigo = mfaCodigo)
        }
    }

    fun validarCodigo(): Int{
        //Código invalido por ser de una longitud distinta a 6
        if(_uiState.value.mfaCodigo.length!=6){
            return 1
        }else{
            return 2
        }
    }
}