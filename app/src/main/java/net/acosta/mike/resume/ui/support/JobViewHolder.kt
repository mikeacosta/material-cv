package net.acosta.mike.resume.ui.support

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_job.view.*
import net.acosta.mike.resume.data.Job


class JobViewHolder(private val context: Context, val view: View,
                    private val jobDetailListener: (Int) -> Unit,
                    private val jobWebsiteListener: (String) -> Unit)
    : RecyclerView.ViewHolder(view) {

    var job: Job? = null
        set(value) {
            field = value
            view.textViewTitle.text = value?.title
            view.textViewCompany.text = value?.company
            view.textViewDates.text = String.format("%s - %s", value?.start, value?.end)

            val id = context.resources.getIdentifier(value?.image, "drawable", context.packageName)
            view.imageViewCompany.setImageResource(id)

            view.imageJobDetail.setOnClickListener { jobDetailListener( value?.id ?: -1) }

            view.imageJobWebsite.setOnClickListener { jobWebsiteListener( value?.website!! )}
        }
}