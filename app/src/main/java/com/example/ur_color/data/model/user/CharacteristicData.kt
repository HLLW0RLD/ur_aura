package com.example.ur_color.data.model.user

import com.example.ur_color.ui.theme.AuraColors
import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicData(
    val energyLevel: Int = 5,
    val mood: Int = 5,
    val stressLevel: Int = 5,
    val focus: Int = 5,
    val motivation: Int = 5,
    val creativity: Int = 5,
    val emotionalBalance: Int = 5,
    val physicalEnergy: Int = 5,
    val sleepQuality: Int = 5,
    val intuitionLevel: Int = 5,
    val socialEnergy: Int = 5,
    val dominantColor: String = AuraColors.WHITE.hex,


    val energyCapacity: List<Int> = List(10) { 1 },
    val moodVector: List<Int> = List(10) { 1 },
    val stressVector: List<Int> = List(10) { 1 },
    val focusVector: List<Int> = List(10) { 1 },
    val motivationVector: List<Int> = List(10) { 1 },
    val creativityVector: List<Int> = List(10) { 1 },
    val emotionalBalanceVector: List<Int> = List(10) { 1 },
    val physicalEnergyVector: List<Int> = List(10) { 1 },
    val sleepQualityVector: List<Int> = List(10) { 1 },
    val intuitionVector: List<Int> = List(10) { 1 },
    val socialVector: List<Int> = List(10) { 1 },
    val colorVector: List<String> = List(10) { AuraColors.WHITE.hex }
)