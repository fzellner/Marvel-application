package com.fzellner.character.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.fzellner.character.R
import kotlinx.android.synthetic.main.character_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroesListFragment : Fragment() {


    private val viewModel: HeroesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.character_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.get()
        viewModel.total.observe(this){

            txtview.text = it.toString()
        }

    }

}
