package net.acosta.mike.resume.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.card_cert.*
import kotlinx.android.synthetic.main.fragment_creds.*
import kotlinx.android.synthetic.main.card_cert.view.*
import kotlinx.android.synthetic.main.card_edu.*
import kotlinx.android.synthetic.main.card_edu.view.*
import kotlinx.android.synthetic.main.card_mscert.view.*
import net.acosta.mike.resume.App
import net.acosta.mike.resume.R
import net.acosta.mike.resume.data.Content
import net.acosta.mike.resume.ui.support.goToWebsite
import net.acosta.mike.resume.ui.support.toSpanned
import net.acosta.mike.resume.viewmodel.ContentViewModel
import net.acosta.mike.resume.viewmodel.ViewModelFactory
import javax.inject.Inject


class CredsFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<ContentViewModel>

    private lateinit var viewModel: ContentViewModel
    private lateinit var textViewCertMain: TextView
    private lateinit var textViewCertSub: TextView
    private lateinit var buttonCert: Button
    private lateinit var buttonExpand: ImageButton
    private lateinit var textViewCertSupport: TextView
    private lateinit var textViewEduMain: TextView
    private lateinit var textViewEduSub: TextView
    private lateinit var textViewEduSupport: TextView
    private lateinit var certUrl: String
    private lateinit var textViewMsCertMain: TextView
    private lateinit var buttonMsCert: Button
    private lateinit var msCertUrl: String

    companion object {
        fun newInstance(): CredsFragment {
            return CredsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_creds, container, false)

        textViewCertMain = view.textViewCertMain
        textViewCertSub = view.textViewCertSub
        buttonCert = view.buttonCert
        buttonExpand = view.buttonExpand
        textViewCertSupport = view.textViewCertSupport
        textViewEduMain = view.textViewEduMain
        textViewEduSub = view.textViewEduSub
        textViewEduSupport = view.textViewEduSupport
        textViewMsCertMain = view.textViewMsCertMain
        buttonMsCert = view.buttonMsCert

        buttonCert.setOnClickListener { goToWebsite(certUrl) }
        buttonMsCert.setOnClickListener { goToWebsite(msCertUrl) }

        buttonExpand.setOnClickListener {
            if (textViewCertSupport.visibility == View.VISIBLE) {
                buttonExpand.setImageResource(R.drawable.ic_expand_more_black_36dp)
                textViewCertSupport.visibility = View.GONE
            } else {
                buttonExpand.setImageResource(R.drawable.ic_expand_less_black_36dp)
                textViewCertSupport.visibility = View.VISIBLE
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(parentFragment!!, viewModelFactory).get(ContentViewModel::class.java)

        progressBar.visibility = View.VISIBLE
        cardViewCert.visibility = View.GONE
        cardViewEdu.visibility = View.GONE

        viewModel.getContent().observe(viewLifecycleOwner, Observer { creds -> updateUI(creds) })
    }

    private fun updateUI(creds: Content?) {
        if (creds != null) {
            textViewCertSupport.text = creds.certSupport.toSpanned()
            textViewEduSupport.text = creds.eduSupport.toSpanned()

            textViewCertSupport.movementMethod = LinkMovementMethod.getInstance()
            textViewEduSupport.movementMethod = LinkMovementMethod.getInstance()
            textViewCertMain.text = creds.certMain
            textViewCertSub.text = creds.certSub
            textViewEduMain.text = creds.eduMain
            textViewEduSub.text = creds.eduSub
            textViewMsCertMain.text = creds.msCertMain

            certUrl = creds.googleCert
            msCertUrl = creds.msCert

            cardViewCert.visibility = View.VISIBLE
            cardViewEdu.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        } else {
            Toast.makeText(this@CredsFragment.context, getString(R.string.errorMsg), Toast.LENGTH_LONG).show()
            progressBar.visibility = View.INVISIBLE
        }
    }
}