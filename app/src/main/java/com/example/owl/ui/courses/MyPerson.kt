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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.owl.ui.theme.blue200
import com.example.owl.ui.theme.blue700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonAppBarnew() {
    TopAppBar(
        title = { Text("Persons") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyPersons(
    persons: List<Person>,
    selectPerson: (Long) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        topBar = { PersonAppBar() },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
}


@Composable
fun Myperson(
    person: Person,
    index: Int,
    selectPerson: (Long) -> Unit,
    navController: NavHostController
) {
    val stagger = if (index % 2 == 0) 32.dp else 16.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.width(stagger))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .border(
                    width = 7.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = Color.Yellow, // 使用你想要的颜色
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(7.dp), // 确保卡片和边框之间有间距
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp,
                    focusedElevation = 10.dp,
                    hoveredElevation = 10.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Row( modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 50.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = person.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = blue700,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = person.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(end = 60.dp)
                            )
                        }
                    }
                    FilledTonalButton(
                        onClick = { navController.navigate("courses/search") },
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = blue700,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Try it!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "Go",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyPersonsPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        MyPersons(
            persons = persons,
            selectPerson = { /* 预览中不需要实际功能 */ },
            navController = navController
        )
    }
}