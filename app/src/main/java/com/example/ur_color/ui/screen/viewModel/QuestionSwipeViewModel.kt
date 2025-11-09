package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.UserDataManager
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.user.aura.AuraGenerator
import com.example.ur_color.utils.logDebug
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class QuestionSwipeViewModel() : BaseViewModel() {

    fun consumeAnswer(context: Context, question: Question, answer: Boolean) {
        viewModelScope.launch {
            var userData = user.value ?: return@launch
            logDebug("question ${question}")
            logDebug(userData)

            for (mod in question.mods) {

                val oldValue = when (mod.targetVariable) {
                    ModType.ENERGY_LEVEL -> userData.energyLevel
                    ModType.MOOD -> userData.mood
                    ModType.STRESS_LEVEL -> userData.stressLevel
                    ModType.FOCUS -> userData.focus
                    ModType.MOTIVATION -> userData.motivation
                    ModType.CREATIVITY -> userData.creativity
                    ModType.EMOTIONAL_BALANCE -> userData.emotionalBalance
                    ModType.PHYSICAL_ENERGY -> userData.physicalEnergy
                    ModType.SLEEP_QUALITY -> userData.sleepQuality
                    ModType.INTUITION_LEVEL -> userData.intuitionLevel
                    ModType.SOCIAL_ENERGY -> userData.socialEnergy
                    ModType.DOMINANT_COLOR -> 0
                }

                val diff = (10 * mod.factor).roundToInt()
                val delta = if (answer) diff else -diff

                val newValue = (oldValue + delta).coerceIn(1, 10)

                userData = when (mod.targetVariable) {

                    ModType.ENERGY_LEVEL -> {
//                        logDebug("delta - ${delta} (${answer * mod.factor}) \nanswer: ${answer} factor: ${mod.factor}\nenergyLevel old ${oldValue} | new ${newValue} \n${updateVector(userData.energyCapacity, newValue)}")
                        userData.copy(
                            energyLevel = newValue,
                            energyCapacity = if (mod.useVector)
                                updateVector(userData.energyCapacity, newValue)
                            else userData.energyCapacity
                        )
                    }

                    ModType.MOOD -> {
                        logDebug("mood old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.moodVector, newValue)}")
                        userData.copy(
                            mood = newValue,
                            moodVector = if (mod.useVector)
                                updateVector(userData.moodVector, newValue)
                            else userData.moodVector
                        )
                    }

                    ModType.STRESS_LEVEL -> {
                        logDebug("stressLevel old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.stressVector, newValue)}")
                        userData.copy(
                            stressLevel = newValue,
                            stressVector = if (mod.useVector)
                                updateVector(userData.stressVector, newValue)
                            else userData.stressVector
                        )
                    }

                    ModType.FOCUS -> {
                        logDebug("focus old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.focusVector, newValue)}")
                        userData.copy(
                            focus = newValue,
                            focusVector = if (mod.useVector)
                                updateVector(userData.focusVector, newValue)
                            else userData.focusVector
                        )
                    }

                    ModType.MOTIVATION -> {
                        logDebug("motivation old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.motivationVector, newValue)}")
                        userData.copy(
                            motivation = newValue,
                            motivationVector = if (mod.useVector)
                                updateVector(userData.motivationVector, newValue)
                            else userData.motivationVector
                        )
                    }

                    ModType.CREATIVITY -> {
                        logDebug("creativity old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.creativityVector, newValue)}")
                        userData.copy(
                            creativity = newValue,
                            creativityVector = if (mod.useVector)
                                updateVector(userData.creativityVector, newValue)
                            else userData.creativityVector
                        )
                    }

                    ModType.EMOTIONAL_BALANCE -> {
                        logDebug("emotionalBalance old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.emotionalBalanceVector, newValue)}")
                        userData.copy(
                            emotionalBalance = newValue,
                            emotionalBalanceVector = if (mod.useVector)
                                updateVector(userData.emotionalBalanceVector, newValue)
                            else userData.emotionalBalanceVector
                        )
                    }

                    ModType.PHYSICAL_ENERGY -> {
                        logDebug("physicalEnergy old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.physicalEnergyVector, newValue)}")
                        userData.copy(
                            physicalEnergy = newValue,
                            physicalEnergyVector = if (mod.useVector)
                                updateVector(userData.physicalEnergyVector, newValue)
                            else userData.physicalEnergyVector
                        )
                    }

                    ModType.SLEEP_QUALITY -> {
                        logDebug("sleepQuality old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.sleepQualityVector, newValue)}")
                        userData.copy(
                            sleepQuality = newValue,
                            sleepQualityVector = if (mod.useVector)
                                updateVector(userData.sleepQualityVector, newValue)
                            else userData.sleepQualityVector
                        )
                    }

                    ModType.INTUITION_LEVEL -> {
                        logDebug("intuitionLevel old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.intuitionVector, newValue)}")
                        userData.copy(
                            intuitionLevel = newValue,
                            intuitionVector = if (mod.useVector)
                                updateVector(userData.intuitionVector, newValue)
                            else userData.intuitionVector
                        )
                    }

                    ModType.SOCIAL_ENERGY -> {
                        logDebug("socialEnergy old ${oldValue} | new ${newValue} \n/ ${updateVector(userData.socialVector, newValue)}")
                        userData.copy(
                            socialEnergy = newValue,
                            socialVector = if (mod.useVector)
                                updateVector(userData.socialVector, newValue)
                            else userData.socialVector
                        )
                    }

                    ModType.DOMINANT_COLOR -> userData
                }
            }

            UserDataManager.updateUser(context, userData)

            val aura = AuraGenerator.applyDynamicAura(user = userData) ?: return@launch
            UserDataManager.updateAura(context, aura)
        }
    }

    private fun updateVector(
        oldVector: List<Int>,
        value: Int,
        maxSize: Int = 10
    ): List<Int> {

        val newList = oldVector + value.coerceIn(1, 10)
        val result = if (newList.size > maxSize) newList.takeLast(maxSize) else newList

        return result
    }
}