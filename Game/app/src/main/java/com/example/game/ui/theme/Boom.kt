package com.example.game.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.game.R
import java.util.Random

class Boom {
    var x = 0
    var y = 0

    var bitmap: Bitmap

    constructor(context: Context, width: Int, height: Int) {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.boom) //What

        x = -300
        y = -300
    }
}