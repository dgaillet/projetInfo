package com.example.cpaceinvadersbeta

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Global.putInt
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.core.graphics.component2
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.io.IOException
import java.lang.Thread.sleep
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.system.exitProcess
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class CanonView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    val backgroundPaint = Paint()
    val textPaint = Paint()
    var gameOver : Boolean = false
    var hearts = 3
    var hits = 0f
    var shots = 0f


    var difficulty = 0

    val random = Random()

    var lesBalles   = ArrayList<Projectile>()
    var lesAsteroides = ArrayList<Asteroide>()
    
    var screenWidth = 0f
    var screenHeight = 0f
    var drawing = false
    lateinit var thread: Thread

    val activity = context as FragmentActivity
    var totalElapsedTime = 0.0


    val canon = Canon(this, resources)

    var mMediaPlayer: MediaPlayer? = null
    var musicCheck = false

    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    var highScore = sharedPref.getFloat(difficulty.toString(), 0f)

    //val ballesIterator = lesBalles.iterator()
    //val asteroidesIterator = lesAsteroides.iterator()


    init {
        backgroundPaint.color = Color.argb(255,25,25,60)
        textPaint.textSize = screenWidth/20
        textPaint.color = Color.RED

    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        highScore = sharedPref.getFloat(difficulty.toString(), 0f)
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    fun stopSong() {
        //met la musique en pause jusqu'?? ce que la fonction playSound() est appel??e
        if (mMediaPlayer != null) {
            mMediaPlayer!!.pause()

        }
    }

    fun playSong() {
        //lance la lecture de la musique
        if(musicCheck) {
                mMediaPlayer!!.start()
        }
    }

    fun updatePositions(elapsedTimeMS: Double) {
        //d??place les ??l??ments, d??tecte la fin de partie


        if(hearts<=0){
            stopSong()
            drawing = false
            gameOver = true
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            highScore = sharedPref.getFloat(difficulty.toString(), 0f)
            val score = hits
            if(score>highScore){
                with (sharedPref.edit()) {
                    putFloat(difficulty.toString(), score)
                    apply()
                }
                highScore = sharedPref.getFloat(difficulty.toString(), 0f)
            }

            showGameOverDialog(R.string.lose)
        }

        try{
            for(b in lesBalles){
                b.move(lesAsteroides,lesBalles,difficulty)
                if(b.r.component2()<0f){
                    b.projOnScreen = false
                    hits--
                }
            }

            for (p in lesAsteroides){

                p.move(lesAsteroides,lesBalles,difficulty)

                if(p.r.component2()>screenHeight){
                    p.asteroideOnScreen = false
                    hearts--
                }

                if(RectF.intersects(p.r,canon.r)){
                    hearts=0

                }

            }
        }catch (e : ConcurrentModificationException){}

        try{
            collectGarb()
        }catch (e: ConcurrentModificationException) {

        }catch (e : ArrayIndexOutOfBoundsException){

        }


    }
    fun collectGarb(){
        //supprime les objets inutilis??s de leur liste

        var lengthBall: Int = lesBalles.size
        var lengthAsteroide: Int = lesAsteroides.size
        var sellaBsel = lesBalles.reversed()

        if (!lesBalles.isEmpty()) {
            var chevre: Int = 0

            for (i in sellaBsel) {

                if (!i.projOnScreen) {
                    lesBalles.removeAt(lengthBall - 1 - chevre)
                }

                chevre++
            }
        }
        var sedioretsAsel = lesAsteroides.reversed()

        if (!lesAsteroides.isEmpty()) {
            var hurluberlu: Int = 0

            for (i in sedioretsAsel) {

                if (!i.asteroideOnScreen) {
                    lesAsteroides.removeAt(lengthAsteroide - 1 - hurluberlu)
                    hits++
                }

                hurluberlu++
            }
        }
    }

    fun showGameOverDialog(messageId: Int) {
        //affiche le score ?? la fin de la partie
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle(resources.getString(messageId))
                builder.setMessage("Ast??ro??des d??truits : ${hits.toInt()} \nPr??cision :" +
                        " ${(hits/shots)*100}%")
                builder.setNegativeButton("difficult??",DialogInterface.OnClickListener{_,_->difficulties()})
                builder.setPositiveButton("menu", DialogInterface.OnClickListener { _, _-> exitProcess(-1) })
                builder.setNeutralButton("Recommencer", DialogInterface.OnClickListener { _, _->newGame()})

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

    fun difficulties(){
        //permet de changer le niveau de difficult?? sans devoir retourner dans le menu
        class Difficult: DialogFragment() {
            lateinit var dialog:AlertDialog
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                val arrayDiff = arrayOf("Fossil","Bleu","Fi??re Poil","Commitard","Vieux C*n")
                builder.setTitle("Niveau de difficult??")
                builder.setSingleChoiceItems(arrayDiff,difficulty,DialogInterface.OnClickListener{dialog, which ->
                    difficulty = which})
                builder.setPositiveButton("ok") {dialog, which ->
                    dialog.dismiss()
                }

                return builder.create()
                dialog.show()
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
                    val diff = Difficult()
                    diff.setCancelable(false)
                    diff.show(ft,"dialog")
                })
    }

    fun newGame() {
        //remet certains param??tres ?? leur valeur initaile et relance une partie
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        highScore = sharedPref.getFloat(difficulty.toString(), 0f)
        hits = 0f
        hearts=3
        shots=0f
        lesAsteroides.clear()
        lesBalles.clear()
        totalElapsedTime = 0.0
        drawing = true

        if (gameOver) {
            gameOver = false
            playSong()
            thread = Thread(this)
            thread.start()
        }

    }

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        canon.canonPaint.color = Color.LTGRAY
        canon.hublotPaint.color = Color.BLUE


        textPaint.setTextSize(w/20f)
        textPaint.isAntiAlias = true
    }

    override fun run() {
        //fonction r??alis??e en boucle tant que le thread associ?? est actif
        var previousFrameTime = System.currentTimeMillis()
        var check = 0.0
        //var checkBns= 0.0


        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS: Double = (currentTime - previousFrameTime).toDouble()
            totalElapsedTime += elapsedTimeMS / 1000.0
            val interval = 1.0 - 0.005*totalElapsedTime
            updatePositions(elapsedTimeMS)

            //if(checkBns>30){ }


            if (check > interval && lesAsteroides.size < 8) {
                var taille = 80f*(1+random.nextInt(4))
                lesAsteroides.add(Asteroide(random.nextInt((screenWidth - taille).toInt()).toFloat(),
                        (random.nextInt(20)).toFloat(), taille, resources, difficulty))
                check = 0.0
            } else check += elapsedTimeMS / 1000.0

            try{
                draw()
            }catch (e : ConcurrentModificationException){

            }catch (e : IllegalStateException){
                holder.unlockCanvasAndPost(canvas)
            }

            previousFrameTime = currentTime
        }
    }

    fun draw() {
        //dessine tout les ??l??ments sur la surface de jeu
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)
            canvas.drawText("${hearts} vie(s)",30f,50f, textPaint)
            canvas.drawText("Score : ${hits.toInt()} (Best : ${highScore.toInt()} ) ",30f,100f, textPaint)
            canvas .drawText("${lesBalles.size} ${lesAsteroides.size}",30f,150f,textPaint)
            for (b in lesBalles){
                if(b.projOnScreen){
                    b.draw(canvas)
                }
            }

            for (p in lesAsteroides){
                if(p.asteroideOnScreen){
                    p.draw(canvas)
                }
            }
            canon.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
}

