package com.iessanalberto.dam2.proyectodi4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.iessanalberto.dam2.proyectodi4.data.RetrofitServiceFactory
import com.iessanalberto.dam2.proyectodi4.data.model.ProyectosFP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.iessanalberto.dam2.proyectodi4.data.RetrofitService
import com.iessanalberto.dam2.proyectodi4.data.model.DataManager
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    HomeScreenBodyContent()
}

@Composable
fun HomeScreenBodyContent() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Home Screen")
        //Acceso a los datos de la api
        val listProyectosFP = DataManager.listProyectosFP
        if (listProyectosFP != null) {
            Text(text = listProyectosFP[0].body)
        } else {
            Text(text = "No se encontraron proyectos")
        }
        val coroutineScope = rememberCoroutineScope()
        val responseState = remember { mutableStateOf<Response<ProyectosFP>?>(null) }
        //Enviar un post a la api
        Button(
            onClick = {
                coroutineScope.launch {
                    //mandar datos por post, cambiar estos por los de la lambda
                    val data = ProyectosFP("202", "bodymia")
                    val service = RetrofitServiceFactory.makeRetrofitService()
                    val response = service.sendData("hola",data)
                    responseState.value = response
                }
            }
        ) {
            Text(text = "Enviar datos")
        }

        // Muestra el resultado de la respuesta de la API
        responseState.value?.let { response ->
            when {
                response.isSuccessful -> {
                    // La solicitud fue exitosa, puedes acceder a los datos de la respuesta
                    val responseBody = response.body()
                    Text(text = "Respuesta exitosa: ${responseBody?.body} ${responseBody?.statusCode}")
                }
                else -> {
                    // La solicitud no fue exitosa, puedes manejar el error aquí
                    Text(text = "Error en la solicitud: ${response.message()}")
                }
            }
        }
    }
}
