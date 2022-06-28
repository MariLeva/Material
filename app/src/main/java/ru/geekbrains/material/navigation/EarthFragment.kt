package ru.geekbrains.material.navigation

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transition.Transition
import ru.geekbrains.material.BuildConfig
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.FragmentEarthBinding
import ru.geekbrains.material.view.PictureOfTheDayData
import ru.geekbrains.material.viewmodel.PictureOfTheDayViewModel

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding get() = _binding!!

    private var isOpen: Boolean = false

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequestTheEarthEPIC()
    }

    private fun renderData(pictureOfTheEarth: PictureOfTheDayData) {
        with(binding) {
            when (pictureOfTheEarth) {
                is PictureOfTheDayData.Error -> {
                    loadingLayout.visibility = View.GONE
                    Toast.makeText(context, "Не удалось ${pictureOfTheEarth}!", Toast.LENGTH_LONG)
                        .show()
                }
                is PictureOfTheDayData.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                is PictureOfTheDayData.SuccessEarth -> {
                    loadingLayout.visibility = View.GONE
                    val strDate =
                        pictureOfTheEarth.pictureResponseData.last().date.split(" ").first()
                    val image = pictureOfTheEarth.pictureResponseData.last().image
                    val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                            strDate.replace("-", "/", true) +
                            "/png/" +
                            "$image" +
                            ".png?api_key=${BuildConfig.NASA_API_KEY}"
                    imageView.load(url)
                    tvCaption.text = pictureOfTheEarth.pictureResponseData.last().caption
                    TransitionManager.beginDelayedTransition(
                        binding.earthLayout,
                        Slide(Gravity.TOP)
                    )
                    binding.tvCaption.visibility = View.VISIBLE
                    changeImageTransform()
                }
                else -> {}
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }

    private fun changeImageTransform() {
        binding.imageView.setOnClickListener {
            isOpen =! isOpen
            TransitionManager.beginDelayedTransition(
                binding.earthLayout, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
            params.height = if (isOpen) ViewGroup.LayoutParams.MATCH_PARENT else
                ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageView.layoutParams = params
            binding.imageView.scaleType = if (isOpen) ImageView.ScaleType.CENTER_CROP else
                ImageView.ScaleType.FIT_CENTER
        }
    }
}
