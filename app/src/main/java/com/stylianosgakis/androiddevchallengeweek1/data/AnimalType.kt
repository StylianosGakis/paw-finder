package com.stylianosgakis.androiddevchallengeweek1.data

enum class AnimalType(val type: String) {
    Dog("Dog"),
    Cat("Cat"),
    Rabbit("Rabbit"),
    SmallAndFurry("Small & Furry"),
    Horse("Horse"),
    Bird("Bird"),
    ScalesFinsAndOther("Scales, Fins & Other"),
    Barnyard("Barnyard");

    val singleWordRepresentation: String
        get() = when (this) {
            SmallAndFurry -> type.split(" ").last()
            else -> type.split(" ").first().removeSuffix(",")
        }
}