package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import kotlin.random.Random

class GameView : SurfaceView, Runnable {

    private var playing : Boolean = false
    private var gameThread : Thread? = null
    private lateinit var surfaceHolder : SurfaceHolder
    private lateinit var canvas: Canvas

    private lateinit var paint : Paint
    private var stars = arrayListOf<Star>()
    private var bricks = arrayListOf<Brick>()
    private var brick_template = Brick(context, 0, 0, Color.BLACK)

    private lateinit var paddle : Paddle
    private lateinit var ball : Ball

    private fun init(context: Context, width: Int, height: Int) {
        surfaceHolder = holder
        paint = Paint()

        for(i in 0..100) {
            stars.add(Star(width, height))
        }

        for(row in 0..3) {
            for(col in 0..8) {
                bricks.add(
                    Brick(
                        context,
                        (col * (brick_template.bitmap.width + 5) + 40),
                        (row * (brick_template.bitmap.height + 5) + 30),
                        argb(255, Random.nextInt(), Random.nextInt(), Random.nextInt())
                    )
                )
            }
        }

        //for(brick in bricks)
            //Log.d("Bomba", brick.x.toString() + " " + brick.y.toString() + " - " + brick.detectCollision)

        paddle = Paddle(context, width, height)
        ball = Ball(context, width, height)
    }

    //What? /!\
    constructor(context: Context?, width: Int, height: Int) : super(context) {
        init(context!!, width, height)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context!!, 0, 0)
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    fun pause() {
        playing = false
        gameThread?.join()
    }

    override fun run() {
        while(playing) {
            update()
            draw()
            control()
        }
    }

    private fun update() {
        for(s in stars) {
            s.update()
        }

        ball.update()

        if(Rect.intersects(ball.detectCollision, paddle.detectCollision)){
            ball.vy = -20

            val hit_pos : Double = ((ball.x.toDouble() + (ball.bitmap.width.toDouble() / 2.0)) - paddle.x.toDouble()) / paddle.bitmap.width.toDouble()

            ball.vx = if(hit_pos < 0.5) -15 else 15
            Log.d("DIR: ", ball.vx.toString())
        }

        //Log.d("Bomba", ball.x.toString() + " " + ball.y.toString())
        for(brick in bricks) {
            //Log.d("I hate my life", Rect.intersects(ball.detectCollision, brick.detectCollision).toString())
            //Log.d("I hate my life2", ball.x.toString() + " " + ball.y.toString())
            if(brick.visible && Rect.intersects(ball.detectCollision, brick.detectCollision)) {
                brick.visible = false
                ball.vy *= -1
                break
                //Log.d("WTF", ball.detectCollision.toString() + " " + brick.detectCollision.toString())
            }
        }
    }

    private fun draw() {
        if(surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()

            canvas.drawColor(Color.BLACK)

            paint.color = Color.YELLOW

            for(star in stars) {
                paint.strokeWidth = star.starWidth.toFloat()
                canvas.drawPoint(star.x.toFloat(), star.y.toFloat(), paint)
            }

            canvas.drawBitmap(ball.bitmap, ball.x.toFloat(), ball.y.toFloat(), paint)
            canvas.drawBitmap(paddle.bitmap, paddle.x.toFloat(), paddle.y.toFloat(), paint)

            for(brick in bricks) {
                if(brick.visible)
                    canvas.drawBitmap(brick.bitmap, brick.x.toFloat(), brick.y.toFloat(), paint)
            }

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun control() {
        Thread.sleep(17)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_MOVE -> {
                paddle.update(event.x.toInt())
            }
        }

        return true
    }
}