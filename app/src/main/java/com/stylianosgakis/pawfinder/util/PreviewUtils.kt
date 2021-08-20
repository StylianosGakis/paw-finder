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
package com.stylianosgakis.pawfinder.util

import com.stylianosgakis.pawfinder.api.model.animal.Animal
import com.stylianosgakis.pawfinder.api.model.animal.GetOneAnimalsResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

val previewAnimal: Animal
    get() {
        return json.decodeFromString<GetOneAnimalsResponse>(
            string = """
{
    "animal": {
        "id": 52688791,
        "organization_id": "VA583",
        "url": "https://www.petfinder.com/dog/rogers-52688791/va/midlothian/sanctuary-rescue-va583/?referrer_id=7be8c2c8-e1a0-4a6c-b728-a7742e45ed29",
        "type": "Dog",
        "species": "Dog",
        "breeds": {
            "primary": "Beagle",
            "secondary": "Retriever",
            "mixed": true,
            "unknown": false
        },
        "colors": {
            "primary": "Tricolor (Brown, Black, & White)",
            "secondary": "Sable",
            "tertiary": null
        },
        "age": "Baby",
        "gender": "Male",
        "size": "Medium",
        "coat": null,
        "attributes": {
            "spayed_neutered": true,
            "house_trained": false,
            "declawed": null,
            "special_needs": false,
            "shots_current": true
        },
        "environment": {
            "children": true,
            "dogs": true,
            "cats": true
        },
        "tags": [
            "Affectionate",
            "Friendly",
            "Curious",
            "Loves kisses"
        ],
        "name": "Rogers",
        "description": "Hello! Thank you for checking out Rogers’  adoption listing. Here are a few key points on our adoption process. Please...",
        "organization_animal_id": null,
        "photos": [
            {
                "small": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434&width=100",
                "medium": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434&width=300",
                "large": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434&width=600",
                "full": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434"
            },
            {
                "small": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/2/?bust=1629197435&width=100",
                "medium": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/2/?bust=1629197435&width=300",
                "large": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/2/?bust=1629197435&width=600",
                "full": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/2/?bust=1629197435"
            },
            {
                "small": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/3/?bust=1629197436&width=100",
                "medium": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/3/?bust=1629197436&width=300",
                "large": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/3/?bust=1629197436&width=600",
                "full": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/3/?bust=1629197436"
            },
            {
                "small": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/4/?bust=1629197436&width=100",
                "medium": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/4/?bust=1629197436&width=300",
                "large": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/4/?bust=1629197436&width=600",
                "full": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/4/?bust=1629197436"
            },
            {
                "small": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/5/?bust=1629197437&width=100",
                "medium": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/5/?bust=1629197437&width=300",
                "large": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/5/?bust=1629197437&width=600",
                "full": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/5/?bust=1629197437"
            },
            {
                "small": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/6/?bust=1629197438&width=100",
                "medium": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/6/?bust=1629197438&width=300",
                "large": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/6/?bust=1629197438&width=600",
                "full": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/6/?bust=1629197438"
            }
        ],
        "primary_photo_cropped": {
            "small": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434&width=300",
            "medium": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434&width=450",
            "large": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434&width=600",
            "full": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/52688791/1/?bust=1629197434"
        },
        "videos": [],
        "status": "adoptable",
        "status_changed_at": "2021-08-17T10:50:39+0000",
        "published_at": "2021-08-17T10:50:39+0000",
        "distance": null,
        "contact": {
            "email": "sanctuaryrescuepetfinder@gmail.com",
            "phone": null,
            "address": {
                "address1": null,
                "address2": null,
                "city": "Midlothian",
                "state": "VA",
                "postcode": "23112",
                "country": "US"
            }
        },
        "_links": {
            "self": {
                "href": "/v2/animals/52688791"
            },
            "type": {
                "href": "/v2/types/dog"
            },
            "organization": {
                "href": "/v2/organizations/va583"
            }
        }
    }
}
            """.trimIndent()
        ).animal
    }
