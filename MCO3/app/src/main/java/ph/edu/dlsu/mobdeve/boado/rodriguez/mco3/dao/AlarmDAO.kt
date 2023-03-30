package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.selects.select
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm

interface AlarmDAO{
    fun addAlarm(alarm: alarm, userID: Int)
    fun removeAlarm(alarmID: Int)
    fun getAlarm(userID: Int) : ArrayList<alarm>
    fun updateAlarm(alarm : alarm)
}

class AlarmDAOSQLLiteImplementation(var context: Context) : AlarmDAO{
    override fun addAlarm(alarm: alarm, userID: Int) {
        val databaseHandlerAlarm:DatabaseHandlerAlarm = DatabaseHandlerAlarm(context)
        val db = databaseHandlerAlarm.readableDatabase
        val contentValues = ContentValues()
        contentValues.put(DatabaseHandlerAlarm.tableAlarmMeal, alarm.meal)
        contentValues.put(DatabaseHandlerAlarm.tableAlarmCalories, alarm.calories)
        contentValues.put(DatabaseHandlerAlarm.tableAlarmFat, alarm.fat)
        contentValues.put(DatabaseHandlerAlarm.tableAlarmCarbs, alarm.carbs)
        contentValues.put(DatabaseHandlerAlarm.tableAlarmProtein, alarm.protein)
        contentValues.put(DatabaseHandlerAlarm.tableAlarmTime, alarm.time)
        contentValues.put(DatabaseHandlerAlarm.tableAlarmUserID, userID)
        val result = db.insert(DatabaseHandlerAlarm.tableAlarm, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeAlarm(alarmID: Int) {
        var databaseHandlerAlarm: DatabaseHandlerAlarm = DatabaseHandlerAlarm(context)
        val db = databaseHandlerAlarm.writableDatabase

        val success = db.delete(DatabaseHandlerAlarm.tableAlarm,
            "${DatabaseHandlerAlarm.tableAlarmID}=$alarmID",
            null)

        db.close()
    }

    override fun updateAlarm(alarm: alarm) {

    }

    @SuppressLint("Range")
    override fun getAlarm(userID: Int): ArrayList<alarm> {
        val alarmList: ArrayList<alarm> = ArrayList()
        val selectQuery = "Select * " +
                "FROM ${DatabaseHandlerAlarm.tableAlarm} WHERE ${DatabaseHandlerAlarm.tableAlarmUserID} = $userID  "

        val databaseHandlerAlarm:DatabaseHandlerAlarm = DatabaseHandlerAlarm(context)
        val db = databaseHandlerAlarm.readableDatabase
        val result = db.rawQuery(selectQuery,null)

        if(result.moveToFirst()){
            do{
                val alarm = alarm(0)
                alarm.id = result.getString(result.getColumnIndex(DatabaseHandlerAlarm.tableAlarmID)).toInt()
                alarm.meal = result.getString(result.getColumnIndex(DatabaseHandlerAlarm.tableAlarmMeal))
                alarm.time = result.getString(result.getColumnIndex(DatabaseHandlerAlarm.tableAlarmTime)).toInt()
                alarm.calories = result.getString(result.getColumnIndex(DatabaseHandlerAlarm.tableAlarmCalories)).toInt()
                alarm.fat = result.getString(result.getColumnIndex(DatabaseHandlerAlarm.tableAlarmFat)).toInt()
                alarm.carbs = result.getString(result.getColumnIndex(DatabaseHandlerAlarm.tableAlarmCarbs)).toInt()
                alarm.protein = result.getString(result.getColumnIndex(DatabaseHandlerAlarm.tableAlarmProtein)).toInt()
                alarmList.add(alarm)
            } while(result.moveToNext())
        }

        return alarmList

    }
}