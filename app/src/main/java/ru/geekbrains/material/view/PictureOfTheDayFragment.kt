package ru.geekbrains.material.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.material.MainActivity
import ru.geekbrains.material.R
import ru.geekbrains.material.viewmodel.PictureOfTheDayViewModel
import ru.geekbrains.material.databinding.FragmentPictureOfTheDayBinding
import ru.geekbrains.material.settings.SettingsFragment

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

        setBottomSheetBehavior(binding.bottomSheetFragment.bottomSheetContainer)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.chipGroup.setOnCheckedChangeListener { group, position ->
            when (position) {
                2131231228 -> {
                    viewModel.sendRequest()
                }
                2131231229 -> {
                    viewModel.sendRequestYesterday()
                }
                2131231227 -> {
                    viewModel.sendRequestTYheDayBeforeYesterday()
                }
            }
            group.findViewById<Chip>(position)?.let {
                Log.d("@@@", "${it.text.toString()} $position")
            }
        }
    }

    private fun renderData(pictureOfTheDayData: PictureOfTheDayData) {
        with(binding) {
            when (pictureOfTheDayData) {
                is PictureOfTheDayData.Error -> {
                    loadingLayout.visibility = View.GONE
                    Toast.makeText(context,"Не удалось ${pictureOfTheDayData}!", Toast.LENGTH_LONG).show()
                }
                is PictureOfTheDayData.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                is PictureOfTheDayData.Success -> {
                    loadingLayout.visibility = View.GONE
                    imageView.load(pictureOfTheDayData.pictureOfTheDayResponseData.url)
                    bottomSheetFragment.title.text =
                        pictureOfTheDayData.pictureOfTheDayResponseData.title
                    bottomSheetFragment.explanation.text =
                        pictureOfTheDayData.pictureOfTheDayResponseData.explanation
                }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> {}
                    BottomSheetBehavior.STATE_COLLAPSED -> {}
                    BottomSheetBehavior.STATE_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_SETTLING -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Snackbar.make(binding.pictureOfTheDay, "$slideOffset", Snackbar.LENGTH_INDEFINITE)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                Log.d("@@@", "app_bar_fav")
            }
            R.id.app_bar_setting -> {
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack("").add(R.id.container, SettingsFragment.newInstance()).commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(
                    requireActivity().supportFragmentManager,
                    "tag"
                )

            }
        }
        return super.onOptionsItemSelected(item)
    }

}