/*
* 프로젝트명 : 가수 그룹 관리 DB 만들기
* 작성자 : 2019038004 조민우
* 작성일 : 2023. 5. 3.
* 프로그램 설명 : 가수 그룹 관리 DB
 */


package com.hyunsa.currentusing


import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var myHelper: myDBHelper? = null
    var edtName: EditText? = null
    var edtNumber: EditText? = null
    var edtNameResult: EditText? = null
    var edtNumberResult: EditText? = null
    var btnInit: Button? = null
    var btnInsert: Button? = null
    var btnUpdate: Button? = null
    var btnDelete: Button? = null
    var btnSelect: Button? = null
    var sqlDB: SQLiteDatabase? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sqldb = this.sqlDB
        title = "가수 그룹 관리 DB (수정)"
        edtName = findViewById<View>(R.id.edtName) as EditText
        edtNumber = findViewById<View>(R.id.edtNumber) as EditText
        edtNameResult = findViewById<View>(R.id.edtNameResult) as EditText
        edtNumberResult = findViewById<View>(R.id.edtNumberResult) as EditText
        btnInit = findViewById<View>(R.id.btnInit) as Button
        btnInsert = findViewById<View>(R.id.btnInsert) as Button
        btnUpdate = findViewById<View>(R.id.btnUpdate) as Button
        btnDelete = findViewById<View>(R.id.btnDelete) as Button
        btnSelect = findViewById<View>(R.id.btnSelect) as Button
        myHelper = myDBHelper(this)
        btnInit!!.setOnClickListener {
            sqlDB = myHelper!!.writableDatabase
            myHelper!!.onUpgrade(sqldb, 1, 2)
            sqldb?.close()
        }
        btnInsert!!.setOnClickListener {
            sqlDB = myHelper!!.writableDatabase
            sqldb?.execSQL(
                "INSERT INTO groupTBL VALUES ( '"
                        + edtName!!.text.toString() + "' , "
                        + edtNumber!!.text.toString() + ");"
            )
            sqldb?.close()
            Toast.makeText(
                applicationContext, "입력됨",
                Toast.LENGTH_SHORT
            ).show()
            btnSelect!!.callOnClick()
        }
        btnUpdate!!.setOnClickListener {
            sqlDB = myHelper!!.writableDatabase
            if (edtName!!.text.toString() !== "") {
                sqldb?.execSQL(
                    "UPDATE groupTBL SET gNumber ="
                            + edtNumber!!.text + " WHERE gName = '"
                            + edtName!!.text.toString() + "';"
                )
            }
            sqldb?.close()
            Toast.makeText(
                applicationContext, "수정됨",
                Toast.LENGTH_SHORT
            ).show()
            btnSelect!!.callOnClick()
        }
        btnDelete!!.setOnClickListener {
            sqlDB = myHelper!!.writableDatabase
            if (edtName!!.text.toString() !== "") {
                sqldb?.execSQL(
                    "DELETE FROM groupTBL WHERE gName = '"
                            + edtName!!.text.toString() + "';"
                )
            }
            sqldb?.close()
            Toast.makeText(
                applicationContext, "삭제됨",
                Toast.LENGTH_SHORT
            ).show()
            btnSelect!!.callOnClick()
        }
        btnSelect!!.setOnClickListener {
            sqlDB = myHelper!!.readableDatabase
            val cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null)
            var strNames = """
                그룹이름
                --------
                
                """.trimIndent()
            var strNumbers = """
                인원
                --------
                
                """.trimIndent()
            while (cursor.moveToNext()) {
                strNames += """
                    ${cursor.getString(0)}
                    
                    """.trimIndent()
                strNumbers += """
                    ${cursor.getString(1)}
                    
                    """.trimIndent()
            }
            edtNameResult!!.setText(strNames)
            edtNumberResult!!.setText(strNumbers)
            cursor.close()
            sqldb?.close()
        }
    }

    inner class myDBHelper(context: Context?) :
        SQLiteOpenHelper(context, "groupDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE  groupTBL ( gName CHAR(20) PRIMARY KEY, gNumber INTEGER);")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}