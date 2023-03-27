package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAO
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.dao.AlarmDAOSQLLiteImplementation
import ph.edu.dlsu.mobdeve.boado.rodriguez.mco3.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding

    private var alarmAdapter: AlarmAdapter? = null

    private lateinit var alarmDAO: AlarmDAO

    private lateinit var itemTouchHelper: ItemTouchHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmDAO = AlarmDAOSQLLiteImplementation(applicationContext)

        alarmAdapter = AlarmAdapter(applicationContext,alarmDAO.getAlarm())

        binding.alarmList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

        binding.alarmList.adapter = alarmAdapter

        var swipeCallback = SwipeCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        swipeCallback.alarmAdapter = alarmAdapter
        itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.alarmList)

        binding.addAlarmBtn.setOnClickListener(){
            val goToAddAlarm = Intent(this,AddAlarm::class.java)
            startActivity(goToAddAlarm)
        }

    }
}