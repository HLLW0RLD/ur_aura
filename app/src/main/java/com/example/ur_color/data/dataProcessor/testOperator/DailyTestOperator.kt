package com.example.ur_color.data.dataProcessor.testOperator

import android.content.Context
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.model.user.CharacteristicData
import com.example.ur_color.data.model.user.UserData
import kotlin.collections.iterator
import kotlin.math.roundToInt

object DailyTestOperator {

    private val accumulators = mutableMapOf<ModType, MutableList<Float>>()

    fun consumeAnswer(question: Question, answer: Boolean) {
        for (mod in question.mods) {
            val diff = (10 * mod.factor)
            val delta = if (answer) diff else -diff

            accumulators
                .getOrPut(mod.targetVariable) { mutableListOf() }
                .add(delta.toFloat())
        }
    }

    suspend fun applyDailyResult(context: Context, user: UserData?) {
        if (user == null) return
        var updated = user!!

        for ((type, list) in accumulators) {
            if (list.isEmpty()) continue

            val oldValue = getValue(user, type)

            // средний вклад всех вопросов по этой переменной
            val avgDelta = list.average()

            val newValue = (oldValue + avgDelta).roundToInt().coerceIn(1, 10)

            updated = updateUserValue(updated, type, newValue)
        }

        PersonalDataManager.saveUser(context, updated)

        val aura = AuraGenerator.applyDynamicAura(updated)
        if (aura != null) PersonalDataManager.saveAura(context, aura)

        resetDaily()
    }

    private fun updateUserValue(user: UserData, type: ModType, value: Int): UserData {
        fun vec(v: List<Int>) = updateVector(v, value)

        return when (type) {
            ModType.ENERGY_LEVEL -> {
                user.characteristics.copy(
                    energyLevel = value,
                    energyCapacity = vec(user.characteristics.energyCapacity)
                )
                user
            }
            ModType.MOOD -> {
                user.characteristics.copy(
                    mood = value,
                    moodVector = vec(user.characteristics.moodVector)
                )
                user
            }
            ModType.STRESS_LEVEL -> {
                user.characteristics.copy(
                    stressLevel = value,
                    stressVector = vec(user.characteristics.stressVector)
                )
                user
            }
            ModType.FOCUS -> {
                user.characteristics.copy(
                    focus = value,
                    focusVector = vec(user.characteristics.focusVector)
                )
                user
            }
            ModType.MOTIVATION -> {
                user.characteristics.copy(
                    motivation = value,
                    motivationVector = vec(user.characteristics.motivationVector)
                )
                user
            }
            ModType.CREATIVITY -> {
                user.characteristics.copy(
                    creativity = value,
                    creativityVector = vec(user.characteristics.creativityVector)
                )
                user
            }
            ModType.EMOTIONAL_BALANCE -> {
                user.characteristics.copy(
                    emotionalBalance = value,
                    emotionalBalanceVector = vec(user.characteristics.emotionalBalanceVector)
                )
                user
            }
            ModType.PHYSICAL_ENERGY -> {
                user.characteristics.copy(
                    physicalEnergy = value,
                    physicalEnergyVector = vec(user.characteristics.physicalEnergyVector)
                )
                user
            }
            ModType.SLEEP_QUALITY -> {
                user.characteristics.copy(
                    sleepQuality = value,
                    sleepQualityVector = vec(user.characteristics.sleepQualityVector)
                )
                user
            }
            ModType.INTUITION_LEVEL -> {
                user.characteristics.copy(
                    intuitionLevel = value,
                    intuitionVector = vec(user.characteristics.intuitionVector)
                )
                user
            }
            ModType.SOCIAL_ENERGY -> {
                user.characteristics.copy(
                    socialEnergy = value,
                    socialVector = vec(user.characteristics.socialVector)
                )
                user
            }
            ModType.DOMINANT_COLOR -> user

        }
    }

    private fun getValue(user: UserData, type: ModType): Int =
        when (type) {
            ModType.ENERGY_LEVEL -> user.characteristics.energyLevel
            ModType.MOOD -> user.characteristics.mood
            ModType.STRESS_LEVEL -> user.characteristics.stressLevel
            ModType.FOCUS -> user.characteristics.focus
            ModType.MOTIVATION -> user.characteristics.motivation
            ModType.CREATIVITY -> user.characteristics.creativity
            ModType.EMOTIONAL_BALANCE -> user.characteristics.emotionalBalance
            ModType.PHYSICAL_ENERGY -> user.characteristics.physicalEnergy
            ModType.SLEEP_QUALITY -> user.characteristics.sleepQuality
            ModType.INTUITION_LEVEL -> user.characteristics.intuitionLevel
            ModType.SOCIAL_ENERGY -> user.characteristics.socialEnergy
            ModType.DOMINANT_COLOR -> 0
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

    fun resetDaily() {
        accumulators.clear()
    }
}