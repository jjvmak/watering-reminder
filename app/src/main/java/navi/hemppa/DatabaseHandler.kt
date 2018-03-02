package navi.hemppa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


val DATABASE_NAME ="DB"
val TABLE_NAME="WateringDates"
val COL_DATE = "Date"

class DatabaseHandler(var context : Context) :  SQLiteOpenHelper(context,DATABASE_NAME,null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_DATE +"  VARCHAR(256))"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun insertData(date : LocalDateTime){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_DATE,date.toString())
        var result = db.insert(TABLE_NAME,null,cv)
        if(result == (-1).toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()

    }


    fun readData() : MutableList<Date>{
        var list : MutableList<Date> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        val sdf = SimpleDateFormat("yyyy-MM-dd'-'HH:mm")

        if(result.moveToFirst()){
            do {
                var d = result.getString(result.getColumnIndex(COL_DATE))
                val date = sdf.parse(d)
                //Log.v("SAATANA",sdf.format(date))




                list.add(date)

            }while (result.moveToNext())
        }

        result.close()
        db.close()

        return list
    }

    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }

}
