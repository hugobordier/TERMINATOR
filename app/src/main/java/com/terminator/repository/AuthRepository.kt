package com.terminator.repository

import com.terminator.model.*
import com.terminator.network.FakeStoreApiService

class AuthRepository(private val api: FakeStoreApiService) {

    suspend fun login(username: String, password: String): LoginResponse {
        return api.login(LoginRequest(username, password))
    }

    suspend fun register(username: String, email: String, password: String): RegisterResponse {
        return api.register(RegisterRequest(username, password, email))
    }
}
