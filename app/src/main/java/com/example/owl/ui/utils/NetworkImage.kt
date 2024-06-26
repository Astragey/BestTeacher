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

package com.example.owl.ui.utils

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.size.pxOrElse
import com.example.owl.R
import com.example.owl.ui.theme.compositedOnSurface
import okhttp3.HttpUrl.Companion.toHttpUrl
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.request.ImageRequest

/**
 * A wrapper around [AsyncImage], setting a default [contentScale] and showing
 * content while loading.
 */


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color = MaterialTheme.colors.compositedOnSurface(0.2f)
) {


    val imageUrls = listOf(
        "https://i.ibb.co/1QmgXPr/K2-A000979-N000000000-PAD.jpg",
    )
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })
    val placeholder = painterResource(R.drawable.photo_architecture)

//    val request = ImageRequest.Builder(LocalContext.current)
//        .data("http://123.60.217.228:8000/image?name=test.jpg")
//        .build()


    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        println("12345")
        AsyncImage(
            model = imageUrls[page],
            contentDescription = contentDescription,
            placeholder = placeholder,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )
        println("6789")


    }


//    AsyncImage(
//        model = url,
//        contentDescription = contentDescription,
//        placeholder = painterResource(R.drawable.photo_architecture),
//        modifier = modifier,
//        contentScale = contentScale
////        onSuccess = { bitmap ->
////            val drawable = BitmapDrawable(LocalContext.current.resources, bitmap)
////            // Use the drawable bitmap
////        },
////        onError = {
////            // Handle image load error
////        }
//    )
}

/**
 * A Coil [Interceptor] which appends query params to Unsplash urls to request sized images.
 */
object UnsplashSizingInterceptor : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val data = chain.request.data
        val widthPx = chain.size.width.pxOrElse { -1 }
        val heightPx = chain.size.height.pxOrElse { -1 }
        if (widthPx > 0 && heightPx > 0 && data is String &&
            data.startsWith("https://images.unsplash.com/photo-")
        ) {
            val url = data.toHttpUrl()
                .newBuilder()
                .addQueryParameter("w", widthPx.toString())
                .addQueryParameter("h", heightPx.toString())
                .build()
            val request = chain.request.newBuilder().data(url).build()
            return chain.proceed(request)
        }
        return chain.proceed(chain.request)
    }
}
