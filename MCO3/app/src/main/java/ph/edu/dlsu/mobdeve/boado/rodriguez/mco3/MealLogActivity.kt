package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.CaloriesDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.CaloriesDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityAlarmBinding
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityHomeBinding
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityMealLogBinding

class MealLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealLogBinding



    private var caloriesAdapter: CaloriesAdapter? = null

    private lateinit var caloriesDAO: CaloriesDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)

        var userID = sharePreference.getInt("ID",0)

        caloriesDAO = CaloriesDAOSQLLiteImplementation(applicationContext)

        caloriesAdapter = CaloriesAdapter(applicationContext,caloriesDAO.getCalories(userID))

        binding.caloriesList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

        binding.caloriesList.adapter = caloriesAdapter



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