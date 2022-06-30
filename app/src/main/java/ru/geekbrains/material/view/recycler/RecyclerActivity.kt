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
        initNavigationBar()

        val list = arrayListOf(
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Mars", "Mars des", TYPE_MARS),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH),
            DataRecycler("Earth", "Earth des", TYPE_EARTH)
        )
        binding.recyclerView.adapter = RecyclerActivityAdapter(list)
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.navigation_recycler
    }

    private fun initNavigationBar() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_one -> {
                    startMainActivity()
                    true
                }
                R.id.navigation_two -> {
                    startApiActivity()
                    true
                }
                else ->
                    true
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
    }

    private fun startApiActivity(){
        startActivity(Intent(this, ApiActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
    }
}