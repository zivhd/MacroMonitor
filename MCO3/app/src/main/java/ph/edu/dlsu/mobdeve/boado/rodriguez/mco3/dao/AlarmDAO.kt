package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import kotlinx.coroutines.selects.select
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm

interface AlarmDAO{
    fun addAlarm(alarm: alarm)
    fun removeAlarm(alarmID: Int)
    fun getAlarm() : ArrayList<alarm>
    fun updateAlarm(alarm : alarm)
}

class AlarmDAOSQLLiteImplementation(var context: Context) : AlarmDAO{
    override fun addAlarm(alarm: alarm) {
        TODO("Not yet implemented")
    }

    override fun removeAlarm(alarmID: Int) {
        TODO("Not yet implemented")
    }

    override fun updateAlarm(alarm: alarm) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    override fun getAlarm(): ArrayList<alarm> {
        val alarmList: ArrayList<alarm> = ArrayList()
        val selectQuery = "Select ${DatabaseHandlerAlarm.tableAlarmID}, ${DatabaseHandlerAlarm.tableAlarmCalories}," +
                "${DatabaseHandlerAlarm.tableAlarmMeal}, " +
                "${DatabaseHandlerAlarm.tableAlarmUserID}, " +
                "${DatabaseHandlerAlarm.tableAlarmTime} " +
                "FROM ${DatabaseHandlerAlarm.tableAlarm}"

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
                alarmList.add(alarm)
            } while(result.moveToNext())
        }

        return alarmList

    }
}