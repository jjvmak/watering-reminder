package w.watering_reminder

import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

val DATABASE_NAME ="DB"
val TABLE_NAME="WateringDates"
val COL_DATE = "Date"
val COL_ID = "ID"
val COL_NDATE = "NDate"
val COL_NAME = "Name"


class DatabaseHandler(var context : Context) :  SQLiteOpenHelper(context, DATABASE_NAME,null,1) {


    @TargetApi(23)
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_NAME+ " VARCHAR(256),"+
                COL_NDATE+ " VARCHAR(256),"+
                COL_DATE +" VARCHAR(256))"

        db?.execSQL(createTable)

    }

    @TargetApi(23)
    fun drop(){
        val db = this.writableDatabase
        db?.execSQL("DROP TABLE "+ TABLE_NAME)
    }

    @TargetApi(23)
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @TargetApi(23)
    fun insertPlant(plantName : String){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME,plantName)
        cv.put(COL_DATE,"2000-02-02-02:02")
        cv.put(COL_NDATE,"2000-02-02-02:02")
        var result = db.insert(TABLE_NAME,null,cv)
        if(result == (-1).toLong())
            Toast.makeText(context,"Tietokanta virhe!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Lisätty!", Toast.LENGTH_SHORT).show()

    }




    @TargetApi(23)
    fun readData() : MutableList<Plant>{
        var list : MutableList<Plant> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        val sdf = SimpleDateFormat("yyyy-MM-dd'-'HH:mm")

        if(result.moveToFirst()){
            do {
                //var d = result.getString(result.getColumnIndex(COL_DATE))
                //val date = sdf.parse(d)
                //Log.v("SAATANA",sdf.format(date))

                var plant = Plant()
                plant.name = result.getString(result.getColumnIndex(COL_NAME))
                plant.id = result.getInt(result.getColumnIndex(COL_ID))
                plant.date = result.getString(result.getColumnIndex(COL_DATE))
                plant.nDate = result.getString(result.getColumnIndex(COL_NDATE))

                list.add(plant)

            }while (result.moveToNext())
        }

        result.close()
        db.close()

        return list
    }

    @TargetApi(23)
    fun getPlantInstance(name : String) : Plant {

        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COL_NAME +" = '"+name+"'"
        val result = db.rawQuery(query,null)
        var plant = Plant()
        if(result.moveToFirst()){
            do {

                plant.name = result.getString(result.getColumnIndex(COL_NAME))
                plant.id = result.getInt(result.getColumnIndex(COL_ID))
                plant.date = result.getString(result.getColumnIndex(COL_DATE))
                plant.nDate = result.getString(result.getColumnIndex(COL_NDATE))


            }while (result.moveToNext())
        }

        result.close()
        db.close()

        return plant

    }

    @TargetApi(23)
    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }


    @TargetApi(23)
    fun deletePlant(name : String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COL_NAME+" = ?", arrayOf(name))
        db.close()
    }

    @TargetApi(23)
    fun updateData(date : String, name: String) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_DATE, date)
        db.update(TABLE_NAME, cv, COL_NAME+" = ?", arrayOf(name))
        db.close()

    }

    @TargetApi(23)
    fun updateNutrient(date : String, name: String) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NDATE, date)
        db.update(TABLE_NAME, cv, COL_NAME+" = ?", arrayOf(name))
        db.close()

    }

}
