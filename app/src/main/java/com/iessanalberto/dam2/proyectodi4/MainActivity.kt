package com.iessanalberto.dam2.proyectodi4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.loginfactoriaproyectos.navigation.AppNavigation
import com.example.loginfactoriaproyectos.navigation.AppScreens
import com.iessanalberto.dam2.proyectodi4.data.RetrofitServiceFactory
import com.iessanalberto.dam2.proyectodi4.data.model.DataManager
import com.iessanalberto.dam2.proyectodi4.data.model.DataManager.listProyectosFP
import com.iessanalberto.dam2.proyectodi4.screens.HomeScreen
import com.iessanalberto.dam2.proyectodi4.screens.HomeScreenBodyContent
import com.iessanalberto.dam2.proyectodi4.ui.theme.ProyectoDI4Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoDI4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Hacemos un get de los datos de la api y los mandamos a
                    val service = RetrofitServiceFactory.makeRetrofitService()
                    val dataLoaded = remember { mutableStateOf(false) }
                    lifecycleScope.launch {
                        val listProjects = service.listData()
                        DataManager.listProyectosFP = listOf(listProjects)
                        dataLoaded.value = true
                    }
                    AppNavigation()
                }
            }
        }
    }
}

