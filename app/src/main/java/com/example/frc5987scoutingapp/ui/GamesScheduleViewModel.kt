package com.example.frc5987scoutingapp.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

class GamesScheduleViewModel : ViewModel() {
    private val _excelData = mutableStateOf<List<List<String>>?>(null)
    val excelData: State<List<List<String>>?> = _excelData

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadExcelData(uri: Uri, context: Context) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = readExcelDataInternal(uri, context)
                _excelData.value = data
            } catch (e: Exception) {
                Log.e("GamesScheduleVM", "Error loading excel", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearData() {
        _excelData.value = null
    }

    private suspend fun readExcelDataInternal(uri: Uri, context: Context): List<List<String>> = withContext(Dispatchers.IO) {
        System.setProperty("javax.xml.stream.XMLInputFactory", "com.ctc.wstx.stax.WstxInputFactory")
        System.setProperty("javax.xml.stream.XMLOutputFactory", "com.ctc.wstx.stax.WstxOutputFactory")
        System.setProperty("javax.xml.stream.XMLEventFactory", "com.ctc.wstx.stax.WstxEventFactory")

        val data = mutableListOf<List<String>>()
        val formatter = DataFormatter()
        
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.use {
                val workbook = WorkbookFactory.create(it)
                val sheet = workbook.getSheetAt(0)
                
                for (rowIndex in 0 until 67) {
                    val row = sheet.getRow(rowIndex)
                    val rowData = mutableListOf<String>()
                    for (colIndex in 0 until 7) {
                        rowData.add(formatter.formatCellValue(row?.getCell(colIndex)))
                    }
                    data.add(rowData)
                }
                workbook.close()
            }
        } catch (t: Throwable) {
            Log.e("GamesScheduleVM", "Excel parsing failed", t)
        }
        data
    }
}
