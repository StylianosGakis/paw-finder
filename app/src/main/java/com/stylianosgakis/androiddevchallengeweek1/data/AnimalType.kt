/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
