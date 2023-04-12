package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.Calories

interface CaloriesDAO{
    fun addCalories(calories: Calories, userID: Int)

    fun removeCalories(caloriesID: Int)

    fun getCalories(userID: Int) : ArrayList<Calories>
    fun getCaloriesFromDate(date: String) : ArrayList<Calories>
    fun updateCalories(calories: Calories)

}

class CaloriesDAOSQLLiteImplementation(var context: Context) : CaloriesDAO {
    override fun addCalories(calories: Calories, userID: Int) {
        val databaseHandlerCalories: DatabaseHandlerCalories = DatabaseHandlerCalories(context)
        val db =databaseHandlerCalories.readableDatabase
        val contentValues = ContentValues()

        contentValues.put(DatabaseHandlerCalories.tableCaloriesDate, calories.date)
        contentValues.put(DatabaseHandlerCalories.tableCaloriesTime, calories.time)
        contentValues.put(DatabaseHandlerCalories.tableCaloriesTotal, calories.totalCalories)
        contentValues.put(DatabaseHandlerCalories.tableCaloriesFat, calories.totalFat)
        contentValues.put(DatabaseHandlerCalories.tableCaloriesProtein, calories.totalProtein)
        contentValues.put(DatabaseHandlerCalories.tableCaloriesMeal, calories.meal)
        contentValues.put(DatabaseHandlerCalories.tableCaloriesCarbs, calories.totalCarbs)
        contentValues.put(DatabaseHandlerCalories.tableCaloriesUserID, userID)
        val result = db.insert(DatabaseHandlerCalories.tableCalories, null, contentValues)

        if(result==(0).toLong())
            Toast.makeText(context,"Failed to log meal", Toast.LENGTH_LONG).show()
        else Toast.makeText(context,"Succesfully logged meal", Toast.LENGTH_LONG).show()
    }

    override fun removeCalories(caloriesID: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    override fun getCalories(userID: Int): ArrayList<Calories> {
        val caloriesList: ArrayList<Calories> = ArrayList()
        val selectQuery = "Select * " +
                "FROM ${DatabaseHandlerCalories.tableCalories} WHERE ${DatabaseHandlerCalories.tableCaloriesUserID} = $userID"

        val databaseHandlerCalories:DatabaseHandlerCalories = DatabaseHandlerCalories(context)
        val db = databaseHandlerCalories.readableDatabase
        val result = db.rawQuery(selectQuery,null)

        if(result.moveToFirst()){
            do{
                val calories = Calories(0)
                calories.id = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesID)).toInt()
                calories.meal = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesMeal))
                calories.time = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesTime)).toInt()
                calories.totalCalories = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesTotal)).toInt()
                calories.totalCarbs = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesCarbs)).toInt()
                calories.totalFat = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesFat)).toInt()
                calories.totalProtein = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesProtein)).toInt()
                calories.date = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesDate))
                caloriesList.add(calories)
            }while(result.moveToNext())
        }
        return caloriesList
    }
    @SuppressLint("Range")
    override fun getCaloriesFromDate(date: String): ArrayList<Calories> {
        val caloriesList: ArrayList<Calories> = ArrayList()
        val selectQuery = "SELECT * " +
                "FROM ${DatabaseHandlerCalories.tableCalories} WHERE ${DatabaseHandlerCalories.tableCaloriesDate} = '$date'"

        val databaseHandlerCalories:DatabaseHandlerCalories = DatabaseHandlerCalories(context)
        val db = databaseHandlerCalories.readableDatabase
        val result = db.rawQuery(selectQuery,null)

        if(result.moveToFirst()){
            do{
                val calories = Calories(0)
                calories.id = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesID)).toInt()
                calories.meal = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesMeal))
                calories.time = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesTime)).toInt()
                calories.totalCalories = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesTotal)).toInt()
                calories.totalCarbs = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesCarbs)).toInt()
                calories.totalFat = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesFat)).toInt()
                calories.totalProtein = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesProtein)).toInt()
                calories.date = result.getString(result.getColumnIndex(DatabaseHandlerCalories.tableCaloriesDate))
                caloriesList.add(calories)
            }while(result.moveToNext())
        }
        return caloriesList
    }
    override fun updateCalories(calories: Calories) {
        TODO("Not yet implemented")
    }

}