package net.acosta.mike.resume.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import net.acosta.mike.resume.R


class SplashActivity : AppCompatActivity() {

    private val delay = 1000

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_splash)

        val zoom: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        imageViewSplash.startAnimation(zoom)

        zoom.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                Handler().postDelayed({
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    this@SplashActivity.startActivity(intent)
                    this@SplashActivity.finish()
                }, delay.toLong())
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
    }
}