package com.example.cpaceinvadersbeta

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.component2
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.nio.file.Files.size
import java.util.ArrayList

class CanonView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    val backgroundPaint = Paint()
    val textPaint = Paint()
    var timeLeft = 0.0
    val MISS_PENALTY = 2
    val HIT_REWARD = 3
    var gameOver : Boolean = false

    var lesBalles   = ArrayList<Balle>()
    var lesAsteroides = ArrayList<Asteroide>()


    var screenWidth = 0f
    var screenHeight = 0f
    var drawing = false
    lateinit var thread: Thread

    var shotsFired : Int = 0

    val activity = context as FragmentActivity

    var totalElapsedTime = 0.0


    val canon = Canon(0f, 0f, 0f, 0f, this)


    init {
        backgroundPaint.color = Color.WHITE
        textPaint.textSize = screenWidth/20
        textPaint.color = Color.DKGRAY
        timeLeft = 1000.0
    }
    fun pause() {
        drawing = false
        thread.join()
    }
    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    fun updatePositions(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0
        timeLeft-=interval
        if(timeLeft<=0.0){
            drawing = false
            gameOver = true
            showGameOverDialog(R.string.lose)
        }

        for(b in lesBalles){
            b.move(lesAsteroides,lesBalles)
            if(b.r.component2()<0f){
                b.ballOnScreen = false
            }
        }
        var lengthBall : Int = lesBalles.size
        var lengthAsteroide : Int = lesAsteroides.size

        var sellaBsel = lesBalles.reversed()

        if(!lesBalles.isEmpty()) {
            var chevre : Int = 0
            for (i in sellaBsel) {
                if (!i.ballOnScreen) {
                    lesBalles.removeAt(lengthBall-1-chevre)
                }
                chevre++
            }
        }

        var sedioretsAsel = lesAsteroides.reversed()
        if(!lesAsteroides.isEmpty()) {
            var hurluberlu : Int = 0
            for (i in sedioretsAsel) {
                if (!i.asteroideOnScreen) {
                    lesAsteroides.removeAt(lengthAsteroide-1-hurluberlu)
                }
                hurluberlu++
            }
        }

    }

    fun gameOver(){
        drawing = false
        showGameOverDialog(R.string.win)
        //cible.accelerate()   remplacer par asteroide
        gameOver = true
    }

    fun showGameOverDialog(messageId: Int) {
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle(resources.getString(messageId))
                builder.setMessage("Nombre de tirs : {shotsFired} Temps écoulé : {totalElapsedTime}")

                builder.setPositiveButton("Recommencer", DialogInterface.OnClickListener { _, _->newGame()})
                return builder.create()
            }
        }
        activity.runOnUiThread(
            Runnable {
                val ft = activity.supportFragmentManager.beginTransaction()
                val prev = activity.supportFragmentManager.findFragmentByTag("dialog")
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.addToBackStack(null)
                val gameResult = GameResult()
                gameResult.setCancelable(false)
                gameResult.show(ft,"dialog")
            }

        )
    }



    fun reduceTimeLeft(){
        timeLeft-=MISS_PENALTY
    }
    fun increaseTimeLeft(){
        timeLeft+=HIT_REWARD
    }

    fun newGame() {
        timeLeft = 100000.0

        shotsFired = 0
        totalElapsedTime = 0.0
        drawing = true
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
    }


    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        canon.canonBaseRadius = (h / 18f)
        canon.canonLongueur = (w / 8f)
        canon.largeur = (w / 24f)
        canon.setFinCanon(w/2f,h -10f )
        canon.canonPaint.color = Color.CYAN

        for (b in lesBalles){
            b.ballRadius = (w/36f)
            b.ballVitesse = (w*3/1f)
        }

        textPaint.setTextSize(w/20f)
        textPaint.isAntiAlias = true
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS:Double = (currentTime-previousFrameTime).toDouble()
            totalElapsedTime+=elapsedTimeMS/1000.0
            updatePositions(elapsedTimeMS)
            draw()
            previousFrameTime = currentTime
        }
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)
            canon.draw(canvas)
            val formatted = String.format("%.2f",timeLeft)
            canvas.drawText("Il reste $formatted secondes.",30f,50f,textPaint)
            canvas.drawText("${lesBalles.size} balles actives", 30f, 100f, textPaint)
            canvas.drawText("${lesAsteroides.size} asteroides actifs", 30f, 150f, textPaint)
            for (b in lesBalles){
                if(b.ballOnScreen){
                    b.draw(canvas)
                }
            }

            for (p in lesAsteroides){
                if(p.asteroideOnScreen){
                    p.draw(canvas)
                }
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
}

