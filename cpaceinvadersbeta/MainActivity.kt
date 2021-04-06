package com.example.cpaceinvadersbeta

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener {
    lateinit var canonView: CanonView
    //lateinit var canon: BalleCanon
    var shotsFired = 0





    override fun onPause() {
        super.onPause()
        canonView.pause()
    }
    override fun onResume() {
        super.onResume()
        canonView.resume()
    }
    override fun onClick(v: View?){

        when(v?.id){

            R.id.shoot ->{
                if(canonView.lesBalles.size<9){
                    val x = canonView.screenWidth/2 + canonView.canon.pos -15
                    val y = canonView.canon.finCanon.y -450 -15
                    canonView.lesBalles.add(Projectile(x.toFloat(), y.toFloat(), 30f))
                    shotsFired++
                }

            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        left.setOnTouchListener(this)
        //left.setOnClickListener(this)
        right.setOnTouchListener(this)
        //right.setOnClickListener(this)
        shoot.setOnClickListener(this)
        shoot.setOnTouchListener(this)


        canonView = findViewById<CanonView>(R.id.vMain)

    }

    override fun onTouch(v: View?,e: MotionEvent): Boolean {

        val action = e.action
        if (action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE) {
            when(v?.id) {
                R.id.left -> {
                    canonView.canon.pos -= 25f
                }
                R.id.right -> {
                    canonView.canon.pos += 25f
                }

            }
        }
        return v?.onTouchEvent(e) ?: true
    }



}




fun AlertDialog.Builder.setMessage(string: String, shotsFired: String, totalElapsedTime: String) {

}