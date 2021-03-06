package com.example.cpaceinvaders

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class Canon (var canonBaseRadius: Float, var canonLongueur: Float, hauteur: Float, var largeur: Float, val view: CanonView) {
    val canonPaint = Paint()
    var pos = 0f
    //var finCanon = PointF(canonLongueur, hauteur)
    var finCanon = PointF(
        (canonLongueur * Math.sin(0.0) + view.screenWidth/2).toFloat(), (-canonLongueur * Math.cos(
        0.0
    )
            + view.screenHeight).toFloat()
    )
    fun draw(canvas: Canvas) {
        canonPaint.strokeWidth = largeur * 1.5f
        canvas.drawLine(view.screenWidth/2 + pos, view.screenHeight, finCanon.x + pos,
            finCanon.y, canonPaint)
        canvas.drawCircle(view.screenWidth/2 + pos, view.screenHeight, canonBaseRadius,
            canonPaint)
    }
    fun setFinCanon(canonLongueur: Float, hauteur: Float) {
        finCanon.set(canonLongueur, hauteur)
    }

    fun align(angle: Double) {
        finCanon.x = (canonLongueur * Math.sin(angle) + view.screenWidth/2).toFloat()
        finCanon.y = (-canonLongueur * Math.cos(angle)
                + view.screenHeight).toFloat()
    }
}