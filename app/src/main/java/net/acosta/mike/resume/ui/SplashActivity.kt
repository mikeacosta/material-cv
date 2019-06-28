package net.acosta.mike.resume.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_splash.*
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.data.api.TokenProvider
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject
    internal lateinit var tokenProvider: TokenProvider

    private val delay = 1000
    private var isTokenSet = false
    private var isAnimationDone = false

    public override fun onCreate(icicle: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(icicle)
        setContentView(R.layout.activity_splash)

        tokenProvider.setToken().observe(this, Observer{ result ->
            isTokenSet = result

            if (isTokenSet)
                proceed()
            else
                stop()
        })

        val zoom: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        imageViewSplash.startAnimation(zoom)

        zoom.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                isAnimationDone = true
                imageViewSplash.visibility = View.GONE
                splashProgressBar.visibility = View.VISIBLE
                proceed()
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })

        proceed()
    }

    private fun proceed()
    {
        if (!isTokenSet || !isAnimationDone)
            return

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(intent)
            this@SplashActivity.finish()
        }, delay.toLong())

        isAnimationDone = false
    }

    private fun stop()
    {
        imageViewSplash.visibility = View.GONE
        // do something graceful
    }
}