package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, i: Intent?) {

        val calories = i!!.getIntExtra("calories",0).toString()
        val meal = i!!.getStringExtra("meal")
        val carbs = i!!.getIntExtra("carbs",0).toString()
        val fat = i!!.getIntExtra("fat",0).toString()
        val protein = i!!.getIntExtra("protein",0).toString()
        val time = i!!.getIntExtra("time",0).toString()
        val ssid = "$calories$meal$carbs$fat$protein$time"
        val intent = Intent(context, StopAlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("ssid",ssid)


        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context!!, "mco3")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(meal)
            .setContentText("Time to Eat!")
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




    }




}