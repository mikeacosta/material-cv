package net.acosta.mike.resume.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_info.view.*
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.ui.support.InfoPagerAdapter
import net.acosta.mike.resume.viewmodel.ContentViewModel
import net.acosta.mike.resume.viewmodel.ViewModelFactory
import javax.inject.Inject


class InfoFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<ContentViewModel>

    private lateinit var viewModel: ContentViewModel

    companion object {
        fun newInstance(): InfoFragment {
            val fragment = InfoFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        val viewPager = view.infoViewPager
        val tabLayout = view.tabs

        viewPager.adapter = InfoPagerAdapter(context!!, childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ContentViewModel::class.java)
    }
}