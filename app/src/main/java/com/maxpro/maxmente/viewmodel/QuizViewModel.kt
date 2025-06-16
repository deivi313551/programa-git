package com.maxpro.maxmente.viewmodel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.maxpro.maxmente.model.Question             // <<< ASEGÚRATE DE TENER ESTA
import com.maxpro.maxmente.model.QuestionRepository   // <<< ASEGÚRATE DE TENER ESTA
import com.maxpro.maxmente.model.QuizAttempt          // <<< ASEGÚRATE DE TENER ESTA
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
// import kotlinx.coroutines.tasks.await // Si la usas en el futuro

class QuizViewModel : ViewModel() {

    // ... (estados existentes: questions, currentQuestionIndex, score, etc.) ...
    var questions by mutableStateOf<List<Question>>(emptyList())
        private set
    var currentQuestionIndex by mutableStateOf(0)
        private set
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)
    var score by mutableStateOf(0)
        private set
    var isQuizFinished by mutableStateOf(false)
        private set
    var selectedOptionIndex by mutableStateOf<Int?>(null)
        private set
    var answerSubmitted by mutableStateOf(false)
        private set

    // --- NUEVOS ESTADOS Y LÓGICA PARA EL TEMPORIZADOR ---
    companion object {
        const val TIME_PER_QUESTION_SECONDS = 20 // <<< TIEMPO POR PREGUNTA EN SEGUNDOS >>>
    }

    var timeLeftForQuestion by mutableStateOf(TIME_PER_QUESTION_SECONDS) // Tiempo restante para la pregunta actual
        private set
    var isTimeUpForQuestion by mutableStateOf(false) // Indica si el tiempo para la pregunta actual se agotó
        private set

    private var timerJob: Job? = null // Job para controlar la coroutine del temporizador
    // --- FIN NUEVOS ESTADOS Y LÓGICA PARA EL TEMPORIZADOR ---

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun loadQuestions(category: String, count: Int = 15) {
        questions = QuestionRepository.getQuestions(category, count)
        currentQuestionIndex = 0
        score = 0
        isQuizFinished = false
        resetAndStartTimerForNewQuestion() // <<< MODIFICACIÓN: Iniciar temporizador para la primera pregunta >>>
        Log.d("QuizViewModel", "Preguntas cargadas para $category: ${questions.size} preguntas")
    }

    // <<< NUEVA FUNCIÓN: Inicia/reinicia el temporizador para una pregunta >>>
    private fun startTimer() {
        timerJob?.cancel() // Cancela cualquier temporizador anterior
        isTimeUpForQuestion = false // Resetea el estado de tiempo agotado
        timeLeftForQuestion = TIME_PER_QUESTION_SECONDS // Resetea el tiempo
        selectedOptionIndex = null // Resetea la opción seleccionada
        answerSubmitted = false    // Resetea el estado de envío

        timerJob = viewModelScope.launch {
            while (timeLeftForQuestion > 0 && !answerSubmitted && !isQuizFinished) { // Continuar si hay tiempo y no se ha respondido ni terminado el quiz
                delay(1000) // Espera 1 segundo
                timeLeftForQuestion--
            }
            if (timeLeftForQuestion == 0 && !answerSubmitted && !isQuizFinished) {
                // Tiempo agotado y no se había enviado respuesta para esta pregunta
                isTimeUpForQuestion = true
                submitAnswer() // Enviar respuesta automáticamente (o considerarla incorrecta)
            }
        }
    }

    // <<< NUEVA FUNCIÓN: Combina el reseteo de estados y el inicio del temporizador >>>
    private fun resetAndStartTimerForNewQuestion() {
        selectedOptionIndex = null
        answerSubmitted = false
        startTimer() // Inicia el temporizador para la nueva pregunta
    }
    // <<< FIN NUEVAS FUNCIONES >>>

    fun selectOption(optionIndex: Int) {
        if (!answerSubmitted && !isTimeUpForQuestion) { // Solo permitir si no se ha enviado y hay tiempo
            selectedOptionIndex = optionIndex
        }
    }

    fun submitAnswer() {
        if (answerSubmitted) return // Evitar envíos múltiples

        timerJob?.cancel() // <<< MODIFICACIÓN: Detener el temporizador al enviar respuesta >>>
        answerSubmitted = true
        val question = currentQuestion ?: return

        if (isTimeUpForQuestion && selectedOptionIndex == null) {
            // Si el tiempo se agotó y no se seleccionó nada, no se suma puntaje (o se considera incorrecta)
            Log.d("QuizViewModel", "Tiempo agotado para la pregunta, sin selección.")
            // No es necesario hacer score++ aquí, ya que no hay respuesta correcta seleccionada
        } else if (selectedOptionIndex == question.correctAnswerIndex) {
            score++
            Log.d("QuizViewModel", "Respuesta correcta seleccionada.")
        } else {
            Log.d("QuizViewModel", "Respuesta incorrecta seleccionada o tiempo agotado con selección incorrecta.")
        }
        // No llamamos a nextQuestion() aquí directamente, la UI lo hará
    }

    fun nextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            resetAndStartTimerForNewQuestion() // <<< MODIFICACIÓN: Iniciar temporizador para la nueva pregunta >>>
        } else {
            isQuizFinished = true
            timerJob?.cancel() // Detener el temporizador si el quiz ha terminado
            currentQuestion?.let {
                saveQuizAttemptToFirestore(it.category, score, questions.size)
            }
        }
    }

    fun restartQuiz(category: String, count: Int = 15) {
        loadQuestions(category, count) // Esto ya llama a resetAndStartTimerForNewQuestion
    }

    // ... (función saveQuizAttemptToFirestore sin cambios) ...
    private fun saveQuizAttemptToFirestore(category: String, currentScore: Int, questionsInAttempt: Int) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val quizAttempt = QuizAttempt(
                userId = userId,
                category = category,
                score = currentScore,
                totalQuestions = questionsInAttempt
            )
            db.collection("users").document(userId)
                .collection("quiz_attempts")
                .add(quizAttempt)
                .addOnSuccessListener { documentReference ->
                    Log.d("QuizViewModel_Firestore", "Intento de cuestionario guardado en Firestore con ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("QuizViewModel_Firestore", "Error al guardar intento en Firestore", e)
                }
        } else {
            Log.w("QuizViewModel_Firestore", "No hay usuario logueado para guardar el intento.")
        }
    }
}