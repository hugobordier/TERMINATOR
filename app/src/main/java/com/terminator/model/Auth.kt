package com.terminator.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginRequest(
    val username: String,
    val password: String
) : Parcelable

@Parcelize
data class LoginResponse(
    val token: String // ce que l'API renvoie, par ex un JWT
) : Parcelable

@Parcelize
data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
) : Parcelable

@Parcelize
data class RegisterResponse(
    val id: Int,
    val username: String,
    val email: String
) : Parcelable
