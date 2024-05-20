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

@Immutable
data class Topic(
    val name: String,
    val courses: Int,
    val imageUrl: String
)

val topics = listOf(
    Topic("唐诗", 58, "https://images.unsplash.com/photo-1479839672679-a46483c0e7c8"),
    Topic("宋词", 121, "https://images.unsplash.com/photo-1422246358533-95dcd3d48961"),
    Topic("古代文化", 78, "https://images.unsplash.com/photo-1507679799987-c73779587ccf"),
    Topic("诗人", 118, "https://images.unsplash.com/photo-1551218808-94e220e084d2"),
    Topic("青铜器", 423, "https://images.unsplash.com/photo-1493932484895-752d1471eab5"),
    Topic("古代绘画", 92, "https://images.unsplash.com/photo-1517840545241-b491010a8af4"),
    Topic(" 文物", 165, "https://images.unsplash.com/photo-1518676590629-3dcbd9c5a5c9"),
    )
