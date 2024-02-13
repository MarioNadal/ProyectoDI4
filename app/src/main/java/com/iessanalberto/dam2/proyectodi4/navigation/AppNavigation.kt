package com.example.loginfactoriaproyectos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iessanalberto.dam2.proyectodi.screens.ContrasenaOlvidadaScreen
import com.iessanalberto.dam2.proyectodi.screens.HomeScreen
import com.iessanalberto.dam2.proyectodi.screens.LoginScreen
import com.iessanalberto.dam2.proyectodi.screens.MFAScreen
import com.iessanalberto.dam2.proyectodi.screens.RegisterScreen
import com.iessanalberto.dam2.proyectodi.viewmodels.ConfirmacionCambioContrasenaScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.ContrasenaOlvidadaScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.LoginScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.MFAScreenViewModel
import com.iessanalberto.dam2.proyectodi.viewmodels.RegisterScreenUiState
import com.iessanalberto.dam2.proyectodi.viewmodels.RegisterScreenViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    //Este es el gestor de rutas para poder ir a la pantalla que queramos en cualquier momento
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        //Esta es la pantalla del Login donde el usuario podrá verificarse para entrar a la aplicación
        composable(route = AppScreens.LoginScreen.route) { LoginScreen(navController, loginScreenViewModel = LoginScreenViewModel()) }
        //Esta es la pantalla del Registro donde el usuario podrá registrarse si no tiene cuenta
        composable(route = AppScreens.RegisterScreen.route) { RegisterScreen(navController, registerScreenViewModel = RegisterScreenViewModel()) }
        //Esta es la pantalla del código de verificación MFA donde el usuario podrá verificar su correo electróncio y por lo tanto su usuario
        composable(route = AppScreens.MFAScreen.route + "/{text}",
            arguments = listOf(navArgument(name = "text") {type= NavType.StringType})
        ){ MFAScreen(navController, mfaScreenViewModel = MFAScreenViewModel(), registerScreenUiState = RegisterScreenUiState(),text = it.arguments?.getString("text")) }
        //Esta es la pantalla del Home donde el usuario podrá ver toda la información sobre la aplicación una vez se haya verificadoweq
        composable(route = AppScreens.HomeScreen.route) { HomeScreen(navController)}
        //Esta es la pantalla de usuario
        composable(route = AppScreens.ContrasenaOlvidadaScreen.route) { ContrasenaOlvidadaScreen(
            navController = navController,
            contrasenaOlvidadaScreenViewModel = ContrasenaOlvidadaScreenViewModel(),
            confirmacionCambioContrasenaScreenViewModel = ConfirmacionCambioContrasenaScreenViewModel()
        )}
    }
}