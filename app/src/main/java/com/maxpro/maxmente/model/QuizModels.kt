package com.maxpro.maxmente.model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.maxpro.maxmente.model.QuizAttempt // Importa tu nueva data class
import kotlinx.coroutines.tasks.await // Para trabajar con Tasks de Firebase en coroutines

data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val category: String
)

object QuestionRepository {

    private val generalKnowledgeQuestions = listOf(
        Question(
            id = 1,
            text = "¿Cuál es la capital de Australia?",
            options = listOf("Sídney", "Melbourne", "Canberra", "Perth"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 2,
            text = "¿En qué continente se encuentra el Desierto del Sahara?",
            options = listOf("Asia", "África", "América del Sur", "Australia"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 3,
            text = "¿Cuál es el océano más pequeño del mundo?",
            options = listOf("Pacífico", "Atlántico", "Índico", "Ártico"),
            correctAnswerIndex = 3,
            category = "general_knowledge"
        ),
        Question(
            id = 4,
            text = "¿Quién escribió 'Romeo y Julieta'?",
            options = listOf("Charles Dickens", "William Shakespeare", "Jane Austen", "Leo Tolstoy"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 5,
            text = "¿Cuántos corazones tiene un pulpo?",
            options = listOf("Uno", "Dos", "Tres", "Cuatro"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 6,
            text = "¿Cuál es el país más grande del mundo por área terrestre?",
            options = listOf("Canadá", "China", "Estados Unidos", "Rusia"),
            correctAnswerIndex = 3,
            category = "general_knowledge"
        ),
        Question(
            id = 7,
            text = "¿Quién pintó 'La noche estrellada'?",
            options = listOf("Claude Monet", "Vincent van Gogh", "Pablo Picasso", "Salvador Dalí"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 8,
            text = "¿Cuál es la moneda oficial de Japón?",
            options = listOf("Yuan", "Won", "Yen", "Baht"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 9,
            text = "¿En qué año comenzó la Primera Guerra Mundial?",
            options = listOf("1905", "1914", "1918", "1923"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 10,
            text = "¿Cuál es el elemento químico más abundante en el universo?",
            options = listOf("Oxígeno", "Helio", "Hidrógeno", "Carbono"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 11,
            text = "¿Qué famoso científico desarrolló la teoría de la relatividad?",
            options = listOf("Isaac Newton", "Galileo Galilei", "Albert Einstein", "Nikola Tesla"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 12,
            text = "¿Cuál es la montaña más alta del mundo?",
            options = listOf("K2", "Monte Everest", "Kangchenjunga", "Makalu"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 13,
            text = "¿En qué país se originaron los Juegos Olímpicos antiguos?",
            options = listOf("Italia", "Egipto", "Grecia", "Roma"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 14,
            text = "¿Cuál es el libro más vendido de todos los tiempos (excluyendo textos religiosos)?",
            options = listOf("Don Quijote de la Mancha", "Historia de dos ciudades", "El Señor de los Anillos", "El Principito"),
            correctAnswerIndex = 0,
            category = "general_knowledge"
        ),
        Question(
            id = 15,
            text = "¿Qué instrumento musical tiene cuerdas, teclas y martillos?",
            options = listOf("Guitarra", "Violín", "Piano", "Arpa"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 16,
            text = "¿Cuál es la capital de Canadá?",
            options = listOf("Toronto", "Montreal", "Vancouver", "Ottawa"),
            correctAnswerIndex = 3,
            category = "general_knowledge"
        ),
        Question(
            id = 17,
            text = "¿Qué gas es esencial para la respiración humana?",
            options = listOf("Nitrógeno", "Oxígeno", "Dióxido de carbono", "Hidrógeno"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 18,
            text = "¿Quién fue el primer presidente de los Estados Unidos?",
            options = listOf("Abraham Lincoln", "Thomas Jefferson", "George Washington", "John Adams"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 19,
            text = "¿Cuál es el animal terrestre más rápido?",
            options = listOf("León", "Tigre", "Guepardo", "Leopardo"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 20,
            text = "¿En qué ciudad se encuentra la Torre Eiffel?",
            options = listOf("Londres", "Roma", "París", "Berlín"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 21,
            text = "¿Cuál es el idioma más hablado en el mundo por número de hablantes nativos?",
            options = listOf("Inglés", "Español", "Chino Mandarín", "Hindi"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 22,
            text = "¿Qué planeta es conocido como el 'Planeta Rojo'?",
            options = listOf("Júpiter", "Marte", "Venus", "Saturno"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 23,
            text = "¿Quién escribió 'Hamlet'?",
            options = listOf("William Shakespeare", "Christopher Marlowe", "Ben Jonson", "John Milton"),
            correctAnswerIndex = 0,
            category = "general_knowledge"
        ),
        Question(
            id = 24,
            text = "¿Cuál es el hueso más largo del cuerpo humano?",
            options = listOf("Tibia", "Fémur", "Húmero", "Peroné"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 25,
            text = "¿En qué país se encuentra el Taj Mahal?",
            options = listOf("Pakistán", "India", "Bangladesh", "Nepal"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 26,
            text = "¿Qué civilización antigua construyó Machu Picchu?",
            options = listOf("Azteca", "Maya", "Inca", "Egipcia"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 27,
            text = "¿Cuál es la capital de Italia?",
            options = listOf("Milán", "Venecia", "Roma", "Nápoles"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 28,
            text = "¿Qué artista es famoso por cortar parte de su propia oreja?",
            options = listOf("Pablo Picasso", "Claude Monet", "Vincent van Gogh", "Salvador Dalí"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 29,
            text = "¿Cuántos lados tiene un hexágono?",
            options = listOf("Cinco", "Seis", "Siete", "Ocho"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 30,
            text = "¿Cuál es el principal gas de efecto invernadero responsable del calentamiento global?",
            options = listOf("Metano", "Óxido nitroso", "Dióxido de carbono", "Ozono"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 31,
            text = "¿En qué país nació Wolfgang Amadeus Mozart?",
            options = listOf("Alemania", "Italia", "Austria", "Francia"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 32,
            text = "¿Cuál es el desierto más grande y frío del mundo?",
            options = listOf("Sahara", "Gobi", "Kalahari", "Antártico"),
            correctAnswerIndex = 3,
            category = "general_knowledge"
        ),
        Question(
            id = 33,
            text = "¿Qué se celebra el 4 de julio en Estados Unidos?",
            options = listOf("Día de Acción de Gracias", "Día de la Independencia", "Día del Trabajo", "Navidad"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 34,
            text = "¿Quién es conocido como el 'Rey del Pop'?",
            options = listOf("Elvis Presley", "Michael Jackson", "Prince", "Madonna"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 35,
            text = "¿Cuál es el componente principal del Sol?",
            options = listOf("Oxígeno", "Helio", "Hierro", "Hidrógeno"),
            correctAnswerIndex = 3,
            category = "general_knowledge"
        ),
        Question(
            id = 36,
            text = "¿En qué ciudad italiana se encuentra el Coliseo?",
            options = listOf("Florencia", "Venecia", "Roma", "Milán"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 37,
            text = "¿Qué significan las siglas 'FIFA'?",
            options = listOf("Federación Internacional de Fútbol Asociado", "Federación Internacional de Baloncesto", "Fondo Internacional para la Fauna", "Fuerza Internacional de Asistencia"),
            correctAnswerIndex = 0,
            category = "general_knowledge"
        ),
        Question(
            id = 38,
            text = "¿Cuál es la capital de Rusia?",
            options = listOf("San Petersburgo", "Moscú", "Kazán", "Sochi"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 39,
            text = "¿Cuántos dientes permanentes tiene un ser humano adulto típicamente?",
            options = listOf("28", "30", "32", "34"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 40,
            text = "¿Quién fue la primera mujer en ganar un Premio Nobel?",
            options = listOf("Marie Curie", "Rosalind Franklin", "Ada Lovelace", "Jane Goodall"),
            correctAnswerIndex = 0,
            category = "general_knowledge"
        ),
        Question(
            id = 41,
            text = "¿En qué país se encuentra la Gran Muralla China?",
            options = listOf("Japón", "Corea del Sur", "China", "Mongolia"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 42,
            text = "¿Cuál es el animal nacional de Australia?",
            options = listOf("Koala", "Canguro", "Emú", "Ornitorrinco"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 43,
            text = "¿Cuál es el resultado de 25 x 4?",
            options = listOf("80", "90", "100", "120"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 44,
            text = "¿Qué gas utilizan las plantas para la fotosíntesis?",
            options = listOf("Oxígeno", "Nitrógeno", "Dióxido de carbono", "Hidrógeno"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 45,
            text = "¿Quién escribió 'El Principito'?",
            options = listOf("J.K. Rowling", "Antoine de Saint-Exupéry", "Roald Dahl", "Hans Christian Andersen"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 46,
            text = "¿Cuál es el metal líquido a temperatura ambiente?",
            options = listOf("Plomo", "Aluminio", "Mercurio", "Hierro"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 47,
            text = "¿En qué año cayó el Muro de Berlín?",
            options = listOf("1985", "1989", "1991", "1993"),
            correctAnswerIndex = 1,
            category = "general_knowledge"
        ),
        Question(
            id = 48,
            text = "¿Cuál es el órgano más grande del cuerpo humano?",
            options = listOf("Hígado", "Cerebro", "Piel", "Pulmones"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 49,
            text = "¿Qué famoso explorador es conocido por haber 'descubierto' América en 1492?",
            options = listOf("Vasco da Gama", "Fernando de Magallanes", "Cristóbal Colón", "Marco Polo"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        ),
        Question(
            id = 50,
            text = "¿Cuál es la capital de Alemania?",
            options = listOf("Múnich", "Hamburgo", "Berlín", "Fráncfort"),
            correctAnswerIndex = 2,
            category = "general_knowledge"
        )
    )

    private val icfesQuestions = listOf(
        Question(
            id = 101,
            text = "Lee el siguiente fragmento: 'La noche estrellada sobre el Ródano es una de las pinturas al óleo sobre lienzo del pintor postimpresionista neerlandés Vincent van Gogh. Representa la vista desde el este de la Place du Forum en Arlés.' ¿Cuál es el propósito principal del fragmento?",
            options = listOf("Narrar una historia sobre Van Gogh", "Describir una obra de arte de Van Gogh", "Criticar la técnica de Van Gogh", "Comparar a Van Gogh con otros artistas"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 102,
            text = "En la oración 'El perro, guardián fiel de la casa, ladró fuertemente', la frase 'guardián fiel de la casa' cumple la función de:",
            options = listOf("Sujeto", "Predicado", "Aposición", "Complemento directo"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 103,
            text = "Si 3x - 5 = 10, ¿cuál es el valor de x?",
            options = listOf("3", "5", "7", "15"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 104,
            text = "¿Cuál de las siguientes opciones NO es un derecho fundamental consagrado en la Constitución Política de Colombia?",
            options = listOf("Derecho a la vida", "Derecho a la educación gratuita universal", "Libertad de expresión", "Derecho al trabajo"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 105,
            text = "El proceso mediante el cual las plantas convierten la luz solar en energía química se llama:",
            options = listOf("Respiración", "Transpiración", "Fotosíntesis", "Digestión"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 106,
            text = "Un antónimo de la palabra 'efímero' es:",
            options = listOf("Breve", "Fugaz", "Duradero", "Corto"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 107,
            text = "Un mapa a escala 1:100.000 significa que 1 cm en el mapa representa:",
            options = listOf("100.000 cm en la realidad", "1.000 cm en la realidad", "100 cm en la realidad", "10.000 cm en la realidad"),
            correctAnswerIndex = 0,
            category = "icfes"
        ),
        Question(
            id = 108,
            text = "La Revolución Industrial comenzó principalmente en:",
            options = listOf("Francia", "Alemania", "Estados Unidos", "Gran Bretaña"),
            correctAnswerIndex = 3,
            category = "icfes"
        ),
        Question(
            id = 109,
            text = "Identifica la figura literaria en: 'Sus cabellos eran de oro'.",
            options = listOf("Símil", "Hipérbole", "Metáfora", "Personificación"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 110,
            text = "Si un evento tiene una probabilidad de 0.25 de ocurrir, ¿cuál es la probabilidad de que NO ocurra?",
            options = listOf("0.25", "0.50", "0.75", "1.00"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 111,
            text = "El autor del Popol Vuh es:",
            options = listOf("Conocido, un cronista español", "Anónimo, de tradición oral maya-quiché", "Gabriel García Márquez", "Un fraile franciscano"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 112,
            text = "¿Cuál de las siguientes fuentes de energía es renovable?",
            options = listOf("Petróleo", "Carbón", "Energía solar", "Gas natural"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 113,
            text = "En un debate, el argumento que busca desacreditar a la persona en lugar de su idea se conoce como falacia:",
            options = listOf("Ad hominem", "Ad populum", "De autoridad", "Hombre de paja"),
            correctAnswerIndex = 0,
            category = "icfes"
        ),
        Question(
            id = 114,
            text = "Un triángulo con tres lados de igual longitud se llama:",
            options = listOf("Isósceles", "Escaleno", "Equilátero", "Rectángulo"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 115,
            text = "El principal objetivo de la rama legislativa en un gobierno democrático es:",
            options = listOf("Ejecutar las leyes", "Interpretar las leyes", "Crear y modificar las leyes", "Administrar justicia"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 116,
            text = "La frase 'Más vale pájaro en mano que cien volando' es un ejemplo de:",
            options = listOf("Dicho", "Refrán", "Adivinanza", "Trabalenguas"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 117,
            text = "Si un artículo cuesta \$5000 y tiene un descuento del 20%, ¿cuál es el precio final?",
            options = listOf("\$1000", "\$3000", "\$4000", "\$4800"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 118,
            text = "El efecto invernadero es un fenómeno natural, pero su intensificación por actividades humanas causa:",
            options = listOf("Enfriamiento global", "Calentamiento global", "Destrucción de la capa de ozono", "Lluvia ácida"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 119,
            text = "En la oración 'Aunque llovía, salimos al parque', la palabra 'Aunque' es:",
            options = listOf("Un sustantivo", "Un adjetivo", "Una conjunción concesiva", "Un adverbio de modo"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 120,
            text = "El sistema de coordenadas cartesianas utiliza dos ejes perpendiculares llamados:",
            options = listOf("Radio y diámetro", "Latitud y longitud", "Abscisa y ordenada (x e y)", "Hipotenusa y cateto"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 121,
            text = "La Declaración Universal de los Derechos Humanos fue proclamada por:",
            options = listOf("La Liga de las Naciones", "La Organización de los Estados Americanos", "La Organización de las Naciones Unidas", "La Unión Europea"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 122,
            text = "'Crónica de una muerte anunciada' es una obra de:",
            options = listOf("Mario Vargas Llosa", "Julio Cortázar", "Gabriel García Márquez", "Jorge Luis Borges"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 123,
            text = "Si se lanzan dos dados comunes, ¿cuál es la suma más probable de obtener?",
            options = listOf("2", "7", "10", "12"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 124,
            text = "La capa más externa de la Tierra se llama:",
            options = listOf("Manto", "Núcleo", "Corteza", "Atmósfera"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 125,
            text = "El término 'biodiversidad' se refiere a:",
            options = listOf("La cantidad de ecosistemas en una región", "La variedad de vida en la Tierra", "La adaptación de las especies", "La cadena alimenticia"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 126,
            text = "¿Cuál de las siguientes palabras está escrita correctamente?",
            options = listOf("Exhuberante", "Exuberante", "Exuverante", "Ecsuberante"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 127,
            text = "Un ángulo de 90 grados se llama ángulo:",
            options = listOf("Agudo", "Obtuso", "Recto", "Llano"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 128,
            text = "El periodo de la historia de Colombia conocido como 'La Violencia' ocurrió principalmente entre los partidos:",
            options = listOf("Conservador y Liberal", "Socialista y Comunista", "Federalista y Centralista", "Realista e Independentista"),
            correctAnswerIndex = 0,
            category = "icfes"
        ),
        Question(
            id = 129,
            text = "El tema principal del poema 'Nocturno III' de José Asunción Silva es:",
            options = listOf("El amor patriótico", "La crítica social", "La angustia existencial y la muerte", "La belleza de la naturaleza"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 130,
            text = "Si una mezcla contiene sal y agua, ¿cómo se podría separar la sal?",
            options = listOf("Filtración", "Decantación", "Evaporación", "Magnetismo"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 131,
            text = "El género literario que narra sucesos legendarios o históricos de importancia nacional, usualmente en verso, es:",
            options = listOf("Lírica", "Drama", "Épica", "Ensayo"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 132,
            text = "Una empresa produce 300 unidades en 6 horas. ¿Cuántas unidades producirá en 8 horas al mismo ritmo?",
            options = listOf("350", "400", "450", "500"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 133,
            text = "¿Cuál de los siguientes es un mecanismo de participación ciudadana en Colombia?",
            options = listOf("El estado de sitio", "El voto", "La extradición", "El indulto presidencial"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 134,
            text = "La energía cinética de un objeto depende de su:",
            options = listOf("Masa y altura", "Masa y velocidad", "Velocidad y altura", "Solo su masa"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 135,
            text = "En la frase 'El libro que me prestaste es muy interesante', 'que me prestaste' es una oración subordinada:",
            options = listOf("Sustantiva", "Adjetiva", "Adverbial de modo", "Adverbial de tiempo"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 136,
            text = "Si el perímetro de un cuadrado es 20 cm, ¿cuál es su área?",
            options = listOf("20 cm²", "25 cm²", "40 cm²", "100 cm²"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 137,
            text = "La Guerra Fría fue un enfrentamiento principalmente entre:",
            options = listOf("Estados Unidos y China", "Reino Unido y Alemania", "Estados Unidos y la Unión Soviética", "Francia y Rusia"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 138,
            text = "El Modernismo en la literatura hispanoamericana tuvo como uno de sus máximos exponentes a:",
            options = listOf("Gabriel García Márquez", "Rubén Darío", "Jorge Luis Borges", "Pablo Neruda"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 139,
            text = "El pH neutro tiene un valor de:",
            options = listOf("0", "7", "14", "1"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 140,
            text = "La palabra 'democracia' etimológicamente significa:",
            options = listOf("Poder del pueblo", "Gobierno de los ricos", "Poder de uno solo", "Gobierno de los sabios"),
            correctAnswerIndex = 0,
            category = "icfes"
        ),
        Question(
            id = 141,
            text = "Un texto que tiene como objetivo principal convencer o persuadir al lector sobre una idea es un texto:",
            options = listOf("Narrativo", "Descriptivo", "Expositivo", "Argumentativo"),
            correctAnswerIndex = 3,
            category = "icfes"
        ),
        Question(
            id = 142,
            text = "Si la media de 5 números es 10, y cuatro de los números son 7, 9, 11, 13, ¿cuál es el quinto número?",
            options = listOf("8", "10", "12", "14"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 143,
            text = "Los tres poderes públicos en Colombia son:",
            options = listOf("Ejecutivo, Legislativo y Judicial", "Presidencial, Ministerial y Departamental", "Central, Regional y Municipal", "Civil, Militar y Eclesiástico"),
            correctAnswerIndex = 0,
            category = "icfes"
        ),
        Question(
            id = 144,
            text = "El cambio de estado de sólido a gas, sin pasar por líquido, se llama:",
            options = listOf("Fusión", "Vaporización", "Condensación", "Sublimación"),
            correctAnswerIndex = 3,
            category = "icfes"
        ),
        Question(
            id = 145,
            text = "En la expresión 'correr como el viento', ¿qué figura retórica se utiliza?",
            options = listOf("Metáfora", "Símil", "Hipérbole", "Personificación"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 146,
            text = "Si un ángulo mide 45°, su ángulo complementario mide:",
            options = listOf("45°", "55°", "135°", "90°"),
            correctAnswerIndex = 0,
            category = "icfes"
        ),
        Question(
            id = 147,
            text = "El bogotazo, ocurrido el 9 de abril de 1948, fue desencadenado por el asesinato de:",
            options = listOf("Gustavo Rojas Pinilla", "Laureano Gómez", "Jorge Eliécer Gaitán", "Mariano Ospina Pérez"),
            correctAnswerIndex = 2,
            category = "icfes"
        ),
        Question(
            id = 148,
            text = "La obra 'La vorágine' de José Eustasio Rivera pertenece al género literario de:",
            options = listOf("Poesía lírica", "Novela regionalista/de la selva", "Teatro costumbrista", "Ensayo filosófico"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 149,
            text = "Los cromosomas se encuentran en:",
            options = listOf("El citoplasma de la célula", "El núcleo de la célula", "La membrana celular", "Las mitocondrias"),
            correctAnswerIndex = 1,
            category = "icfes"
        ),
        Question(
            id = 150,
            text = "Un texto que presenta información objetiva sobre un tema específico sin incluir opiniones personales es principalmente:",
            options = listOf("Argumentativo", "Narrativo", "Expositivo", "Descriptivo"),
            correctAnswerIndex = 2,
            category = "icfes"
        )
    )

    private val saberProQuestions = listOf(
        Question(
            id = 201,
            text = "Un proyecto de inversión requiere \$10,000.000 y se espera que genere flujos de caja anuales de \$3,000.000 durante 5 años. Sin considerar la tasa de descuento, ¿cuál es el periodo de recuperación simple de la inversión?",
            options = listOf("3 años", "3.33 años", "4 años", "5 años"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 202,
            text = "Lea el siguiente argumento: 'Todos los mamíferos son vertebrados. El delfín es un mamífero. Por lo tanto, el delfín es un vertebrado.' Este es un ejemplo de razonamiento:",
            options = listOf("Inductivo", "Deductivo", "Analógico", "Abductivo"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 203,
            text = "Si la inflación anual es del 5%, ¿cuál será aproximadamente el valor real de \$100.000 después de un año?",
            options = listOf("\$105.000", "\$100.000", "\$95.238", "\$95.000"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 204,
            text = "Una política pública que busca reducir la desigualdad de ingresos mediante impuestos progresivos y subsidios directos a los más pobres se basa en el principio de:",
            options = listOf("Libre mercado absoluto", "Eficiencia económica pura", "Justicia distributiva", "Competencia perfecta"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 205,
            text = "En un estudio sobre el efecto de un nuevo medicamento, un grupo recibe el medicamento y otro grupo recibe un placebo. Este último grupo se denomina:",
            options = listOf("Grupo experimental", "Grupo de control", "Grupo de tratamiento", "Grupo aleatorio"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 206,
            text = "Si la probabilidad de que un estudiante apruebe un examen es de 0.7, y la de que apruebe otro examen diferente e independiente es de 0.8, ¿cuál es la probabilidad de que apruebe ambos?",
            options = listOf("1.5", "0.56", "0.75", "0.5"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 207,
            text = "El concepto de 'desarrollo sostenible' implica equilibrar el crecimiento económico, la equidad social y:",
            options = listOf("La innovación tecnológica", "La globalización", "La protección del medio ambiente", "La estabilidad política"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 208,
            text = "Una empresa tiene costos fijos de \$20.000.000 y costos variables por unidad de \$5.000. Si vende cada unidad a \$10.000, ¿cuántas unidades debe vender para alcanzar el punto de equilibrio?",
            options = listOf("2.000", "3.000", "4.000", "5.000"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 209,
            text = "La afirmación 'La Tierra es el centro del universo' fue refutada principalmente por el modelo heliocéntrico propuesto por:",
            options = listOf("Ptolomeo", "Aristóteles", "Nicolás Copérnico", "Isaac Newton"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 210,
            text = "Un gerente se enfrenta a la decisión de despedir a un empleado poco productivo o invertir en su capacitación, considerando el impacto en la moral del equipo y los costos. Este es un dilema fundamentalmente:",
            options = listOf("Financiero", "Legal", "Ético y de gestión", "Técnico"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 211,
            text = "Si el PIB de un país crece un 3% y su población crece un 1%, el crecimiento del PIB per cápita es aproximadamente:",
            options = listOf("1%", "2%", "3%", "4%"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 212,
            text = "En la filosofía de Platón, el 'mundo de las Ideas' representa:",
            options = listOf("La realidad material y sensible.", "Una utopía social inalcanzable.", "La verdadera realidad, eterna e inmutable, accesible por la razón.", "Las opiniones subjetivas de cada individuo."),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 213,
            text = "Una encuesta revela que el 60% de los encuestados prefiere el producto A, con un margen de error del +/- 3% y un nivel de confianza del 95%. Esto significa que:",
            options = listOf("Exactamente el 60% de la población prefiere el producto A.", "Es 95% seguro que entre el 57% y el 63% de la población prefiere el producto A.", "El 3% de los encuestados podría haber mentido.", "El producto A es definitivamente mejor que otros."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 214,
            text = "La globalización económica ha llevado, entre otras cosas, a:",
            options = listOf("Una menor interdependencia entre países.", "Un aumento en las barreras comerciales.", "Una mayor integración de los mercados financieros y de bienes.", "El fortalecimiento de las economías locales exclusivamente."),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 215,
            text = "El Teorema de Pitágoras (a² + b² = c²) se aplica a qué tipo de triángulos?",
            options = listOf("Equiláteros", "Isósceles", "Escalenos", "Rectángulos"),
            correctAnswerIndex = 3,
            category = "saber_pro"
        ),
        Question(
            id = 216,
            text = "Un argumento que concluye que, dado que dos cosas son similares en algunos aspectos, también deben serlo en otros, es un argumento por:",
            options = listOf("Deducción", "Inducción", "Autoridad", "Analogía"),
            correctAnswerIndex = 3,
            category = "saber_pro"
        ),
        Question(
            id = 217,
            text = "Si la desviación estándar de un conjunto de datos es grande, esto indica que:",
            options = listOf("Los datos están muy agrupados alrededor de la media.", "Los datos están muy dispersos respecto a la media.", "La media es un valor atípico.", "No hay valores atípicos en los datos."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 218,
            text = "El concepto de 'Estado de Derecho' implica que:",
            options = listOf("El Estado tiene poder absoluto sobre los ciudadanos.", "Los gobernantes están por encima de la ley.", "Tanto gobernantes como gobernados están sometidos a la ley.", "La ley puede ser ignorada en situaciones de emergencia."),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 219,
            text = "Si una función f(x) = 2x² - 3x + 1, ¿cuál es el valor de f(2)?",
            options = listOf("1", "3", "8", "15"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 220,
            text = "En el contexto de la ética profesional, el principio de 'no maleficencia' significa:",
            options = listOf("Hacer siempre el mayor bien posible.", "No causar daño intencionadamente.", "Ser justo y equitativo en todas las acciones.", "Respetar la autonomía del cliente o paciente."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 221,
            text = "Una correlación positiva fuerte entre dos variables X e Y significa que:",
            options = listOf("Cuando X aumenta, Y tiende a disminuir.", "Cuando X aumenta, Y tiende a aumentar.", "No hay relación aparente entre X e Y.", "X causa Y."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 222,
            text = "Según la teoría de la oferta y la demanda, si el precio de un bien aumenta (ceteris paribus), la cantidad demandada de ese bien tiende a:",
            options = listOf("Aumentar", "Disminuir", "Permanecer igual", "Volverse indeterminada"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 223,
            text = "El cálculo del Valor Actual Neto (VAN) de un proyecto se utiliza para:",
            options = listOf("Determinar el tiempo que tarda en recuperarse la inversión.", "Medir la rentabilidad absoluta del proyecto en términos monetarios actuales.", "Calcular la tasa de rentabilidad interna del proyecto.", "Evaluar el riesgo del proyecto."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 224,
            text = "El principio de subsidiariedad en la organización social y política sugiere que:",
            options = listOf("Todas las decisiones deben ser tomadas por la autoridad central.", "Los problemas deben ser resueltos por la autoridad más cercana o de menor nivel posible.", "El Estado debe intervenir en todos los aspectos de la vida ciudadana.", "Las decisiones importantes solo pueden ser tomadas por expertos."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 225,
            text = "Si la derivada de una función f(x) en un punto x=a es positiva (f'(a) > 0), esto indica que la función en ese punto es:",
            options = listOf("Decreciente", "Creciente", "Constante", "Tiene un máximo local"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 226,
            text = "Un líder que toma decisiones consultando a su equipo y fomentando la participación, pero reteniendo la autoridad final, exhibe un estilo de liderazgo:",
            options = listOf("Autocrático", "Laissez-faire", "Democrático o participativo", "Transaccional"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 227,
            text = "Si una inversión de \$1.000 produce \$1.100 después de un año, la tasa de rendimiento simple es del:",
            options = listOf("5%", "10%", "11%", "100%"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 228,
            text = "En el contexto de la investigación científica, una hipótesis es:",
            options = listOf("Un hecho comprobado y universalmente aceptado.", "Una pregunta que guía la investigación.", "Una suposición o explicación provisional que debe ser probada.", "El resumen de los resultados obtenidos."),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 229,
            text = "La 'mano invisible' de Adam Smith se refiere a cómo:",
            options = listOf("La intervención gubernamental guía la economía eficientemente.", "Las acciones egoístas individuales pueden llevar al bienestar colectivo en un mercado libre.", "Las corporaciones controlan secretamente el mercado.", "Las crisis económicas son inevitables y misteriosas."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 230,
            text = "El área de un triángulo con base 10 cm y altura 6 cm es:",
            options = listOf("16 cm²", "30 cm²", "60 cm²", "100 cm²"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 231,
            text = "La validez de un argumento deductivo depende de:",
            options = listOf("La verdad de sus premisas únicamente.", "La estructura lógica que conecta las premisas con la conclusión.", "La aceptación general de la conclusión.", "La cantidad de evidencia empírica que lo respalda."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 232,
            text = "Si el costo marginal de producir una unidad adicional es mayor que el ingreso marginal obtenido por venderla, la empresa debería:",
            options = listOf("Aumentar la producción.", "Disminuir la producción.", "Mantener la producción igual.", "Cerrar inmediatamente."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 233,
            text = "El concepto de 'accountability' en la gestión pública se refiere principalmente a:",
            options = listOf("La eficiencia en el uso de los recursos.", "La obligación de los funcionarios de rendir cuentas por sus acciones.", "La popularidad de las políticas implementadas.", "La capacidad de generar superávit fiscal."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 234,
            text = "Si una matriz A tiene 3 filas y 4 columnas, y una matriz B tiene 4 filas y 2 columnas, el producto A x B será una matriz de:",
            options = listOf("3 filas y 2 columnas", "4 filas y 4 columnas", "3 filas y 4 columnas", "No se pueden multiplicar"),
            correctAnswerIndex = 0,
            category = "saber_pro"
        ),
        Question(
            id = 235,
            text = "Un conflicto de interés surge cuando:",
            options = listOf("Dos personas tienen opiniones diferentes sobre un tema.", "Los intereses personales de un individuo podrían influir indebidamente en sus deberes profesionales.", "Una empresa compite con otra en el mercado.", "Un proyecto no cumple con sus objetivos iniciales."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 236,
            text = "Si la tasa de interés nominal es del 8% y la tasa de inflación es del 3%, la tasa de interés real aproximada es del:",
            options = listOf("3%", "5%", "8%", "11%"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 237,
            text = "La falsabilidad, según Karl Popper, es un criterio clave para distinguir:",
            options = listOf("La verdad de la falsedad.", "Las teorías científicas de las no científicas.", "Los argumentos válidos de los inválidos.", "Los hechos de las opiniones."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 238,
            text = "El Producto Interno Bruto (PIB) mide:",
            options = listOf("La riqueza total de los ciudadanos de un país, incluyendo activos en el extranjero.", "El valor de todos los bienes y servicios finales producidos dentro de un país en un período determinado.", "Los ingresos totales del gobierno de un país.", "El nivel de bienestar de la población de un país."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 239,
            text = "Una organización que divide el trabajo en tareas especializadas y coordina estas tareas mediante una jerarquía clara está utilizando el principio de:",
            options = listOf("Centralización", "Descentralización", "División del trabajo y jerarquía de autoridad", "Gestión de la calidad total"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 240,
            text = "La integral definida de ∫(de 0 a 1) 2x dx es igual a:",
            options = listOf("0", "1", "2", "0.5"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 241,
            text = "Una empresa está considerando dos proyectos mutuamente excluyentes. El Proyecto A tiene un VAN de \$50.000 y el Proyecto B tiene un VAN de \$60.000. ¿Cuál proyecto debería elegir?",
            options = listOf("Proyecto A", "Proyecto B", "Ambos", "Ninguno si la TIR es baja"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 242,
            text = "En un sistema democrático, la separación de poderes (ejecutivo, legislativo, judicial) busca principalmente:",
            options = listOf("Aumentar la eficiencia del gobierno.", "Concentrar el poder en una sola rama para decisiones rápidas.", "Prevenir el abuso de poder y garantizar los controles y equilibrios.", "Reducir la participación ciudadana en la política."),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 243,
            text = "Si el precio de las acciones de una empresa baja significativamente después de un anuncio de malos resultados, esto es un ejemplo de eficiencia del mercado en su forma:",
            options = listOf("Débil", "Semilineal", "Fuerte", "Semi-fuerte"),
            correctAnswerIndex = 3,
            category = "saber_pro"
        ),
        Question(
            id = 244,
            text = "El costo de oportunidad de una decisión es:",
            options = listOf("El precio monetario directo de la opción elegida.", "El valor de la mejor alternativa sacrificada al tomar una decisión.", "Todos los costos hundidos asociados con la decisión.", "Los beneficios futuros esperados de la opción elegida."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 245,
            text = "Resolver la ecuación log₂(x) = 3 para x.",
            options = listOf("x = 1.5", "x = 6", "x = 8", "x = 9"),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 246,
            text = "El concepto de 'responsabilidad social empresarial' (RSE) implica que las empresas:",
            options = listOf("Solo deben enfocarse en maximizar las ganancias para los accionistas.", "Deben considerar el impacto de sus operaciones en la sociedad y el medio ambiente, además de sus objetivos económicos.", "Son responsables únicamente ante las autoridades gubernamentales.", "Deben donar un porcentaje fijo de sus ganancias a la caridad."),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 247,
            text = "Si en una muestra de 100 personas, 20 tienen una característica particular, la proporción muestral es:",
            options = listOf("0.02", "0.20", "20", "80"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 248,
            text = "Un sistema de votación donde el candidato que obtiene más votos gana, incluso si no es la mayoría absoluta, se llama:",
            options = listOf("Votación proporcional", "Votación por mayoría simple (pluralidad)", "Votación por mayoría absoluta", "Votación preferencial"),
            correctAnswerIndex = 1,
            category = "saber_pro"
        ),
        Question(
            id = 249,
            text = "El punto donde la curva de oferta y la curva de demanda se intersectan representa:",
            options = listOf("El precio máximo legal.", "El precio mínimo legal.", "El precio y la cantidad de equilibrio del mercado.", "Un excedente de producción."),
            correctAnswerIndex = 2,
            category = "saber_pro"
        ),
        Question(
            id = 250,
            text = "Si A y B son dos eventos mutuamente excluyentes, la probabilidad de que ocurra A o B (P(A ∪ B)) es:",
            options = listOf("P(A) + P(B)", "P(A) * P(B)", "P(A) + P(B) - P(A ∩ B)", "P(A) / P(B)"),
            correctAnswerIndex = 0,
            category = "saber_pro"
        )
    )

    fun getQuestions(category: String, count: Int): List<Question> {
        return when (category) {
            "general_knowledge" -> generalKnowledgeQuestions.shuffled().take(count)
            "icfes" -> icfesQuestions.shuffled().take(count)
            "saber_pro" -> saberProQuestions.shuffled().take(count)
            else -> emptyList()
        } // Eliminé el .take(100) al final, asumiendo que las listas ya tienen el tamaño deseado
        // y `count` determinará cuántas tomar. Si quieres limitar el máximo a 100 independientemente
        // del tamaño de las listas, puedes volver a añadir `.take(100)`.
    }
}