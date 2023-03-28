package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.UserDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.User
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regbtn?.setOnClickListener{
            if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailText.getText().toString()).matches()
                || binding.passwordText.text.toString().length < 8
                || binding.fnameText.text.toString().length < 1
                || binding.lnameText.text.toString().length < 1){
                Toast.makeText(this, "Please input valid details. Password length must be 8 minimum", Toast.LENGTH_LONG).show();
            }
            else{
                val userDAO = UserDAOSQLLiteImplementation(this)
                val newUser = User(binding.emailText.text.toString())
                newUser.fname = binding.fnameText.text.toString()
                newUser.lname = binding.lnameText.text.toString()
                newUser.password = binding.passwordText.text.toString()
                if(userDAO.checkIfUserExists(binding.emailText.text.toString()) == false){
                    if(userDAO.addUser(newUser) == true){
                        Toast.makeText(this, "Registered!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Failed to Register", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                else{
                    Toast.makeText(this, "Email Already Exists!", Toast.LENGTH_LONG).show()
                }

            }

        }
    }
}