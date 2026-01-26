package com.example.frc5987scoutingapp.ui.confetti

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


@Composable
fun ConfettiRain(show: Boolean) {
    if (!show) return

    val party = Party(
        speed = 0f,
        maxSpeed = 30f,
        damping = 0.9f,
        spread = 500,
        colors = listOf(0xFFFFFFFF.toInt(), 0xFF2196F3.toInt()),
        position = Position.Relative(0.5, -0.1),
        emitter = Emitter(duration = 5, TimeUnit.SECONDS).max(500)
    )

    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = listOf(party)
    )
}
