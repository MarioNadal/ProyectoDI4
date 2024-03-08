package com.iessanalberto.dam2.proyectodi4

import com.iessanalberto.dam2.proyectodi.viewmodels.ConfirmacionCambioContrasenaScreenUiState
import com.iessanalberto.dam2.proyectodi.viewmodels.ConfirmacionCambioContrasenaScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.ContrasenaOlvidadaScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.LoginScreenViewModel
import org.junit.Assert
import org.junit.Test

/**
 * Conjunto de pruebas unitarias utilizando el framework JUnit para realizar las pruebas pertinentes,
 * que evaluan el comportamiento de ContrasenaOlvidadaScreenViewModel y
 * ConfirmacionCambioContrasenaScreenViewModel en diferentes escenarios.
 */
class ContrasenaOlvidadaScrenTestsUnitarios {

    //Inicialización de los ViewModel para ser utilizados en la diferentes pruebas
    val viewModel = ContrasenaOlvidadaScreenViewModel()
    val viewModelConfirmacion = ConfirmacionCambioContrasenaScreenViewModel()


    //Pruebas para ContrasenaOlvidadaScreenViewModel
    /**
     * Prueba para usuario vacío.
     * Se espera que el método contrasenaOlvidada devuelva 1.
     */
    @Test
    fun userVacio(){
        viewModel.onChanged("")
        Assert.assertEquals(1, viewModel.contrasenaOlvidada())
    }
    /**
     * Prueba para usuario sin @.
     * Se espera que el método contrasenaOlvidada devuelva 2.
     */
    @Test
    fun userSinArroba(){
        viewModel.onChanged("ejemplocorreo.com")
        Assert.assertEquals(2, viewModel.contrasenaOlvidada())
    }
    /**
     * Prueba para usuario demasiado largo.
     * Se espera que el método contrasenaOlvidada devuelva 2.
     */
    @Test
    fun userLargo(){
        viewModel.onChanged("ejemplo@correoiessanalberto.com")
        Assert.assertEquals(2, viewModel.contrasenaOlvidada())
    }
    /**
     * Prueba para formato de usuario correcto.
     * Se espera que el método contrasenaOlvidada devuelva 3.
     */
    @Test
    fun userCorrecto(){
        viewModel.onChanged("ejemplo@correo.com")
        Assert.assertEquals(3, viewModel.contrasenaOlvidada())
    }

    //-----------------------------

    //Pruebas para ConfirmacionCambioContrasenaScreenViewModel

    /**
     * Prueba para todos los campos vacíos.
     * Se espera que el método confirmacionContrasena devuelva 1.
     */
    @Test
    fun camposVaciosConfirmacion(){
        viewModelConfirmacion.onChanged("","")
        Assert.assertEquals(1, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para pasword vacía.
     * Se espera que el método confirmacionContrasena devuelva 1.
     */
    @Test
    fun passwordVaciaConfirmacion(){
        viewModelConfirmacion.onChanged("","ABC123")
        Assert.assertEquals(1, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para codigo vacío.
     * Se espera que el método confirmacionContrasena devuelva 1.
     */
    @Test
    fun codigoVacioConfirmacion(){
        viewModelConfirmacion.onChanged("password123","")
        Assert.assertEquals(1, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para pasword corta menos de 9 carcteres.
     * Se espera que el método confirmacionContrasena devuelva 2.
     */
    @Test
    fun passworCortaConfirmacion(){
        viewModelConfirmacion.onChanged("psw12345","ABC123")
        Assert.assertEquals(2, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para pasword larga más de 30 carcteres.
     * Se espera que el método confirmacionContrasena devuelva 2.
     */
    @Test
    fun passworLargaConfirmacion(){
        viewModelConfirmacion.onChanged(
            "psw123456789123456789123456789123","ABC123")
        Assert.assertEquals(2, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para pasword sin sñimbolos
     * Se espera que el método confirmacionContrasena devuelva 3.
     */
    @Test
    fun passworConfirmacionSinSimbolo(){
        viewModelConfirmacion.onChanged("psw123456","ABC123")
        Assert.assertEquals(3, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para pasword sin mayúsculas
     * Se espera que el método confirmacionContrasena devuelva 4.
     */
    @Test
    fun passworConfirmacionSinMayus(){
        viewModelConfirmacion.onChanged("psw@123456","ABC123")
        Assert.assertEquals(4, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para pasword sin minúsculas
     * Se espera que el método confirmacionContrasena devuelva 4.
     */
    @Test
    fun passworConfirmacionSinMinus(){
        viewModelConfirmacion.onChanged("PSW@123456","ABC123")
        Assert.assertEquals(4, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para pasword sin números
     * Se espera que el método confirmacionContrasena devuelva 4.
     */
    @Test
    fun passworConfirmacionSinNumeros(){
        viewModelConfirmacion.onChanged("PSW@abcdefg","ABC123")
        Assert.assertEquals(4, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para codigo corto menos de 6 caracteres
     * Se espera que el método confirmacionContrasena devuelva 5.
     */
    @Test
    fun codigoCorto(){
        viewModelConfirmacion.onChanged("PSW@123abc", "ABC12")
        Assert.assertEquals(5, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para codigo largo más de 6 caracteres
     * Se espera que el método confirmacionContrasena devuelva 5.
     */
    @Test
    fun codigoLargo(){
        viewModelConfirmacion.onChanged("PSW@123abc", "ABC1234")
        Assert.assertEquals(5, viewModelConfirmacion.confirmacionContrasena())
    }
    /**
     * Prueba para formato de los campos correctos
     * Se espera que el método confirmacionContrasena devuelva 6.
     */
    @Test
    fun dotosConfirmacionCorrectos(){
        viewModelConfirmacion.onChanged("PSW@123abc", "ABC123")
        Assert.assertEquals(6, viewModelConfirmacion.confirmacionContrasena())
    }
}