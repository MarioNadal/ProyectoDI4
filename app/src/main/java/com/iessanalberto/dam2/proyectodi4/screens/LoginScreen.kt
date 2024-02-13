package com.iessanalberto.dam2.proyectodi.screens

import android.content.res.Configuration
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.core.Amplify
import com.example.loginfactoriaproyectos.navigation.AppScreens
import com.iessanalberto.dam2.proyectodi.viewmodels.LoginScreenViewModel
import com.iessanalberto.dam2.proyectodi4.R
import java.util.Locale

@Composable
fun LoginScreen(navController: NavController, loginScreenViewModel: LoginScreenViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginScreenBodyContent(navController, loginScreenViewModel)
    }
}

@Composable
fun LoginScreenBodyContent(
    navController: NavController,
    loginScreenViewModel: LoginScreenViewModel
) {
    //Datos guardados (correo y contraseña)
    val loginScreenuiState by loginScreenViewModel.uiState.collectAsState()
    //Número de intentos para inicar sesión
    var numIntentos by remember { mutableStateOf(3) }
    //Si se superan el número de intentos esta variable se pondrá en true
    var mostrarAlertDialogNumIntentosSuperado by remember { mutableStateOf(false) }
    val context = LocalContext.current
    //Esta variable sirve para cambiar la contraseña de puntos a letras para poder ver lo que escribes
    var passwordVisible by remember { mutableStateOf(false) }
    //Al poner esta variabla a true se habrá hecho logIn correctamente
    var navigationHome = remember { mutableStateOf(false) }
    //Al poner esta variable a true se habrá hehco un logIn incorrecto
    var errorNavigationHome = remember { mutableStateOf(false) }

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
                .testTag("MenuLenguaje")

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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Spacer(modifier = Modifier.height(50.dp))
        //Campo de entrada del correo
        OutlinedTextField(value = loginScreenuiState.correo,
            onValueChange = {
                loginScreenViewModel.onChanged(
                    correoUi = it,
                    passwordUi = loginScreenuiState.password
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
        Spacer(modifier = Modifier.height(20.dp))
        //Campo de entrada de la contraseña
        OutlinedTextField(value = loginScreenuiState.password,
            onValueChange = {
                loginScreenViewModel.onChanged(
                    correoUi = loginScreenuiState.correo,
                    passwordUi = it
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
            //Aqui se indica cuando debe ser visible la contraseña y cuando estar en modo puntitos para que no se vea
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            //Este es el botón que sale al lado del campo de entrada para poder cambiar el modo de vista de la contraseña
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(R.drawable.ojocontrasena),
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        //Botón para hacer logIn
        Button(onClick = {
            if (numIntentos > 0) {
                //Error con algun campo vacio
                if (loginScreenViewModel.loginUsuario() == 1) {
                    numIntentos--
                    Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_SHORT)
                        .show()
                    Toast.makeText(
                        context,
                        "Le quedan " + numIntentos + " intentos restantes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Error con el correo
                else if (loginScreenViewModel.loginUsuario() == 2) {
                    numIntentos--
                    Toast.makeText(
                        context,
                        "El correo debe contener un @ y tener menos de 30 carácteres",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        context,
                        "Le quedan " + numIntentos + " intentos restantes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Error con la longitud de la contraseña
                else if (loginScreenViewModel.loginUsuario() == 3) {
                    numIntentos--
                    Toast.makeText(
                        context,
                        "La contraseña debe tener entre 9 y 30 carácteres",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        context,
                        "Le quedan " + numIntentos + " intentos restantes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Error en la contraseña ya que no contiene ningún simbolo
                else if (loginScreenViewModel.loginUsuario() == 4) {
                    numIntentos--
                    Toast.makeText(
                        context,
                        "La contraseña no contiene un simbolo (@,#,$,% o &)",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        context,
                        "Le quedan " + numIntentos + " intentos restantes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Error en la contraseña ya que no contiene como mínimo una mayuscula, una minuscula y un número
                else if (loginScreenViewModel.loginUsuario() == 5) {
                    numIntentos--
                    Toast.makeText(
                        context,
                        "La contraseña debe tener una mayuscula, una minuscula y un número",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        context,
                        "Le quedan " + numIntentos + " intentos restantes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Comporbación de usuario contra los usuarios de AWS con Auth y Cognito
                else {
                    //Comprobamos si el correo y la contraseña estan ya creados y son usuarios válidos
                    Amplify.Auth.signIn(loginScreenuiState.correo, loginScreenuiState.password,
                        { result ->
                            if (result.isSignedIn) {
                                Log.i("AuthQuickstart", "Sign in complete")
                                navigationHome.value = true
                            } else {
                                numIntentos--
                                Log.i("AuthQuickstart", "Sign in not complete")
                                errorNavigationHome.value = true
                            }
                        },
                        { Log.e("AuthQuickstart", "Failed to sign in", it) }
                    )
                }
                //Si el número de intentos llega a 0, se ha superado el número de intentos
            } else {
                mostrarAlertDialogNumIntentosSuperado = true
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
        //Botón para hacer Sign Out a una cuenta ya abierta con anterioridad y se ha quedado abierta
        Button(onClick = {
            Amplify.Auth.signOut { signOutResult ->
                when (signOutResult) {
                    is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
                        // Sign Out completed fully and without errors.
                        Log.i("AuthQuickStart", "Signed out successfully")
                    }

                    is AWSCognitoAuthSignOutResult.PartialSignOut -> {
                        // Sign Out completed with some errors. User is signed out of the device.
                        signOutResult.hostedUIError?.let {
                            Log.e("AuthQuickStart", "HostedUI Error", it.exception)
                            // Optional: Re-launch it.url in a Custom tab to clear Cognito web session.

                        }
                        signOutResult.globalSignOutError?.let {
                            Log.e("AuthQuickStart", "GlobalSignOut Error", it.exception)
                            // Optional: Use escape hatch to retry revocation of it.accessToken.
                        }
                        signOutResult.revokeTokenError?.let {
                            Log.e("AuthQuickStart", "RevokeToken Error", it.exception)
                            // Optional: Use escape hatch to retry revocation of it.refreshToken.
                        }
                    }

                    is AWSCognitoAuthSignOutResult.FailedSignOut -> {
                        // Sign Out failed with an exception, leaving the user signed in.
                        Log.e("AuthQuickStart", "Sign out Failed", signOutResult.exception)
                    }
                }
            }
        }
        ) {
            if (selectedLanguage.equals("Español")) {
                Text(text = "Desconectar")
            }
            if (selectedLanguage.equals("English")) {
                Text(text = "Sign Out")
            }
            if (selectedLanguage.equals("Français")) {
                Text(text = "Déconnecter")
            }
            if(selectedLanguage.equals("Deutsch")){
                Text(text = "Trennen")
            }
            if (selectedLanguage.equals("中国人")) {
                Text(text = "断开")
            }
        }
        //Si el signIn es correcto
        if (navigationHome.value) {
            //Mostramos al usuario la pantalla Home con todos los datos de la aplicación (API)
            navController.navigate(AppScreens.HomeScreen.route)
            navigationHome.value = false
        }
        //Si el singIn es incorrecto
        if (errorNavigationHome.value) {
            //Mostramos al usuario que algo no es correcto en el logIn
            Toast.makeText(
                LocalContext.current,
                "Usuario o contraseña incorrecto",
                Toast.LENGTH_SHORT
            ).show()
            errorNavigationHome.value = false
        }
        //Si el número de intentos se ha superado le mostramos la advertencia y le echamos de la app
        if (mostrarAlertDialogNumIntentosSuperado) {
            AlertDialog(
                modifier = Modifier.testTag("Alert"),
                title = { Text(text = "Superado número de intentos") },
                text = { Text(text = "Has superado el número de intentos para iniciar sesión.") },
                onDismissRequest = { System.exit(0) },
                confirmButton = {
                    TextButton(onClick = { System.exit(0) }) {
                        Text(text = "OK")
                    }
                })
        }
        Spacer(modifier = Modifier.height(15.dp))
        //Opción de recuperar la contraseña si se le ha olvidado al usaurio
        if (selectedLanguage.equals("Español")) {
            ClickableText(text = AnnotatedString("He olvidado mi contraseña"),
                onClick = {
                    navController.navigate(AppScreens.ContrasenaOlvidadaScreen.route)
                })
        }
        if (selectedLanguage.equals("English")) {
            ClickableText(text = AnnotatedString("I forgot my password"),
                onClick = {
                    navController.navigate(AppScreens.ContrasenaOlvidadaScreen.route)
                })
        }
        if (selectedLanguage.equals("Français")) {
            ClickableText(text = AnnotatedString("J'ai oublié mon mot de passe"),
                onClick = {
                    navController.navigate(AppScreens.ContrasenaOlvidadaScreen.route)
                })
        }
        if (selectedLanguage.equals("Deutsch")) {
            ClickableText(text = AnnotatedString("Ich habe mein Passwort vergessen"),
                onClick = {
                    navController.navigate(AppScreens.ContrasenaOlvidadaScreen.route)
                })
        }
        if (selectedLanguage.equals("中国人")) {
            ClickableText(text = AnnotatedString("我忘记了我的密码"),
                onClick = {
                    navController.navigate(AppScreens.ContrasenaOlvidadaScreen.route)
                })
        }

        Spacer(modifier = Modifier.height(15.dp))
        //Opción de crear una nueva cuenta si el usuario no tiene ninguna
        if (selectedLanguage.equals("Español")) {
            ClickableText(text = AnnotatedString("Registrarse"),
                onClick = {
                    navController.navigate(AppScreens.RegisterScreen.route)
                })
        }
        if (selectedLanguage.equals("English")) {
            ClickableText(text = AnnotatedString("Check in"),
                onClick = {
                    navController.navigate(AppScreens.RegisterScreen.route)
                })
        }
        if (selectedLanguage.equals("Français")) {
            ClickableText(text = AnnotatedString("Enregistrement"),
                onClick = {
                    navController.navigate(AppScreens.RegisterScreen.route)
                })
        }
        if (selectedLanguage.equals("Deutsch")) {
            ClickableText(text = AnnotatedString("Einchecken"),
                onClick = {
                    navController.navigate(AppScreens.ContrasenaOlvidadaScreen.route)
                })
        }
        if (selectedLanguage.equals("中国人")) {
            ClickableText(text = AnnotatedString("报到"),
                onClick = {
                    navController.navigate(AppScreens.RegisterScreen.route)
                })
        }
    }
}


