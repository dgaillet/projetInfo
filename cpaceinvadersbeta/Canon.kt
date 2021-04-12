package com.example.cpaceinvadersbeta

import android.graphics.*
import android.os.Build

class Canon ( view: CanonView) {
    val canonPaint = Paint()
    val hublotPaint = Paint()



    val r = RectF(view.screenWidth+510   ,view.screenHeight+1550,view.screenWidth+580 , view.screenHeight+1700)
    //val r = RectF(view.screenWidth/2   ,view.screenHeight - 200,view.screenWidth/2  + 400 , view.screenHeight -100)
    //val r = RectF(view.screenWidth/2, view.screenHeight-200, view.screenWidth/2+70,view.screenHeight-350)


    fun draw(canvas: Canvas?) {
        //canonPaint.strokeWidth = largeur * 1.5f

        //canvas?.drawLine(view.screenWidth/2 + pos, view.screenHeight - 200, view.screenWidth/2 + pos +400, view.screenHeight -100, canonPaint)

        //canvas?.drawCircle(view.screenWidth/2+pos, view.screenHeight-200, 200f, canonPaint)
        //canvas?.drawRect(r,canonPaint)

        canvas?.drawOval(r, canonPaint)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawOval(r.left +20, r.top +20, r.right -20, r.top +70, hublotPaint)
            canvas?.drawArc(r.left -10,r.top + 100,r.right + 10,r.bottom+40,0f,-180f,true,canonPaint)
        }
        //canvas?.drawRect(r.left -10,r.top + 100,r.right + 10,r.bottom-5,canonPaint)
        


    }


}

