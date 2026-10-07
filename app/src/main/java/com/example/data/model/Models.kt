package com.example.data.model

import com.example.R

enum class CharacterType(
    val displayName: String,
    val greeting: String,
    val introSpeech: String,
    val drawableRes: Int
) {
    AHMAD(
        displayName = "Ahmad",
        greeting = "Assalamualaikum! Saya Ahmad!",
        introSpeech = "Assalamualaikum kawan-kawan! Jom kita belajar solat bersama-sama saya!",
        drawableRes = R.drawable.char_ahmad
    ),
    AISYAH(
        displayName = "Aisyah",
        greeting = "Assalamualaikum! Saya Aisyah!",
        introSpeech = "Assalamualaikum kawan-kawan! Saya Aisyah, seronoknya dapat belajar solat sama-sama!",
        drawableRes = R.drawable.char_aisyah
    )
}

enum class RukunCategory(
    val title: String,
    val description: String
) {
    QALBI("Rukun Qalbi", "Dilakukan di dalam hati"),
    FI_LI("Rukun Fi'li", "Dilakukan dengan perbuatan fizikal"),
    QAULI("Rukun Qauli", "Dilafazkan dengan bacaan suara yang didengari sendiri")
}

data class WudukStep(
    val stepNumber: Int,
    val title: String,
    val arabicText: String,
    val transliteration: String,
    val translation: String,
    val kidTips: String,
    val isSunat: Boolean = false
)

data class RukunSolat(
    val id: Int,
    val number: Int,
    val name: String,
    val category: RukunCategory,
    val arabicLafaz: String,
    val transliteration: String,
    val meaning: String,
    val kidGuide: String,
    val motionTips: String
)

data class BacaanSolat(
    val id: Int,
    val number: Int,
    val name: String,
    val arabicText: String,
    val transliteration: String,
    val meaning: String,
    val tips: String
)

data class SolatWaktu(
    val id: Int,
    val name: String,
    val rakaat: Int,
    val timeDescription: String,
    val niatArab: String,
    val niatRumi: String,
    val niatMaksud: String
)

data class QuizQuestion(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class BadgeItem(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val targetType: String,
    val requiredCount: Int
)

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CharacterSelect : Screen("character_select")
    object MainMenu : Screen("main_menu")
    object WudukModule : Screen("wuduk_module")
    object RukunList : Screen("rukun_list")
    data class RukunDetail(val rukunId: Int) : Screen("rukun_detail/$rukunId")
    object BacaanList : Screen("bacaan_list")
    object Quiz : Screen("quiz")
    object Reward : Screen("reward")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object MiniGame : Screen("mini_game") // Screen 12: Kuiz Mini Susun Rukun & Simulasi Solat
    object SolatWaktuGuide : Screen("solat_waktu_guide")
}
