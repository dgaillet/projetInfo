package com.example.cpaceinvadersbeta

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener {
    lateinit var canonView: CanonView
    var size : Float = 30f

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
                    val x = canonView.canon.r.centerX() -15
                    val y = canonView.canon.r.centerY()
                    canonView.lesBalles.add(Projectile(x.toFloat(), y.toFloat(), size))
                    canonView.shots++
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

        canonView = findViewById<CanonView>(R.id.vMain)

    }

    override fun onTouch(v: View?,e: MotionEvent): Boolean {
            val action = e.action

                if (action == MotionEvent.ACTION_DOWN
                    /*|| action == MotionEvent.ACTION_MOVE*/
                ) {
                    when (v?.id) {
                        R.id.left -> {
                            canonView.canon.r.offset(-110f, 0f)
                        }
                        R.id.right -> {
                            canonView.canon.r.offset(110f, 0f)
                        }
                    }
                }

            return v?.onTouchEvent(e) ?: true

    }


}


fun AlertDialog.Builder.setMessage(string: String, shotsFired: String, totalElapsedTime: String) {

}