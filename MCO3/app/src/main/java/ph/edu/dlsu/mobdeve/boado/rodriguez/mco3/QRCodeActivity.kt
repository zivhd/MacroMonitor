package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.DatabaseHandlerAlarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityQrcodeBinding



class QRCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding
    private lateinit var alarmDAO: AlarmDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmDAO = AlarmDAOSQLLiteImplementation(applicationContext)
        val context = this
        val db = DatabaseHandlerAlarm(context)
        val alarm = alarm(0)
        val calories = intent.getStringExtra("calories")!!.toInt()
        val meal = intent.getStringExtra("meal")!!
        val carbs = intent.getStringExtra("carbs")!!.toInt()
        val fat = intent.getStringExtra("fat")!!.toInt()
        val protein = intent.getStringExtra("protein")!!.toInt()
        val time = intent.getStringExtra("time")!!.toInt()
        val ssid = calories.toString() + meal + carbs.toString() + fat.toString() +protein.toString() + time.toString()
        binding.idIVQrcode.setImageBitmap(getQrCodeBitmap(ssid))


        binding.saveAlarm.setOnClickListener(){
        alarm.calories = calories
        alarm.meal = meal
        alarm.carbs = carbs
        alarm.fat = fat
        alarm.protein = protein
        alarm.time = time
        alarmDAO.addAlarm(alarm)
            //CURRENTLY THE QR CODE THING DOES NOT SAVE
            val gotoAlarmActivity = Intent(this,AlarmActivity::class.java)
            startActivity(gotoAlarmActivity)
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