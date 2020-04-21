package com.fzellner.heroes.ui

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import coil.transform.CircleCropTransformation
import com.fzellner.heroes.R
import com.fzellner.heroes.domain.model.Hero
import kotlinx.android.synthetic.main.hero_detail_fragment.*


class HeroDetailFragment : Fragment() {

    lateinit var hero: Hero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        activity?.window?.sharedElementExitTransition = ChangeBounds()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hero_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViewWith()
        defineBehavior()
        ViewCompat.setTransitionName(
            heroImageView,
            context?.getString(R.string.hero_image_transition, hero.id)
        )

    }

    private fun defineBehavior() {
        comicButton.setOnClickListener {
            findNavController().navigate(HeroDetailFragmentDirections.toComicNavigation(hero.id))
        }
    }

    private fun populateViewWith() {
        val args: HeroDetailFragmentArgs by navArgs()
        hero = args.selectedHero
        hero.let {
            heroImageView.load(it.thumbnail){
                transformations(CircleCropTransformation())
            }
            heroNameTextView.text = it.name
            descriptionTextView.text = it.description
        }
    }

}