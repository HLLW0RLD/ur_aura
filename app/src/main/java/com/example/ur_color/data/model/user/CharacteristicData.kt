package com.example.ur_color.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicData(

    // нужно создать отдельныйй дата класс под каждую хар-ку и него будет 3 параметра stat, vector и name
    //
    // CharacteristicData(val stats: List<Stat>)
    // data class Stat(stat, vector, name)


    val energy: Float = 50f,
    val mood: Float = 50f,
    val stress: Float = 50f,
    val focus: Float = 50f,
    val motivation: Float = 50f,
    val charisma: Float = 50f,
    val physicalEnergy: Float = 50f,
    val sleepQuality: Float = 50f,
    val communication: Float = 50f,
    val socialEnergy: Float = 50f,
    val anxiety: Float = 50f,
    val fatigue: Float = 50f,


    val energyVector: List<Float> = List(10) { 50f },
    val moodVector: List<Float> = List(10) { 50f },
    val stressVector: List<Float> = List(10) { 50f },
    val focusVector: List<Float> = List(10) { 50f },
    val motivationVector: List<Float> = List(10) { 50f },
    val charismaVector: List<Float> = List(10) { 50f },
    val physicalEnergyVector: List<Float> = List(10) { 50f },
    val sleepQualityVector: List<Float> = List(10) { 50f },
    val communicationVector: List<Float> = List(10) { 50f },
    val socialVector: List<Float> = List(10) { 50f },
    val anxietyVector: List<Float> = List(10) { 50f },
    val fatigueVector: List<Float> = List(10) { 05f }
)