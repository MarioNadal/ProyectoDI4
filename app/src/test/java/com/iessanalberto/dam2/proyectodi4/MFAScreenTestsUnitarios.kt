package com.iessanalberto.dam2.proyectodi4

import com.iessanalberto.dam2.proyectodi.viewmodels.MFAScreenViewModel
import org.junit.Assert
import org.junit.Test

/**
 * Conjunto de pruebas unitarias utilizando el framework JUnit para realizar las pruebas pertinentes,
 * que evaluan el comportamiento de MFAScreenViewModel en diferentes escenarios.
 */

//Test unitarios para MFAScreen
class MFAScreenTestsUnitarios {
    //Inicialización del ViewModel para ser utilizado en las pruebas
    val viewModel = MFAScreenViewModel()

    /**
     * Prueba para código vacío.
     * Se espera que el método validarCodigo devuelva 1.
     */
    @Test
    fun codigoVacio(){
        viewModel.onChanged("")
        Assert.assertEquals(1, viewModel.validarCodigo())
    }
    /**
     * Prueba para código corto.
     * Se espera que el método validarCodigo devuelva 1.
     */
    @Test
    fun codigoCorto(){
        viewModel.onChanged("ab123")
        Assert.assertEquals(1, viewModel.validarCodigo())
    }
    /**
     * Prueba para código largo.
     * Se espera que el método validarCodigo devuelva 1.
     */
    @Test
    fun codigoLargo(){
        viewModel.onChanged("ab12345")
        Assert.assertEquals(1, viewModel.validarCodigo())
    }
    /**
     * Prueba para código correcto.
     * Se espera que el método validarCodigo devuelva 2.
     */
    @Test
    fun longitudCodigoCorrecta(){
        viewModel.onChanged("ab1234")
        Assert.assertEquals(2, viewModel.validarCodigo())
    }
}