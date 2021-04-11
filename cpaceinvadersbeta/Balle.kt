package com.example.cpaceinvadersbeta

import android.graphics.*
import androidx.core.graphics.component2
import java.util.*


abstract class Balle(x : Float, y : Float, var diametre : Float){


    val random = Random()
    val paint = Paint()
    //var ballOnScreen = true
    var ballVitesse = 0f

    val r = RectF(x, y, x + diametre, y + diametre)
    var color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    var showText = false
    abstract var dy : Double

    fun draw(canvas: Canvas?){
        paint.color = color
        canvas?.drawOval(r, paint)

    }
    open fun move(lesAsteroides: ArrayList<Asteroide>, lesBalles: ArrayList<Projectile>){
        r.offset(0f, (40.0*dy).toFloat())


    }

}





