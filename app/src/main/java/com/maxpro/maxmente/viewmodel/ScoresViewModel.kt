package com.maxpro.maxmente.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.maxpro.maxmente.model.QuizAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class ScoresScreenUiState(
    val isLoading: Boolean = true,
    val quizAttempts: List<QuizAttempt> = emptyList(),
    val errorMessage: String? = null
)

class ScoresViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(ScoresScreenUiState())
    val uiState: StateFlow<ScoresScreenUiState> = _uiState.asStateFlow()

    init {
        loadQuizAttempts()
    }

    fun loadQuizAttempts() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            _uiState.value = ScoresScreenUiState(isLoading = false, errorMessage = "Usuario no autenticado.")
            Log.w("ScoresViewModel", "No hay usuario autenticado para cargar puntajes.")
            return
        }

        _uiState.value = ScoresScreenUiState(isLoading = true) // Indicar carga

        viewModelScope.launch {
            try {
                val attemptsQuery = db.collection("users").document(currentUser.uid)
                    .collection("quiz_attempts")
                    .orderBy("timestamp", Query.Direction.DESCENDING) // Mostrar más recientes primero
                    .get()
                    .await() // Usar await para operaciones asíncronas de Firebase

                val attemptsList = attemptsQuery.documents.mapNotNull { document ->
                    document.toObject(QuizAttempt::class.java)
                }
                _uiState.value = ScoresScreenUiState(isLoading = false, quizAttempts = attemptsList)
                Log.d("ScoresViewModel", "Intentos cargados: ${attemptsList.size}")

            } catch (e: Exception) {
                _uiState.value = ScoresScreenUiState(isLoading = false, errorMessage = "Error al cargar puntajes: ${e.localizedMessage}")
                Log.e("ScoresViewModel", "Error al cargar intentos de Firestore", e)
            }
        }
    }
}