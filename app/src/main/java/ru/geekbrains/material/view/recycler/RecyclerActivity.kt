package ru.geekbrains.material.view.recycler

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import ru.geekbrains.material.MainActivity
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.ActivityRecyclerBinding
import ru.geekbrains.material.navigation.ApiActivity

class RecyclerActivity : AppCompatActivity(), OnListItemClickListener {

    lateinit var binding: ActivityRecyclerBinding
    lateinit var adapter: RecyclerActivityAdapter

    private var list = arrayListOf(
        Pair(DataRecycler("HEADER", "", TYPE_HEADER), false),
        Pair(DataRecycler("Earth", "Earth des", TYPE_EARTH), false),
        Pair(DataRecycler("Earth", "Earth des", TYPE_EARTH), false),
        Pair(DataRecycler("Earth", "Earth des", TYPE_EARTH), false),
        Pair(DataRecycler("Mars", "", TYPE_MARS), false),
        Pair(DataRecycler("Earth", "Earth des", TYPE_EARTH), false),
        Pair(DataRecycler("Mars", null, TYPE_MARS), false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerActivityAdapter(this)
        adapter.setList(list)
        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            onAddBtnClick(list.size)
        }

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_one -> {
                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    )
                    true
                }
                R.id.navigation_two -> {
                    startActivity(
                        Intent(
                            this,
                            ApiActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    )
                    true
                }
                else ->
                    true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.navigation_recycler
    }

    override fun onItemClick(dataRecycler: DataRecycler) {
        //
    }

    override fun onAddBtnClick(position: Int) {
        list.add(position, Pair(DataRecycler("Mars", "Mars des", TYPE_MARS), false))
        adapter.serAddToList(list, position)
    }

    override fun onRemoveBtnClick(position: Int) {
        list.removeAt(position)
        adapter.setRemoveToList(list, position)
    }
}