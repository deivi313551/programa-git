package com.maxpro.maxmente.theme // Asegúrate que el paquete sea el correcto

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maxpro.maxmente.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro de Usuario", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña (mín. 6 caracteres)") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    if (password.length >= 6) {
                        isLoading = true
                        viewModel.register(email, password,
                            onSuccess = {
                                isLoading = false
                                Log.d("RegisterScreen", "Registration successful, navigating to quiz_selection_screen") // Mensaje de Log actualizado
                                Toast.makeText(context, "Registro exitoso. Serás dirigido a la app.", Toast.LENGTH_SHORT).show()
                                navController.navigate("quiz_selection_screen") { // <<< CAMBIO AQUÍ
                                    popUpTo("register") { inclusive = true } // Limpia la pantalla de registro
                                    popUpTo("login") { inclusive = true }    // Limpia también la de login
                                    launchSingleTop = true // Evita múltiples instancias
                                }
                            },
                            onFailure = { exception ->
                                isLoading = false
                                val message = exception?.message ?: "Error de registro desconocido"
                                Log.e("RegisterScreen", "Registration failed: $message")
                                Toast.makeText(context, "Error en el registro: $message", Toast.LENGTH_LONG).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Registrar")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true } // Si vas a login desde aquí, limpia el registro
            }
        }) {
            Text("¿Ya tienes cuenta? Inicia Sesión")
        }
    }
}