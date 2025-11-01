package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.local.dataManager.UserDataManager
import com.example.ur_color.data.user.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuraDetailsViewModel(

) : BaseViewModel() {

    private fun updateVector(
        oldVector: List<Int>,
        value: Int,
        maxSize: Int = 10
    ): List<Int> {
        val newList = oldVector + value
            .coerceIn(1, 10)

        val result = if (newList.size > maxSize) newList.takeLast(maxSize) else newList
        return result
    }

    fun consumeAnswer(context: Context, question: Question, answer: Int) {

        viewModelScope.launch {
            UserDataManager.update(context) { user ->
                var updatedUser = user

                for (mod in question.mods) {

                    val oldValue = when (mod.targetVariable) {
                        ModType.ENERGY_LEVEL -> user.energyLevel
                        ModType.MOOD -> user.mood
                        ModType.STRESS_LEVEL -> user.stressLevel
                        ModType.FOCUS -> user.focus
                        ModType.MOTIVATION -> user.motivation
                        ModType.CREATIVITY -> user.creativity
                        ModType.EMOTIONAL_BALANCE -> user.emotionalBalance
                        ModType.PHYSICAL_ENERGY -> user.physicalEnergy
                        ModType.SLEEP_QUALITY -> user.sleepQuality
                        ModType.INTUITION_LEVEL -> user.intuitionLevel
                        ModType.SOCIAL_ENERGY -> user.socialEnergy
                        ModType.DOMINANT_COLOR -> 0
                    }

                    val delta = (answer * mod.factor).toInt()
                    val newValue = (oldValue + delta).coerceIn(1, 10)

                    updatedUser = when (mod.targetVariable) {
                        ModType.ENERGY_LEVEL -> updatedUser.copy(
                            energyLevel = newValue,
                            energyCapacity = if (mod.useVector)
                                updateVector(user.energyCapacity, newValue)
                            else user.energyCapacity
                        )

                        ModType.MOOD -> updatedUser.copy(
                            mood = newValue,
                            moodVector = if (mod.useVector)
                                updateVector(user.moodVector, newValue)
                            else user.moodVector
                        )

                        ModType.STRESS_LEVEL -> updatedUser.copy(
                            stressLevel = newValue,
                            stressVector = if (mod.useVector)
                                updateVector(user.stressVector, newValue)
                            else user.stressVector
                        )

                        ModType.FOCUS -> updatedUser.copy(
                            focus = newValue,
                            focusVector = if (mod.useVector)
                                updateVector(user.focusVector, newValue)
                            else user.focusVector
                        )

                        ModType.MOTIVATION -> updatedUser.copy(
                            motivation = newValue,
                            motivationVector = if (mod.useVector)
                                updateVector(user.motivationVector, newValue)
                            else user.motivationVector
                        )

                        ModType.CREATIVITY -> updatedUser.copy(
                            creativity = newValue,
                            creativityVector = if (mod.useVector)
                                updateVector(user.creativityVector, newValue)
                            else user.creativityVector
                        )

                        ModType.EMOTIONAL_BALANCE -> updatedUser.copy(
                            emotionalBalance = newValue,
                            emotionalBalanceVector = if (mod.useVector)
                                updateVector(user.emotionalBalanceVector, newValue)
                            else user.emotionalBalanceVector
                        )

                        ModType.PHYSICAL_ENERGY -> updatedUser.copy(
                            physicalEnergy = newValue,
                            physicalEnergyVector = if (mod.useVector)
                                updateVector(user.physicalEnergyVector, newValue)
                            else user.physicalEnergyVector
                        )

                        ModType.SLEEP_QUALITY -> updatedUser.copy(
                            sleepQuality = newValue,
                            sleepQualityVector = if (mod.useVector)
                                updateVector(user.sleepQualityVector, newValue)
                            else user.sleepQualityVector
                        )

                        ModType.INTUITION_LEVEL -> updatedUser.copy(
                            intuitionLevel = newValue,
                            intuitionVector = if (mod.useVector)
                                updateVector(user.intuitionVector, newValue)
                            else user.intuitionVector
                        )

                        ModType.SOCIAL_ENERGY -> updatedUser.copy(
                            socialEnergy = newValue,
                            socialVector = if (mod.useVector)
                                updateVector(user.socialVector, newValue)
                            else user.socialVector
                        )

                        ModType.DOMINANT_COLOR -> updatedUser
                    }
                }
                updatedUser
            }
        }
    }
}