package w.watering_reminder

import android.annotation.TargetApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import w.watering_remainder.R
import java.util.*
import java.text.SimpleDateFormat

import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    var db = DatabaseHandler(this)

    @TargetApi(23)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInfo()

        waterButton.setOnClickListener({
            val currentTime = Calendar.getInstance()
            val time = SimpleDateFormat("yyyy-MM-dd'-'kk:mm").format(currentTime.getTime())


            var list : MutableList<Date> = ArrayList()
            list = db.readData()

            Log.v("SAATANA",""+ list.size)

            if (list.size < 1) {
                db.insertData(time)
                initInfo()
            }

            else {
                db.updateData(time)
                initInfo()
            }


        })

    }

    @TargetApi(23)
    fun initInfo() {
        var list : MutableList<Date> = ArrayList()
        list = db.readData()

        if (list.size < 1 ) {
            infoText.text = "Aloita kastelu."
        }

        else {

            val sdf = SimpleDateFormat("yyyy-MM-dd'-'kk:mm")
            list = db.readData()
            Log.v("SAATANA",""+ list.size)
            val date1 = list.get(list.size-1)
            val currentTime = Calendar.getInstance()
            val time = SimpleDateFormat("yyyy-MM-dd'-'kk:mm").format(currentTime.getTime())
            val date2 = sdf.parse(time)
            val diff = getDateDiff(date1, date2, TimeUnit.DAYS)

            if (diff.toDouble() == 0.0) {
                infoText.text = "Kasteltu tänään."
            }
            else {
                infoText.text = "Kasteltu viimeksi " + diff +" päivää sitten."
            }

        }




        //infoText.text = sdf.format(list.get(list.size-1))

    }

    fun getDateDiff(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
        val diffInMillies = date2.time - date1.time
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS)
    }
}