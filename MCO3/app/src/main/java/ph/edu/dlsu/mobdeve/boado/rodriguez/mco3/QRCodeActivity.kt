package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Display
import android.view.WindowManager
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.DatabaseHandlerAlarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityQrcodeBinding
import java.util.*


class QRCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding
    private lateinit var alarmDAO: AlarmDAO
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


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
            alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            pendingIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this, 0, intent,  FLAG_IMMUTABLE)
            }

            alarmManager?.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 10 * 1000,
                pendingIntent
            )
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




    private fun setAlarm(){
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )
    }
}