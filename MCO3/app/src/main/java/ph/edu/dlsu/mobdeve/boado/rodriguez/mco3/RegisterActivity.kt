package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
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
            //if wrong email or
            if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailText.getText().toString()).matches()
                || binding.passwordText.text.toString().length < 8
                || binding.fnameText.text.toString().length < 1
                || binding.lnameText.text.toString().length < 1){
                Toast.makeText(this, "Please input valid details. Password length must be 8 minimum", Toast.LENGTH_LONG).show();
            }
            else{
                //add code to check if email already exists
                val userDAO = UserDAOSQLLiteImplementation(this)
                val newUser = User(binding.emailText.toString())
                newUser.fname = binding.fnameText.toString()
                newUser.lname = binding.lnameText.toString()
                newUser.password = binding.passwordText.toString()
                userDAO.addUser(newUser)
                if(userDAO.addUser(newUser) == true){
                    Toast.makeText(this, "ADDED", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "FAILED", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}