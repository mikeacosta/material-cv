package net.acosta.mike.resume.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.card_about.*
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.databinding.FragmentAboutBinding
import net.acosta.mike.resume.ui.support.goToWebsite
import net.acosta.mike.resume.viewmodel.ContentViewModel
import net.acosta.mike.resume.viewmodel.ViewModelFactory
import javax.inject.Inject


class AboutFragment : Fragment(), View.OnClickListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<ContentViewModel>

    private lateinit var viewModel: ContentViewModel
    private lateinit var email: String
    private lateinit var msCertUrl: String
    private lateinit var certUrl: String
    private lateinit var appUrl: String

    private lateinit var binding: FragmentAboutBinding

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        binding.setLifecycleOwner(this)

        binding.includeCardAbout.layoutAboutEmail.setOnClickListener(this)
        binding.includeCardAbout.layoutAboutWeb.setOnClickListener(this)
        binding.includeCardAbout.layoutAboutGithub.setOnClickListener(this)
        binding.includeCardAbout.layoutAboutMsCert.setOnClickListener(this)
        binding.includeCardAbout.layoutAboutCert.setOnClickListener(this)
        binding.includeCardAbout.layoutAboutGooglePlay.setOnClickListener(this)
        binding.fabShare.setOnClickListener(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory).get(ContentViewModel::class.java)
        viewModel.getContent().observe(viewLifecycleOwner, Observer { about ->
            if (about != null) {
                binding.setVariable(BR.viewModel, viewModel)

                email = about.email.trim()
                msCertUrl = about.msCert
                certUrl = about.googleCert
                appUrl = about.playStore
            } else {
                Toast.makeText(this@AboutFragment.context, getString(R.string.errorMsg), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.layoutAboutEmail -> sendEmail()
            R.id.layoutAboutWeb -> goToWebsite(textViewWeb.text.toString())
            R.id.layoutAboutGithub -> goToWebsite(textViewGithub.text.toString())
            R.id.layoutAboutMsCert -> goToWebsite(msCertUrl)
            R.id.layoutAboutCert -> goToWebsite(certUrl)
            R.id.layoutAboutGooglePlay -> goToWebsite(appUrl)
            R.id.fabShare -> share()
            else -> Toast.makeText(this@AboutFragment.context, getString(R.string.comingSoon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail() {
        val mailUri = "mailto:$email" +
                "?subject=${Uri.encode(getString(R.string.emailSubject))}"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse(mailUri)

        try {
            startActivity(intent)
        }
        catch (e: ActivityNotFoundException){
            startActivity(Intent.createChooser(intent, getString(R.string.openWith)))
        }
    }

    private fun share() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,
                "${getString(R.string.shareContent)}\n$appUrl")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, getString(R.string.shareWith)))
    }
}