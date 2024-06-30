package com.example.owl.ui.courses

import androidx.compose.runtime.Immutable
import com.example.owl.model.Course

@Immutable // Tell Compose runtime that this object will not change so it can perform optimizations
data class Person(
    val id: Long,
    val name: String,
    val subject: String,
    val thumbUrl: String,
    val thumbContentDesc: String,
    val description: String = "",
    val instructor: String = "https://i.pravatar.cc/112?$id"
)

val persons = listOf(
    Person(
        id = 0,
        name = "Poem Generation",
        subject = "Arts & Crafts",
        description = "Generate poems based on keywords",
        thumbUrl = "https://images.unsplash.com/photo-1516562309708-05f3b2b2c238",
        thumbContentDesc = ""
    ),
    Person(
        id = 1,
        name = "Poem to Painting",
        subject = "Painting",
        description = "Generate the painting based on the poem",
        thumbUrl = "https://images.unsplash.com/photo-1508261301902-79a2d8e78f71",
        thumbContentDesc = ""
    ),
    Person(
        id = 2,
        name = "Poem Postcards",
        subject = "Design",
        description = "Make a poetry postcard and save it!",
        thumbUrl = "https://images.unsplash.com/photo-1517602302552-471fe67acf66",
        thumbContentDesc = ""
    ),


)
