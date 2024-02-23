package com.iessanalberto.dam2.proyectodi4.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProyectosFP (
    val statusCode: String,
    val body: String
)