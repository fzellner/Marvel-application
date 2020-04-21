package com.fzellner.heroes.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.commom.utils.model.NetworkState
import com.example.commom.utils.ui.NetworkStateItemViewHolder
import com.fzellner.heroes.R
import com.fzellner.heroes.domain.model.Hero

class HereoesAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<Hero, RecyclerView.ViewHolder>(SHOW_COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.hero_item -> (holder as HeroViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.hero_item -> HeroViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(
                parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.hero_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<Hero>() {
            override fun areItemsTheSame(oldHero: Hero, newHero: Hero): Boolean =
                oldHero.id == newHero.id

            override fun areContentsTheSame(oldHero: Hero, newHero: Hero): Boolean =
                oldHero == newHero
        }
    }
}