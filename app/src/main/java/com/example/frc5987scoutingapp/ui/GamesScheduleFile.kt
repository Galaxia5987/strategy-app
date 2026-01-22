package com.example.frc5987scoutingapp.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GamesScheduleFile() {
    val context = LocalContext.current
    // Scope the ViewModel to the Activity so data persists during navigation
    val gamesScheduleViewModel: GamesScheduleViewModel = viewModel(
        viewModelStoreOwner = context as ComponentActivity
    )
    
    val excelData by gamesScheduleViewModel.excelData
    val isLoading by gamesScheduleViewModel.isLoading

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                gamesScheduleViewModel.loadExcelData(it, context)
            }
        }
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            // Capture excelData in a local variable for safe null-checking
            val currentData = excelData
            
            if (currentData == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Button(
                            onClick = { filePickerLauncher.launch("*/*") },
                            shape = CircleShape,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Add GameSchedule (Excel)", fontSize = 18.sp)
                        }
                    }
                }
            } else {
                Text(
                    text = "Game Schedule",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .border(2.dp, Color.Black)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        // Use the safe local copy 'currentData' to avoid crashes during clear
                        itemsIndexed(currentData) { rowIndex, row ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Max)
                            ) {
                                row.forEachIndexed { colIndex, cellValue ->
                                    val backgroundColor = when (colIndex) {
                                        in 1..3 -> Color.Red
                                        in 4..6 -> Color.Blue
                                        else -> Color.White
                                    }
                                    val textColor = if (colIndex in 1..6) Color.White else Color.Black
                                    
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .background(backgroundColor)
                                            .border(0.5.dp, Color.LightGray)
                                            .padding(4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = cellValue,
                                            fontSize = 12.sp,
                                            color = textColor,
                                            fontWeight = if (rowIndex == 0) FontWeight.Bold else FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }
                    }
                    
                    IconButton(
                        onClick = { gamesScheduleViewModel.clearData() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(36.dp)
                            .background(Color.DarkGray.copy(alpha = 0.8f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
