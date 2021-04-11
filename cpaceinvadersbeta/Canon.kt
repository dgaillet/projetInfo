package com.example.cpaceinvadersbeta

import android.graphics.*

class Canon ( var largeur: Float, val view: CanonView) {
    val canonPaint = Paint()



    val r = RectF(view.screenWidth+510   ,view.screenHeight+1600,view.screenWidth+580 , view.screenHeight+1750)
    //val r = RectF(view.screenWidth/2   ,view.screenHeight - 200,view.screenWidth/2  + 400 , view.screenHeight -100)
    //val r = RectF(view.screenWidth/2, view.screenHeight-200, view.screenWidth/2+70,view.screenHeight-350)



    fun draw(canvas: Canvas?) {
        //canonPaint.strokeWidth = largeur * 1.5f

        //canvas?.drawLine(view.screenWidth/2 + pos, view.screenHeight - 200, view.screenWidth/2 + pos +400, view.screenHeight -100, canonPaint)

        //canvas?.drawCircle(view.screenWidth/2+pos, view.screenHeight-200, 200f, canonPaint)
        //canvas?.drawRect(r,canonPaint)

        canvas?.drawOval(r, canonPaint)


    }
    fun reset(){
        //r = RectF(view.screenWidth+510   ,view.screenHeight+1600,view.screenWidth+580 , view.screenHeight+1750)
        //r = RectF(view.screenWidth/2, view.screenHeight-200, view.screenWidth/2+70,view.screenHeight-350)

    }

}

