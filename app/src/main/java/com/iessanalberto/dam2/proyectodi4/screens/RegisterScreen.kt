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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.loginfactoriaproyectos.navigation.AppScreens
import com.iessanalberto.dam2.proyectodi.viewmodels.RegisterScreenViewModel
import com.iessanalberto.dam2.proyectodi4.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, registerScreenViewModel: RegisterScreenViewModel) {
    RegisterScreenBodyContent(navController = navController,registerScreenViewModel = RegisterScreenViewModel())
}


@Composable
fun RegisterScreenBodyContent(
    navController: NavController,
    registerScreenViewModel: RegisterScreenViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Datos guardados (correo, usuario, contraseña y confirmación de contraseña)
        val registerUiState by registerScreenViewModel.uiState.collectAsState()
        val context = LocalContext.current
        //Si el registro es correcto la variable se pondrá a true
        var navigationMFA = remember { mutableStateOf(false) }
        //Si el registro es incorrecto la variable se pondrá a true
        var errorNavigationMFA = remember { mutableStateOf(false) }
        //Esta variable sirve para cambiar la contraseña de puntos a letras para poder ver lo que escribes
        var passwordVisible by remember { mutableStateOf(false) }

        //Variable para expandir el menú de idiomas
        var expanded by remember { mutableStateOf(false) }
        //Idioma seleccionado
        var selectedLanguage by remember { mutableStateOf("Español") }
        //Estructura de menú para selectro de idioma
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

        //Campo de entrada del correo
        OutlinedTextField(value = registerUiState.registroCorreo, onValueChange = {
            registerScreenViewModel.onChanged(
                registerUiState.registroUsuario,
                it,
                registerUiState.registroPassword,
                registerUiState.registroConfPasw
            )
        }, label = {
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
        Spacer(modifier = Modifier.height(50.dp))
        //Campo de entrada del usuario (para el usuario es una confirmación del correo electrónico)
        OutlinedTextField(value = registerUiState.registroUsuario, onValueChange = {
            registerScreenViewModel.onChanged(
                it,
                registerUiState.registroCorreo,
                registerUiState.registroPassword,
                registerUiState.registroConfPasw
            )
        }, label = {
            if (selectedLanguage.equals("Español")) {
                Text(text = "Confirmar correo")
            }
            if (selectedLanguage.equals("English")) {
                Text(text = "Mail confirmation")
            }
            if (selectedLanguage.equals("Français")) {
                Text(text = "Confirmation de l'émail")
            }
            if(selectedLanguage.equals("Deutsch")){
                Text(text = "Post bestätigen")
            }
            if (selectedLanguage.equals("中国人")) {
                Text(text = "邮件确认")
            }
        })
        Spacer(modifier = Modifier.height(50.dp))
        //Campo de entrada de la contraseña
        OutlinedTextField(value = registerUiState.registroPassword,
            onValueChange = {
                registerScreenViewModel.onChanged(
                    registerUiState.registroUsuario,
                    registerUiState.registroCorreo,
                    it,
                    registerUiState.registroConfPasw
                )
            },
            label = {
                if (selectedLanguage.equals("Español")) {
                    Text(text = "Contraseña")
                }
                if (selectedLanguage.equals("English")) {
                    Text(text = "Password")
                }
                if (selectedLanguage.equals("Français")) {
                    Text(text = "Mot de passe")
                }
                if(selectedLanguage.equals("Deutsch")){
                    Text(text = "Passwort")
                }
                if (selectedLanguage.equals("中国人")) {
                    Text(text = "密码")
                }
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
            })
        Spacer(modifier = Modifier.height(50.dp))
        //Campo de entrada de confirmación de la contraseña
        OutlinedTextField(value = registerUiState.registroConfPasw,
            onValueChange = {
                registerScreenViewModel.onChanged(
                    registerUiState.registroUsuario,
                    registerUiState.registroCorreo,
                    registerUiState.registroPassword,
                    it
                )
            },
            label = {
                if (selectedLanguage.equals("Español")) {
                    Text(text = "Confirmar contraseña")
                }
                if (selectedLanguage.equals("English")) {
                    Text(text = "Confirm Password")
                }
                if (selectedLanguage.equals("Français")) {
                    Text(text = "Confirmer mot de passe")
                }
                if(selectedLanguage.equals("Deutsch")){
                    Text(text = "Bestätigen Sie Ihr Passwort")
                }
                if (selectedLanguage.equals("中国人")) {
                    Text(text = "确认密码")
                }
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
            })
        Spacer(modifier = Modifier.height(50.dp))
        //Botón de Registro
        Button(onClick = {
            //Error con algun campo vacio
            if (registerScreenViewModel.registrarUsuario() == 1) {
                Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
            //Error en la longitud del correo siendo mayor a 30 carácteres
            else if (registerScreenViewModel.registrarUsuario() == 2) {
                Toast.makeText(
                    context, "El correo no puede superar los 30 carácteres", Toast.LENGTH_SHORT
                ).show()
            }
            //Error en el correo sin contener el simbolo @
            else if (registerScreenViewModel.registrarUsuario() == 3) {
                Toast.makeText(context, "El corre debe contener un @", Toast.LENGTH_SHORT).show()
            }
            //Error en la contraseña con la longitud siendo menor a 9 o mayor a 30
            else if (registerScreenViewModel.registrarUsuario() == 4) {
                Toast.makeText(
                    context,
                    "La contraseña no puede tener menos de 9 carácteres ni más de 30",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Error en la contraseña sin ningún simbolo
            else if (registerScreenViewModel.registrarUsuario() == 5) {
                Toast.makeText(
                    context,
                    "La contraseña no contiene un simbolo (@,#,$,% o &)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Error en la contraseña ya que no contiene como mínimo una mayuscula, una minuscula y un número
            else if (registerScreenViewModel.registrarUsuario() == 6) {
                Toast.makeText(
                    context,
                    "La contraseña debe tener una mayuscula, una minuscula y un número",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Error en la confirmación de contraseña sin que sea igual que la contraseña
            else if (registerScreenViewModel.registrarUsuario() == 7) {
                Toast.makeText(context, "La contraseña no coincide", Toast.LENGTH_SHORT).show()
            }
            //Error en el usuario (confirmación de correo) sin ser igual que el correo
            else if (registerScreenViewModel.registrarUsuario() == 8) {
                Toast.makeText(context, "Los correos no son iguales", Toast.LENGTH_SHORT).show()
            }
            //Registro
            else {
                //variable que manda al correo el código MFA para validar la cuenta
                val options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), registerUiState.registroCorreo)
                    .build()
                //Registro de la cuenta con usuario y contraseña
                Amplify.Auth.signUp(registerUiState.registroUsuario,
                    registerUiState.registroConfPasw,
                    options,
                    { result ->
                        Log.i("AuthQuickStart", "Result: $result")
                        navigationMFA.value = true
                    },
                    { error -> Log.e("AuthQuickStart", "Sign up failed", error) })
            }
        }) {
            if (selectedLanguage.equals("Español")) {
                Text(text = "Registratse")
            }
            if (selectedLanguage.equals("English")) {
                Text(text = "Check in")
            }
            if (selectedLanguage.equals("Français")) {
                Text(text = "Enregistrement")
            }
            if(selectedLanguage.equals("Deutsch")){
                Text(text = "Einchecken")
            }
            if (selectedLanguage.equals("中国人")) {
                Text(text = "报到")
            }
        }
        //Si el registro es correcto
        if (navigationMFA.value) {
            //Se mostrará al usuario la pantalla de verificación MFA y se mandará el usuario para poder usarlo en la pantalla MFAScreen
            navController.navigate(route = AppScreens.MFAScreen.route + "/" + registerUiState.registroUsuario)
            navigationMFA.value = false
        }
        //Si el registro es incorrecto
        if (errorNavigationMFA.value) {
            //Mostraremos al usuario que el usuario no es válido, no se puede registrar
            Toast.makeText(
                context, "Error en el registro, usuario ya registrado", Toast.LENGTH_SHORT
            ).show()
            errorNavigationMFA.value = false
        }
    }
}