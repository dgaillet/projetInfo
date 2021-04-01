package com.example.cpaceinvadersbeta

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.component1
import androidx.core.graphics.component2

class Asteroide(x1 : Float,y1 : Float, x2 : Float, y2 : Float) {
    var r = RectF(x1,y1,x2,y2)
    val paint = Paint()
    var asteroideOnScreen = true

    fun draw(canvas: Canvas){
        paint.color = Color.DKGRAY

    }
    fun gereBalle(b:Balle, p:Asteroide){
        if(RectF.intersects(p.r,b.r)){
            b.ballOnScreen = false
            p.asteroideOnScreen = false
        }

    }

}