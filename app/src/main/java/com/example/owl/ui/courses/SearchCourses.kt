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

@Composable
fun SearchCourses(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var temp = ""
    val (searchTerm, updateSearchTerm) = remember { mutableStateOf(TextFieldValue("")) }
    LazyColumn(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxHeight()
    ) {

        item { AppBar(searchTerm, updateSearchTerm) }

        item {
            IconButton(onClick = {
                navController.navigate("courses/my")
            } ) {
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
                                temp = performLongRunningTask("写一首关于冬天的古诗，要求词藻华丽")
                                println("Server Response: $temp")
                            }

                            textState.value = TextFieldValue()


                        }
                    )
                )


            }
            Box( modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                contentAlignment = Alignment.Center){

                Text(
                    text = temp,
                    modifier = Modifier
                        // 添加顶部间距
                        .align(Alignment.Center), // 文本居底部居中对齐
                )

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

