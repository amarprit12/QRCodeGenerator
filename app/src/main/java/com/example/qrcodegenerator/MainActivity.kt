package com.example.qrcodegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : AppCompatActivity() {
    private var etInput: EditText? = null
    private var btGenerate: Button? = null
    private var ivOutput: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etInput = findViewById(R.id.et_input)
        btGenerate = findViewById(R.id.btn_generate_qr_code)
        ivOutput = findViewById(R.id.iv_output)

        btGenerate?.setOnClickListener {
            //Get input value from editText
            val text = etInput?.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(this@MainActivity, "Text is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Initialize multi format writer
            val writer = MultiFormatWriter()
            try {
                // Initialize bit matrix
                val matrix = writer.encode(text, BarcodeFormat.QR_CODE, 350, 350)
                // Initialize barCode encoder
                val encoder = BarcodeEncoder()
                // Initialize bitmap
                val bitmap = encoder.createBitmap(matrix)
                // Set bitmap on image View
                ivOutput?.setImageBitmap(bitmap)
                // Initialize input manager
                val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                // Hide soft keyboard
                manager.hideSoftInputFromWindow(etInput?.applicationWindowToken, 0)

            } catch (e: WriterException) {
                Log.e("MainActivity", "Error: " + e.message)
            }
        }
    }
}