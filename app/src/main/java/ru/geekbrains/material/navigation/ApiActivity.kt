package ru.geekbrains.material.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.ActivityApiBinding
import ru.geekbrains.material.navigation.viewpager.ViewPagerAdapter

class ApiActivity : AppCompatActivity() {

    lateinit var binding: ActivityApiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}