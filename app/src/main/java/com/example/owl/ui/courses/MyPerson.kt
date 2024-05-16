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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.owl.R
import com.example.owl.model.Course
import com.example.owl.ui.common.CourseListItem
import com.example.owl.ui.theme.BlueTheme
import com.example.owl.ui.common.PersonListItem




@Composable
fun MyPersons(
    persons: List<Person>,
    selectPerson: (Long) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    LazyColumn(modifier) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        item {
            PersonAppBar()
        }
        itemsIndexed(
            items = persons,
            key = { _, person -> person.id }
        ) { index, person ->
            Myperson(
                person = person,
                index = index,
                selectPerson = selectPerson,
                navController = navController
            )
        }
    }
}


@Composable
fun Myperson(
    person: Person,
    index: Int,
    selectPerson: (Long) -> Unit,
    navController: NavHostController
) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        val stagger = if (index % 2 == 0) 72.dp else 16.dp
        Spacer(modifier = Modifier.width(stagger))
        Box(){

        }
        Spacer(modifier = Modifier.width(stagger))
        Box(){

            PersonListItem(
                person = person,
                onClick = { },
                shape = RoundedCornerShape(topStart = 24.dp),
                modifier = Modifier.height(96.dp)
            )

            Button(
                onClick = { navController.navigate("courses/search") },
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 10.dp) // 调整右侧和底部的填充以将按钮左移
                    .width(80.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = "Try it!",
                    fontSize = 14.sp, // 将字体大小增加以使其更大
                    modifier = Modifier.fillMaxWidth() // 使文本占据整个按钮的宽度
                )
            }
        }

    }
}


