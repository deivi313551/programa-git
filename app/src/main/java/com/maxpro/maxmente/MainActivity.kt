package com.maxpro.maxmente

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maxpro.maxmente.theme.* // Importa todas tus pantallas, incluyendo las nuevas
import com.maxpro.maxmente.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaxMenteTheme { // Asegúrate de que tu tema principal envuelva el NavHost
                val navController = rememberNavController()

                // Determinar el destino inicial
                // Si el usuario ya inició sesión, ir a la pantalla de selección de quizzes,
                // de lo contrario, ir a la pantalla de login.
                val startDestination = if (authViewModel.getCurrentUser() != null) {
                    "quiz_selection_screen"
                } else {
                    "login"
                }

                NavHost(navController = navController, startDestination = startDestination) {
                    composable("login") {
                        LoginScreen(navController = navController, viewModel = authViewModel)
                    }
                    composable("register") {
                        RegisterScreen(navController = navController, viewModel = authViewModel)
                    }
                    // La ruta "home" original podría ya no ser necesaria si "quiz_selection_screen"
                    // es la nueva pantalla principal después del login.
                    // Si aún la necesitas para algo, puedes mantenerla.
                    // composable("home") {
                    //     HomeScreen(navController = navController, viewModel = authViewModel)
                    // }
                    composable("quiz_selection_screen") {
                        QuizSelectionScreen(navController = navController, viewModel = authViewModel)
                    }
                    // Rutas para las pantallas placeholder de cuestionarios y puntajes
                    composable("general_knowledge_quiz") {
                        GeneralKnowledgeQuizScreen(navController = navController)
                    }
                    composable("icfes_quiz") {
                        IcfesQuizScreen(navController = navController)
                    }
                    composable("saber_pro_quiz") {
                        SaberProQuizScreen(navController = navController)
                    }
                    composable("scores_screen") {
                        ScoresScreen(navController = navController)
                    }
                }
            }
        }
    }
}

// Puedes mantener o eliminar estas funciones de Greeting si no las usas.
// @Composable
// fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
// }

// @Preview(showBackground = true)
// @Composable
// fun GreetingPreview() {
//    MaxMenteTheme {
//        Greeting("Android")
//    }
// }