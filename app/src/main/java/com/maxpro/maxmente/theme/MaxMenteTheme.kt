package com.maxpro.maxmente.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme // o darkColorScheme, etc.
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color // Ejemplo de importación de color

// Define tus esquemas de color (ejemplo básico)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Ejemplo, puedes definir tus colores en Color.kt
    secondary = Color(0xFF03DAC5)
    /* Otros colores según necesites:
    tertiary = ...
    */
)

@Composable
fun MaxMenteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // Simplificado, puedes implementar lógica para tema oscuro

    MaterialTheme(
        colorScheme = colorScheme,
        // typography = Typography, // Si tienes un archivo Typography.kt
        content = content
    )
}