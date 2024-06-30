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

package com.example.owl.ui.courses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material.icons.rounded.OndemandVideo
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.owl.R
import com.example.owl.model.Course
import com.example.owl.model.courses
import com.example.owl.ui.common.OutlinedAvatar
import com.example.owl.ui.theme.BlueTheme
import com.example.owl.ui.utils.NetworkImage
import java.util.Locale
import kotlin.math.ceil
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.owl.ui.theme.blue700

@Composable
fun LayoutToggleSwitch(
    isTwoColumnLayout: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val customColor = Color(0xFFFF0366)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Filled.ViewList,
            contentDescription = "Single column layout",
            tint = if (!isTwoColumnLayout) customColor else MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )
        Switch(
            checked = isTwoColumnLayout,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = customColor,
                checkedTrackColor = customColor.copy(alpha = 0.5f),
                uncheckedThumbColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                uncheckedTrackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .scale(0.8f)
        )
        Icon(
            imageVector = Icons.Filled.ViewModule,
            contentDescription = "Two column layout",
            tint = if (isTwoColumnLayout) customColor else MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FeaturedCourses(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var isTwoColumnLayout by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {


            CoursesAppBar(
                modifier = Modifier
                    .align(Alignment.Center)
            )

            LayoutToggleSwitch(
                isTwoColumnLayout = isTwoColumnLayout,
                onToggle = { isTwoColumnLayout = it },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }
        AnimatedContent(
            targetState = isTwoColumnLayout,
            transitionSpec = {
                if (targetState) {
                    slideInHorizontally { width -> width } + fadeIn() with
                            slideOutHorizontally { width -> -width } + fadeOut()
                } else {
                    slideInHorizontally { width -> -width } + fadeIn() with
                            slideOutHorizontally { width -> width } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { isTwoColumn ->
            if (isTwoColumn) {
                StaggeredVerticalGrid(
                    maxColumnWidth = 220.dp,
                    modifier = Modifier.padding(4.dp)
                ) {
                    courses.forEach { course ->
                        FeaturedCourse(course, selectCourse)
                    }
                }
            } else {
                Column {
                    courses.forEach { course ->
                        FeaturedCourse(course, selectCourse, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}


@Composable
fun FeaturedCourse(
    course: Course,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val customFont = FontFamily(
        Font(R.font.ming)
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { selectCourse(course.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                NetworkImage(
                    url = course.thumbUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.featured).uppercase(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                OutlinedAvatar(
                    url = course.instructor,
                    outlineColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = 32.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = course.subject.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.h6,
                    fontFamily = customFont,
                    color = blue700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.h4,
                    fontFamily = customFont,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.OndemandVideo,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${course.steps} lessons",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

@Preview(name = "Featured Course")
@Composable
private fun FeaturedCoursePreview() {
    BlueTheme {
        FeaturedCourse(
            course = courses.first(),
            selectCourse = { }
        )
    }
}

@Preview(name = "Featured Courses Portrait")
@Composable
private fun FeaturedCoursesPreview() {
    BlueTheme {
        FeaturedCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}

@Preview(name = "Featured Courses Dark")
@Composable
private fun FeaturedCoursesPreviewDark() {
    BlueTheme(darkTheme = true) {
        FeaturedCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}

@Preview(
    name = "Featured Courses Landscape",
    widthDp = 640,
    heightDp = 360
)
@Composable
private fun FeaturedCoursesPreviewLandscape() {
    BlueTheme {
        FeaturedCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}
