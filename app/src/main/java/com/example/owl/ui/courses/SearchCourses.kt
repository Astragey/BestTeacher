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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.owl.R
import com.example.owl.model.Topic
import com.example.owl.model.courses
import com.example.owl.ui.theme.BlueTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

import androidx.compose.material.Surface

import com.example.owl.ui.theme.OwlTheme
import androidx.compose.material.Shapes
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.ui.unit.dp
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import android.content.ClipData
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle


suspend fun performLongRunningTask(prompt: String): String {
    val client = OkHttpClient()
    var res = "111"
    val url = "http://123.60.217.228:7001/api/ai/$prompt"
    val request = Request.Builder()
        .url(url)
        .build()

    // 使用协程发送网络请求
    val response = withContext(Dispatchers.IO) {
        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            null
        }
    }

    // 处理服务器响应
    response?.use {
        if (!it.isSuccessful) throw IOException("Unexpected code ${it.code}")

        val responseBody = it.body?.string()
        println("HERE RES $responseBody")
        res = responseBody ?: ""
    }

    println("RES123 $res")
    return res
}

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", text)
    clipboardManager.setPrimaryClip(clipData)
}

@Composable
fun SearchCourses(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var temp = ""
    val (searchTerm, updateSearchTerm) = remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val showSecondBox = remember { mutableStateOf(false) }
    LazyColumn(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxHeight()
    ) {

        item { AppBar(searchTerm, updateSearchTerm) }

        item {
            IconButton(onClick = {
                navController.navigate("courses/my")
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.label_back)
                )
            }
        }
        item {

            val textState = remember { mutableStateOf(TextFieldValue()) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center // 内容居中对齐
            ) {


                TextField(
                    value = textState.value,
                    onValueChange = {
                        // 在输入文本时更新文本状态
                        textState.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth() // 填充最大宽度
                        .padding(16.dp) // 添加内边距
                        .heightIn(min = 56.dp), // 设置最小高度，确保文本框有一定的高度
                    textStyle = TextStyle(color = Color.White), // 将文本颜色设置为白色
                    keyboardOptions = KeyboardOptions.Default.copy(
                        // 定义按下回车时的行为
                        imeAction = ImeAction.Done
                    ),

                    keyboardActions = KeyboardActions(
                        onDone = {
                            // 在按下回车时清空文本框内容
                            println(textState.value.text)
                            runBlocking {
                                temp = performLongRunningTask(textState.value.text)
                                println("Server Response: $temp")
                            }

                            textState.value = TextFieldValue()

                            showSecondBox.value = true

                        }
                    )
                )


            }
            if (showSecondBox.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.padding(16.dp), // 为Surface添加4dp的外边距
//                    elevation =  300.dp, // 设置Surface的阴影高度
                        // 创建一个自定义的RoundedCornerShape，并设置不同的圆角大小
                        shape = RoundedCornerShape(
                            topStart = 16.dp,    // 左上角圆角大小
                            topEnd = 16.dp,      // 右上角圆角大小
                            bottomEnd = 16.dp,   // 右下角圆角大小
                            bottomStart = 16.dp  // 左下角圆角大小
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center) // 确保Column在Surface内居中对齐
                                .padding(16.dp) // 为内部组件添加间距
                        ) {
                            // 您的文本内容
                            Text(
//                      text = temp,
                                text = buildAnnotatedString {
                                    // 使用 AnnotatedString.Builder 来构建文本并为特定部分设置样式
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("春日\n") // 为第一行设置加大加粗的样式
                                    }
                                    // 其他文本使用默认样式
                                    append(
                                        "春日融融万物苏，柳丝轻拂绿波湖。\n" +
                                                "桃花含笑迎风舞，燕子呢喃绕屋庐。\n" +
                                                "远山如黛添新翠，近水含烟染碧芜。\n" +
                                                "美景良辰人共醉，诗情画意满皇都。"
                                    )
                                },
                                // 确保文本也居中对齐
                                modifier = Modifier
//                                .align(Alignment.Center)
                                    .fillMaxWidth() // 填充最大宽度
                                    .padding(16.dp) // 添加内边距
                                    .heightIn(min = 56.dp), // 设置最小高度，确保文本框有一定的高度
                                // 样式、颜色等其他属性
                                textAlign = TextAlign.Center,
                                color = Color.Black // 例如，设置文本颜色为白色
                            )
                            // 添加复制图标
                            Row(
                                modifier = Modifier
                                    .padding(end = 16.dp) // 在按钮右侧添加间距，避免与屏幕边缘重叠
                            ) {
                                // 复制按钮
                                IconButton(onClick = {
                                    val textToCopy = temp // 假定temp是您要复制的文本
                                    copyToClipboard(context, textToCopy)
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                }

                                // 收藏按钮
                                IconButton(onClick = {
                                    // 点击收藏按钮的逻辑
                                    // 例如，将内容添加到收藏列表
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Bookmark,
                                        contentDescription = "Favorite",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                }
                                IconButton(onClick = {
                                    // 点击分享按钮的逻辑
                                    // 例如，使用Intent启动分享对话框
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Share,
                                        contentDescription = "Share",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

/**
 * This logic should live outside UI, but full arch omitted for simplicity in this sample.
 */
private fun getTopics(
    searchTerm: String,
    topics: List<Topic>
): List<Topic> {
    return if (searchTerm != "") {
        topics.filter { it.name.contains(searchTerm, ignoreCase = true) }
    } else {
        topics
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AppBar(
    searchTerm: TextFieldValue,
    updateSearchTerm: (TextFieldValue) -> Unit
) {
    TopAppBar(elevation = 0.dp) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Poem Generate",
                fontSize = 30.sp,
                style = MaterialTheme.typography.subtitle1
                )
        }
    }
}

