/*
 * Copyright 2020 The Android Open Source Project
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

package com.example.owl.model

import androidx.compose.runtime.Immutable

@Immutable // Tell Compose runtime that this object will not change so it can perform optimizations
data class Course(
    val id: Long,
    val name: String,
    val subject: String,
    val thumbUrl: String,
    val thumbContentDesc: String,
    val description: String = "",
    val steps: Int,
    val step: Int,
    val instructor: String = "https://i.pravatar.cc/112?$id",
    var isFavorite: Boolean = false
)

/**
 * A fake repo
 */
object CourseRepo {
    fun getCourse(courseId: Long): Course = courses.find { it.id == courseId }!!
    fun getRelated(@Suppress("UNUSED_PARAMETER") courseId: Long): List<Course> = courses
}

val courses = listOf(
    Course(
        id = 0,
        name = "静夜思",
        subject = "唐 李白",
        thumbUrl = "https://images.unsplash.com/photo-1516562309708-05f3b2b2c238",
        instructor = "https://i.ibb.co/vBMC66h/2024-06-30-160324.png",
        thumbContentDesc = "",
        steps = 7,
        step = 1
    ),
    Course(
        id = 1,
        name = "将进酒",
        subject = "唐 李白",
        thumbUrl = "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
        instructor = "https://i.ibb.co/vBMC66h/2024-06-30-160324.png",
        thumbContentDesc = "",
        steps = 5,
        step = 1
    ),

    Course(
        id = 2,
        name = "春晓",
        subject = "唐 孟浩然",
        thumbUrl = "https://images.unsplash.com/photo-1470740700928-18938af6c8e0",
        thumbContentDesc = "",
        instructor = "https://i.ibb.co/HHTXD7M/2024-06-30-161208.png",
        steps = 4,
        step = 1
    ),

    Course(
        id = 3,
        name = "登鹳雀楼",
        subject = "唐 王之涣",
        thumbUrl = "https://images.unsplash.com/photo-1494783367193-149034c05e8f",
        thumbContentDesc = "",
        instructor = "https://i.ibb.co/QczLWWh/2024-06-30-161420.png",
        steps = 6,
        step = 1
    ),

    Course(
        id = 4,
        name = "枫桥夜泊",
        subject = "唐 张继",
        thumbUrl = "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
        instructor = "https://i.ibb.co/NFm70gW/2024-06-30-161540.png",
        thumbContentDesc = "",
        steps = 8,
        step = 1
    ),

    Course(
        id = 5,
        name = "送杜少府之任蜀州",
        subject = "唐 王勃",
        thumbUrl = "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
        instructor = "https://i.ibb.co/gWQv99F/2024-06-30-161641.png",
        thumbContentDesc = "",
        steps = 5,
        step = 1
    ),

    Course(
        id = 6,
        name = "游子吟",
        subject = "唐 孟郊",
        thumbUrl = "https://images.unsplash.com/photo-1483794344563-d27a8d38e87b",
        instructor = "https://i.ibb.co/YhmvkVk/2024-06-30-161755.png",
        thumbContentDesc = "",
        steps = 4,
        step = 1
    ),

    Course(
        id = 7,
        name = "黄鹤楼送孟浩然之广陵",
        subject = "唐 李白",
        thumbUrl = "https://images.unsplash.com/photo-1517677208171-0bc6725a3e60",
        instructor = "https://i.ibb.co/vBMC66h/2024-06-30-160324.png",
        thumbContentDesc = "",
        steps = 6,
        step = 1
    ),

    Course(
        id = 8,
        name = "静夜思",
        subject = "唐 李白",
        thumbUrl = "https://images.unsplash.com/photo-1516562309708-05f3b2b2c238",
        instructor = "https://i.ibb.co/vBMC66h/2024-06-30-160324.png",
        thumbContentDesc = "",
        steps = 7,
        step = 1
    ),

    Course(
        id = 9,
        name = "早发白帝城",
        subject = "唐 李白",
        thumbUrl = "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
        instructor = "https://i.ibb.co/vBMC66h/2024-06-30-160324.png",
        thumbContentDesc = "",
        steps = 5,
        step = 1
    ),

    Course(
        id = 10,
        name = "秋词",
        subject = "唐 刘禹锡",
        thumbUrl = "https://images.unsplash.com/photo-1470740700928-18938af6c8e0",
        instructor = "https://i.ibb.co/ByQ1MGw/2024-06-30-161936.png",
        thumbContentDesc = "",
        steps = 3,
        step = 1
    ),

    Course(
        id = 11,
        name = "闻王昌龄左迁龙标遥有此寄",
        subject = "唐 李白",
        thumbUrl = "https://images.unsplash.com/photo-1494783367193-149034c05e8f",
        instructor = "https://i.ibb.co/vBMC66h/2024-06-30-160324.png",
        thumbContentDesc = "",
        steps = 6,
        step = 1
    ),

    Course(
        id = 12,
        name = "夜泊牛渚怀古",
        subject = "唐 李白",
        thumbUrl = "https://images.unsplash.com/photo-1483794344563-d27a8d38e87b",
        instructor = "https://i.ibb.co/vBMC66h/2024-06-30-160324.png",
        thumbContentDesc = "",
        steps = 4,
        step = 1
    ),
)
