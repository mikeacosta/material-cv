package net.acosta.mike.resume.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.android.synthetic.main.fragment_jobs.view.*
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.data.Job
import net.acosta.mike.resume.ui.support.JobsAdapter
import net.acosta.mike.resume.ui.support.goToWebsite
import net.acosta.mike.resume.viewmodel.JobsViewModel
import net.acosta.mike.resume.viewmodel.ViewModelFactory
import javax.inject.Inject


class JobsFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<JobsViewModel>
    private lateinit var viewModel: JobsViewModel

    companion object {
        fun newInstance(): JobsFragment {
            return JobsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_jobs, container, false)
        view.recyclerViewJobs.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBar.visibility = View.VISIBLE
        configureViewModel()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobsViewModel::class.java)
        viewModel.getJobs().observe(viewLifecycleOwner, Observer { jobs -> updateUI(jobs) })
    }

    private fun updateUI(jobs: List<Job>?) {
        recyclerViewJobs.adapter = JobsAdapter(jobs
                ?: emptyList(),
                { jobId: Int -> jobClicked(jobId) },
                { website: String -> goToWebsite(website) })

        if (jobs == null)
            Toast.makeText(this@JobsFragment.context, getString(R.string.errorMsg), Toast.LENGTH_LONG).show()

        progressBar.visibility = View.INVISIBLE
    }

    private fun jobClicked(jobId: Int) {
        val intent = Intent(context, JobActivity::class.java)
        intent.putExtra("id", jobId)
        startActivity(intent)
    }
}
