package net.acosta.mike.resume.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.databinding.FragmentCodeBinding
import net.acosta.mike.resume.viewmodel.ContentViewModel
import net.acosta.mike.resume.viewmodel.ViewModelFactory
import javax.inject.Inject


class CodeFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<ContentViewModel>
    private lateinit var viewModel: ContentViewModel
    private lateinit var binding: FragmentCodeBinding

    companion object {
        fun newInstance(): CodeFragment {
            return CodeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_code, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureViewModel()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory).get(ContentViewModel::class.java)
        viewModel.getContent().observe(viewLifecycleOwner, Observer { content ->
            if (content != null) {
                binding.setVariable(BR.viewModel, viewModel)
            } else {
                Toast.makeText(this@CodeFragment.context, getString(R.string.errorMsg), Toast.LENGTH_LONG).show()
            }
        })
    }

}