package com.example.frc5987scoutingapp.ui.confetti

import android.graphics.drawable.shapes.Shape

/**
 * Particle holding exact data to instruct where and how to draw a particle
 * @param x the absolute x position on the canvas
 * @param y the absolute y position on the canvas
 * @param width the current width of the confetti
 * @param height the current height of a confetti
 * @param color the color that will be used to paint the confetti
 * @param rotation the current rotation of the confetti in degrees
 * @param scaleX the current scale of the confetti used to create a 3D rotation
 * @param shape the Shape of the confetti such as a circle, square of custom shape
 * @param alpha the transparency of the confetti between 0 - 255
 */
internal data class Particle(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val color: Int,
    val rotation: Float,
    val scaleX: Float,
    val shape: Shape,
    val alpha: Int,
)


internal sealed interface CoreRect {
    val x: Float
    val y: Float
    val width: Float
    val height: Float

    fun contains(
        px: Int,
        py: Int,
    ): Boolean {
        return px >= x && px <= x + width && py >= y && py <= y + height
    }

    data class CoreRectImpl(
        override val x: Float = 0f,
        override val y: Float = 0f,
        override val width: Float = 0f,
        override val height: Float = 0f,
    ) : CoreRect
}

