package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DBNAME, null, DBVER) {
    // DatabaseHandler parameters
    // context = tells android what is running, where it is running, and who is running
    // SQLiteOpenHelper parameters
    // context = same
    // name of database
    // factory = manages who creates the objects and how it is created
    // important in large apps especially in corporate setting
    // limits connections
    // version of database
    companion object {
        private val DBVER = 1 // must be incremented every time changes are made to the database handler
        private val DBNAME = "UsersDatabase"

        const val tableUser = "user_table" // the database table

        // the database table fields (based on Character class)
        const val tableUserID = "user_id"
        const val tableUserFName = "user_fname"
        const val tableUserLName = "user_lname"
        const val tableUserEmail = "user_email"
        const val tableUserPassword = "user_password"
    }

    override fun onCreate(db: SQLiteDatabase?){
        // creates the database
        // written in SQL
        // note: mind the spaces
        val createAccountsTable = "CREATE TABLE $tableUser " +
                "($tableUserID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$tableUserFName TEXT, " +
                "$tableUserLName TEXT, " +
                "$tableUserEmail TEXT, " +
                "$tableUserPassword TEXT)"

        db?.execSQL(createAccountsTable)

        // create data sample
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Ahri')")
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Malphite')")
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Miss Fortune')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db!!.execSQL("DROP TABLE IF EXISTS $tableUser")
        onCreate(db)
    }
}