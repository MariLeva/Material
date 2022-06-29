package ru.geekbrains.material.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import ru.geekbrains.material.*
import ru.geekbrains.material.databinding.FragmentPictureOfTheDayBinding
import ru.geekbrains.material.databinding.FragmentSettingsBinding
import ru.geekbrains.material.view.PictureOfTheDayFragment


class SettingsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!
    private lateinit var parentActivity: MainActivity

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = (context as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rbThemePink.setOnClickListener(this)
        binding.rbThemeBlueGrey.setOnClickListener(this)
        binding.rbThemeBrown.setOnClickListener(this)

        when (parentActivity.getCurrentTheme()){
            1 -> binding.radioGroup.check(R.id.rb_theme_pink)
            2 -> binding.radioGroup.check(R.id.rb_theme_blue_grey)
            3 -> binding.radioGroup.check(R.id.rb_theme_brown)
        }

        binding.backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.rb_theme_pink -> parentActivity.setCurrentTheme(MyPinkTheme)
            R.id.rb_theme_blue_grey -> parentActivity.setCurrentTheme(MyBlueGreyTheme)
            R.id.rb_theme_brown -> parentActivity.setCurrentTheme(MyBrownTheme)
        }
        parentActivity.recreate()
    }
}