/*
* 프로젝트명 : MP3 플레이어 만들기
* 작성자 : 2019038004 조민우
* 작성일 : 2023. 5. 3.
* 프로그램 설명 : MP3 플레이어
 */


package com.hyunsa.currentusing

import android.Manifest
import android.R
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var listViewMP3: ListView? = null
    var btnPlay: Button? = null
    var btnPause: Button? = null
    var btnStop: Button? = null
    var tvMP3: TextView? = null
    var pbMP3: ProgressBar? = null
    var mp3List: ArrayList<String>? = null
    var selectedMP3: String? = null
    var mp3Path = Environment.getExternalStorageDirectory().path + "/"
    var mPlayer: MediaPlayer? = null
    var PAUSED = false
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "MP3 플레이어"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setIcon(R.drawable.music)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            MODE_PRIVATE
        )


        mp3List = ArrayList()
        val listFiles = File(mp3Path).listFiles()
        var fileName: String
        var extName: String
        for (file in listFiles) {
            fileName = file.name
            extName = fileName.substring(fileName.length - 3)
            if (extName == "mp3")
                mp3List!!.add(fileName)
        }
        val listViewMP3 = findViewById<View>(R.id.listViewMP3) as ListView
        val adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_single_choice, mp3List!!
        )
        listViewMP3.choiceMode = ListView.CHOICE_MODE_SINGLE
        listViewMP3.adapter = adapter
        listViewMP3.setItemChecked(0, true)
        listViewMP3.onItemClickListener =
            OnItemClickListener { arg0, arg1, arg2, arg3 -> selectedMP3 = mp3List!![arg2] }
        selectedMP3 = mp3List!![0]
        btnPlay = findViewById<View>(R.id.btnPlay) as Button
        btnPause = findViewById<View>(R.id.btnPause) as Button
        btnStop = findViewById<View>(R.id.btnStop) as Button
        tvMP3 = findViewById<View>(R.id.tvMP3) as TextView
        pbMP3 = findViewById<View>(R.id.pbMP3) as ProgressBar
        btnPlay!!.setOnClickListener {
            try {
                mPlayer = MediaPlayer()
                mPlayer!!.setDataSource(mp3Path + selectedMP3)
                mPlayer!!.prepare()
                mPlayer!!.start()
                btnPlay!!.isClickable = false
                btnPause!!.isClickable = true
                btnStop!!.isClickable = true
                tvMP3!!.text = "실행중인 음악 :  $selectedMP3"
                pbMP3!!.visibility = View.VISIBLE
            } catch (e: IOException) {
            }
        }
        btnPause!!.setOnClickListener {
            if (PAUSED == false) {
                mPlayer!!.pause()
                btnPause!!.text = "이어듣기"
                PAUSED = true
                pbMP3!!.visibility = View.INVISIBLE
            } else {
                mPlayer!!.start()
                PAUSED = false
                btnPause!!.text = "일시정지"
                pbMP3!!.visibility = View.VISIBLE
            }
        }
        btnPause!!.isClickable = false
        btnStop!!.setOnClickListener {
            mPlayer!!.stop()
            mPlayer!!.reset()
            btnPlay!!.isClickable = true
            btnPause!!.isClickable = false
            btnPause!!.text = "일시정지"
            btnStop!!.isClickable = false
            tvMP3!!.text = "실행중인 음악 :  "
            pbMP3!!.visibility = View.INVISIBLE
        }
        btnStop!!.isClickable = false
    }
}
