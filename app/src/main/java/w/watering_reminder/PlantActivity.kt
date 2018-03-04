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
import android.util.Log
import android.widget.Toast


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
            Log.v("mita", "päivitä: "+plant.name)
            initInfo()
            Toast.makeText(this,"Kasteltu!", Toast.LENGTH_SHORT).show()
        })

        nutrientbutton.setOnClickListener({
            val currentTime = Calendar.getInstance()
            val time = SimpleDateFormat("yyyy-MM-dd'-'kk:mm").format(currentTime.getTime())

            db.updateNutrient(time, plant.name)
            Log.v("mita", "päivitä: "+plant.name)
            initInfo()
            Toast.makeText(this,"Ravinteet annettu!", Toast.LENGTH_SHORT).show()
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
            ab.setMessage("Haluatko varmasti poistaa?").setPositiveButton("Kyllä", dialogClickListener)
                    .setNegativeButton("Ei", dialogClickListener).show()
        })



    }

    fun initInfo() {
        plant = db.getPlantInstance(plantName)
        textView.text = plant.name

        val currentTime = Calendar.getInstance()
        val time = SimpleDateFormat("yyyy-MM-dd'-'kk:mm").format(currentTime.getTime())
        val sdf = SimpleDateFormat("yyyy-MM-dd'-'kk:mm")

        val date1 = sdf.parse(time)
        val date2 = sdf.parse(plant.date)
        val date3 = sdf.parse(plant.nDate)

        val diff = getDateDiff(date1, date2, TimeUnit.DAYS)
        val ndiff = getDateDiff(date1, date3, TimeUnit.DAYS)

        if (diff > 100) {
            infoText.text = "Aloita kastelu."
        }

        else if(ndiff > 100) {
            infoText.text = "Kasteltu viimeksi "+diff+" päivää sitten."
        }

        else {
            infoText.text = "Kasteltu viimeksi "+diff+" päivää sitten.\n"+""+
                            "Ravinteet annettu viimeksi "+ndiff+" päivää sitten."
        }

       /* var list : MutableList<Plant> = ArrayList()
        list = db.readData()


        for (plant in list) {
            Log.v("mita", plant.name +" "+plant.date)
        }*/

    }

    fun getDateDiff(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
        val diffInMillies = abs(date2.time - date1.time)
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS)
    }

    override fun onBackPressed() {

        this.finish()
    }
}
