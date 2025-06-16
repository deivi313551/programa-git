package com.maxpro.maxmente.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

// NO DEBE HABER NINGUNA CLASE MainActivity AQUÍ

class AuthViewModel : ViewModel() { // Abre la CLASE AuthViewModel
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Función para obtener el usuario actual, útil para verificar si alguien ya inició sesión
    fun getCurrentUser() = auth.currentUser // No usa corchetes porque es una expresión simple

    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) { // Abre la FUNCIÓN register
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> // Abre el LAMBDA de addOnCompleteListener
                if (task.isSuccessful) { // Abre el IF
                    Log.d("AuthViewModel", "Registration successful")
                    onSuccess()
                } else { // Abre el ELSE
                    Log.w("AuthViewModel", "Error en el registro:", task.exception)
                    onFailure(task.exception)
                } // Cierra el ELSE
            } // Cierra el LAMBDA de addOnCompleteListener
    } // Cierra la FUNCIÓN register

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) { // Abre la FUNCIÓN login
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> // Abre el LAMBDA de addOnCompleteListener
                if (task.isSuccessful) { // Abre el IF
                    Log.d("AuthViewModel", "Login successful")
                    onSuccess()
                } else { // Abre el ELSE
                    Log.w("AuthViewModel", "Error en el inicio de sesión:", task.exception)
                    onFailure(task.exception)
                } // Cierra el ELSE
            } // Cierra el LAMBDA de addOnCompleteListener
    } // Cierra la FUNCIÓN login

    fun logout() {
        auth.signOut()
        Log.d("AuthViewModel", "User logged out")
    }

}