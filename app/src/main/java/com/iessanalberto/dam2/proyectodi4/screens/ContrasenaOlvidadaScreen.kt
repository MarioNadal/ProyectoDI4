package com.iessanalberto.dam2.proyectodi.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amplifyframework.core.Amplify
import com.example.loginfactoriaproyectos.navigation.AppScreens
import com.iessanalberto.dam2.proyectodi.viewmodels.ConfirmacionCambioContrasenaScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.ContrasenaOlvidadaScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.ContrasenaOlvidadaUiState
import com.iessanalberto.dam2.proyectodi4.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContrasenaOlvidadaScreen(
    navController: NavController,
    contrasenaOlvidadaScreenViewModel: ContrasenaOlvidadaScreenViewModel,
    confirmacionCambioContrasenaScreenViewModel: ConfirmacionCambioContrasenaScreenViewModel
) {
    ContrasenaOlvidadaScreenBodyContent(
        navController, contrasenaOlvidadaScreenViewModel,
        confirmacionCambioContrasenaScreenViewModel = confirmacionCambioContrasenaScreenViewModel
    )
}

@Composable
fun ContrasenaOlvidadaScreenBodyContent(
    navController: NavController,
    contrasenaOlvidadaScreenViewModel: ContrasenaOlvidadaScreenViewModel,
    confirmacionCambioContrasenaScreenViewModel: ConfirmacionCambioContrasenaScreenViewModel
) {
    val contrasenaOlvidadaScreenuiState by contrasenaOlvidadaScreenViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var navigationCambiarContrasena = remember { mutableStateOf(false) }
    var navigationCambiarContrasenaEscribir = remember { mutableStateOf(false) }
    var errorNavigationCambiarContrasena = remember { mutableStateOf(false) }
    val confirmacionCambioContraseñaScreenuiState by confirmacionCambioContrasenaScreenViewModel.uiState.collectAsState()
    var navigationHome = remember { mutableStateOf(false) }
    //Esta variable sirve para cambiar la contraseña de puntos a letras para poder ver lo que escribes
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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

        Spacer(modifier = Modifier.height(50.dp))
        //Campo de entrada de texto de Usuario
        OutlinedTextField(
            value = contrasenaOlvidadaScreenuiState.username,
            onValueChange = {
                contrasenaOlvidadaScreenViewModel.onChanged(
                    usernameUi = it
                )
            },
            label = {
                if (selectedLanguage.equals("Español")) {
                    Text(text = "Correo")
                }
                if (selectedLanguage.equals("English")) {
                    Text(text = "Mail")
                }
                if (selectedLanguage.equals("Français")) {
                    Text(text = "Mail")
                }
                if(selectedLanguage.equals("Deutsch")){
                    Text(text = "Post")
                }
                if (selectedLanguage.equals("中国人")) {
                    Text(text = "邮件")
                }
            })
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            //Campo en blanco
            if (contrasenaOlvidadaScreenViewModel.contrasenaOlvidada() == 1) {
                Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
            //Error con el correo
            else if (contrasenaOlvidadaScreenViewModel.contrasenaOlvidada() == 2) {
                Toast.makeText(
                    context,
                    "El correo debe contener un @ y tener menos de 30 carácteres",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Amplify.Auth.resetPassword(contrasenaOlvidadaScreenuiState.username,
                    {
                        Log.i("AuthQuickstart", "Password reset OK: $it")
                        navigationCambiarContrasena.value = true
                    },
                    {
                        Log.e("AuthQuickstart", "Password reset failed", it)
                        errorNavigationCambiarContrasena.value = true
                    }
                )
            }
        }) {
            if (selectedLanguage.equals("Español")) {
                Text(text = "Obtener código")
            }
            if (selectedLanguage.equals("English")) {
                Text(text = "Get code")
            }
            if (selectedLanguage.equals("Français")) {
                Text(text = "Obtenir le code")
            }
            if(selectedLanguage.equals("Deutsch")){
                Text(text = "Code erhalten")
            }
            if (selectedLanguage.equals("中国人")) {
                Text(text = "获取代码")
            }
        }
        if (navigationCambiarContrasena.value) {
            Toast.makeText(context, "Código de verificación enviado", Toast.LENGTH_SHORT).show()
            navigationCambiarContrasena.value = false
            navigationCambiarContrasenaEscribir.value = true
        }
        if (errorNavigationCambiarContrasena.value) {
            Toast.makeText(context, "Error en el correo", Toast.LENGTH_SHORT).show()
            errorNavigationCambiarContrasena.value = false
        }
        Spacer(modifier = Modifier.height(40.dp))
        //Campo de entrada de la nueva contraseña
        OutlinedTextField(
            value = confirmacionCambioContraseñaScreenuiState.password,
            enabled = navigationCambiarContrasenaEscribir.value,
            onValueChange = {
                confirmacionCambioContrasenaScreenViewModel.onChanged(
                    it,
                    confirmacionCambioContraseñaScreenuiState.codigoverif
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(R.drawable.ojocontrasena),
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            label = {
                if (selectedLanguage.equals("Español")) {
                    Text(text = "Nueva contraseña")
                }
                if (selectedLanguage.equals("English")) {
                    Text(text = "New password")
                }
                if (selectedLanguage.equals("Français")) {
                    Text(text = "Nouveau mot de passe")
                }
                if(selectedLanguage.equals("Deutsch")){
                    Text(text = "Neues Kennwort")
                }
                if (selectedLanguage.equals("中国人")) {
                    Text(text = "新密码")
                }
            })
        Spacer(modifier = Modifier.height(20.dp))
        //Campo de entrada del código de verificación
        OutlinedTextField(
            value = confirmacionCambioContraseñaScreenuiState.codigoverif,
            enabled = navigationCambiarContrasenaEscribir.value,
            onValueChange = {
                confirmacionCambioContrasenaScreenViewModel.onChanged(
                    confirmacionCambioContraseñaScreenuiState.password,
                    it
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            label = {
                if (selectedLanguage.equals("Español")) {
                    Text(text = "Código de verificación")
                }
                if (selectedLanguage.equals("English")) {
                    Text(text = "Verification code")
                }
                if (selectedLanguage.equals("Français")) {
                    Text(text = "Le code de vérification")
                }
                if(selectedLanguage.equals("Deutsch")){
                    Text(text = "Bestätigungscode")
                }
                if (selectedLanguage.equals("中国人")) {
                    Text(text = "验证码")
                }
            })
        Spacer(modifier = Modifier.height(40.dp))
        //Boton para verificar usuario
        Button(onClick = {
            if (confirmacionCambioContrasenaScreenViewModel.confirmacionContrasena() == 1) {
                Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
            //Error con el correo
            else if (confirmacionCambioContrasenaScreenViewModel.confirmacionContrasena() == 2) {
                Toast.makeText(
                    context,
                    "La contraseña debe tener un mínimo de 9 carácteres y un máximo de 30",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Contraseña sin simbolos
            else if (confirmacionCambioContrasenaScreenViewModel.confirmacionContrasena() == 3) {
                Toast.makeText(
                    context,
                    "La contraseña no contiene un simbolo (@,#,$,% o &)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Contraseña de tipo válido una mayusucla, una minuscula y un número
            else if (confirmacionCambioContrasenaScreenViewModel.confirmacionContrasena() == 4) {
                Toast.makeText(
                    context,
                    "La contraseña debe tener una mayuscula, una minuscula y un número",
                    Toast.LENGTH_SHORT
                ).show()
                //Código de verificación con longitud incorrecta de carácteres
            } else if (confirmacionCambioContrasenaScreenViewModel.confirmacionContrasena() == 5) {
                Toast.makeText(
                    context,
                    "El código de verificación debe tener 6 números",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Recuperación de la contraseña del usuario
            else {
                Amplify.Auth.confirmResetPassword(contrasenaOlvidadaScreenuiState.username,
                    confirmacionCambioContraseñaScreenuiState.password,
                    confirmacionCambioContraseñaScreenuiState.codigoverif,
                    {
                        Log.i("AuthQuickstart", "New password confirmed")
                        navigationHome.value = true
                    },
                    { Log.e("AuthQuickstart", "Failed to confirm password reset", it) }
                )
            }
        }) {
            if (selectedLanguage.equals("Español")) {
                Text(text = "Acceder")
            }
            if (selectedLanguage.equals("English")) {
                Text(text = "To access")
            }
            if (selectedLanguage.equals("Français")) {
                Text(text = "Accéder")
            }
            if(selectedLanguage.equals("Deutsch")){
                Text(text = "Zugreifen")
            }
            if (selectedLanguage.equals("中国人")) {
                Text(text = "访问")
            }
        }
        if (navigationHome.value) {
            navController.navigate(AppScreens.LoginScreen.route)
            navigationHome.value = false
        }
    }
}