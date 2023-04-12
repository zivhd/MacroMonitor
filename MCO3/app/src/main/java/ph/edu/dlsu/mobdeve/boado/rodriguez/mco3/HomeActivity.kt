package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.UserDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val current = formatter.format(date)
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

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 3.0),
                DataPoint(2.0, 4.0),
                DataPoint(3.0, 9.0),
                DataPoint(4.0, 6.0),
                DataPoint(5.0, 3.0),
                DataPoint(6.0, 6.0),
                DataPoint(7.0, 1.0),
                DataPoint(8.0, 2.0)
            )
        )
        binding.calorieGraph.viewport.isScalable = true
        binding.calorieGraph.viewport.isScrollable = true
        binding.calorieGraph.viewport.setScalableY(true)
        binding.calorieGraph.viewport.setScrollableY(true)
        series.color = R.color.purple_200
        binding.calorieGraph.addSeries(series)
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

        binding.alarmBtn.setOnClickListener{
            val goToAlarm = Intent(this,AlarmActivity::class.java)
            startActivity(goToAlarm)
        }
        binding.meal.setOnClickListener{
            val goToMeal = Intent(this,MealLogActivity::class.java)
            startActivity(goToMeal)
        }
    }

}