package ru.geekbrains.material.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.BottomNavigationLayoutBinding

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.designNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_one -> {
                    Log.d("@@@", R.string.navigation_one.toString())
                }
                R.id.navigation_two -> {
                    Log.d("@@@", R.string.navigation_two.toString())
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomSheetDialogFragment()
    }
}