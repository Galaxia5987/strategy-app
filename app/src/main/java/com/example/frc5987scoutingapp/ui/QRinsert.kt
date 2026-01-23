package com.example.frc5987scoutingapp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.ui.allianceView.AllianceViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


@Composable
fun QRinsert(allianceViewModel: AllianceViewModel, navController: NavController) {
    val context = LocalContext.current
    // Use LaunchedEffect to start the scanner only once.
    LaunchedEffect(Unit) {
        startQRCamera(context, allianceViewModel, navController)
    }
}

private fun startQRCamera(context: Context, allianceViewModel: AllianceViewModel, navController: NavController) {
    val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAutoZoom()
        .build()

    val scanner = GmsBarcodeScanning.getClient(context, options)

    scanner.startScan()
        .addOnSuccessListener { barcode ->
            val result: Array<String> = barcode.rawValue?.split("	")?.toTypedArray() ?: emptyArray()
            if (result.size >= 35) {
                try {
                    val gameData = GameData.createFromArray(result)
                    allianceViewModel.insertGameData(gameData)
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to parse QR data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Invalid QR code data. Expected 35 fields, but found ${result.size}", Toast.LENGTH_LONG).show()
            }
            navController.popBackStack()
        }
        .addOnCanceledListener {
            navController.popBackStack()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "QR scan failed: ${e.message}", Toast.LENGTH_LONG).show()
            navController.popBackStack()
        }
}
