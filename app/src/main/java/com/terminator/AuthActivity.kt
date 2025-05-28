package com.terminator.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.terminator.MainActivity
import com.terminator.databinding.ActivityAuthBinding
import com.terminator.network.RetrofitInstance
import com.terminator.repository.AuthRepository
import com.terminator.utils.SessionManager
import com.terminator.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val sessionManager by lazy { SessionManager(this) }
    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(RetrofitInstance.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.login(username, password)
        }

        lifecycleScope.launch {
            viewModel.loginResult.collectLatest { loginResponse ->
                if (loginResponse != null) {
                    sessionManager.saveAuthToken(loginResponse.token)
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@AuthActivity, "Erreur login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
