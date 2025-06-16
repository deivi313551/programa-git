package com.maxpro.maxmente.theme // Asegúrate que este paquete coincida con tu estructura de carpetas.
// Si lo creaste en otra carpeta, ajusta el 'package' aquí.

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maxpro.maxmente.viewmodel.AuthViewModel // Asegúrate que la importación del ViewModel sea correcta.

@Composable
fun QuizSelectionScreen(navController: NavController, viewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona una Actividad", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("general_knowledge_quiz") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("100 Preguntas de Cultura General")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("icfes_quiz") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("100 Preguntas Tipo ICFES")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("saber_pro_quiz") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("100 Preguntas Prueba Saber Pro")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("scores_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Puntajes")
        }
        Spacer(modifier = Modifier.height(48.dp)) // Un poco más de espacio antes del botón de logout

        Button(
            onClick = {
                viewModel.logout() // Llama a la función logout del ViewModel
                // Navega a la pantalla de login y limpia el backstack
                navController.navigate("login") {
                    popUpTo("quiz_selection_screen") { inclusive = true }
                    launchSingleTop = true // Evita múltiples instancias de la pantalla de login
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error) // Color distintivo para logout
        ) {
            Text("Cerrar Sesión")
        }
    }
}



