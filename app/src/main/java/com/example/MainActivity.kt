package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.CharacterType
import com.example.data.model.Screen
import com.example.ui.MainViewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.util.AudioHelper

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var audioHelper: AudioHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        audioHelper = AudioHelper(this)

        setContent {
            MyApplicationTheme {
                val studentName by viewModel.studentName.collectAsStateWithLifecycle()
                val selectedCharacter by viewModel.selectedCharacter.collectAsStateWithLifecycle()
                val starsCount by viewModel.starsCount.collectAsStateWithLifecycle()
                val completedWudukSteps by viewModel.completedWudukSteps.collectAsStateWithLifecycle()
                val masteredRukunSteps by viewModel.masteredRukunSteps.collectAsStateWithLifecycle()
                val masteredBacaanIds by viewModel.masteredBacaanIds.collectAsStateWithLifecycle()
                val unlockedBadges by viewModel.unlockedBadges.collectAsStateWithLifecycle()
                val quizHighScore by viewModel.quizHighScore.collectAsStateWithLifecycle()
                val musicEnabled by viewModel.musicEnabled.collectAsStateWithLifecycle()
                val sfxEnabled by viewModel.sfxEnabled.collectAsStateWithLifecycle()
                val isEnglish by viewModel.isEnglish.collectAsStateWithLifecycle()
                val dailySolatChecks by viewModel.dailySolatChecks.collectAsStateWithLifecycle()

                var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (val screen = currentScreen) {
                        is Screen.Home -> {
                            HomeScreen(
                                onStartClick = { currentScreen = Screen.CharacterSelect },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.CharacterSelect -> {
                            BackHandler { currentScreen = Screen.Home }
                            CharacterSelectScreen(
                                selectedCharacter = selectedCharacter,
                                onSelectCharacter = { viewModel.setSelectedCharacter(it) },
                                onProceed = { currentScreen = Screen.MainMenu },
                                onBackClick = { currentScreen = Screen.Home },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.MainMenu -> {
                            BackHandler { currentScreen = Screen.Home }
                            MainMenuScreen(
                                studentName = studentName,
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                completedWudukCount = completedWudukSteps.size,
                                masteredRukunCount = masteredRukunSteps.size,
                                masteredBacaanCount = masteredBacaanIds.size,
                                quizHighScore = quizHighScore,
                                onNavigateWuduk = { currentScreen = Screen.WudukModule },
                                onNavigateRukun = { currentScreen = Screen.RukunList },
                                onNavigateBacaan = { currentScreen = Screen.BacaanList },
                                onNavigateQuiz = { currentScreen = Screen.Quiz },
                                onNavigateMiniGame = { currentScreen = Screen.MiniGame },
                                onNavigateSolatWaktu = { currentScreen = Screen.SolatWaktuGuide },
                                onNavigateReward = { currentScreen = Screen.Reward },
                                onNavigateProfile = { currentScreen = Screen.Profile },
                                onNavigateSettings = { currentScreen = Screen.Settings },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.WudukModule -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            WudukModuleScreen(
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                completedSteps = completedWudukSteps,
                                onStepCompleted = { viewModel.markWudukStepCompleted(it) },
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.RukunList -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            RukunSolatListScreen(
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                masteredRukunIds = masteredRukunSteps,
                                onSelectRukun = { currentScreen = Screen.RukunDetail(it) },
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.RukunDetail -> {
                            BackHandler { currentScreen = Screen.RukunList }
                            RukunSolatDetailScreen(
                                rukunId = screen.rukunId,
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                isMastered = masteredRukunSteps.contains(screen.rukunId),
                                onMastered = { viewModel.markRukunMastered(it) },
                                onNavigateNext = { nextId -> currentScreen = Screen.RukunDetail(nextId) },
                                onNavigatePrev = { prevId -> currentScreen = Screen.RukunDetail(prevId) },
                                onBackClick = { currentScreen = Screen.RukunList },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.BacaanList -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            BacaanSolatScreen(
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                masteredBacaanIds = masteredBacaanIds,
                                onMasteredBacaan = { viewModel.markBacaanMastered(it) },
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.Quiz -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            QuizScreen(
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                onQuizCompleted = { score ->
                                    viewModel.updateQuizScore(score)
                                    currentScreen = Screen.Reward
                                },
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.Reward -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            RewardScreen(
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                unlockedBadges = unlockedBadges,
                                onBackToMenu = { currentScreen = Screen.MainMenu },
                                onPlayAgain = { currentScreen = Screen.Quiz },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.Profile -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            ProfileScreen(
                                studentName = studentName,
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                unlockedBadges = unlockedBadges,
                                completedWudukCount = completedWudukSteps.size,
                                masteredRukunCount = masteredRukunSteps.size,
                                masteredBacaanCount = masteredBacaanIds.size,
                                dailySolatChecks = dailySolatChecks,
                                onUpdateName = { viewModel.setStudentName(it) },
                                onSwitchCharacter = { viewModel.setSelectedCharacter(it) },
                                onToggleDailySolat = { viewModel.toggleDailySolat(it) },
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.Settings -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            SettingsScreen(
                                musicEnabled = musicEnabled,
                                sfxEnabled = sfxEnabled,
                                isEnglish = isEnglish,
                                onToggleMusic = { viewModel.setMusicEnabled(it) },
                                onToggleSfx = { viewModel.setSfxEnabled(it) },
                                onToggleLanguage = { viewModel.setIsEnglish(it) },
                                onResetProgress = { viewModel.resetAllProgress() },
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.MiniGame -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            MiniGameSolatScreen(
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                onEarnStars = { viewModel.addStars(it) },
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }

                        is Screen.SolatWaktuGuide -> {
                            BackHandler { currentScreen = Screen.MainMenu }
                            SolatWaktuScreen(
                                characterType = selectedCharacter,
                                starsCount = starsCount,
                                onBackClick = { currentScreen = Screen.MainMenu },
                                audioHelper = audioHelper
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioHelper.release()
    }
}
