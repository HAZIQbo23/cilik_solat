package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CharacterType
import com.example.data.repository.PreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PreferencesRepository(application)

    val studentName: StateFlow<String> = repository.studentName
    val selectedCharacter: StateFlow<CharacterType> = repository.selectedCharacter
    val starsCount: StateFlow<Int> = repository.starsCount
    val completedWudukSteps: StateFlow<Set<Int>> = repository.completedWudukSteps
    val masteredRukunSteps: StateFlow<Set<Int>> = repository.masteredRukunSteps
    val masteredBacaanIds: StateFlow<Set<Int>> = repository.masteredBacaanIds
    val unlockedBadges: StateFlow<Set<String>> = repository.unlockedBadges
    val quizHighScore: StateFlow<Int> = repository.quizHighScore
    val musicEnabled: StateFlow<Boolean> = repository.musicEnabled
    val sfxEnabled: StateFlow<Boolean> = repository.sfxEnabled
    val isEnglish: StateFlow<Boolean> = repository.isEnglish
    val dailySolatChecks: StateFlow<Set<String>> = repository.dailySolatChecks

    fun setStudentName(name: String) = repository.setStudentName(name)
    fun setSelectedCharacter(character: CharacterType) = repository.setSelectedCharacter(character)
    fun addStars(amount: Int) = repository.addStars(amount)
    fun markWudukStepCompleted(step: Int) = repository.markWudukStepCompleted(step)
    fun markRukunMastered(rukunId: Int) = repository.markRukunMastered(rukunId)
    fun markBacaanMastered(bacaanId: Int) = repository.markBacaanMastered(bacaanId)
    fun updateQuizScore(score: Int) = repository.updateQuizScore(score)
    fun toggleDailySolat(solatName: String) = repository.toggleDailySolat(solatName)
    fun setMusicEnabled(enabled: Boolean) = repository.setMusicEnabled(enabled)
    fun setSfxEnabled(enabled: Boolean) = repository.setSfxEnabled(enabled)
    fun setIsEnglish(english: Boolean) = repository.setIsEnglish(english)
    fun resetAllProgress() = repository.resetAllProgress()
}
