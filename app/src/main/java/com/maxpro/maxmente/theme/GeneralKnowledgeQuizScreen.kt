package com.maxpro.maxmente.theme // o com.maxpro.maxmente.screens

import android.util.Log // Para los logs de depuración
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.* // Importación clave para Material 3
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // Para la función viewModel()
import androidx.navigation.NavController
import com.maxpro.maxmente.viewmodel.QuizViewModel   // Para la clase QuizViewModel
import com.maxpro.maxmente.util.ScoreManager      // O com.maxpro.maxmente.data.ScoreManager según donde lo tengas

@OptIn(ExperimentalMaterial3Api::class) // Para Scaffold, TopAppBar, etc.
@Composable
fun GeneralKnowledgeQuizScreen(
    navController: NavController,
    quizViewModel: QuizViewModel = viewModel() // Inyección del ViewModel
) {
    val context = LocalContext.current

    // --- VVV ESTA SECCIÓN ES CRUCIAL Y PROBABLEMENTE LA QUE TE FALTABA O TENÍAS INCOMPLETA VVV ---
    LaunchedEffect(Unit) {
        quizViewModel.loadQuestions("general_knowledge", 15)
    }

    val currentQuestion = quizViewModel.currentQuestion
    val options = currentQuestion?.options ?: emptyList()
    val selectedOption = quizViewModel.selectedOptionIndex
    val score = quizViewModel.score
    val isQuizFinished = quizViewModel.isQuizFinished
    val answerSubmitted = quizViewModel.answerSubmitted
    val totalQuestions = quizViewModel.questions.size
    val timeLeft = quizViewModel.timeLeftForQuestion
    val isTimeUp = quizViewModel.isTimeUpForQuestion

    val currentProgress = if (totalQuestions > 0) {
        if (isQuizFinished) 1f else (quizViewModel.currentQuestionIndex.toFloat()) / totalQuestions.toFloat()
    } else {
        0f
    }
    // --- ^^^ FIN DE LA SECCIÓN CRUCIAL ^^^ ---

    LaunchedEffect(isQuizFinished, score, totalQuestions) {
        if (isQuizFinished && totalQuestions > 0) {
            Log.d("GenKnowledgeScreen_Save", "Finalizó Quiz. Guardando puntaje: $score para ${ScoreManager.CATEGORY_KEY_GENERAL} en SharedPreferences")
            ScoreManager.saveBestScoreForCategory(
                context,
                ScoreManager.CATEGORY_KEY_GENERAL,
                score
            )
            // La llamada a Firestore ya está en QuizViewModel
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (isQuizFinished) {
            // --- Pantalla de Resultados ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text("¡Cuestionario Finalizado!", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Tu puntaje: $score / $totalQuestions", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                val bestScore = ScoreManager.getBestScoreGeneralKnowledge(context)
                Text("Mejor puntaje en esta categoría: $bestScore", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = {
                    quizViewModel.restartQuiz("general_knowledge", 15)
                }) {
                    Text("Jugar de Nuevo (Cultura General)")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver a Selección de Temas")
                }
            }
        } else if (currentQuestion != null) {
            // --- Pantalla de Pregunta ---
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Pregunta ${quizViewModel.currentQuestionIndex + 1} / $totalQuestions",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Tiempo: $timeLeft s",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (timeLeft <= 5 && timeLeft > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Puntaje: $score",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                LinearProgressIndicator(
                    progress = { currentProgress },
                    modifier = Modifier.fillMaxWidth().height(8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = currentQuestion.text,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                options.forEachIndexed { index, optionText ->
                    val isSelected = selectedOption == index
                    val isCorrect = index == currentQuestion.correctAnswerIndex
                    val buttonEnabled = !answerSubmitted && !isTimeUp

                    val baseButtonColors = ButtonDefaults.buttonColors()
                    val (containerColor, contentColor) = when {
                        answerSubmitted && isCorrect -> Pair(Color.Green.copy(alpha = 0.7f), Color.Black)
                        answerSubmitted && isSelected && !isCorrect -> Pair(Color.Red.copy(alpha = 0.7f), Color.White)
                        isSelected -> Pair(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
                        else -> Pair(baseButtonColors.containerColor, baseButtonColors.contentColor)
                    }

                    Button(
                        onClick = { quizViewModel.selectOption(index) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = contentColor,
                            disabledContainerColor = containerColor.copy(alpha = 0.5f),
                            disabledContentColor = contentColor.copy(alpha = 0.7f)
                        ),
                        enabled = buttonEnabled,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(optionText, modifier = Modifier.weight(1f))
                            if (answerSubmitted) {
                                if (isCorrect) {
                                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "Respuesta Correcta")
                                } else if (isSelected && !isCorrect) {
                                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Respuesta Incorrecta")
                                }
                            }
                        }
                    }
                }
                if (answerSubmitted && selectedOption != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    val feedbackText = if (selectedOption == currentQuestion.correctAnswerIndex) "¡Correcto!" else "Incorrecto."
                    val feedbackTextColor = if (selectedOption == currentQuestion.correctAnswerIndex) Color(0xFF008000) else Color.Red
                    Text(
                        text = feedbackText,
                        color = feedbackTextColor,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                } else if (isTimeUp && !answerSubmitted) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "¡Tiempo agotado!",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (!answerSubmitted) {
                    Button(
                        onClick = { quizViewModel.submitAnswer() },
                        enabled = selectedOption != null && !isTimeUp
                    ) {
                        Text("Enviar Respuesta")
                    }
                } else {
                    Button(onClick = { quizViewModel.nextQuestion() }) {
                        Text("Siguiente Pregunta")
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator() // Asegúrate de importar androidx.compose.material3.CircularProgressIndicator
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Cargando preguntas...")
                }
            }
        }
    }
}