package com.example.cpaceinvadersbeta

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

class Asteroide(x: Float, y: Float, diametre: Float) : Balle(x, y, diametre) {

    var asteroideOnScreen = true
    override var dy: Double = 0.17 + (( random.nextInt(10))/10).toDouble()
    var color = Color.argb(255, 139,69,19)


    fun gereBalle(b:Projectile, p:Asteroide){
        if(RectF.intersects(p.r,b.r)){
            b.projOnScreen = false
            p.asteroideOnScreen = false

        }

    }

    override fun draw(canvas: Canvas?) {
        paint.color = color
        super.draw(canvas)
    }



}




