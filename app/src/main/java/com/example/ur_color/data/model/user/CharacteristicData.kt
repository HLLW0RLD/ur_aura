package com.example.ur_color.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicData(
    val energy: Float = 5f,
    val mood: Float = 5f,
    val stress: Float = 5f,
    val focus: Float = 5f,
    val motivation: Float = 5f,
    val charisma: Float = 5f,
    val physicalEnergy: Float = 5f,
    val sleepQuality: Float = 5f,
    val communication: Float = 5f,
    val socialEnergy: Float = 5f,
    val anxiety: Float = 5f,
    val fatigue: Float = 5f,


    val energyVector: List<Float> = List(10) { 5f },
    val moodVector: List<Float> = List(10) { 5f },
    val stressVector: List<Float> = List(10) { 5f },
    val focusVector: List<Float> = List(10) { 5f },
    val motivationVector: List<Float> = List(10) { 5f },
    val charismaVector: List<Float> = List(10) { 5f },
    val physicalEnergyVector: List<Float> = List(10) { 5f },
    val sleepQualityVector: List<Float> = List(10) { 5f },
    val communicationVector: List<Float> = List(10) { 5f },
    val socialVector: List<Float> = List(10) { 5f },
    val anxietyVector: List<Float> = List(10) { 5f },
    val fatigueVector: List<Float> = List(10) { 5f }
)