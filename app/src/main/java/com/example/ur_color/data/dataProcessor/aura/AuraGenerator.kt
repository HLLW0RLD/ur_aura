package com.example.ur_color.data.dataProcessor.aura

import android.graphics.Bitmap
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData

object AuraGenerator {

    fun generateBaseAura(user: UserData, width: Int = 1080, height: Int = 1080): Bitmap {
        val base = PatternLibrary.backgroundLayer(width, height, user)
        val zodiac = ZodiacLibrary.zodiacFrame(width, height, user)
        val pattern = PatternLibrary.basePattern(width, height, user)
        return AuraLayer.combine(listOf(base, zodiac, pattern))
    }

    fun applyExtendedAura(user: UserData): Bitmap? {
//        val base = PersonalDataManager.aura.value
//
//        val width = base?.width ?: 0
//        val height = base?.height ?: 0
//
//        val archetype = PatternLibrary.archetypeLayer(width, height, user)
//        val symbol = PatternLibrary.symbolLayer(width, height, user)
//
//        return base?.let { AuraLayer.overlay(it, listOf(archetype, symbol)) }
        return null
    }

    fun applyDynamicAura(user: UserData): Bitmap? {
//        val base = PersonalDataManager.aura.value
//
//        val width = base?.width ?: 0
//        val height = base?.height ?: 0
//
//        val dynamic = DynamicLayerLibrary.vectorHistoryLayer(width, height, user)
//        val particles = DynamicLayerLibrary.particleLayer(width, height, user)
//
//        return base?.let { AuraLayer.overlay(it, listOf(dynamic, particles)) }
        return null
    }

//    fun generateFullAura(user: UserData, width: Int = 1080, height: Int = 1080): Bitmap {
//        val base = generateBaseAura(user, width, height)
//        val extended = applyExtendedAura(base, user)
//        val dynamic = applyDynamicAura(extended, user)
//        return dynamic
//    }
}
