package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model

import java.time.LocalDate
import java.util.*

class Calories(var id: Int) {
    var userID: Int = 0
    var totalCalories: Int = 0
    var totalProtein: Int = 0
    var totalCarbs: Int = 0
    var totalFat: Int = 0
    var date: LocalDate = LocalDate.now()
}