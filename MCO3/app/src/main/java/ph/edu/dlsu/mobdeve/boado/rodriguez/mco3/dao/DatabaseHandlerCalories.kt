package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandlerCalories(context: Context): SQLiteOpenHelper(context, DBNAME, null, DBVER) {
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
        private val DBNAME = "CaloriesDatabase"

        const val tableCalories = "calories_table" // the database table

        // the database table fields (based on Character class)
        const val tableCaloriesID = "calories_id"
        const val tableCaloriesUserID = "calories_userid"
        const val tableCaloriesTotal = "calories_total"
        const val tableCaloriesDate= "calories_date"
    }

    override fun onCreate(db: SQLiteDatabase?){
        // creates the database
        // written in SQL
        // note: mind the spaces
        val createAccountsTable = "CREATE TABLE $tableCalories " +
                "($tableCaloriesID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$tableCaloriesUserID INTEGER, " +
                "$tableCaloriesTotal INTEGER, " +
                "$tableCaloriesDate DATE)"

        db?.execSQL(createAccountsTable)

        // create data sample
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Ahri')")
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Malphite')")
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Miss Fortune')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db!!.execSQL("DROP TABLE IF EXISTS $tableCalories")
        onCreate(db)
    }
}