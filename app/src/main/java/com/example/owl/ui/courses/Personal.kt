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
        name = "Basic Blocks and Woodturning",
        subject = "Arts & Crafts",
        thumbUrl = "https://images.unsplash.com/photo-1516562309708-05f3b2b2c238",
        thumbContentDesc = ""
    ),
    Person(
        id = 1,
        name = "An Introduction To Oil Painting On Canvas",
        subject = "Painting",
        thumbUrl = "https://images.unsplash.com/photo-1508261301902-79a2d8e78f71",
        thumbContentDesc = ""
    ),
    Person(
        id = 2,
        name = "Understanding the Composition of Modern Cities",
        subject = "Architecture",
        thumbUrl = "https://images.unsplash.com/photo-1519999482648-25049ddd37b1",
        thumbContentDesc = ""
    ),
    Person(
        id = 3,
        name = "Learning The Basics of Brand Identity",
        subject = "Design",
        thumbUrl = "https://images.unsplash.com/photo-1517602302552-471fe67acf66",
        thumbContentDesc = ""
    ),
    Person(
        id = 4,
        name = "Wooden Materials and Sculpting Machinery",
        subject = "Arts & Crafts",
        thumbUrl = "https://images.unsplash.com/photo-1547609434-b732edfee020",
        thumbContentDesc = ""
    ),

)
