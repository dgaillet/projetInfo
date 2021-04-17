package com.example.cpaceinvadersbeta

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener {
    lateinit var canonView: CanonView
    var size : Float = 30f
    var test = 2
    var sound = false

    private var soundPool: SoundPool? = null
    private val soundId = 1



    override fun onPause() {
        super.onPause()
        canonView.pause()
    }

    fun playSound(view: View){
        soundPool?.play(soundId,1f,1f,0,0,1f)
    }

    override fun onResume() {
        super.onResume()
        canonView.resume()
    }

    override fun onClick(v: View?){

        when(v?.id){

            R.id.shoot ->{
                if(canonView.lesBalles.size<9){
                    canonView.lesBalles.add(Projectile((canonView.canon.r.centerX() -15).toFloat(), canonView.canon.r.centerY().toFloat(), size,canonView.difficulty))
                    canonView.shots++
                    canonView.hits++
                    if(sound){
                        playSound(canonView)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        left.setOnTouchListener(this)
        right.setOnTouchListener(this)
        shoot.setOnClickListener(this)
        val posit = intent.getIntExtra("com.example.cpaceinvadersbeta.difff", 0)
        val soundOn = intent.getBooleanExtra("com.example.cpaceinvadersbeta.sound", false)
        canonView = findViewById<CanonView>(R.id.vMain)
        canonView.difficulty = posit
        sound = soundOn
        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        soundPool!!.load(baseContext, R.raw.laser2, 1)
    }

    override fun onTouch(v: View?,e: MotionEvent): Boolean {
            val action = e.action

                if (action == MotionEvent.ACTION_DOWN
                    /*|| action == MotionEvent.ACTION_MOVE*/
                ) {
                    when (v?.id) {
                        R.id.left -> {
                            canonView.canon.r.offset(-100f, 0f)
                        }
                        R.id.right -> {
                            canonView.canon.r.offset(100f, 0f)
                        }
                    }
                }

            return v?.onTouchEvent(e) ?: true
    }

}
