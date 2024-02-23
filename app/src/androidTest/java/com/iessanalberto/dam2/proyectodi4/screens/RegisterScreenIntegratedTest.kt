package com.iessanalberto.dam2.proyectodi4.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.loginfactoriaproyectos.navigation.AppScreens
import com.iessanalberto.dam2.proyectodi.viewmodels.RegisterScreenViewModel
import org.junit.Rule
import org.junit.Test



class RegisterScreenIntegratedTest {
    @get:Rule
    val rule = createComposeRule()
    //Inicialización del ViewModel para ser utilizado en las pruebas


    @Test
    fun testDropdownMenuIdiomaIngles() {
        //Variable para expandir el menú de idiomas

        rule.setContent {
            var expanded = false
            //Idioma seleccionado
            var selectedLanguage: String = "Español"
            //Menú de idiomas
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = true },
                modifier = Modifier
                    .width(150.dp)
                    .padding(16.dp)
            ) {
                //Idioma Español
                DropdownMenuItem(modifier = Modifier.testTag("opcionEspañol"),
                    text = { Text(text = "Español") },
                    onClick = {
                        selectedLanguage = "Español"
                        expanded = false
                    })
                //Idioma Ingles
                DropdownMenuItem(modifier = Modifier.testTag("opcionIngles"),
                    text = { Text(text = "English") },
                    onClick = {
                        selectedLanguage = "English"
                        expanded = false
                    })
                //Idioma Francés

                rule.onNodeWithTag("opcionEspañol").performClick()
                rule.onNodeWithTag("opcionIngles").assertExists()
                rule.onNodeWithTag("opcionIngles").performClick()
                rule.onNodeWithTag("opcionEspañol").assertDoesNotExist()
                rule.onNodeWithTag("opcionIngles").assertExists()
            }
        }
    }

    @Test
    fun tesOutlainedFixedFunciona() {
        rule.setContent {
            val registerScreenViewModel = RegisterScreenViewModel()
            val registerUiState by registerScreenViewModel.uiState.collectAsState()
            OutlinedTextField(value = registerUiState.registroCorreo, onValueChange = {
                registerScreenViewModel.onChanged(
                    registerUiState.registroUsuario,
                    it,
                    registerUiState.registroPassword,
                    registerUiState.registroConfPasw
                )
                rule.onNodeWithTag("input").assertIsDisplayed()
                rule.onNodeWithTag("input").assertTextEquals("")
                rule.onNodeWithTag("input").performTextInput("example@example.com")
                rule.onNodeWithTag("input").assertTextEquals("example@example.com")


            }, label = { "Español" },
                modifier = Modifier.testTag("input")
            )

        }


    }

    @Test
    fun cambiaIdiomaFunciona() {
        rule.setContent {

            var expanded = false
            //Idioma seleccionado
            var selectedLanguage: String = "Español"
            //Menú de idiomas
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = true },
                modifier = Modifier
                    .width(150.dp)
                    .padding(16.dp)
            ) {
                //Idioma Español
                DropdownMenuItem(modifier = Modifier.testTag("opcionEspañol"),
                    text = { Text(text = "Español") },
                    onClick = {
                        selectedLanguage = "Español"
                        expanded = false
                    })
                //Idioma Ingles
                DropdownMenuItem(modifier = Modifier.testTag("opcionIngles"),
                    text = { Text(text = "English") },
                    onClick = {
                        selectedLanguage = "English"
                        expanded = false
                    })
                if (selectedLanguage.equals("Español")) {
                    Text(modifier = Modifier.testTag("textoEspañol"), text = "Confirmar contraseña")
                }
                else if (selectedLanguage.equals("English")) {
                    Text(modifier = Modifier.testTag("textoIngles"), text = "Confirm Password")
                }

                    rule.onNodeWithTag("opcionEspañol").performClick()
                    rule.onNodeWithTag("opcionIngles").performClick()
                    rule.onNodeWithTag("textoEspañol").assertTextEquals("Confirmar contraseña")
                    rule.onNodeWithTag("textoIngles").assertTextEquals("Confirm Password")
            }
        }
    }
    @Test
    fun redirigirMFAScreenSiRegistroExitoso(){
        rule.setContent {
            val registerScreenViewModel = RegisterScreenViewModel()
            val registerUiState by registerScreenViewModel.uiState.collectAsState()
            var navController = rememberNavController()
            var registroValido = true
            Button(modifier = Modifier.testTag("button"), onClick = {registroValido = true }) {

                if (registroValido) {
                    //Se mostrará al usuario la pantalla de verificación MFA y se mandará el usuario para poder usarlo en la pantalla MFAScreen
                    navController.navigate(route = AppScreens.MFAScreen.route + "/" + registerUiState.registroUsuario)
                    registroValido = false
                }
            rule.onNodeWithTag("button").performClick()

            }



        }

    }
}