package com.fzellner.heroes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.fzellner.heroes.R
import com.fzellner.heroes.domain.model.Hero
import com.fzellner.heroes.ui.HeroesListFragmentDirections


class HeroViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.heroNameTextView)
    private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)

    private var hero: Hero? = null

    init {
        view.setOnClickListener {
            val directions = HeroesListFragmentDirections.toHeroDetailFragment(hero!!)
            val extras = FragmentNavigatorExtras(
                thumbnail to itemView.context.getString(R.string.hero_image_transition, hero?.id)
            )
            it.findNavController().navigate(directions, extras)
        }

    }


    fun bind(hero: Hero?) {
        this.hero = hero
        title.text = hero?.name
        thumbnail.load(hero?.thumbnail){
            transformations(CircleCropTransformation())
        }
        thumbnail.transitionName = itemView.context.getString(R.string.hero_image_transition, hero?.id)
    }

    companion object {
        fun create(
            parent: ViewGroup): HeroViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hero_item, parent, false)
            return HeroViewHolder(view)
        }
    }
}