package ru.geekbrains.material.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.material.databinding.FragmentTehTransferBinding
import ru.geekbrains.material.view.PictureOfTheDayData
import ru.geekbrains.material.viewmodel.PictureOfTheDayViewModel


class TehTransferFragment : Fragment() {

    private var _binding: FragmentTehTransferBinding? = null
    private val binding: FragmentTehTransferBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTehTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TehTransferFragment()
    }
}