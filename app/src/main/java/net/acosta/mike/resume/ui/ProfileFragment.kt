package net.acosta.mike.resume.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.viewmodel.ProfileViewModel
import net.acosta.mike.resume.viewmodel.ViewModelFactory
import net.acosta.mike.resume.data.ProfileItem
import net.acosta.mike.resume.data.ProfileType
import net.acosta.mike.resume.ui.support.ExpandableListAdapter
import javax.inject.Inject
import com.google.android.material.appbar.AppBarLayout
import net.acosta.mike.resume.utils.FontCache
import kotlin.math.roundToInt


class ProfileFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<ProfileViewModel>
    private lateinit var viewModel: ProfileViewModel
    @Inject
    internal lateinit var fontCache: FontCache

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.toolbarProfile.logo = ContextCompat.getDrawable(requireContext(), R.drawable.toolbar_profile_icon)
        view.toolbarProfile.logo.alpha = 0

        val typeface = fontCache.get(R.font.poppins)
        view.collapsingToolbar.setCollapsedTitleTypeface(typeface)
        view.collapsingToolbar.setExpandedTitleTypeface(typeface)

        view.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1)
                    scrollRange = appBarLayout.totalScrollRange

                view.toolbarProfile.logo.alpha = getIconAlpha(scrollRange + verticalOffset)
            }
        })

        view.expandableList.setOnGroupClickListener { parent, _, groupPosition, _ ->
            setListViewHeight(parent, groupPosition)
            false
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        progressBar.visibility = View.VISIBLE
        configureViewModel()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
        viewModel.getProfile().observe(viewLifecycleOwner, Observer{ it -> updateUI(it) })
    }

    private fun updateUI(profileList: List<ProfileItem>?) {
        if (profileList == null) {
            Toast.makeText(this@ProfileFragment.context, getString(R.string.errorMsg), Toast.LENGTH_LONG).show()
            progressBar.visibility = View.INVISIBLE

            return
        }

        textViewName.text = profileList.filter { it.type == ProfileType.Name }.map { name -> name.text }[0]
        textViewSummary.text = profileList.filter { it.type == ProfileType.Summary }.map { profile -> profile.text }[0]

        val headers = profileList.filter { it.type == ProfileType.Header }.map { header -> header.text }
        val highlights = profileList.filter { it.type == ProfileType.Highlight }.map { hilite -> hilite.text }
        val skills = profileList.filter { it.type == ProfileType.Skill }.map { skill -> skill.text }

        val detail = HashMap<String, List<String>>()
        detail[headers[0]] = highlights
        detail[headers[1]] = skills

        expandableList.setAdapter(ExpandableListAdapter(context!!, headers, detail))
        progressBar.visibility = View.INVISIBLE
    }

    private fun setListViewHeight(listView: ExpandableListView, groupPosition: Int) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.EXACTLY)

        for (i in 0 until listAdapter.groupCount) {
            val groupItem = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)

            totalHeight += groupItem.measuredHeight

            if (listView.isGroupExpanded(i) && i != groupPosition || !listView.isGroupExpanded(i) && i == groupPosition) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem = listAdapter.getChildView(i, j, false, null, listView)
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
        }

        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.groupCount - 1)

        listView.layoutParams = params
        listView.requestLayout()
    }

    private fun getIconAlpha(expanded: Int) : Int {
        return when {
            expanded > 100 -> 0
            expanded == 0 -> 255
            else -> (100 - expanded).times(0.01).times(255).roundToInt()
        }
    }
}