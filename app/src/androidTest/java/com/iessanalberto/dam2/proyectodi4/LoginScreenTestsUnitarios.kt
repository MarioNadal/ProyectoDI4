package com.iessanalberto.dam2.proyectodi4

import com.iessanalberto.dam2.proyectodi.viewmodels.LoginScreenViewModel
import org.junit.Assert
import org.junit.Test

/**
 * Conjunto de pruebas unitarias utilizando el framework JUnit para realizar las pruebas pertinentes,
 * que evaluan el comportamiento de LoginScreenViewModel en diferentes escenarios.
 */

//Test unitarios para LoginScreen
class LoginScreenTestsUnitarios {
    //Inicialización del ViewModel para ser utilizado en las pruebas
    val viewModel = LoginScreenViewModel()

    /**
     * Prueba para campos vacíos.
     * Se espera que el método registrarUsuario devuelva 1.
     */
    @Test
    fun camposVacios() {

        viewModel.onChanged(
            "",
            "")
        Assert.assertEquals(1, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo correo sin el símbolo @.
     * Se espera que el método registrarUsuario devuelva 2.
     */
    @Test
    fun emailSinArroba() {

        viewModel.onChanged(
            "ejmplocorreo.com",
            "Ab@1234567")
        Assert.assertEquals(2, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo email mayor de 30 carácteres.
     * Se espera que el método registrarUsuario devuelva 2.
     */
    @Test
    fun emailLargo() {

        viewModel.onChanged(
            "ejmplo@correoiessanalbertomagno.com",
            "Ab@1234567")
        Assert.assertEquals(2, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo contraseña menor de 9 carácteres.
     * Se espera que el método registrarUsuario devuelva 3.
     */
    @Test
    fun passwordCorta() {

        viewModel.onChanged(
            "ejmplo@correo.com",
            "Ab@12345")
        Assert.assertEquals(3, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo contraseña > 30 carácteres
     * Se espera que el método registrarUsuario devuelva 3.
     */
    @Test
    fun passwordLarga() {

        viewModel.onChanged(
            "ejmplo@correo.com",
            "Ab@123456789123456789123456789123")
        Assert.assertEquals(3, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo contraseña sin símbolo
     * Se espera que el método registrarUsuario devuelva 4.
     */
    @Test
    fun passwordSinSimbolo() {

        viewModel.onChanged(
            "ejmplo@correo.com",
            "Ab12345678")
        Assert.assertEquals(4, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo contraseña sin letras mayúsculas
     * Se espera que el método registrarUsuario devuelva 5.
     */
    @Test
    fun passwordSinMayus() {

        viewModel.onChanged(
            "ejmplo@correo.com",
            "ab@12345678")
        Assert.assertEquals(5, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo contraseña sin letras minúsculas
     * Se espera que el método registrarUsuario devuelva 5.
     */
    @Test
    fun passwordSinMinus() {

        viewModel.onChanged(
            "ejmplo@correo.com",
            "AB@12345678")
        Assert.assertEquals(5, viewModel.loginUsuario())
    }

    /**
     * Prueba para campo contraseña sin numeros
     * Se espera que el método registrarUsuario devuelva 5.
     */
    @Test
    fun passwordSinNum() {

        viewModel.onChanged(
            "ejmplo@correo.com",
            "AB@aaaaaaa")
        Assert.assertEquals(5, viewModel.loginUsuario())
    }

}