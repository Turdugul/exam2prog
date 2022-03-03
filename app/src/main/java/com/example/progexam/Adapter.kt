package com.example.progexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(private val click: (character: Character) -> Unit) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var list: List<Character> = mutableListOf()

    fun setData(list: List<Character>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(itemView, click)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        itemView: View, private val click: (character: Character) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character) {
            val img = itemView.findViewById<AppCompatImageView>(R.id.imgItem)
            val txtName = itemView.findViewById<AppCompatTextView>(R.id.NameItem)
            val txtStatus = itemView.findViewById<AppCompatTextView>(R.id.StatusItem)
            val txtSpecies = itemView.findViewById<AppCompatTextView>(R.id.SpeciesItem)
            val txtLocationName = itemView.findViewById<AppCompatTextView>(R.id.LocationNameItem)

            Glide.with(itemView.context)
                .load(character.image)
                .into(img)

            txtName.text = character.name
            txtStatus.text = character.status
            txtSpecies.text = character.species
            txtLocationName.text = character.location.name

            itemView.setOnClickListener {
                click.invoke(character)
            }
        }
    }
}