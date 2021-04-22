package com.example.cpaceinvadersbeta

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.SparseIntArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.lang.Thread.sleep

class MenuView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0):
        SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {

    lateinit var thread: Thread
    lateinit var canvas2: Canvas

    var menuPaint = Paint()
    var drawing = true
    var sound = true
    var music = 0

    var screenWidth = 0f
    var screenHeight = 0f

    val cercle = BitmapFactory.decodeResource(resources, R.drawable.couverture)
    var difficulty  = 0
    val activity = context as FragmentActivity


    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun run(){
        while(drawing){
            draw()
        }
    }

    fun draw(){
        if (holder.surface.isValid) {
            canvas2 = holder.lockCanvas()
            menuPaint.color = Color.argb(255, 25, 25, 60)
            canvas2.drawRect(0f, 0f, canvas2.width.toFloat(), canvas2.height.toFloat(), menuPaint)
            menuPaint.color = Color.RED
            menuPaint.textSize = 120f
            menuPaint.textSkewX = 0.6f
            canvas2.drawText("CPace", 140f,200f, menuPaint)
            menuPaint.textSkewX = 0f
            canvas2.drawText("-",490f,200f, menuPaint)
            menuPaint.textSkewX = -0.6f
            canvas2.drawText("Invaders", 530f,200f, menuPaint)
            canvas2.drawBitmap(cercle, Rect(0, 0, 630, 500), Rect(400,250,800,550), menuPaint)

            holder.unlockCanvasAndPost(canvas2)
        }
    }


    fun difficulti(){
        class Difficult: DialogFragment() {
            lateinit var dialog: AlertDialog
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                val arrayDiff = arrayOf("Fossil","Bleu","Fière Poil","Commitard","Vieux C*n")
                builder.setTitle("Niveau de difficulté")
                builder.setSingleChoiceItems(arrayDiff,difficulty, DialogInterface.OnClickListener{ dialog, which ->
                    difficulty = which
                })
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
                }

        )
    }


    fun soundEff(){
         class Sound: DialogFragment() {
            lateinit var dialog: AlertDialog
            var ind : Int = 0
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                val arrayDiff = arrayOf("On","Off")
                builder.setTitle("Effets sonores")

                builder.setSingleChoiceItems(arrayDiff,ind, DialogInterface.OnClickListener{ dialog, which ->
                    ind = which
                    if(ind==0){
                        sound = true
                    }
                    else{
                        sound=false
                    }
                })

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
                    val son = Sound()
                    son.setCancelable(false)
                    son.show(ft,"dialog")
                }

        )
    }


    fun music(){
        class Sound: DialogFragment() {
            lateinit var dialog: AlertDialog
            var ind : Int = 0
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                val arrayDiff = arrayOf("off","Daft Punk - Around the world","David Bowie - Starman","Elton John - Rocketman","Tryhard")
                builder.setTitle("Musique (écouteurs fortement recommandés)")

                builder.setSingleChoiceItems(arrayDiff,ind, DialogInterface.OnClickListener{ dialog, which ->
                    ind = which
                    music=ind
                })

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
                    val son = Sound()
                    son.setCancelable(false)
                    son.show(ft,"dialog")
                }

        )
    }


    fun soundSet(){
        class Sound: DialogFragment() {
            lateinit var dialog: AlertDialog
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                val arrayDiff = arrayOf("On","Off")
                builder.setTitle("Effets sonores")

                builder.setNegativeButton("Effets sonores", DialogInterface.OnClickListener { _, _->soundEff()})
                builder.setPositiveButton("Musique", DialogInterface.OnClickListener { _, _->music()})
                builder.setNeutralButton("ok") {dialog, which ->
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
                    val son = Sound()
                    son.setCancelable(false)
                    son.show(ft,"dialog")
                }

        )
    }



    fun credit(){
        class Credit: DialogFragment() {
            lateinit var dialog: AlertDialog
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle("Crédits")
                builder.setMessage("CROHIN Guillaume\n\nGAILLET Damien\n\nNASCIMENTO Nathan\n\nStack Overflow")
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
                    val cre = Credit()
                    cre.setCancelable(false)
                    cre.show(ft,"dialog")
                }

        )
    }



    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun surfaceCreated(holder: SurfaceHolder) {}


}