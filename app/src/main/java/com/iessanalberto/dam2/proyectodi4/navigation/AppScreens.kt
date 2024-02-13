package com.example.loginfactoriaproyectos.navigation

//Objetos posibles a navegar entre pestañas (todas las screens de la app)
sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens (route = "login_screen")
    object HomeScreen: AppScreens(route = "home_screen")
    object RegisterScreen: AppScreens(route = "register_screen")
    object MFAScreen: AppScreens(route = "mfa_screen")
    object ContrasenaOlvidadaScreen: AppScreens(route = "contrasenaOlvidada_screen")
}
