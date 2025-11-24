package com.example.ur_color.data.user.aura

import android.content.Context
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.user.UserData
import com.example.ur_color.utils.logDebug
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

    suspend fun applyDailyResult(context: Context, user: UserData) {
        var updated = user

        for ((type, list) in accumulators) {
            if (list.isEmpty()) continue

            val oldValue = getValue(user, type)

            // средний вклад всех вопросов по этой переменной
            val avgDelta = list.average()

            val newValue = (oldValue + avgDelta).roundToInt().coerceIn(1, 10)

            updated = updateUserValue(updated, type, newValue)
        }

        PersonalDataManager.updateUser(context, updated)

        val aura = AuraGenerator.applyDynamicAura(updated)
        if (aura != null) PersonalDataManager.updateAura(context, aura)

        resetDaily()
    }

    private fun updateUserValue(user: UserData, type: ModType, value: Int): UserData {
        fun vec(v: List<Int>) = updateVector(v, value)

        return when (type) {
            ModType.ENERGY_LEVEL -> user.copy(
                energyLevel = value,
                energyCapacity = vec(user.energyCapacity)
            )
            ModType.MOOD -> user.copy(
                mood = value,
                moodVector = vec(user.moodVector)
            )
            ModType.STRESS_LEVEL -> user.copy(
                stressLevel = value,
                stressVector = vec(user.stressVector)
            )
            ModType.FOCUS -> user.copy(
                focus = value,
                focusVector = vec(user.focusVector)
            )
            ModType.MOTIVATION -> user.copy(
                motivation = value,
                motivationVector = vec(user.motivationVector)
            )
            ModType.CREATIVITY -> user.copy(
                creativity = value,
                creativityVector = vec(user.creativityVector)
            )
            ModType.EMOTIONAL_BALANCE -> user.copy(
                emotionalBalance = value,
                emotionalBalanceVector = vec(user.emotionalBalanceVector)
            )
            ModType.PHYSICAL_ENERGY -> user.copy(
                physicalEnergy = value,
                physicalEnergyVector = vec(user.physicalEnergyVector)
            )
            ModType.SLEEP_QUALITY -> user.copy(
                sleepQuality = value,
                sleepQualityVector = vec(user.sleepQualityVector)
            )
            ModType.INTUITION_LEVEL -> user.copy(
                intuitionLevel = value,
                intuitionVector = vec(user.intuitionVector)
            )
            ModType.SOCIAL_ENERGY -> user.copy(
                socialEnergy = value,
                socialVector = vec(user.socialVector)
            )
            ModType.DOMINANT_COLOR -> user
        }
    }

    private fun getValue(user: UserData, type: ModType): Int =
        when (type) {
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