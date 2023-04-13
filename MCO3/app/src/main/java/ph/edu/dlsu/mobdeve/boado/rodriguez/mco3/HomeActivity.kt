package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.UserDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.CaloriesDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.Calories
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val current = formatter.format(date)

        val date1 = Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24))
        val date1str = formatter.format(date1)

        val date2 = Date(System.currentTimeMillis() - 2*(1000 * 60 * 60 * 24))
        val date2str = formatter.format(date2)

        val date3 = Date(System.currentTimeMillis() - 3*(1000 * 60 * 60 * 24))
        val date3str = formatter.format(date3)

        val date4 = Date(System.currentTimeMillis() - 4*(1000 * 60 * 60 * 24))
        val date4str = formatter.format(date4)

        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val editor = sharePreference.edit()
        var email = sharePreference.getString("EMAIL", "").toString()
        val userDAO = UserDAOSQLLiteImplementation(this)
        var fname = userDAO.getUserFName(email)

        val alarmDAO = AlarmDAOSQLLiteImplementation(this)
        var userID = sharePreference.getInt("ID",0)
        var alarmList = alarmDAO.getAlarm(userID)
        var size = alarmList.size
        val dataIntent = Intent(this, AlarmReceiver::class.java)


//        restarts all user's alarms
//        for(i in 0 until size) {
//            val calendar: Calendar = Calendar.getInstance().apply{
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, alarmList[i].time/3600)
//                set(Calendar.MINUTE, alarmList[i].time/60)
//                set(Calendar.SECOND,0)
//                set(Calendar.MILLISECOND,0)
//
//            }
//            val intentList = ArrayList<PendingIntent>()
//            val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
//            val dataIntent = Intent(this, AlarmReceiver::class.java)
//            dataIntent.putExtra("meal", alarmList[i].meal)
//            dataIntent.putExtra("calories", alarmList[i].calories)
//            dataIntent.putExtra("carbs", alarmList[i].carbs)
//            dataIntent.putExtra("fat", alarmList[i].fat)
//            dataIntent.putExtra("protein", alarmList[i].protein)
//            dataIntent.putExtra("time", alarmList[i].time)
//            val pendingIntent = PendingIntent.getBroadcast(this, alarmDAO.getAlarm(userID)[i].id, dataIntent,
//                PendingIntent.FLAG_IMMUTABLE
//            )
//            intentList.add(pendingIntent)
//            alarmManager?.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                1000 * 60 * 24,
//                pendingIntent
//            )
//        }





        binding.title.text = "WELCOME BACK, $fname"


        binding.profile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.logout.setOnClickListener{
            editor.putString("EMAIL", "")
            editor.putString("PASSWORD", "")
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)

            //cancels all user alarms
           for(i in 0 until size) {
               val pintent = PendingIntent.getBroadcast(
                   this, alarmList[i].id, dataIntent,
                   PendingIntent.FLAG_IMMUTABLE
               )
               val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
               alarmManager!!.cancel(pintent)
           }

            startActivity(intent)
            finish()
        }
// GRAPH
        val xValues = listOf(date4str, date3str, date2str, date1str, current)
        val yValues = listOf(totalCal(date4str).toFloat(), totalCal(date3str).toFloat(),
            totalCal(date2str).toFloat(), totalCal(date1str).toFloat(), totalCal(current).toFloat())
        val entries = mutableListOf<Entry>()
        for (i in yValues.indices) {
            entries.add(Entry(i.toFloat(), yValues[i]))
        }
        val xAxisFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return xValues.getOrNull(value.toInt()) ?: value.toString()
            }
        }
        binding.lineChart.xAxis.valueFormatter = xAxisFormatter
//Part2



//Part3
        val vl = LineDataSet(entries, "Calories")


//Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.purple_200
        vl.fillAlpha = R.color.black

//Part5
        binding.lineChart.xAxis.labelRotationAngle = 0f


//Part6
        binding.lineChart.data = LineData(vl)

//Part7
        binding.lineChart.axisRight.isEnabled = false

//Part8
        binding.lineChart.setTouchEnabled(true)
        binding.lineChart.setPinchZoom(true)

//Part9
        binding.lineChart.setNoDataText("No data yet!")

//Part10
        binding.lineChart.animateX(1800, Easing.EaseInExpo)

//GRAPH

//MACROS

        val xValues1 = listOf(date4str, date3str, date2str, date1str, current)
        val yValues1 = listOf(totalPro(date4str).toFloat(), totalPro(date3str).toFloat(), totalPro(date2str).toFloat(), totalPro(date1str).toFloat(), totalPro(current).toFloat())


        val xValues2 = listOf(date4str, date3str, date2str, date1str, current)
        val yValues2 = listOf(totalFat(date4str).toFloat(), totalFat(date3str).toFloat(), totalFat(date2str).toFloat(), totalFat(date1str).toFloat(), totalFat(current).toFloat())

        val xValues3 = listOf(date4str, date3str, date2str, date1str, current)
        val yValues3 = listOf(totalCarbs(date4str).toFloat(), totalCarbs(date3str).toFloat(), totalCarbs(date2str).toFloat(), totalCarbs(date1str).toFloat(), totalCarbs(current).toFloat())

        val entries1 = mutableListOf<Entry>()
        for (i in yValues1.indices) {
            entries1.add(Entry(i.toFloat(), yValues1[i]))
        }


        val entries2 = mutableListOf<Entry>()
        for (i in yValues2.indices) {
            entries2.add(Entry(i.toFloat(), yValues2[i]))
        }

        val entries3 = mutableListOf<Entry>()
        for (i in yValues3.indices) {
            entries3.add(Entry(i.toFloat(), yValues3[i]))
        }

        val dataSet1 = LineDataSet(entries1, "PROTEIN")
        val dataSet2 = LineDataSet(entries2, "FAT")
        val dataSet3 = LineDataSet(entries3, "CARBS")

        dataSet1.setColors(Color.RED)
        dataSet2.setColors(Color.BLUE)
        dataSet3.setColors(Color.GREEN)

        val lineData = LineData(dataSet1, dataSet2, dataSet3)


        binding.macroChart.data = lineData


        val xAxisFormatter1 = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val xValues = if (value < xValues1.size) xValues1 else xValues2
                return xValues.getOrNull(value.toInt()) ?: value.toString()
            }
        }
        binding.macroChart.xAxis.valueFormatter = xAxisFormatter1
        binding.alarmBtn.setOnClickListener{
            val goToAlarm = Intent(this,AlarmActivity::class.java)
            startActivity(goToAlarm)
        }
        binding.meal.setOnClickListener{
            val goToMeal = Intent(this,MealLogActivity::class.java)
            startActivity(goToMeal)
        }


    }
    private fun totalCal(date: String): Int{
        var Calories: ArrayList<Calories>
        var cals = 0
        val calDAO = CaloriesDAOSQLLiteImplementation(this)
        Calories = calDAO.getCaloriesFromDate(date)
        for(i in Calories){
            cals = cals + i.totalCalories
        }
        return cals
    }
    private fun totalPro(date: String): Int{
        var Calories: ArrayList<Calories>
        var cals = 0
        val calDAO = CaloriesDAOSQLLiteImplementation(this)
        Calories = calDAO.getCaloriesFromDate(date)
        for(i in Calories){
            cals = cals + i.totalProtein
        }
        return cals
    }
    private fun totalFat(date: String): Int{
        var Calories: ArrayList<Calories>
        var cals = 0
        val calDAO = CaloriesDAOSQLLiteImplementation(this)
        Calories = calDAO.getCaloriesFromDate(date)
        for(i in Calories){
            cals = cals + i.totalFat
        }
        return cals
    }
    private fun totalCarbs(date: String): Int{
        var Calories: ArrayList<Calories>
        var cals = 0
        val calDAO = CaloriesDAOSQLLiteImplementation(this)
        Calories = calDAO.getCaloriesFromDate(date)
        for(i in Calories){
            cals = cals + i.totalCarbs
        }
        return cals
    }
}