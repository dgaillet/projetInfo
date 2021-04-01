package com.example.cpaceinvadersbeta

import android.graphics.*
import androidx.core.graphics.component2
import java.util.*


public class Balle(x : Float,y : Float, var diametre : Float){

    val random = Random()
    val paint = Paint()
    var ballOnScreen = true

    var ballVitesse = 0f

    var ballRadius = 20f
    var canonballPaint = Paint()
    init {
        canonballPaint.color = Color.GREEN
    }

    val r = RectF(x, y, x + diametre, y + diametre)
    var color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    var showText = false
    var dy : Int = -1


    fun draw(canvas: Canvas?){
        paint.color = color
        canvas?.drawOval(r, paint)

    }
    fun move(lesAsteroides: ArrayList<Asteroide>, lesBalles: ArrayList<Balle>){
        r.offset(0f,40.0F*dy)

        for (p in lesAsteroides){
            p.gereBalle(this, p)
        }
    }
    

}

