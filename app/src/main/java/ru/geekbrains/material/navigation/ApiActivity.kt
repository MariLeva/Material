package ru.geekbrains.material.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.material.MainActivity
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.ActivityApiBinding
import ru.geekbrains.material.navigation.viewpager.ViewPagerAdapter

class ApiActivity : AppCompatActivity() {

    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MyBlueGreyTheme)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        initTabLayout();
        initNavigationBar();
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.navigation_two
    }
    private fun initNavigationBar() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_one -> {
                    startMainActivity()
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

    private fun initTabLayout() {
        binding.tabLayoutApi.apply {
            setupWithViewPager(binding.viewPager)
            getTabAt(0)?.setIcon(R.drawable.ic_earth)
            getTabAt(1)?.setIcon(R.drawable.ic_mars)
            getTabAt(2)?.setIcon(R.drawable.ic_system)
        }
    }


}