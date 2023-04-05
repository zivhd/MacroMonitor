package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.UserDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val editor = sharePreference.edit()
        var email = sharePreference.getString("EMAIL", "").toString()
        val userDAO = UserDAOSQLLiteImplementation(this)
        var fname = userDAO.getUserFName(email)
        var lname = userDAO.getUserLName(email)

        binding.title.text = "$fname's Profile Page"
        binding.name.text = "Name: $fname $lname"
        binding.email.text = "Email: $email"
        binding.alarmBtn.setOnClickListener{
            val goToAlarm = Intent(this, AlarmActivity::class.java)
            startActivity(goToAlarm)
        }
        binding.meal.setOnClickListener{
            val goToMeal = Intent(this, MealLogActivity::class.java)
            startActivity(goToMeal)
        }
        binding.homeBtn.setOnClickListener(){
            val goToHome = Intent(this, HomeActivity::class.java)
            startActivity(goToHome)
        }
    }
}