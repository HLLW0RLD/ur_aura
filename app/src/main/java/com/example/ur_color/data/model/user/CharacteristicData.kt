package com.example.ur_color.data.model.user

import com.example.ur_color.ui.theme.AuraColors
import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicData(
    val energy: Float = 5f,
    val mood: Float = 5f,
    val stressLevel: Float = 5f,
    val focus: Float = 5f,
    val motivation: Float = 5f,
    val creativity: Float = 5f,
    val emotionalBalance: Float = 5f,
    val physicalEnergy: Float = 5f,
    val sleepQuality: Float = 5f,
    val intuitionLevel: Float = 5f,
    val socialEnergy: Float = 5f,
    val dominantColor: String = AuraColors.WHITE.hex,


    val energyVector: List<Float> = List(10) { 5f },
    val moodVector: List<Float> = List(10) { 5f },
    val stressVector: List<Float> = List(10) { 5f },
    val focusVector: List<Float> = List(10) { 5f },
    val motivationVector: List<Float> = List(10) { 5f },
    val creativityVector: List<Float> = List(10) { 5f },
    val emotionalBalanceVector: List<Float> = List(10) { 5f },
    val physicalEnergyVector: List<Float> = List(10) { 5f },
    val sleepQualityVector: List<Float> = List(10) { 5f },
    val intuitionVector: List<Float> = List(10) { 5f },
    val socialVector: List<Float> = List(10) { 5f },
    val colorVector: List<String> = List(10) { AuraColors.WHITE.hex }
)