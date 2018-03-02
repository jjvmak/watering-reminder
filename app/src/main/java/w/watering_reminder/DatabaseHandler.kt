package w.watering_reminder

import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

val DATABASE_NAME ="DB"
val TABLE_NAME="WateringDates"
val COL_DATE = "Date"

class DatabaseHandler(var context : Context) :  SQLiteOpenHelper(context, DATABASE_NAME,null,1) {

    @TargetApi(23)
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_DATE +"  VARCHAR(256))"

        db?.execSQL(createTable)

    }

    @TargetApi(23)
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @TargetApi(23)
    fun insertData(date : String){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_DATE,date)
        var result = db.insert(TABLE_NAME,null,cv)
        if(result == (-1).toLong())
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show()

    }

    @TargetApi(23)
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

    @TargetApi(23)
    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }

    @TargetApi(23)
    fun updateData(date : String) {
        val db = this.writableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_DATE, date)
                db.update(TABLE_NAME,cv, COL_DATE + "=?",
                        arrayOf(result.getString(result.getColumnIndex(COL_DATE))))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }

}
