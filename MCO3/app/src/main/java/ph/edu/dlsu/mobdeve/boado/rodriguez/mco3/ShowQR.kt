package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityQrcodeBinding
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityShowQrBinding

class ShowQR : AppCompatActivity() {

    private lateinit var  binding: ActivityShowQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ssid = intent.getStringExtra("ssid")!!
        binding.idIVQrcode.setImageBitmap(getQrCodeBitmap(ssid))
        binding.goBackBtn.setOnClickListener(){
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun getQrCodeBitmap(ssid: String): Bitmap {
        val size = 512 //pixels
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(ssid, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}