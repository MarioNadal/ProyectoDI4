package com.iessanalberto.dam2.proyectodi.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amplifyframework.core.Amplify
import com.example.loginfactoriaproyectos.navigation.AppScreens
import com.iessanalberto.dam2.proyectodi.viewmodels.MFAScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.RegisterScreenUiState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MFAScreen(
    navController: NavController,
    mfaScreenViewModel: MFAScreenViewModel,
    registerScreenUiState: RegisterScreenUiState,
    text: String?
) {
    MFABodyContent(
        navController = navController, mfaScreenViewModel = MFAScreenViewModel(),
        registerScreenUiState = registerScreenUiState, text = text
    )
}

@Composable
fun MFABodyContent(
    navController: NavController,
    mfaScreenViewModel: MFAScreenViewModel,
    registerScreenUiState: RegisterScreenUiState,
    text: String?
) {
    //Datos guardados (código)
    val mfaUiState by mfaScreenViewModel.uiState.collectAsState()
    //Si el código de validación es correcto se pondrá a true
    var navigationHome = remember { mutableStateOf(false) }
    //Si el código de validación es incorrecto se pondrá a true
    var errorNavigationHome = remember { mutableStateOf(false) }
    val context = LocalContext.current
    //Variable pasada desde RegisterScreen de el usuario que se ha creado para poder verificarlo
    var usuario: String = ""

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Variable para expandir el menú de idiomas
        var expanded by remember { mutableStateOf(false) }
        //Idioma seleccionado
        var selectedLanguage by remember { mutableStateOf("Español") }
        //Estructura de menú para selectro de idioma
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { expanded = true }
        ) {
            Row(
                modifier = Modifier
                    .width(150.dp)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedLanguage,
                    fontWeight = FontWeight.Bold
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            //Menú de idiomas
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(150.dp)
                    .padding(16.dp)
            ) {
                //Idioma Español
                DropdownMenuItem(text = { Text(text = "Español") },
                    onClick = {
                        selectedLanguage = "Español"
                        expanded = false
                    })
                //Idioma Ingles
                DropdownMenuItem(text = { Text(text = "English") },
                    onClick = {
                        selectedLanguage = "English"
                        expanded = false
                    })
                //Idioma Francés
                DropdownMenuItem(text = { Text(text = "Français") },
                    onClick = {
                        selectedLanguage = "Français"
                        expanded = false
                    })
                //Idioma Aleman
                DropdownMenuItem(text = { Text(text = "Deutsch") },
                    onClick = {
                        selectedLanguage = "Deutsch"
                        expanded = false
                    })
                //Idioma Chino
                DropdownMenuItem(text = { Text(text = "中国人") },
                    onClick = {
                        selectedLanguage = "中国人"
                        expanded = false
                    })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        //Usuario coge el valor pasado desde RegisterScreen que es el usuario creado
        usuario = text.toString()
        //Campo de entrada del códifo de validación MFA
        OutlinedTextField(
            value = mfaUiState.mfaCodigo, onValueChange = { mfaScreenViewModel.onChanged(it) },
            label = {
                if (selectedLanguage.equals("Español")) {
                    Text(text = "Código")
                }
                if (selectedLanguage.equals("English")) {
                    Text(text = "Code")
                }
                if (selectedLanguage.equals("Français")) {
                    Text(text = "Code")
                }
                if(selectedLanguage.equals("Deutsch")){
                    Text(text = "Code")
                }
                if (selectedLanguage.equals("中国人")) {
                    Text(text = "代码")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        //Botón de validación del código enviado por correo
        Button(onClick = {
            //Error código con lonngitud incorrecta siendo distinta a 6
            if (mfaScreenViewModel.validarCodigo() == 1) {
                Toast.makeText(context, "Debe ser un código de 6 números.", Toast.LENGTH_SHORT)
                    .show()
            }
            //Comprobación de código
            else {
                //Confirmación para el usuario creado que los códigos son iguales
                Amplify.Auth.confirmSignUp(
                    usuario, mfaUiState.mfaCodigo,
                    { result ->
                        if (result.isSignUpComplete) {
                            Log.i("AuthQuickstart", "Confirm signUp succeeded")
                            navigationHome.value = true

                        } else {
                            Log.i("AuthQuickstart", "Confirm sign up not complete")
                            errorNavigationHome.value = true

                        }
                    },
                    { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
                )
            }
        })
        {
            if (selectedLanguage.equals("Español")) {
                Text(text = "Verificar")
            }
            if (selectedLanguage.equals("English")) {
                Text(text = "Verify")
            }
            if (selectedLanguage.equals("Français")) {
                Text(text = "Vérifier")
            }
            if(selectedLanguage.equals("Deutsch")){
                Text(text = "Verifizieren")
            }
            if (selectedLanguage.equals("中国人")) {
                Text(text = "核实")
            }
        }
        //Si el código es el correcto
        if (navigationHome.value) {
            //Mostraremos la pantalla con la información de la aplicacíon al usaurio que será validado
            navController.navigate(AppScreens.HomeScreen.route)
            navigationHome.value = false
        }
        //Si el código no es el correcto
        if (errorNavigationHome.value) {
            //Mostraremos al usaurio que el código no es correcto
            Toast.makeText(LocalContext.current, "Código no correcto", Toast.LENGTH_SHORT)
                .show()
            errorNavigationHome.value = false
        }
    }
}
