package com.fzellner.heroes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.common.utils.ui.afterMeasure
import com.fzellner.heroes.R
import com.fzellner.heroes.ui.adapter.HereoesAdapter
import kotlinx.android.synthetic.main.hero_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HeroesListFragment : Fragment() {

    private val viewModel: HeroesViewModel by viewModel()
    private lateinit var heroAdapter: HereoesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hero_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        setObservers()
    }

    private fun setObservers() {
        viewModel.heroList.observe(this) {
            heroAdapter.submitList(it)
        }
        viewModel.networkState.observe(this) {
            heroAdapter.setNetworkState(it)
        }
    }

    private fun initAdapter() {
        heroAdapter = HereoesAdapter { viewModel.getHeroes() }
        heroRecyclerView.adapter = heroAdapter
        heroRecyclerView.afterMeasure { startPostponedEnterTransition() }


    }

}
