package ru.geekbrains.material.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.bind.DateTypeAdapter
import ru.geekbrains.material.databinding.ActivityRecyclerEarthBinding
import ru.geekbrains.material.databinding.ActivityRecyclerHeaderBinding
import ru.geekbrains.material.databinding.ActivityRecyclerMarsBinding

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

class RecyclerActivityAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var list: MutableList<Pair<DataRecycler, Boolean>>

    fun setList(newList: List<Pair<DataRecycler, Boolean>>) {
        this.list = newList.toMutableList()
    }

    fun serAddToList(newList: List<Pair<DataRecycler, Boolean>>, position: Int) {
        this.list = newList.toMutableList()
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: List<Pair<DataRecycler, Boolean>>, position: Int) {
        this.list = newList.toMutableList()
        notifyItemRemoved(position)
    }

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
            TYPE_HEADER -> {
                HeaderViewHolder(ActivityRecyclerHeaderBinding.inflate(LayoutInflater.from(parent.context)).root)
            }
            else -> {
                val view =
                    ActivityRecyclerMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].first.type
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
        fun myBind(listItem: Pair<DataRecycler, Boolean>) {
            (ActivityRecyclerEarthBinding.bind(itemView)).apply {
                title.text = listItem.first.someText
                descriptionTextView.text = listItem.first.someDescription
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun myBind(listItem: Pair<DataRecycler, Boolean>) {
            (ActivityRecyclerHeaderBinding.bind(itemView)).apply {
                header.text = listItem.first.someText
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun myBind(listItem: Pair<DataRecycler, Boolean>) {
            (ActivityRecyclerMarsBinding.bind(itemView)).apply {
                title.text = listItem.first.someText
                addItemImageView.setOnClickListener {
                    onListItemClickListener.onAddBtnClick(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    onListItemClickListener.onRemoveBtnClick(layoutPosition)
                }
                moveItemDown.setOnClickListener {
                    list.removeAt(layoutPosition).apply {
                        list.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
                moveItemUp.setOnClickListener {
                    list.removeAt(layoutPosition).apply {
                        list.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
                marsImageView.setOnClickListener {
                    list[layoutPosition] = list[layoutPosition].let {
                        it.first to !it.second
                    }
                    marsDescriptionTextView.visibility = if (list[layoutPosition].second) View.VISIBLE else View.GONE
                }
            }
        }
    }
}