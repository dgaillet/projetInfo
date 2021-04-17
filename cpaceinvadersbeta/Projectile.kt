package com.example.cpaceinvadersbeta

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.ArrayList

class Projectile(x: Float, y: Float, diametre: Float, lvl: Int) : Balle(x, y, diametre, lvl) {

    var projOnScreen = true
    override var dy: Double = -1.0

    override fun move(lesAsteroides: ArrayList<Asteroide>, lesBalles: ArrayList<Projectile>,lvl: Int) {
        super.move(lesAsteroides, lesBalles,lvl)
        for (p in lesAsteroides){
            p.gereBalle(this, p,lvl)
        }
    }

    override fun draw(canvas: Canvas?) {
        paint.color = Color.RED
        super.draw(canvas)
    }
}





