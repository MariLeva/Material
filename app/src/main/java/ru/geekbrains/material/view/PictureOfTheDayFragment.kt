package ru.geekbrains.material.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.*
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import ru.geekbrains.material.MainActivity
import ru.geekbrains.material.R
import ru.geekbrains.material.viewmodel.PictureOfTheDayViewModel
import ru.geekbrains.material.databinding.FragmentPictureOfTheDayStartBinding
import ru.geekbrains.material.settings.SettingsFragment

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayStartBinding? = null
    private val binding: FragmentPictureOfTheDayStartBinding
        get() = _binding!!
    private var flag = false
    private val duration: Long = 1000

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
        _binding = FragmentPictureOfTheDayStartBinding.inflate(inflater, container, false)

        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
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
        setWiki()

        binding.chipGroup.setOnCheckedChangeListener { group, position ->
            when (position) {
                R.id.chip_today -> {
                    viewModel.sendRequest()
                }
                R.id.chip_yesterday -> {
                    viewModel.sendRequestYesterday()
                }
                R.id.chip_the_day_before_yesterday -> {
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
                    Toast.makeText(context, "???? ?????????????? ${pictureOfTheDayData}!", Toast.LENGTH_LONG)
                        .show()
                }
                is PictureOfTheDayData.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                is PictureOfTheDayData.Success -> {
                    loadingLayout.visibility = View.GONE
                    imageView.load(pictureOfTheDayData.pictureOfTheDayResponseData.url)
                    bottomSheetFragment.title.typeface =
                        Typeface.createFromAsset(requireActivity().assets, "robotInvaders.ttf")
                    val textSpannableTitle = pictureOfTheDayData.pictureOfTheDayResponseData.title

                    val spannableString: SpannableString = SpannableString(textSpannableTitle)
                    spannableString.setSpan(
                        StyleSpan(Typeface.BOLD_ITALIC),
                        0,
                        textSpannableTitle.length,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    bottomSheetFragment.title.text = textSpannableTitle

                    val textSpannable = pictureOfTheDayData.pictureOfTheDayResponseData.explanation
                    val spannedString: SpannedString
                    var spannableStringBuilder: SpannableStringBuilder =
                        SpannableStringBuilder(textSpannable)
                    bottomSheetFragment.explanation.setText(
                        spannableStringBuilder,
                        TextView.BufferType.EDITABLE
                    )
                    spannableStringBuilder =
                        bottomSheetFragment.explanation.text as SpannableStringBuilder

                    spannableStringBuilder.setSpan(
                        LeadingMarginSpan.Standard(15),
                        0,
                        textSpannable.length,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        spannableStringBuilder.setSpan(
                            LineHeightSpan.Standard(80),
                            0,
                            textSpannable.length,
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    val listColor = arrayListOf(
                        ContextCompat.getColor(requireActivity(), R.color.red),
                        ContextCompat.getColor(requireActivity(), R.color.orange),
                        ContextCompat.getColor(requireActivity(), R.color.yellow),
                        ContextCompat.getColor(requireActivity(), R.color.green),
                        ContextCompat.getColor(requireActivity(), R.color.light_blue),
                        ContextCompat.getColor(requireActivity(), R.color.blue),
                        ContextCompat.getColor(requireActivity(), R.color.violet)
                    )
                    var begin = 0
                    while (begin < textSpannable.length - 1) {
                        for (j in 0..listColor.size - 1) {
                            val end = begin + 1
                            if (end <= textSpannable.length) {
                                spannableStringBuilder.setSpan(
                                    ForegroundColorSpan(listColor[j]),
                                    begin,
                                    end,
                                    SpannableString.SPAN_INCLUSIVE_INCLUSIVE
                                )
                                begin++
                            }
                        }
                    }
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

    private fun setWiki() {
        binding.wikiButton.setOnClickListener {
            flag = !flag
            if (flag) {
                ObjectAnimator.ofFloat(binding.wikiButton, View.ROTATION, 0f, 405f)
                    .setDuration(duration).start()
            } else {
                ObjectAnimator.ofFloat(binding.wikiButton, View.ROTATION, 405f, 0f)
                    .setDuration(duration).start()
            }
        }
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
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, SettingsFragment.newInstance()).addToBackStack("").commit()
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
