package com.example.cpaceinvaders

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF

class BalleCanon (var view: CanonView, val cible: Cible) {
    var canonball = PointF()
    var canonballVitesse = 0f
    var canonballVitesseX = 0f
    var canonballVitesseY = 0f
    var canonballOnScreen = true
    var canonballRadius = 0f
    var canonballPaint = Paint()
    init {
        canonballPaint.color = Color.GREEN
    }
    fun launch(angle: Double) {
        canonball.x = canonballRadius + view.screenWidth/2f
        canonball.y = view.screenHeight -10
        canonballVitesseX=(canonballVitesse*Math.sin(angle)).toFloat()
        canonballVitesseY=(-canonballVitesse*Math.cos(angle)).toFloat()
        canonballOnScreen = true
    }
    fun draw(canvas: Canvas) {
        canvas.drawCircle(canonball.x, canonball.y, canonballRadius,
            canonballPaint)
    }

    fun resetCanonBall(){
        canonballOnScreen = false
    }

    fun update(interval: Double) {
        if (canonballOnScreen) {
            canonball.x += (interval * canonballVitesseX).toFloat()
            canonball.y += (interval * canonballVitesseY).toFloat()
            // Si elle sorte de l'écran
            if (canonball.x + canonballRadius > view.screenWidth
                || canonball.x - canonballRadius < 0) {
                canonballOnScreen = false
            }
            else if (canonball.y + canonballRadius > view.screenHeight
                || canonball.y - canonballRadius < 0) {
                canonballOnScreen = false
            }
            else if (canonball.x+canonballRadius>cible.cible.left
                && canonball.y+canonballRadius>cible.cible.top
                && canonball.y-canonballRadius<cible.cible.bottom) {
                cible.detectChoc(this)

                /* Pour l'instant rien ne se passe lorsque balle heurte la cible */
            }
        }
    }
}