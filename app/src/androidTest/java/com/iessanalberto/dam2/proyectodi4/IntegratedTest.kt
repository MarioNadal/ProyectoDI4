package com.iessanalberto.dam2.proyectodi4

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.test.assertIsDisplayed

import androidx.compose.ui.test.assertTextEquals


import androidx.compose.ui.test.junit4.createComposeRule

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText

import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.loginfactoriaproyectos.navigation.AppScreens
import com.iessanalberto.dam2.proyectodi.screens.LoginScreen
import com.iessanalberto.dam2.proyectodi.screens.RegisterScreen
import com.iessanalberto.dam2.proyectodi.viewmodels.LoginScreenViewModel


import com.iessanalberto.dam2.proyectodi.viewmodels.RegisterScreenViewModel
import com.iessanalberto.dam2.proyectodi4.R
import org.junit.Rule
import org.junit.Test



class IntegratedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testDropdownMenuIdiomaIngles() {
        // Este test verifica el funcionamiento del menú desplegable de idiomas.
        // Se crea un menú con opciones de idioma español e inglés.
        // Se realiza un clic en la opción de idioma inglés y se verifica la actualización correcta.
        // También se asegura de que la opción anterior desaparezca después del cambio.

        rule.setContent {
            var expanded = false
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
                        expanded = false
                    })
                //Idioma Ingles
                DropdownMenuItem(modifier = Modifier.testTag("opcionIngles"),
                    text = { Text(text = "English") },
                    onClick = {
                        expanded = false
                    })

                rule.onNodeWithTag("opcionEspañol").performClick()
                rule.onNodeWithTag("opcionIngles").assertExists()
                rule.onNodeWithTag("opcionIngles").performClick()
                rule.onNodeWithTag("opcionEspañol").assertDoesNotExist()
                rule.onNodeWithTag("opcionIngles").assertExists()
            }
        }
    }

    @Test
    fun tesOutlainedTextFixeldFunciona() {
        // Este test verifica el funcionamiento del componente OutlinedTextField.
        // Se simula el ingreso de texto y se verifica que el componente esté presente y tenga el valor esperado.
        // En este caso, se utiliza un ViewModel asociado para manejar el estado del campo de texto.
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
        // Este test verifica el cambio de idioma en un menú desplegable y en el Text.
        // Se cambia el idioma de español a inglés y se verifica que el texto asociado se actualice correctamente.
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
                else if(selectedLanguage.equals("English")) {
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
    fun passwordVisibleFunciona() {
        // Este test verifica el comportamiento de la visibilidad de la contraseña en un campo de contraseña.
        // Se verifica que al hacer clic en un botón de visibilidad, la contraseña se muestre o se oculte correctamente.

        rule.setContent {
            var passwordVisible by remember { mutableStateOf(false) }
            var otfValue by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.testTag("otf"),
                value = otfValue,
                onValueChange = { otfValue = it },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.testTag("iconButton"),
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            modifier = Modifier.testTag("icon"),
                            painter = painterResource(R.drawable.ojocontrasena),
                            contentDescription = "Toggle password visibility"

                        )
                    }
                }
            )
        }
        rule.onNodeWithTag("iconButton").assertIsDisplayed()
        rule.onNodeWithTag("otf").performTextInput("password123")
        rule.onNodeWithTag("otf").assertTextEquals("•••••••••••")
        rule.onNodeWithTag("iconButton").performClick()
        rule.onNodeWithTag("otf").assertTextEquals("password123")
    }
    @Test
    fun buttonYTextFunciona(){
        // Este test verifica la interacción entre un botón y un texto.
        // Se verifica que el texto inicial sea correcto.
        // Luego, se hace clic en el botón y se verifica que el texto se actualice correctamente.

        rule.setContent{
            var value by remember { mutableStateOf(1) }
            val context = LocalContext.current
            Button(  modifier = Modifier.testTag("button"),onClick = {
                    value ++
            }){
                Text(text = "Mostrar Toast")

            }
            Text(modifier = Modifier.testTag("text"), text = value.toString())

        }
        rule.onNodeWithTag("text").assertIsDisplayed()
        rule.onNodeWithTag("text").assertTextEquals("1")
        rule.onNodeWithTag("button").assertIsDisplayed().performClick()
        rule.onNodeWithTag("text").assertTextEquals("2")

    }

}