package com.peter.webkeysnews.ui.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.peter.webkeysnews.R
import com.peter.webkeysnews.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    lateinit var viewModel: DetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        @Suppress("UNUSED_VARIABLE")

        val binding = FragmentDetailsBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        val article = DetailsFragmentArgs.fromBundle(arguments!!).selectedProperty

        val viewModelFactory = DetailViewModelFactory(article, application)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.navToInternet.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(it)
                startActivity(i)
                viewModel.displayInternetComplete()
            }
        })
        viewModel.navToShare.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                onShare()
                viewModel.displayShareComplete()
            }
        })

        return binding.root
    }

    private fun onShare() {
        val shareIntent = activity?.let {
            ShareCompat.IntentBuilder.from(it)
                .setText("This Article " + viewModel.selectedProperty.value!!.title + " is Good continue " + viewModel.selectedProperty.value!!.url)
                .setType("text/plain")
                .intent
        }
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context, getString(R.string.sharing_not_available),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}