package net.acosta.mike.resume.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.analytics.tracking.android.EasyTracker
import kotlinx.android.synthetic.main.activity_main.*
import net.acosta.mike.resume.R


class MainActivity : AppCompatActivity() {

    private val profileFragment = ProfileFragment.newInstance()
    private val jobsFragment = JobsFragment.newInstance()
    private val infoFragment = InfoFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.toolbar_icon)
        setupBottomNavigationView()
    }

    override fun onStart() {
        super.onStart()
        EasyTracker.getInstance(this).activityStart(this)
    }

    override fun onStop() {
        super.onStop()
        EasyTracker.getInstance(this).activityStop(this)
    }

    private fun setupBottomNavigationView() {
        if (supportFragmentManager.fragments.size == 0)
            supportFragmentManager.beginTransaction().add(R.id.frame, profileFragment).commit()

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()

            when (item.itemId) {
                R.id.profile -> transaction.replace(R.id.frame, profileFragment)
                R.id.work -> transaction.replace(R.id.frame, jobsFragment)
                R.id.info -> transaction.replace(R.id.frame, infoFragment)
            }

            transaction.commit()

            true
        }
    }
}
