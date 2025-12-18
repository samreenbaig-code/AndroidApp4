package com.example.androidapp4.data

import kotlin.random.Random

object AffirmationRepository {
    private val items = listOf(
        Affirmation("I am capable of achieving my goals."),
        Affirmation("I deserve good things and I welcome them."),
        Affirmation("I can handle anything that comes my way."),
        Affirmation("I am growing every day, step by step."),
        Affirmation("My mind is calm. My heart is strong.")
    )

    fun randomAffirmation(): Affirmation = items[Random.nextInt(items.size)]
}
