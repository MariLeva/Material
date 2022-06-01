package ru.geekbrains.material.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import coil.load
import ru.geekbrains.material.viewmodel.PictureOfTheDayViewModel
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.FragmentPictureOfTheDayBinding

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    val binding: FragmentPictureOfTheDayBinding
    get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayFragment()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest()
    }

    private fun renderData(pictureOfTheDayData: PictureOfTheDayData){
        when (pictureOfTheDayData){
            is PictureOfTheDayData.Error -> {}
            is PictureOfTheDayData.Loading -> {}
            is PictureOfTheDayData.Success -> {
                binding.imageView.load(pictureOfTheDayData.pictureOfTheDayResponseData.url)
            }
        }
    }

}