package com.example.recyclerviewlab.sectionList

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.example.recyclerviewlab.R
import kotlinx.android.synthetic.main.people_item_list.view.*

class PeopleListRecyclerViewAdapter(
    private val values: List<People>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<PeopleListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.people_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.root.first_name.text = item.firstName
        holder.root.last_name.text = item.lastName
        holder.root.city.text = item.city
        holder.root.user_image.load(item.image) {
            transformations(CircleCropTransformation())
        }
        holder.root.setOnClickListener { listener(item.uuid) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: View = view.findViewById(R.id.people_list_root)
    }
}