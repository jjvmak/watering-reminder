package navi.hemppa

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi

import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime

import java.util.*

class MainActivity : AppCompatActivity() {


    var db = DatabaseHandler(this)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       db.readData()
        waterButton.setOnClickListener({
             var time = LocalDateTime.now()
            db.insertData(time)
           infoText.text = initInfo()
        })



    }


   fun initInfo(): String {
       var list : MutableList<Date> = ArrayList()
        val sdf = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
        list = db.readData()

        return sdf.format(list.get(list.size-1))


    }
}
