package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Context
import android.text.format.DateUtils.formatElapsedTime
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.data.model.alarm
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ItemAlarmBinding

class AlarmAdapter(private val context: Context,
private var alarmList: ArrayList<alarm>) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmAdapter.ViewHolder {
        val itemBinding = ItemAlarmBinding. inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: AlarmAdapter.ViewHolder, position: Int) {
        holder.bindItems(alarmList[position])
    }

    inner class ViewHolder(private val itemBinding: ItemAlarmBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bindItems(alarm: alarm){
            itemBinding.alarmName.text = alarm.meal
            itemBinding.alarmCalories.text = alarm.calories.toString()  + " Calories"
            itemBinding.alarmTime.text = formatElapsedTime(alarm.time.toLong())
        }
    }
}