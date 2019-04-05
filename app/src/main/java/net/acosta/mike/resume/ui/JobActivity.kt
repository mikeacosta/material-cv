package net.acosta.mike.resume.ui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.analytics.tracking.android.EasyTracker
import kotlinx.android.synthetic.main.activity_job.*
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.viewmodel.JobViewModel
import net.acosta.mike.resume.viewmodel.ViewModelFactory
import javax.inject.Inject


class JobActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<JobViewModel>

    private lateinit var viewModel: JobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        layoutJob.background.alpha = 80

        val jobId = intent.extras?.getInt("id")

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobViewModel::class.java)

        viewModel.getJob(jobId!!).observe(this, Observer { job ->

            val builder = SpannableStringBuilder()

            for (item in job.content) {
                builder.append(" \n")
                builder.append(item)
                builder.append(" \n")

                builder.setSpan(BulletSpan(30, Color.BLACK),
                        builder.indexOf(item),
                        builder.indexOf(item) + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            textViewJob.text = builder
            supportActionBar?.title = job?.company
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        EasyTracker.getInstance(this).activityStart(this)
    }

    override fun onStop() {
        super.onStop()
        EasyTracker.getInstance(this).activityStop(this)
    }
}