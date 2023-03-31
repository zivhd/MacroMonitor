package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ItemAlarmBinding


class AlarmAdapter(private val context: Context,
private var alarmList: ArrayList<alarm>) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemAlarmBinding. inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(alarmList[position])
    }

    inner class ViewHolder(private val itemBinding: ItemAlarmBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bindItems(alarm: alarm){
            val calories = alarm.calories
            val meal = alarm.meal
            val carbs = alarm.carbs
            val fat = alarm.fat
            val protein = alarm.protein
            val time = alarm.time
            itemBinding.alarmName.text = meal
            itemBinding.alarmCalories.text = calories.toString() + " calories"
            itemBinding.alarmCarbs.text = carbs.toString() + "g carbs"
            itemBinding.alarmFat.text = fat.toString() + "g fat"
            itemBinding.alarmProtein.text = protein.toString() + "g protein"
            itemBinding.alarmTime.text = convertSecondstoAMPM(alarm.time)

            itemBinding.card.setOnClickListener(){
                val intent = Intent(context, ShowQR::class.java)
                val ssid = "$calories$meal$carbs$fat$protein$time"
                intent.putExtra("ssid",ssid)
                this.itemView.context.startActivity(intent)

            }

            itemBinding.editBtn.setOnClickListener(){
                val intent = Intent(context, EditAlarm::class.java)
                val id = alarm.id
                intent.putExtra("ID",id)
                intent.putExtra("meal", meal)
                intent.putExtra("calories", calories)
                intent.putExtra("fat", fat)
                intent.putExtra("protein", protein)
                this.itemView.context.startActivity(intent)
            }
        }
    }
    fun deleteAlarm(position: Int) {
        val alarmDAO = AlarmDAOSQLLiteImplementation(context)
        val intent = Intent(context, AlarmReceiver::class.java)
        val pintent = PendingIntent.getBroadcast(context, alarmList[position].id, intent, FLAG_IMMUTABLE)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmDAO.removeAlarm(alarmList[position].id)
        alarmList.removeAt(position)
        notifyItemRemoved(position)
        alarmManager!!.cancel(pintent)
    }

    fun convertSecondstoAMPM(time : Int): String{
        var hours = time/3600
        var minutes = (time % 3600)/60

        if(hours > 12)
            return (hours-12).toString() +":"+ (minutes).toString().padStart(2,'0') + " PM"
        else return hours.toString()  +":"+minutes.toString().padStart(2,'0') + " AM"
    }
}