package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.User
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm

interface UserDAO{
    fun addUser(user: User): Boolean
    fun getUsers(): ArrayList<User>
    fun checkIfUserExists(email: String): Boolean
    fun checkIfCredentialsMatch(email: String, password: String): Boolean

    fun getUserFName(email: String): String

    fun getUserID(email: String):Int
}

class UserDAOSQLLiteImplementation(var context: Context): UserDAO{
    override fun addUser(user: User): Boolean{
        val databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val cv = ContentValues()
        cv.put(DatabaseHandler.tableUserFName, user.fname)
        cv.put(DatabaseHandler.tableUserLName, user.lname)
        cv.put(DatabaseHandler.tableUserEmail, user.email)
        cv.put(DatabaseHandler.tableUserPassword, user.password)

        val status = db.insert(DatabaseHandler.tableUser, null, cv)
        if(status==-1.toLong()){
            return false
        }
        return true
        db.close()
    }
    @SuppressLint("Range")
    override fun getUsers(): ArrayList<User>{
        val userList: ArrayList<User> = ArrayList()
        val selectQuery = "SELECT ${DatabaseHandler.tableUserEmail}, ${DatabaseHandler.tableUserID}, ${DatabaseHandler.tableUserPassword} FROM ${DatabaseHandler.tableUser}"
        val databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        val result = db.rawQuery(selectQuery,null)
        if(result.moveToFirst()){
            do{
                val user = User(" ")
                user.id = result.getString(result.getColumnIndex(DatabaseHandler.tableUserID)).toInt()
                user.email = result.getString(result.getColumnIndex(DatabaseHandler.tableUserEmail))
                user.password = result.getString(result.getColumnIndex(DatabaseHandler.tableUserPassword))
            } while(result.moveToNext())
        }
        return userList
    }

    override fun checkIfUserExists(email: String): Boolean {
        val databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("SELECT * FROM ${DatabaseHandler.tableUser} where ${DatabaseHandler.tableUserEmail} = "+'"'+email+'"', null);
        if(cursor.count < 1){
            return false
        }
        return true
    }

    override fun checkIfCredentialsMatch(email: String, password: String): Boolean {
        val databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("SELECT * FROM ${DatabaseHandler.tableUser} where ${DatabaseHandler.tableUserEmail} = "+'"'+email+'"'+" AND ${DatabaseHandler.tableUserPassword} = "+'"'+password+'"', null);
        if(cursor.count < 1){
            return false
        }
        return true
    }

    override fun getUserFName(email: String): String {
        val databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("SELECT * FROM ${DatabaseHandler.tableUser} where ${DatabaseHandler.tableUserEmail} = "+'"'+email+'"', null);
        var fname = ""
        if (cursor != null){
            cursor.moveToFirst()
        }
        fname = cursor.getString(cursor.getColumnIndexOrThrow("user_fname"))
        return fname
    }

    override fun getUserID(email: String): Int{
        val databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("SELECT * FROM ${DatabaseHandler.tableUser} where ${DatabaseHandler.tableUserEmail} = "+'"'+email+'"', null);
        var userID = 0
        if (cursor != null){
            cursor.moveToFirst()
        }
        userID = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
        return userID
    }
}