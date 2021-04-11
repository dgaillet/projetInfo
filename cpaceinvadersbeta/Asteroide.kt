package com.example.cpaceinvadersbeta

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import java.util.*

class Asteroide(x: Float, y: Float, diametre: Float) : Balle(x, y, diametre) {

    var asteroideOnScreen = true
    override var dy: Double = 0.3 + (( random.nextInt(10))/10).toDouble()


    fun gereBalle(b:Projectile, p:Asteroide){
        if(RectF.intersects(p.r,b.r)){
            b.projOnScreen = false
            p.asteroideOnScreen = false
        }

    }



}




