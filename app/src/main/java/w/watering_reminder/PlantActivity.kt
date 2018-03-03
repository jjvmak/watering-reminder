package w.watering_reminder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_plant.*
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog


class PlantActivity : AppCompatActivity() {

    var plantName = ""
    var db = DatabaseHandler(this)
    var plant = Plant()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant)


        if (intent.hasExtra("plantInfo")) {
            plantName = intent.extras!!.getString("plantInfo")
            initInfo()
        }


        waterButton.setOnClickListener({
            val currentTime = Calendar.getInstance()
            val time = SimpleDateFormat("yyyy-MM-dd'-'kk:mm").format(currentTime.getTime())

            db.updateData(time, plant.name)
            initInfo()
        })

        deleteButton.setOnClickListener({
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        db.deletePlant(plant.name)
                        val inputIntent = Intent(applicationContext, PlantList::class.java)
                        startActivity(inputIntent)
                        this.finish()
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {

                    }
                }

            }
            val ab = AlertDialog.Builder(this)
            ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show()
        })



    }

    fun initInfo() {
        plant = db.getPlantInstance(plantName)
        nameText.text = plant.name

        val currentTime = Calendar.getInstance()
        val time = SimpleDateFormat("yyyy-MM-dd'-'kk:mm").format(currentTime.getTime())
        val sdf = SimpleDateFormat("yyyy-MM-dd'-'kk:mm")

        val date1 = sdf.parse(time)
        val date2 = sdf.parse(plant.date)

        val diff = getDateDiff(date1, date2, TimeUnit.DAYS)

        if (diff > 100) {
            infoText.text = "Aloita kastelu."
        }

        else {
            infoText.text = "Kasteltu viimeksi "+diff+" päivää sitten"
        }

    }

    fun getDateDiff(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
        val diffInMillies = abs(date2.time - date1.time)
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS)
    }

    override fun onBackPressed() {

        this.finish()
    }
}
