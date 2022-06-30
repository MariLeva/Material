package ru.geekbrains.material.view.recycler

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.material.MainActivity
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.ActivityRecyclerBinding
import ru.geekbrains.material.navigation.ApiActivity

class RecyclerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = arrayListOf(
            DataRecycler("HEADER","", TYPE_HEADER),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Mars", "", TYPE_MARS),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Mars", null, TYPE_MARS)
        )
        binding.recyclerView.adapter = RecyclerActivityAdapter(list)

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
}