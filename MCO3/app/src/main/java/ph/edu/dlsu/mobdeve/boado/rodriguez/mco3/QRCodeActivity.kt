package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var dataIntent: Intent
    private lateinit var intentList: ArrayList<PendingIntent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        var userID = sharePreference.getInt("ID",0)
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
        val hour = intent.getIntExtra("hour",0)
        val minute = intent.getIntExtra("minute",0)

        val calendar: Calendar = Calendar.getInstance().apply{
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND,0)
            set(Calendar.MILLISECOND,0)

        }



        val ssid = "$calories$meal$carbs$fat$protein$time"
        binding.idIVQrcode.setImageBitmap(getQrCodeBitmap(ssid))


        binding.saveAlarm.setOnClickListener(){
        alarm.calories = calories
        alarm.meal = meal
        alarm.carbs = carbs
        alarm.fat = fat
        alarm.protein = protein
        alarm.time = time
        alarmDAO.addAlarm(alarm,userID)

            //CURRENTLY THE QR CODE THING DOES NOT SAVE
            val intentList = ArrayList<PendingIntent>()
            alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val dataIntent = Intent(this@QRCodeActivity, AlarmReceiver::class.java)
            dataIntent.putExtra("meal", meal)
            dataIntent.putExtra("calories", calories)
            dataIntent.putExtra("carbs", carbs)
            dataIntent.putExtra("fat", fat)
            dataIntent.putExtra("protein", protein)
            dataIntent.putExtra("time", time)
            val pendingIntent = PendingIntent.getBroadcast(this@QRCodeActivity, alarmDAO.getAlarm(userID).last().id, dataIntent, FLAG_IMMUTABLE)
            intentList.add(pendingIntent)





            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 60 * 24,
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

}