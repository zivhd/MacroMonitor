package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, i: Intent?) {

        val calories = i!!.getIntExtra("calories",0)
        val meal = i!!.getStringExtra("meal")!!
        val carbs = i!!.getIntExtra("carbs",0)
        val fat = i!!.getIntExtra("fat",0)
        val protein = i!!.getIntExtra("protein",0)
        val id = i!!.getIntExtra("id",0)
        val time = i!!.getIntExtra("time",0)
        val ssid = "$calories$meal$carbs$fat$protein$time"
        Log.d("ID:", id.toString())
        val intent = Intent(context, StopAlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("ssid",ssid!!)
        intent.putExtra("meal",meal!!)
        intent.putExtra("id",id!!)
        intent.putExtra("carbs",carbs!!)
        intent.putExtra("fat",fat!!)
        intent.putExtra("protein",protein!!)
        intent.putExtra("time",time!!)
        intent.putExtra("calories",calories!!)



        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context!!, "mco3")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(meal)
            .setContentText("calories" + calories)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notification = builder.build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)

        AudioPlay.playAudio(context, R.raw.alarm)
        AudioPlay.continuePlaying(context, R.raw.alarm)




    }




}