    package com.maxpro.maxmente.model

    import com.google.firebase.firestore.FieldValue
    import com.google.firebase.firestore.ServerTimestamp
    import java.util.Date

    data class QuizAttempt(
        val userId: String = "", // UID del usuario de Firebase Auth
        val category: String = "",
        val score: Int = 0,
        val totalQuestions: Int = 0,
        @ServerTimestamp // Esto le dice a Firestore que llene este campo con la hora del servidor
        val timestamp: Date? = null, // Firestore lo convertirá a su tipo Timestamp
        // No necesitamos un ID para el intento aquí si Firestore lo genera automáticamente,
        // pero podría ser útil si lo lees y necesitas referenciarlo.
        // val attemptId: String = "" (si lo gestionas tú o lo recuperas después)
    ) {
        // Constructor sin argumentos requerido por Firestore para deserializar
        constructor() : this("", "", 0, 0, null)
    }