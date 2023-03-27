package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.DatabaseHandlerAlarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityAddAlarmBinding
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityAlarmBinding

class AddAlarm : AppCompatActivity() {
    private lateinit var binding: ActivityAddAlarmBinding
    private lateinit var alarmDAO: AlarmDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmDAO = AlarmDAOSQLLiteImplementation(applicationContext)
        val context = this
        val db = DatabaseHandlerAlarm(context)
        binding.saveBtn.setOnClickListener(){
            if(binding.caloriesText.text.toString().isNotEmpty() &&
                    binding.nameText.text.toString().isNotEmpty() &&
                    binding.carbsText.text.toString().isNotEmpty() &&
                    binding.proteinText.text.toString().isNotEmpty() &&
                    binding.fatText.text.toString().isNotEmpty()
                    ){
                val alarm = alarm(0)
                alarm.calories = binding.caloriesText.text.toString().toInt()
                alarm.meal = binding.nameText.text.toString()
                alarm.carbs = binding.carbsText.text.toString().toInt()
                alarm.fat = binding.fatText.text.toString().toInt()
                alarm.protein = binding.proteinText.text.toString().toInt()
                val hour = binding.timePicker1.currentHour * 3600
                val minutes = binding.timePicker1.currentMinute * 60
                val time = hour + minutes
                alarm.time = time
                alarmDAO.addAlarm(alarm)
                val goToAlarm = Intent(this,AlarmActivity::class.java)
                startActivity(goToAlarm)
                finish()
            }else {
                Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}