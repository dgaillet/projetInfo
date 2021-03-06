package com.example.cpaceinvaders

import android.graphics.*

class Cible (var cibleDistance: Float, var cibleDebut: Float, var cibleFin: Float, var cibleVitesseInitiale: Float, var width: Float, var view: CanonView) {
    val CIBLE_PIECES = 7
    val cible = RectF(cibleDistance, cibleDebut,
        cibleDistance + width, cibleFin)
    var cibleTouchee = BooleanArray(CIBLE_PIECES)
    val ciblePaint = Paint()
    var longueurPiece = 0f
    var cibleVitesse = cibleVitesseInitiale
    var nbreCiblesTouchees = 0
    fun draw(canvas: Canvas) {
        val currentPoint = PointF()
        currentPoint.x = cible.left
        currentPoint.y = cible.top
        var a = 0
        for(y in 0 until 10){
            for (i in 0 until CIBLE_PIECES) {
                if (!cibleTouchee[i]) {
                    if (a==0){
                        ciblePaint.color = Color.BLUE
                        a=1
                    }
                    else if(a==1){
                        ciblePaint.color = Color.YELLOW
                        a=2
                    }
                    else{
                        ciblePaint.color = Color.GREEN
                        a=0
                    }
                    canvas.drawRect(currentPoint.x,currentPoint.y,cible.right,
                        currentPoint.y+longueurPiece,ciblePaint)
                }
                currentPoint.y += longueurPiece
            }
            var again : Int = 0
            for (i in 0 until CIBLE_PIECES){
                if(cibleTouchee[i]){
                    again++
                }

            }
            if(again==CIBLE_PIECES){
                for (i in 0 until CIBLE_PIECES){
                    cibleTouchee[i]=false
                }
                cibleVitesse*=1.2f
            }

        }

    }
    fun accelerate(){
        cibleVitesseInitiale = cibleVitesseInitiale*1.5f
    }

    fun setRect() {
        cible.set(cibleDistance, cibleDebut,
            cibleDistance + width, cibleFin)
        cibleVitesse = cibleVitesseInitiale
        longueurPiece = (cibleFin - cibleDebut) / CIBLE_PIECES
    }

    fun update(interval : Double){
        var up = (interval*cibleVitesse).toFloat()
        cible.offset(0f,up)
        if(cible.top<0 || cible.bottom>view.screenHeight){
            cibleVitesse*=-1f
            up = (interval*3*cibleVitesse).toFloat()
            cible.offset(0f,up)
        }
    }

    fun detectChoc(balle: BalleCanon) {
        val section=((balle.canonball.y-cible.top)/longueurPiece).toInt()
        if (section>=0&&section<CIBLE_PIECES&&!cibleTouchee[section]) {
            cibleTouchee[section] = true
            balle.resetCanonBall()
            view.increaseTimeLeft()
            if(++nbreCiblesTouchees==CIBLE_PIECES) view.gameOver()
        }

    }
    fun resetCible(){
        for (i in 0 until CIBLE_PIECES){
            cibleTouchee[i]=false
        }
        nbreCiblesTouchees = 0
        cibleVitesse = cibleVitesseInitiale
        cible.set(cibleDistance,cibleDebut,cibleDebut+width,cibleFin)
    }


}