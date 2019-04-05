package net.acosta.mike.resume.ui.support

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.acosta.mike.resume.R
import net.acosta.mike.resume.data.Job


class JobsAdapter(private val jobList : List<Job>,
                  private val jobDetailListener: (Int) -> Unit,
                  private val jobWebsiteListener: (String) -> Unit)
    : RecyclerView.Adapter<JobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_job, parent, false)

        return JobViewHolder(parent.context, view, jobDetailListener, jobWebsiteListener)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.job = jobList[position]
    }
}