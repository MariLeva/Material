package ru.geekbrains.material.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.FragmentEarthBinding
import ru.geekbrains.material.databinding.FragmentMarsBinding

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }
}