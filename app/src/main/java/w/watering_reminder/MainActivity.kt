package w.watering_reminder

import android.annotation.TargetApi
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    var db = DatabaseHandler(this)

    @TargetApi(23)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


      /*  var list : MutableList<Plant> = ArrayList()
        list = db.readData()


        for (plant in list) {
            Log.v("SAATANA", plant.name +" "+plant.date)
        }*/

        addPlant.setOnClickListener({
            val inputIntent = Intent(applicationContext, InputActivity::class.java)
            startActivity(inputIntent)

        })

        listButton.setOnClickListener({
            val inputIntent = Intent(applicationContext, PlantList::class.java)
            startActivity(inputIntent)

        })
    }

    override fun onBackPressed() {

        this.finish()
    }
}
