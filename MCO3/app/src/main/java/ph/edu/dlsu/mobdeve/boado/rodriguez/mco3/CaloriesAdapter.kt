package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3


import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.Calories
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ItemLogsBinding


class CaloriesAdapter(private val context: Context,
                   private var caloriesList: ArrayList<Calories>) : RecyclerView.Adapter<CaloriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemLogsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return caloriesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(caloriesList[position])
    }

    inner class ViewHolder(private val itemBinding: ItemLogsBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bindItems(calories: Calories){
            val total = calories.totalCalories
            val meal = calories.meal
            val carbs = calories.totalCarbs
            val fat = calories.totalFat
            val protein = calories.totalProtein
            val time = calories.time
            val date = calories.date
            itemBinding.mealName.text = meal
            itemBinding.mealCalories.text = total.toString() + " calories"
            itemBinding.mealCarbs.text = carbs.toString() + "g carbs"
            itemBinding.mealFat.text = fat.toString() + "g fat"
            itemBinding.mealProtein.text = protein.toString() + "g protein"
            itemBinding.mealTime.text = convertSecondstoAMPM(time)
            Log.d("DATE",date)
            itemBinding.mealDate.text = date


        }
    }

    fun convertSecondstoAMPM(time : Int): String{
        var hours = time/3600
        var minutes = (time % 3600)/60

        if(hours > 12)
            return (hours-12).toString() +":"+ (minutes).toString().padStart(2,'0') + " PM"
        else return hours.toString()  +":"+minutes.toString().padStart(2,'0') + " AM"
    }
}