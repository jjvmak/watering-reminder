package w.watering_reminder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_plant_list.*
import java.util.ArrayList

class PlantList : AppCompatActivity() {

    var db = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_list)

        var list : MutableList<Plant> = ArrayList()
        list = db.readData()

        var plantNames = ArrayList<String>()

        for (plant in list) {
            plantNames.add(plant.name)
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, plantNames)
        plants.adapter = adapter

        plants.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position) as String
            Log.v("SAATANA", selectedItem)



        }


    }


}
