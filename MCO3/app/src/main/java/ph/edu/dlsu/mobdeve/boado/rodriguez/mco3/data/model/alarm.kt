package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model

import java.sql.Time

class alarm(var id: Int, var time: Time) {
    var userID: Int = 0
    var meal: String = ""
    var calories: Int = 0
    var protein: Int = 0
    var carbs: Int = 0
    var fat: Int = 0
}