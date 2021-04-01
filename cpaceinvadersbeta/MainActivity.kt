package com.example.cpaceinvadersbeta

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
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
            R.id.left ->{
                canonView.canon.pos-=50f
            }
            R.id.right ->{
                canonView.canon.pos+=50f
            }
            R.id.shoot ->{
                if(canonView.lesBalles.size<9){
                    val x = canonView.screenWidth/2 + canonView.canon.pos
                    val y = canonView.screenHeight
                    canonView.lesBalles.add(Balle(x.toFloat(), y.toFloat(), 30f))
                    shotsFired++
                }

            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        left.setOnClickListener(this)
        right.setOnClickListener(this)
        shoot.setOnClickListener(this)
        canonView = findViewById<CanonView>(R.id.vMain)

    }
}

fun AlertDialog.Builder.setMessage(string: String, shotsFired: String, totalElapsedTime: String) {

}