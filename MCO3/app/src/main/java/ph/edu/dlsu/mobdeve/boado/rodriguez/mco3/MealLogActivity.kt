package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityHomeBinding
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityMealLogBinding

class MealLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeBtn.setOnClickListener(){
            val goToHome = Intent(this,HomeActivity::class.java)
            startActivity(goToHome)
        }
        binding.alarmBtn.setOnClickListener{
            val goToAlarm = Intent(this,AlarmActivity::class.java)
            startActivity(goToAlarm)
        }
    }
}