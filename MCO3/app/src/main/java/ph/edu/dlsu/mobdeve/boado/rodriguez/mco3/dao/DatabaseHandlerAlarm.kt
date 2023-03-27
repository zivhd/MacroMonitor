package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandlerAlarm(context: Context): SQLiteOpenHelper(context, DBNAME, null, DBVER) {
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
        private val DBNAME = "AlarmDatabase"

        const val tableAlarm = "alarm_table" // the database table

        // the database table fields (based on Character class)
        const val tableAlarmID = "alarm_id"
        const val tableAlarmUserID = "alarm_userid"
        const val tableAlarmMeal = "alarm_meal"
        const val tableAlarmCalories = "alarm_calories"
        const val tableAlarmFat = "alarm_fat"
        const val tableAlarmProtein = "alarm_protein"
        const val tableAlarmCarbs = "alarm_carbs"
        const val tableAlarmTime = "alarm_time"
    }

    override fun onCreate(db: SQLiteDatabase?){
        // creates the database
        // written in SQL
        // note: mind the space
        val createAccountsTable = "CREATE TABLE $tableAlarm " +
                "($tableAlarmID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$tableAlarmUserID INTEGER, " +
                "$tableAlarmMeal TEXT, " +
                "$tableAlarmCalories INTEGER, " +
                "$tableAlarmFat INTEGER, " +
                "$tableAlarmProtein INTEGER, " +
                "$tableAlarmCarbs INTEGER, " +
                "$tableAlarmTime INTEGER )"


        db?.execSQL(createAccountsTable)

        // create data sample
        db?.execSQL("INSERT INTO $tableAlarm ($tableAlarmMeal, $tableAlarmCalories, " +
                "$tableAlarmFat, $tableAlarmProtein, $tableAlarmCarbs, $tableAlarmTime ) " +
                "VALUES ('Chicken and Broccoli', 550, 2, 2, 2, 10800 )")
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Ahri')")
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Malphite')")
        // db?.execSQL("INSERT INTO $tableCharacter ($tableCharacterName) values ('Miss Fortune')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db!!.execSQL("DROP TABLE IF EXISTS $tableAlarm")
        onCreate(db)
    }
}