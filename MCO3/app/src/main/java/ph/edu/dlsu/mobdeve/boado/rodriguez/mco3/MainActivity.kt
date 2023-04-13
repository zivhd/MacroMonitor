package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.UserDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var email = ""
    var pwd = ""
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userDAO = UserDAOSQLLiteImplementation(this)
        val sharePreference = getSharedPreferences("MY_PRE" , Context.MODE_PRIVATE)
        val editor = sharePreference.edit()
        email = sharePreference.getString("EMAIL" , "").toString()
        pwd = sharePreference.getString("PASSWORD" , "").toString()
        binding.loginBtn.setOnClickListener {
            if (userDAO.checkIfCredentialsMatch(
                    binding.usernameText.text.toString() ,
                    binding.passwordText.text.toString()
                )
            ) {

                editor.putString("EMAIL" , binding.usernameText.text.toString())
                editor.putString("PASSWORD" , binding.passwordText.text.toString())
                editor.putInt("ID" , userDAO.getUserID(binding.usernameText.text.toString()))
                editor.apply()
                Toast.makeText(this , "Successfully Logged In!" , Toast.LENGTH_LONG).show()

                val alarmDAO = AlarmDAOSQLLiteImplementation(this)
                var userID = userDAO.getUserID(binding.usernameText.text.toString())
                Log.d("ID" , userID.toString())
                var alarmList = alarmDAO.getAlarm(userID)
                var size = alarmList.size
                var check = intent.getIntExtra("check" , 0)
                Log.d("check" , check.toString())
                for (i in 0 until size) {
                    val calendar : Calendar = Calendar.getInstance().apply {
                        timeInMillis = System.currentTimeMillis()
                        set(Calendar.HOUR_OF_DAY , alarmList[i].time / 3600)
                        set(Calendar.MINUTE , (alarmList[i].time % 3600) / 60)
                        set(Calendar.SECOND , 0)
                        set(Calendar.MILLISECOND , 0)

                    }
                    Log.d("calendar diff",calendar.before(Calendar.getInstance()).toString())
                    if(calendar.before(Calendar.getInstance())){
                        calendar.add(Calendar.DATE,1)
                    }
                    Log.d("time" , (alarmList[i].time).toString())
                    Log.d("hours" , (alarmList[i].time / 3600).toString())
                    Log.d("minutes" , ((alarmList[i].time % 3600) / 60).toString())
                    val intentList = ArrayList<PendingIntent>()
                    val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
                    val dataIntent = Intent(this , AlarmReceiver::class.java)
                    dataIntent.putExtra("meal" , alarmList[i].meal)
                    dataIntent.putExtra("calories" , alarmList[i].calories)
                    dataIntent.putExtra("carbs" , alarmList[i].carbs)
                    dataIntent.putExtra("fat" , alarmList[i].fat)
                    dataIntent.putExtra("protein" , alarmList[i].protein)
                    dataIntent.putExtra("time" , alarmList[i].time)
                    val pendingIntent = PendingIntent.getBroadcast(
                        this , alarmDAO.getAlarm(userID)[i].id , dataIntent ,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    Log.d("ID of reset alarm" , (alarmDAO.getAlarm(userID)[i].id).toString())
                    intentList.add(pendingIntent)
                    alarmManager?.setRepeating(
                        AlarmManager.RTC_WAKEUP ,
                        calendar.timeInMillis ,
                        1000 * 60 * 24 ,
                        pendingIntent
                    )
                }


                val goToHome = Intent(this , HomeActivity::class.java)
                startActivity(goToHome)
            } else {
                Toast.makeText(this , "Invalid Credentials!" , Toast.LENGTH_LONG).show()
            }

        }
        binding.signupBtn.setOnClickListener {
            val goToReg = Intent(this , RegisterActivity::class.java)
            startActivity(goToReg)
        }


    }

    override fun onStart() {
        super.onStart()
        if (! email.equals("") && ! pwd.equals("")) {
            val i = Intent(this , HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "mc03test"
            val descriptiontext = "test1"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("mco3" , name , importance).apply {
                description = descriptiontext
            }
            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}