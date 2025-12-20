package com.example.ur_color.data.dataProcessor.testOperator

import android.content.Context
import android.graphics.Bitmap
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AuraColors
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

    suspend fun applyDailyResult(context: Context, user: UserData?, aura: Bitmap?) {
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

        val aura = AuraGenerator.updateDynamicAura(aura, updated)
        if (aura != null) PersonalDataManager.saveAura(context, aura)

        resetDaily()
    }

    private fun updateUserValue(user: UserData, type: ModType, value: Int): UserData {
        val c = user.characteristics
        val updated = when (type) {
            ModType.ENERGY_LEVEL -> c.copy(
                energyLevel = value,
                energyCapacity = updateVector(c.energyCapacity, value)
            )
            ModType.MOOD -> c.copy(
                mood = value,
                moodVector = updateVector(c.moodVector, value)
            )
            ModType.STRESS_LEVEL -> c.copy(
                stressLevel = value,
                stressVector = updateVector(c.stressVector, value)
            )
            ModType.FOCUS -> c.copy(
                focus = value,
                focusVector = updateVector(c.focusVector, value)
            )
            ModType.MOTIVATION -> c.copy(
                motivation = value,
                motivationVector = updateVector(c.motivationVector, value)
            )
            ModType.CREATIVITY -> c.copy(
                creativity = value,
                creativityVector = updateVector(c.creativityVector, value)
            )
            ModType.EMOTIONAL_BALANCE -> c.copy(
                emotionalBalance = value,
                emotionalBalanceVector = updateVector(c.emotionalBalanceVector, value)
            )
            ModType.PHYSICAL_ENERGY -> c.copy(
                physicalEnergy = value,
                physicalEnergyVector = updateVector(c.physicalEnergyVector, value)
            )
            ModType.SLEEP_QUALITY -> c.copy(
                sleepQuality = value,
                sleepQualityVector = updateVector(c.sleepQualityVector, value)
            )
            ModType.INTUITION_LEVEL -> c.copy(
                intuitionLevel = value,
                intuitionVector = updateVector(c.intuitionVector, value)
            )
            ModType.SOCIAL_ENERGY -> c.copy(
                socialEnergy = value,
                socialVector = updateVector(c.socialVector, value)
            )
            ModType.DOMINANT_COLOR -> {
                val color = when {
                    value == 1 -> AuraColors.MINT.hex
                    value == 2 -> AuraColors.BLUE.hex
                    value == 3 -> AuraColors.LAVENDER.hex
                    value == 4 -> AuraColors.CORAL.hex
                    value == 5 -> AuraColors.YELLOW.hex
                    value == 6 -> AuraColors.GREEN.hex
                    value == 7 -> AuraColors.RED.hex
                    value == 8 -> AuraColors.PEACH.hex
                    value == 9 -> AuraColors.BURGUNDY.hex
                    value == 10 -> AuraColors.SILVER.hex
                    else -> AuraColors.WHITE.hex
                }

                c.copy(
                    dominantColor = color,
                    colorVector = updateColor(c.colorVector, color)
                )
            }
        }

        return user.copy(characteristics = updated)
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

    private fun updateColor(
        oldVector: List<String>,
        value: String,
        maxSize: Int = 10
    ): List<String> {
        val newList = oldVector + value
        val result = if (newList.size > maxSize) newList.takeLast(maxSize) else newList
        return result
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