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
package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Links(
    val organization: Organization,
    val self: Self,
    val type: Type,
) : Parcelable {
    @Parcelize
    @Serializable
    data class Organization(
        val href: String,
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Self(
        val href: String,
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Type(
        val href: String,
    ) : Parcelable
}
