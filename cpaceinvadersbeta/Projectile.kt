package com.example.cpaceinvadersbeta

import java.util.ArrayList

class Projectile(x: Float, y: Float, diametre: Float) : Balle(x, y, diametre) {

    var projOnScreen = true
    override var dy: Double = -1.0


    override fun move(lesAsteroides: ArrayList<Asteroide>, lesBalles: ArrayList<Projectile>) {
        super.move(lesAsteroides, lesBalles)
        for (p in lesAsteroides){
            p.gereBalle(this, p)
        }
    }
}





