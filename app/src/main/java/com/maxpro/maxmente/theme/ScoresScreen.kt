package com.maxpro.maxmente.theme // O el paquete donde lo tengas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // <<< NUEVA IMPORTACIÓN >>>
import androidx.compose.foundation.lazy.items // <<< NUEVA IMPORTACIÓN >>>
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Para inyectar el ViewModel
import androidx.navigation.NavController
// Ya no necesitamos ScoreManager aquí si solo mostramos historial de Firestore
// import com.maxpro.maxmente.util.ScoreManager
import com.maxpro.maxmente.model.QuizAttempt // <<< NUEVA IMPORTACIÓN >>>
import com.maxpro.maxmente.viewmodel.ScoresViewModel // <<< NUEVA IMPORTACIÓN >>>
import java.text.SimpleDateFormat // <<< NUEVA IMPORTACIÓN >>>
import java.util.Locale // <<< NUEVA IMPORTACIÓN >>>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoresScreen(
    navController: NavController,
    scoresViewModel: ScoresViewModel = viewModel() // Inyectar ScoresViewModel
) {
    val uiState by scoresViewModel.uiState.collectAsState() // Observar el estado del ViewModel

    // Formateador de fecha para mostrar el timestamp de forma legible
    val dateFormatter = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Puntajes") }, // Título actualizado
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp), // Padding horizontal para la lista
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (uiState.isLoading) {
                // --- Pantalla de Carga ---
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.errorMessage != null) {
                // --- Pantalla de Error ---
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = uiState.errorMessage ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else if (uiState.quizAttempts.isEmpty()) {
                // --- Pantalla de No Hay Puntajes ---
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Aún no has completado ningún cuestionario.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // --- Lista de Intentos de Cuestionario ---
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp) // Padding para la lista
                ) {
                    items(uiState.quizAttempts) { attempt ->
                        QuizAttemptItem(attempt = attempt, dateFormatter = dateFormatter)
                        Divider() // Separador entre ítems
                    }
                }
            }
        }
    }
}

// <<< NUEVO Composable para mostrar cada ítem de intento >>>
@Composable
fun QuizAttemptItem(attempt: QuizAttempt, dateFormatter: SimpleDateFormat) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Categoría: ${attempt.category.replace("_", " ").capitalizeWords()}", // Formatear nombre de categoría
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Puntaje: ${attempt.score} / ${attempt.totalQuestions}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            attempt.timestamp?.let { date ->
                Text(
                    text = "Fecha: ${dateFormatter.format(date)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// <<< NUEVA Función de utilidad para capitalizar palabras (opcional, para estética) >>>
fun String.capitalizeWords(): String = split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}