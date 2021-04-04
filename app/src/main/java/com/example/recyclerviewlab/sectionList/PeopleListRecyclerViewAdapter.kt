package com.example.recyclerviewlab.sectionList

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import coil.load
import coil.transform.CircleCropTransformation
import com.example.recyclerviewlab.R
import kotlinx.android.synthetic.main.people_item_list.view.*
import kotlinx.android.synthetic.main.people_item_list_header.view.*

class PeopleListRecyclerViewAdapter(
    peoples: List<People>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val values: List<PeopleRecyclerItem> = peoples.sortedBy { it.firstName }
        .groupBy { it.firstName[0].toString() }
        .flatMap { (category, items) ->
            listOf<PeopleRecyclerItem>(PeopleRecyclerItem.Header(category)) +
                    items.map { PeopleRecyclerItem.Content(it) }+
                    listOf<PeopleRecyclerItem>(PeopleRecyclerItem.Footer(""))
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        TYPE_HEADER -> HeaderViewHolder(parent.inflate(R.layout.people_item_list_header))
        TYPE_ITEM -> ContentViewHolder(parent.inflate(R.layout.people_item_list))
        else -> FooterViewHolder(parent.inflate(R.layout.people_item_list_footer))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(val item = values[holder.adapterPosition]) {
            is PeopleRecyclerItem.Header -> (holder as HeaderViewHolder).bind(item)
            is PeopleRecyclerItem.Content -> (holder as ContentViewHolder).bind(item)
            else -> (holder as FooterViewHolder).bind()
        }
    }

    override fun getItemCount(): Int = values.size

    override fun getItemViewType(position: Int) = when(values[position]) {
        is PeopleRecyclerItem.Content -> TYPE_ITEM
        is PeopleRecyclerItem.Header -> TYPE_HEADER
        else -> TYPE_FOOTER
    }

    inner class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val root: View = view.findViewById(R.id.people_list_root)
        fun bind(item: PeopleRecyclerItem.Content) {
            root.first_name.text = item.people.firstName
            root.last_name.text = item.people.lastName
            root.city.text = item.people.city
            root.user_image.load(item.people.image) {
                transformations(CircleCropTransformation())
            }
            root.setOnClickListener { listener(item.people.uuid) }
        }
    }
    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val root: View = view.findViewById(R.id.people_list_header_root)
        fun bind(item: PeopleRecyclerItem.Header) {
            root.people_list_header_title.text = item.title
        }
    }
    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: View = view.findViewById(R.id.people_list_footer_root)
        fun bind() {

        }
    }

    sealed class PeopleRecyclerItem {
        data class Header(val title: String): PeopleRecyclerItem()
        data class Footer(val title: String = "") : PeopleRecyclerItem()
        data class Content(val people: People) : PeopleRecyclerItem()
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
