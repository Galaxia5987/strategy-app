package com.example.frc5987scoutingapp.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.frc5987scoutingapp.data.model.GameData
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun QRinsert() {
    Box {
        startQRCamera(context = LocalContext.current)
    }

}


fun startQRCamera(context : Context) {
    val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAutoZoom() // Requires version 16.1.0+
        .build()

    val scanner = GmsBarcodeScanning.getClient(context, options)

// Trigger this from a Button or UI event
    scanner.startScan()
        .addOnSuccessListener { barcode ->
            val result: Array<String> = barcode.rawValue?.split("")?.toTypedArray() ?: arrayOf("")
            val gameData = GameData.createFromArray(result)
            println(gameData)
            // Handle result
        }
        .addOnCanceledListener {
            // Handle user cancellation
        }
        .addOnFailureListener { e ->
            // Handle errors
        }

}
