package com.example.cpaceinvadersbeta

import android.content.res.Resources
import android.graphics.*
import android.os.Build

class Canon(view: CanonView, resources: Resources) {
    val canonPaint = Paint()
    val hublotPaint = Paint()


    val r = RectF(view.screenWidth+510   ,view.screenHeight+1550,view.screenWidth+580 , view.screenHeight+1700)
    val cepeee = BitmapFactory.decodeResource(resources, R.drawable.cepeee)

    fun draw(canvas: Canvas?) {

        canvas?.drawOval(r, canonPaint)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawOval(r.left +20, r.top +20, r.right -20, r.top +70, hublotPaint)
            canvas?.drawArc(r.left -10,r.top + 100,r.right + 10,r.bottom+40,0f,
                    -180f,true,canonPaint)
            canvas?.drawBitmap(cepeee,Rect(0,0,1000,1000),Rect((r.left +10).toInt(),
                    (r.top +80).toInt(), (r.right ).toInt(), (r.top +150).toInt()), hublotPaint)

        }
    }
}

