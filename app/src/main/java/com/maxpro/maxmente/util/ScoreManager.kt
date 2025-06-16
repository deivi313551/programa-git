package com.maxpro.maxmente.util



import android.content.Context
import android.content.SharedPreferences

object ScoreManager {

    private const val PREFS_NAME = "MaxMenteScores"
    private const val KEY_BEST_SCORE_GENERAL = "best_score_general_knowledge"
    private const val KEY_BEST_SCORE_ICFES = "best_score_icfes"
    private const val KEY_BEST_SCORE_SABER_PRO = "best_score_saber_pro"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- Funciones para Cultura General ---
    fun saveBestScoreGeneralKnowledge(context: Context, score: Int) {
        val prefs = getPreferences(context)
        val currentBest = getBestScoreGeneralKnowledge(context)
        if (score > currentBest) {
            prefs.edit().putInt(KEY_BEST_SCORE_GENERAL, score).apply()
        }
    }

    fun getBestScoreGeneralKnowledge(context: Context): Int {
        return getPreferences(context).getInt(KEY_BEST_SCORE_GENERAL, 0)
    }

    // --- Funciones para ICFES ---
    fun saveBestScoreIcfes(context: Context, score: Int) {
        val prefs = getPreferences(context)
        val currentBest = getBestScoreIcfes(context)
        if (score > currentBest) {
            prefs.edit().putInt(KEY_BEST_SCORE_ICFES, score).apply()
        }
    }

    fun getBestScoreIcfes(context: Context): Int {
        return getPreferences(context).getInt(KEY_BEST_SCORE_ICFES, 0)
    }

    // --- Funciones para Saber Pro ---
    fun saveBestScoreSaberPro(context: Context, score: Int) {
        val prefs = getPreferences(context)
        val currentBest = getBestScoreSaberPro(context)
        if (score > currentBest) {
            prefs.edit().putInt(KEY_BEST_SCORE_SABER_PRO, score).apply()
        }
    }

    fun getBestScoreSaberPro(context: Context): Int {
        return getPreferences(context).getInt(KEY_BEST_SCORE_SABER_PRO, 0)
    }

    // Función genérica para guardar el mejor puntaje (podríamos refactorizar las anteriores)
    fun saveBestScoreForCategory(context: Context, categoryKey: String, score: Int) {
        val prefs = getPreferences(context)
        val currentBest = prefs.getInt(categoryKey, 0)
        // Log para ver qué se intenta guardar
        android.util.Log.d("ScoreManager_Save", "Intentando guardar para '$categoryKey'. Nuevo puntaje: $score, Mejor actual: $currentBest")
        if (score > currentBest) {
            prefs.edit().putInt(categoryKey, score).apply()
            // Log para confirmar el guardado
            android.util.Log.d("ScoreManager_Save", "Nuevo mejor puntaje guardado para '$categoryKey': $score")
        } else {
            // Log si no se supera el mejor puntaje
            android.util.Log.d("ScoreManager_Save", "El puntaje $score no supera el mejor actual ($currentBest) para '$categoryKey'.")
        }
    }

    // Podríamos tener constantes para las claves de categoría también
    const val CATEGORY_KEY_GENERAL = KEY_BEST_SCORE_GENERAL
    const val CATEGORY_KEY_ICFES = KEY_BEST_SCORE_ICFES
    const val CATEGORY_KEY_SABER_PRO = KEY_BEST_SCORE_SABER_PRO
}