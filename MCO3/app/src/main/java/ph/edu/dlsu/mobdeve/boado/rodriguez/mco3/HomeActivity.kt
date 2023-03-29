package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityHomeBinding
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityRegisterBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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