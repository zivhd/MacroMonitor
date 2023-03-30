package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.gms.vision.Detector.Detections
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.CaloriesDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.CaloriesDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.Calories
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityStopAlarmBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class StopAlarmActivity : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ActivityStopAlarmBinding
    private lateinit var alarmDAO: AlarmDAO
    private lateinit var alarmList: ArrayList<alarm>
    private lateinit var ssid: String
    private var calories = 0
    private lateinit var meal: String
    private var carbs = 0
    private var fat = 0
    private var protein = 0
    private var time = 0
    private var alarmID = 0
    private var userID =0
    private var caloriesAdapter: CaloriesAdapter? = null
    private lateinit var caloriesDAO: CaloriesDAO

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmDAO = AlarmDAOSQLLiteImplementation(applicationContext)
        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
         userID = sharePreference.getInt("ID",0)
        alarmList = alarmDAO.getAlarm(userID)
        caloriesDAO = CaloriesDAOSQLLiteImplementation(applicationContext)
        caloriesAdapter = CaloriesAdapter(applicationContext,caloriesDAO.getCalories(userID))

        alarmID = intent.getIntExtra("id",0)
        calories = intent.getIntExtra("calories",0)
        carbs = intent.getIntExtra("carbs",0)
        fat = intent.getIntExtra("fat",0)
        protein = intent.getIntExtra("protein",0)
        time = intent.getIntExtra("time",0)
        ssid = intent.getStringExtra("ssid")!!
        meal = intent.getStringExtra("meal")!!

        Log.d("alarmID", alarmID.toString())
        Log.d("calories", calories.toString())
        Log.d("carbs", carbs.toString())
        Log.d("fat", fat.toString())
        Log.d("protein", protein.toString())
        Log.d("ssid", ssid.toString())
        Log.d("meal", meal.toString())

        Toast.makeText(this@StopAlarmActivity, calories.toString(), Toast.LENGTH_LONG).show()



        binding.debugStopAlarm.setOnClickListener(){
            stopAlarm(alarmID,meal!!,carbs,fat,protein,time,calories)
        }


        if (ContextCompat.checkSelfPermission(
                this@StopAlarmActivity, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@StopAlarmActivity, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)
    }


    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue




                    //Don't forget to add this line printing value or finishing activity must run on main thread
                    runOnUiThread {
                        //
                        if(scannedValue == ssid){
                            cameraSource.stop()
                            Toast.makeText(this@StopAlarmActivity, "value- $scannedValue", Toast.LENGTH_LONG).show()
                            stopAlarm(alarmID,meal!!,carbs,fat,protein,time,calories)


                        }

                       //
                    }
                }else
                {
                    Toast.makeText(this@StopAlarmActivity, "value- else", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@StopAlarmActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun stopAlarm(ID: Int, meal : String, carbs : Int, fat: Int, protein: Int, time: Int, calories: Int){
        AudioPlay.stopAudio( )
        val intent = Intent(this, MealLogActivity::class.java)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val current = formatter.format(date)
        val cal =  Calories(0)
        Log.d("CUIRRENT DATE", cal.date)
        cal.totalCalories = calories
        cal.totalProtein =protein
        cal.totalFat = fat
        cal.totalCarbs = carbs
        cal.date = current
        cal.time = time
        cal.meal = meal
        cal.userID = userID
        Log.d("CUIRRENT DATE", cal.date)
        caloriesDAO.addCalories(cal,userID)



        startActivity(intent)
        finish()
        AudioPlay.stopAudio()
        finish()
    }
}