package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.UserDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var email = ""
    var pwd = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userDAO = UserDAOSQLLiteImplementation(this)
        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val editor = sharePreference.edit()
        email = sharePreference.getString("EMAIL", "").toString()
        pwd = sharePreference.getString("PASSWORD", "").toString()
        binding.loginBtn.setOnClickListener{
        if(userDAO.checkIfCredentialsMatch(binding.usernameText.text.toString(), binding.passwordText.text.toString())){

            editor.putString("EMAIL", binding.usernameText.text.toString())
            editor.putString("PASSWORD", binding.passwordText.text.toString())
            editor.putInt("ID",userDAO.getUserID(binding.usernameText.text.toString()))
            editor.apply()
            Toast.makeText(this, "Successfully Logged In!", Toast.LENGTH_LONG).show()
            val goToHome = Intent(this,HomeActivity::class.java)
            startActivity(goToHome)
        }
        else{
            Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_LONG).show()
        }

    }
        binding.signupBtn.setOnClickListener{
            val goToReg = Intent(this,RegisterActivity::class.java)
            startActivity(goToReg)
        }
    }
    override fun onStart(){
        super.onStart()
        if(!email.equals("") && !pwd.equals("")){
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "mc03test"
            val descriptiontext = "test1"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("mco3", name, importance).apply {
                description = descriptiontext
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}