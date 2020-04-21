package com.fzellner.comics.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.fzellner.comics.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.comic_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat


class ComicDetailFragment : Fragment() {

    private val comicViewModel: ComicViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.comic_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args: ComicDetailFragmentArgs by navArgs()

        defineObservers()
        comicViewModel.get(args.heroId)

    }

    private fun defineObservers() {

        comicViewModel.comic.observe(this) {
            comicTitleTextView.text = it.title
            descriptionTextView.text = it.description
            comicImageView.load(it.imageUrl){
                crossfade(true)
            }
            priceTextView.text = setMonetaryValue(it.price)
        }
        comicViewModel.loading.observe(this){
            loadingProgressbar.visibility = if(it)View.VISIBLE else View.GONE
        }

        comicViewModel.error.observe(this){
            Snackbar.make(view!!, R.string.comic_not_found, Snackbar.LENGTH_SHORT)
                .show();
            findNavController().popBackStack()
        }

    }

    private fun setMonetaryValue(price: Float):String{

        val monetaryFormat= NumberFormat.getCurrencyInstance()
        return if(price != 0f)
            monetaryFormat.format(price)
        else
            getString(R.string.value_not_provided)
    }

}