package com.maxpro.maxmente.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Importa los componentes de Material3 que uses
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maxpro.maxmente.viewmodel.AuthViewModel
// ... resto del código

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Bienvenido a MaxMente 🧠")
    }
}
