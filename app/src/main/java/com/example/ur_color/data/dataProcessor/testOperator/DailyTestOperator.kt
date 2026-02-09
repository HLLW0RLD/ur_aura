package com.example.ur_color.data.dataProcessor.testOperator

import android.graphics.Bitmap
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.question.ModType
import com.example.ur_color.data.model.question.Question
import com.example.ur_color.data.model.user.UserData
import kotlin.collections.iterator

object DailyTestOperator {

    private val accumulators = mutableMapOf<ModType, MutableList<Float>>()

    fun consumeAnswer(question: Question, answer: Boolean) {
        for (mod in question.mods) {
            val diff = (5 * mod.coef)
            val delta = if (answer) diff else -diff

            accumulators
                .getOrPut(mod.targetVariable) { mutableListOf() }
                .add(delta.toFloat())
        }
    }

    suspend fun applyDailyResult(user: UserData?, aura: Bitmap?) {
        if (user == null) return
        var updated = user!!

        for ((type, list) in accumulators) {
            if (list.isEmpty()) continue

            val oldValue = getValue(updated, type)

            val avgDelta = list.sum().toFloat()

            val newValue = (oldValue + avgDelta).coerceIn(1f, 100f)

            updated = updateUserValue(updated, type, newValue)
        }

        PersonalDataManager.saveUserToCache(updated)

        val aura = AuraGenerator.updateDynamicAura(aura, updated)
        if (aura != null) PersonalDataManager.saveAuraToCache(aura)

        resetDaily()
    }

    private fun getValue(user: UserData, type: ModType): Float =
        when (type) {
            ModType.ENERGY_LEVEL -> user.characteristics.energy
            ModType.MOOD -> user.characteristics.mood
            ModType.STRESS -> user.characteristics.stress
            ModType.FOCUS -> user.characteristics.focus
            ModType.MOTIVATION -> user.characteristics.motivation
            ModType.CHARISMA -> user.characteristics.charisma
            ModType.PHYSICAL_ENERGY -> user.characteristics.physicalEnergy
            ModType.SLEEP_QUALITY -> user.characteristics.sleepQuality
            ModType.COMMUNICATION -> user.characteristics.communication
            ModType.SOCIAL_ENERGY -> user.characteristics.socialEnergy
            ModType.ANXIETY -> user.characteristics.anxiety
            ModType.FATIGUE -> user.characteristics.fatigue
        }

    private fun updateVector(
        oldVector: List<Float>,
        value: Float,
        maxSize: Int = 10
    ): List<Float> {
        val newList = oldVector + value.coerceIn(1f, 100f)
        val result = if (newList.size > maxSize) newList.takeLast(maxSize) else newList
        return result
    }

    private fun updateUserValue(user: UserData, type: ModType, value: Float): UserData {
        val c = user.characteristics
        val updated = when (type) {
            ModType.ENERGY_LEVEL -> c.copy(
                energy = value,
                energyVector = updateVector(c.energyVector, value)
            )
            ModType.MOOD -> c.copy(
                mood = value,
                moodVector = updateVector(c.moodVector, value)
            )
            ModType.STRESS -> c.copy(
                stress = value,
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
            ModType.CHARISMA -> c.copy(
                charisma = value,
                charismaVector = updateVector(c.charismaVector, value)
            )
            ModType.PHYSICAL_ENERGY -> c.copy(
                physicalEnergy = value,
                physicalEnergyVector = updateVector(c.physicalEnergyVector, value)
            )
            ModType.SLEEP_QUALITY -> c.copy(
                sleepQuality = value,
                sleepQualityVector = updateVector(c.sleepQualityVector, value)
            )
            ModType.COMMUNICATION -> c.copy(
                communication = value,
                communicationVector = updateVector(c.communicationVector, value)
            )
            ModType.SOCIAL_ENERGY -> c.copy(
                socialEnergy = value,
                socialVector = updateVector(c.socialVector, value)
            )
            ModType.ANXIETY -> c.copy(
                anxiety = value,
                anxietyVector = updateVector(c.anxietyVector, value)
            )
            ModType.FATIGUE -> c.copy(
                fatigue = value,
                fatigueVector = updateVector(c.fatigueVector, value)
            )
        }

        return user.copy(characteristics = updated)
    }

    fun resetDaily() {
        accumulators.clear()
    }
}