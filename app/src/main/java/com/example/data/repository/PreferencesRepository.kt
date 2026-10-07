package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.CharacterType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("cilik_solat_prefs", Context.MODE_PRIVATE)

    private val _studentName = MutableStateFlow(prefs.getString("student_name", "Haziq") ?: "Haziq")
    val studentName: StateFlow<String> = _studentName.asStateFlow()

    private val _selectedCharacter = MutableStateFlow(
        try {
            CharacterType.valueOf(prefs.getString("selected_character", CharacterType.AHMAD.name) ?: CharacterType.AHMAD.name)
        } catch (e: Exception) {
            CharacterType.AHMAD
        }
    )
    val selectedCharacter: StateFlow<CharacterType> = _selectedCharacter.asStateFlow()

    private val _starsCount = MutableStateFlow(prefs.getInt("stars_count", 25))
    val starsCount: StateFlow<Int> = _starsCount.asStateFlow()

    private val _completedWudukSteps = MutableStateFlow(
        prefs.getStringSet("completed_wuduk", setOf("1", "2"))?.mapNotNull { it.toIntOrNull() }?.toSet() ?: setOf(1, 2)
    )
    val completedWudukSteps: StateFlow<Set<Int>> = _completedWudukSteps.asStateFlow()

    private val _masteredRukunSteps = MutableStateFlow(
        prefs.getStringSet("mastered_rukun", setOf("1", "2", "3"))?.mapNotNull { it.toIntOrNull() }?.toSet() ?: setOf(1, 2, 3)
    )
    val masteredRukunSteps: StateFlow<Set<Int>> = _masteredRukunSteps.asStateFlow()

    private val _masteredBacaanIds = MutableStateFlow(
        prefs.getStringSet("mastered_bacaan", setOf("1", "3"))?.mapNotNull { it.toIntOrNull() }?.toSet() ?: setOf(1, 3)
    )
    val masteredBacaanIds: StateFlow<Set<Int>> = _masteredBacaanIds.asStateFlow()

    private val _unlockedBadges = MutableStateFlow(
        prefs.getStringSet("unlocked_badges", setOf("badge_wuduk")) ?: setOf("badge_wuduk")
    )
    val unlockedBadges: StateFlow<Set<String>> = _unlockedBadges.asStateFlow()

    private val _quizHighScore = MutableStateFlow(prefs.getInt("quiz_high_score", 100))
    val quizHighScore: StateFlow<Int> = _quizHighScore.asStateFlow()

    private val _musicEnabled = MutableStateFlow(prefs.getBoolean("music_enabled", true))
    val musicEnabled: StateFlow<Boolean> = _musicEnabled.asStateFlow()

    private val _sfxEnabled = MutableStateFlow(prefs.getBoolean("sfx_enabled", true))
    val sfxEnabled: StateFlow<Boolean> = _sfxEnabled.asStateFlow()

    private val _isEnglish = MutableStateFlow(prefs.getBoolean("is_english", false))
    val isEnglish: StateFlow<Boolean> = _isEnglish.asStateFlow()

    private val _dailySolatChecks = MutableStateFlow(
        prefs.getStringSet("daily_solat_checks", setOf("Subuh", "Zohor")) ?: setOf("Subuh", "Zohor")
    )
    val dailySolatChecks: StateFlow<Set<String>> = _dailySolatChecks.asStateFlow()

    fun setStudentName(name: String) {
        val trimmed = name.trim().ifEmpty { "Adik Cilik" }
        prefs.edit().putString("student_name", trimmed).apply()
        _studentName.value = trimmed
    }

    fun setSelectedCharacter(character: CharacterType) {
        prefs.edit().putString("selected_character", character.name).apply()
        _selectedCharacter.value = character
    }

    fun addStars(amount: Int) {
        val newTotal = (_starsCount.value + amount).coerceAtLeast(0)
        prefs.edit().putInt("stars_count", newTotal).apply()
        _starsCount.value = newTotal
        checkBadgeUnlocks()
    }

    fun markWudukStepCompleted(step: Int) {
        val current = _completedWudukSteps.value.toMutableSet()
        if (current.add(step)) {
            prefs.edit().putStringSet("completed_wuduk", current.map { it.toString() }.toSet()).apply()
            _completedWudukSteps.value = current
            addStars(3)
        }
    }

    fun markRukunMastered(rukunId: Int) {
        val current = _masteredRukunSteps.value.toMutableSet()
        if (current.add(rukunId)) {
            prefs.edit().putStringSet("mastered_rukun", current.map { it.toString() }.toSet()).apply()
            _masteredRukunSteps.value = current
            addStars(3)
        }
    }

    fun markBacaanMastered(bacaanId: Int) {
        val current = _masteredBacaanIds.value.toMutableSet()
        if (current.add(bacaanId)) {
            prefs.edit().putStringSet("mastered_bacaan", current.map { it.toString() }.toSet()).apply()
            _masteredBacaanIds.value = current
            addStars(3)
        }
    }

    fun updateQuizScore(score: Int) {
        if (score > _quizHighScore.value) {
            prefs.edit().putInt("quiz_high_score", score).apply()
            _quizHighScore.value = score
        }
        addStars(score / 10)
        checkBadgeUnlocks()
    }

    fun toggleDailySolat(solatName: String) {
        val current = _dailySolatChecks.value.toMutableSet()
        if (current.contains(solatName)) {
            current.remove(solatName)
        } else {
            current.add(solatName)
            addStars(5)
        }
        prefs.edit().putStringSet("daily_solat_checks", current).apply()
        _dailySolatChecks.value = current
    }

    fun setMusicEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("music_enabled", enabled).apply()
        _musicEnabled.value = enabled
    }

    fun setSfxEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("sfx_enabled", enabled).apply()
        _sfxEnabled.value = enabled
    }

    fun setIsEnglish(english: Boolean) {
        prefs.edit().putBoolean("is_english", english).apply()
        _isEnglish.value = english
    }

    private fun checkBadgeUnlocks() {
        val currentBadges = _unlockedBadges.value.toMutableSet()
        if (_completedWudukSteps.value.size >= 8) currentBadges.add("badge_wuduk")
        if (_masteredRukunSteps.value.size >= 13) currentBadges.add("badge_solat")
        if (_quizHighScore.value >= 50) currentBadges.add("badge_kuiz")
        if (_masteredBacaanIds.value.size >= 5) currentBadges.add("badge_bacaan")
        if (_starsCount.value >= 50) currentBadges.add("badge_hebat")

        if (currentBadges != _unlockedBadges.value) {
            prefs.edit().putStringSet("unlocked_badges", currentBadges).apply()
            _unlockedBadges.value = currentBadges
        }
    }

    fun resetAllProgress() {
        prefs.edit().clear().apply()
        _studentName.value = "Haziq"
        _selectedCharacter.value = CharacterType.AHMAD
        _starsCount.value = 10
        _completedWudukSteps.value = emptySet()
        _masteredRukunSteps.value = emptySet()
        _masteredBacaanIds.value = emptySet()
        _unlockedBadges.value = emptySet()
        _quizHighScore.value = 0
        _musicEnabled.value = true
        _sfxEnabled.value = true
        _dailySolatChecks.value = emptySet()
    }
}
