package com.fzellner.character.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.commom.utils.ui.afterMeasure
import com.fzellner.character.R
import com.fzellner.character.ui.adapter.HereoesAdapter
import kotlinx.android.synthetic.main.hero_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HeroesListFragment : Fragment() {

    private val viewModel: HeroesListViewModel by viewModel()
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
        viewModel.statementList.observe(this) {
            heroAdapter.submitList(it)
        }
        viewModel.networkState.observe(this) {
            heroAdapter.setNetworkState(it)
        }
    }

    private fun initAdapter() {
        heroAdapter = HereoesAdapter { viewModel.getHeroes() }
        //handling the behavior for starting on the middle list on first load
        heroAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    heroRecyclerView.scrollToPosition(0)
                }
            }
        })
        heroRecyclerView.adapter = heroAdapter
        heroRecyclerView.afterMeasure { startPostponedEnterTransition() }


    }

}
