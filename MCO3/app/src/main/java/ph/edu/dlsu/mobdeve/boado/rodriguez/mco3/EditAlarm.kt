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
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityEditAlarmBinding

class EditAlarm : AppCompatActivity() {
    private lateinit var binding: ActivityEditAlarmBinding
    private lateinit var alarmDAO: AlarmDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmDAO = AlarmDAOSQLLiteImplementation(applicationContext)
        val context = this
        val db = DatabaseHandlerAlarm(context)

        binding.caloriesText.setText(intent.getIntExtra("calories",0).toString())
        binding.fatText.setText(intent.getIntExtra("fat",0).toString())
        binding.carbsText.setText(intent.getIntExtra("carbs",0).toString())
        binding.proteinText.setText(intent.getIntExtra("protein",0).toString())
        binding.nameText.setText(intent.getStringExtra("meal"))

        val id = intent.getIntExtra("id",0)
        binding.saveBtn.setOnClickListener(){
            if(binding.caloriesText.text.toString().isNotEmpty() &&
                binding.nameText.text.toString().isNotEmpty() &&
                binding.carbsText.text.toString().isNotEmpty() &&
                binding.proteinText.text.toString().isNotEmpty() &&
                binding.fatText.text.toString().isNotEmpty()
            ){
                val hour = binding.timePicker1.currentHour * 3600
                val minutes = binding.timePicker1.currentMinute * 60
                val time = hour + minutes
                val position = intent.getIntExtra("position",0)
                val goToQR = Intent(this,QRCodeActivity::class.java)
                goToQR.putExtra("calories", binding.caloriesText.text.toString())
                goToQR.putExtra("meal",binding.nameText.text.toString())
                goToQR.putExtra("carbs",binding.carbsText.text.toString())
                goToQR.putExtra("fat",binding.fatText.text.toString())
                goToQR.putExtra("protein",binding.proteinText.text.toString())
                goToQR.putExtra("time",time.toString())
                goToQR.putExtra("hour",binding.timePicker1.currentHour)
                goToQR.putExtra("minute",binding.timePicker1.currentMinute)
                goToQR.putExtra("position",position)
                goToQR.putExtra("id",id)
                startActivity(goToQR)
                finish()
            }else {
                Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}