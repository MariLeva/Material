package ru.geekbrains.material.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.material.databinding.ActivityRecyclerEarthBinding
import ru.geekbrains.material.databinding.ActivityRecyclerMarsBinding

const val TYPE_EARTH = 1
const val TYPE_MARS = 2

class RecyclerActivityAdapter(private var list: List<DataRecycler>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val view = ActivityRecyclerEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(view.root)
            }
            TYPE_MARS -> {
                val view = ActivityRecyclerMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)
            }
            else -> {
                val view =
                    ActivityRecyclerMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> (holder as EarthViewHolder).myBind(list[position])
            TYPE_MARS -> (holder as MarsViewHolder).myBind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun myBind(data: DataRecycler) {
            (ActivityRecyclerEarthBinding.bind(itemView)).apply {
                title.text = data.someText
                descriptionTextView.text = data.someDescription
            }
        }
    }


    class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun myBind(data: DataRecycler) {
            (ActivityRecyclerMarsBinding.bind(itemView)).apply {
                title.text = data.someText
            }
        }
    }
}