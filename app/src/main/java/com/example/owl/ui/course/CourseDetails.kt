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

package com.example.owl.ui.course

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.PlaylistPlay
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.owl.R
import com.example.owl.model.Course
import com.example.owl.model.CourseRepo
import com.example.owl.model.Lesson
import com.example.owl.model.LessonsRepo
import com.example.owl.model.courses
import com.example.owl.ui.common.CourseListItem
import com.example.owl.ui.common.OutlinedAvatar
import com.example.owl.ui.theme.BlueTheme
import com.example.owl.ui.theme.PinkTheme
import com.example.owl.ui.theme.pink500
import com.example.owl.ui.utils.NetworkImage
import com.example.owl.ui.utils.lerp
import com.example.owl.ui.utils.scrim
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import java.util.Locale
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback
import java.io.IOException

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.Typography
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.unit.sp
import okhttp3.Call


private val FabSize = 56.dp
private const val ExpandedSheetAlpha = 0.96f

var poemRes: String = "111"

fun performLongRunningTask(poemId: Long): String {
    // 模拟耗时操作
    //首先，需要创建一个OkHttpClient实例
    val client = OkHttpClient()

    //创建一个Request对象,url为请求的链接，下面不做解释，括号中的url根据实际填入http连接
    //例如，val url = "https://www.baidu.com/"，以下同理，url为String
    // 类型
    val url = "http://120.46.68.62:7000/api/poem/$poemId"
    val request = Request.Builder()
        .url(url)
        .build()


    // 使用 OkHttpClient 发起异步请求
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // 处理请求失败
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            // 处理响应
            response.use {
                if (!it.isSuccessful) throw IOException("Unexpected code $response")

                // 获取响应体
                val responseBody = it.body?.string()
                println("HERE RES $responseBody")
                poemRes = responseBody!!

            }
        }
    })
    //调用OkHttpClient的newCall()方法来创建一个Call对象，
    //并调用它的execute()方法来发送请求并获取服务器返回的数据
//    return try {
//        // 创建并发送请求
//        val response: Response = client.newCall(request).execute()
//
//        // 检查响应是否成功
//        if (response.isSuccessful) {
//            // 获取响应内容
//            response.body?.string() ?: "No response body"
//        } else {
//            "Request failed with code: ${response.code}"
//        }
//    } catch (e: IOException) {
//        // 捕获网络异常
//        "Network error: ${e.message}"
//    } catch (e: Exception) {
//        // 捕获其他异常
//        "Unexpected error: ${e.message}"
//    }
    println("RES123 $poemRes")
    return poemRes
}

// @SuppressLint("CoroutineCreationDuringComposition")
// @OptIn(DelicateCoroutinesApi::class)
@Composable
fun CourseDetails(
    courseId: Long,
    selectCourse: (Long) -> Unit,
    upPress: () -> Unit
) {
    // Simplified for the sample
    val course = remember(courseId) { CourseRepo.getCourse(courseId) }
    var poem = ""
//    GlobalScope.launch(Dispatchers.IO) {
//        val tempResult = performLongRunningTask(1)
//        println("Respon123 $tempResult")
//        poem = tempResult
////        val parts = tempResult.split(";")
////
////        if (parts.size >= 4) {
////            val part1 = parts[0]
////            val part2 = parts[1]
////            val part3 = parts[2]
////            val part4 = parts[3]
////
////            println("Part 1: $part1")
////            println("Part 2: $part2")
////            println("Part 3: $part3")
////            println("Part 4: $part4")
////        } else {
////            println("Input string does not contain enough parts")
////        }
//    }
    val tempResult = performLongRunningTask(1)
    println("Respon123 $tempResult")
    poem = tempResult
    // TODO: Show error if course not found.
    CourseDetails(course, poem, selectCourse, upPress)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CourseDetails(
    course: Course,
    poem: String,
    selectCourse: (Long) -> Unit,
    upPress: () -> Unit
) {
    println("Poem is $poem")
    PinkTheme {
        BoxWithConstraints {
            val sheetState = rememberSwipeableState(SheetState.Closed)
            val fabSize = with(LocalDensity.current) { FabSize.toPx() }
            val dragRange = constraints.maxHeight - fabSize
            val scope = rememberCoroutineScope()

            BackHandler(
                enabled = sheetState.currentValue == SheetState.Open,
                onBack = {
                    scope.launch {
                        sheetState.animateTo(SheetState.Closed)
                    }
                }
            )

            Box(
                // The Lessons sheet is initially closed and appears as a FAB. Make it openable by
                // swiping or clicking the FAB.
                Modifier.swipeable(
                    state = sheetState,
                    anchors = mapOf(
                        0f to SheetState.Closed,
                        -dragRange to SheetState.Open
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Vertical
                )
            ) {
                val openFraction = if (sheetState.offset.value.isNaN()) {
                    0f
                } else {
                    -sheetState.offset.value / dragRange
                }.coerceIn(0f, 1f)
                CourseDescription(course, poem, selectCourse, upPress)
                LessonsSheet(
                    course,
                    openFraction,
                    this@BoxWithConstraints.constraints.maxWidth.toFloat(),
                    this@BoxWithConstraints.constraints.maxHeight.toFloat()
                ) { state ->
                    scope.launch {
                        sheetState.animateTo(state)
                    }
                }
            }
        }
    }
}

@Composable
private fun CourseDescription(
    course: Course,
    poem: String,
    selectCourse: (Long) -> Unit,
    upPress: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item { CourseDescriptionHeader(course, poem, upPress) }
            item { CourseDescriptionBody(course, poem) }
            item { RelatedCourses(course.id, selectCourse) }
        }
    }
}

@Composable
private fun CourseDescriptionHeader(
    course: Course,
    poem: String,
    upPress: () -> Unit
) {
    val parts = poem.split(";")

    var title = ""
    var dynasty = ""
    var content = ""
    var userName = ""

    if (parts.size >= 4) {
        title = parts[4]
        dynasty = parts[3]
        content = parts[5]
        userName = parts[2]

        println("Part 1: $title")
        println("Part 2: $dynasty")
        println("Part 3: $content")
        println("Part 4: $userName")
    } else {
        println("Input string does not contain enough parts")
    }


    Box {
        NetworkImage(
            url = "http://120.46.68.62:8000/image?name=$dynasty",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scrim(colors = listOf(Color(0x80000000), Color(0x33000000)))
                .aspectRatio(4f / 3f)
        )
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentColor = Color.White, // always white as image has dark scrim
            modifier = Modifier.statusBarsPadding()
        ) {
            IconButton(onClick = upPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.label_back)
                )
            }
//            Image(
//                painter = painterResource(id = R.drawable.logo_title),
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(bottom = 4.dp)
//                    .size(24.dp)
//                    .align(Alignment.CenterVertically)
//            )
            Spacer(modifier = Modifier.weight(1f))
        }
//        OutlinedAvatar(
//            url = course.instructor,
//            modifier = Modifier
//                .size(40.dp)
//                .align(Alignment.BottomCenter)
//                .offset(y = 20.dp) // overlap bottom of image
//        )
    }
}

@Composable
private fun CourseDescriptionBody(course: Course, poem: String) {

    val parts = poem.split(";")
    val customFont = FontFamily(
        Font(R.font.ming)
    )
    var title = ""
    var dynasty = ""
    var content = ""
    var userName = ""

    if (parts.size >= 4) {
        title = parts[4]
        dynasty = parts[3]
        content = parts[5]
        userName = parts[2]

        println("HEREPart 1: $title")
        println("Part 2: $dynasty")
        println("Part 3: $content")
        println("Part 4: $userName")
    } else {
        println("Input string does not contain enough parts")
    }

    title = "静夜思"
    dynasty = "唐"
    content = "床前明月光，疑是地上霜。\n举头望明月，低头思故乡。\n"
    userName = "李白"

//    Text(
//        text = "$userName $dynasty",
//        color = MaterialTheme.colors.primary,
//        style = MaterialTheme.typography.body2,
//        textAlign = TextAlign.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(
//                start = 16.dp,
//                top = 36.dp,
//                end = 16.dp,
//                bottom = 16.dp
//            )
//    )

    Text(
        text = title,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        fontFamily = customFont,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(
                top = 36.dp
            )
    )
    Text(
        text = "Thoughts During the Silent Night",
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        fontFamily = customFont,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Text(
        text = "$dynasty · $userName \n",
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Center,
        fontFamily = customFont,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 36.dp,
                end = 16.dp
            )
    )

    Spacer(modifier = Modifier.height(16.dp))
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = content,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            fontFamily = customFont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
    Divider(modifier = Modifier.padding(16.dp))
//    Text(
//        text = stringResource(id = R.string.what_you_ll_need),
//        style = MaterialTheme.typography.h6,
//        textAlign = TextAlign.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    )
//    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//        Text(
//            text = stringResource(id = R.string.needs),
//            style = MaterialTheme.typography.body1,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    start = 16.dp,
//                    top = 16.dp,
//                    end = 16.dp,
//                    bottom = 32.dp
//                )
//        )
//    }
}

@Composable
private fun RelatedCourses(
    courseId: Long,
    selectCourse: (Long) -> Unit
) {
    val relatedCourses = remember(courseId) { CourseRepo.getRelated(courseId) }
    BlueTheme {
        Surface(
            color = MaterialTheme.colors.primarySurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.navigationBarsPadding()) {
                Text(
                    text = stringResource(id = R.string.you_ll_also_like),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 24.dp
                        )
                )
                LazyRow(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        bottom = 32.dp,
                        end = FabSize + 8.dp
                    )
                ) {
                    items(
                        items = relatedCourses,
                        key = { it.id }
                    ) { related ->
                        CourseListItem(
                            course = related,
                            onClick = { selectCourse(related.id) },
                            titleStyle = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(288.dp, 80.dp),
                            iconSize = 14.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonsSheet(
    course: Course,
    openFraction: Float,
    width: Float,
    height: Float,
    updateSheet: (SheetState) -> Unit
) {
    // Use the fraction that the sheet is open to drive the transformation from FAB -> Sheet
    val fabSize = with(LocalDensity.current) { FabSize.toPx() }
    val fabSheetHeight = fabSize + WindowInsets.systemBars.getBottom(LocalDensity.current)
    val offsetX = lerp(width - fabSize, 0f, 0f, 0.15f, openFraction)
    val offsetY = lerp(height - fabSheetHeight, 0f, openFraction)
    val tlCorner = lerp(fabSize, 0f, 0f, 0.15f, openFraction)
    val surfaceColor = lerp(
        startColor = pink500,
        endColor = MaterialTheme.colors.primarySurface.copy(alpha = ExpandedSheetAlpha),
        startFraction = 0f,
        endFraction = 0.3f,
        fraction = openFraction
    )

}


@Composable
private fun Lesson(lesson: Lesson) {
    Row(
        modifier = Modifier
            .clickable(onClick = { /* todo */ })
            .padding(vertical = 16.dp)
    ) {
        NetworkImage(
            url = lesson.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(112.dp, 64.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = lesson.length,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
        Text(
            text = lesson.formattedStepNumber,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

private enum class SheetState { Open, Closed }

private val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

@Preview(name = "Course Details")
@Composable
private fun CourseDetailsPreview() {
    val courseId = courses.first().id
    CourseDetails(
        courseId = courseId,
        selectCourse = { },
        upPress = { }
    )
}



@Preview(name = "Related")
@Composable
private fun RelatedCoursesPreview() {
    val related = courses.random()
    RelatedCourses(
        courseId = related.id,
        selectCourse = { }
    )
}
