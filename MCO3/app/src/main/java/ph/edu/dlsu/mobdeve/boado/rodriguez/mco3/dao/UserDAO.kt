package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.User

interface UserDAO{
    fun addUser(user: User): Boolean
    fun getUsers(): ArrayList<User>
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
    override fun getUsers(): ArrayList<User>{
        val userList: ArrayList<User> = ArrayList()
        val selectQuery = "SELECT ${DatabaseHandler.tableUserEmail}, ${DatabaseHandler.tableUserID} FROM ${DatabaseHandler.tableUser}"
        val databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        }
        catch(e: SQLiteException){
            db.close()
            return ArrayList()
        }

        var user = User("")
        if(cursor.moveToFirst()){
            do{
                user = User(cursor.getString(0))
                user.id = cursor.getInt(1)
            }while(cursor.moveToNext())
        }
        db.close()
        return userList
    }
}