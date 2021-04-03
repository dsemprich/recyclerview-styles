package com.example.recyclerviewlab.sectionList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewlab.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private lateinit var viewModel: PeopleListViewModel
    @Inject lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_contacts, container, false)
        setupViewModel()
        observeLoader()
        observePeopleLists(view)
        return view
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PeopleListViewModel::class.java)
    }

    private fun observePeopleLists(view: View) {
        viewModel.peoples.observe(this as LifecycleOwner) { peoples ->
            if (peoples.getOrNull() != null) {
                setupList(view.peopleLists_list, peoples.getOrNull()!!)
            } else {
                //TODO
            }
        }
    }

    private fun setupList(
        view: View?,
        peoples: List<People>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = PeopleListRecyclerViewAdapter(peoples) { uuid ->

            }
        }
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                else -> loader.visibility = View.GONE

            }
        }
    }
}