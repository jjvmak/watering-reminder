package w.watering_reminder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_input.*
import java.util.ArrayList

class InputActivity : AppCompatActivity() {

    var db = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        button.setOnClickListener({
            val plantName : String = editText.text.toString()
            editText.setText("")
            Log.v("SAATANA", "added: "+plantName)
            db.insertPlant(plantName)
            var list : MutableList<Plant> = ArrayList()
            list = db.readData()

            for (plant in list) {
                Log.v("SAATANA", plant.name +" "+plant.id)
            }



        })
    }
}
