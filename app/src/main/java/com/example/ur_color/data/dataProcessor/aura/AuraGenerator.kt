package com.example.ur_color.data.dataProcessor.aura

import android.graphics.Bitmap
import com.example.ur_color.data.model.user.UserData

object AuraGenerator {

    fun generateBaseAura(user: UserData, width: Int = 1080, height: Int = 1080): Bitmap? {
        val base = PatternLibrary.backgroundLayer(width, height, user)
        val zodiac = ZodiacLibrary.zodiacFrame(width, height, user)
        val pattern = PatternLibrary.basePattern(width, height, user)
        val combinedBase = AuraLayer.combine(listOf(base, zodiac, pattern))

        val extended = updateExtendedAura(combinedBase, user)
        val fullAura = updateDynamicAura(extended, user)
        return fullAura
    }

    fun updateExtendedAura(base: Bitmap, user: UserData): Bitmap {
        val width = base.width
        val height = base.height

        val archetype = PatternLibrary.archetypeLayer(width, height, user)
        val symbol = PatternLibrary.symbolLayer(width, height, user)

        return AuraLayer.overlay(base, listOf(archetype, symbol))
    }

    fun updateDynamicAura(base: Bitmap?, user: UserData): Bitmap? {
        if (base == null) return null

        val width = base.width
        val height = base.height

        val vectorLayer = DynamicLayerLibrary.vectorHistoryLayer(width, height, user)
        val particleLayer = DynamicLayerLibrary.particleLayer(width, height, user)

        return AuraLayer.overlay(base, listOf(vectorLayer, particleLayer))
    }

//    fun generateFullAura(user: UserData, width: Int = 1080, height: Int = 1080): Bitmap {
//        val base = generateBaseAura(user, width, height)
//        val extended = applyExtendedAura(base, user)
//        val dynamic = applyDynamicAura(extended, user)
//        return dynamic
//    }
}
