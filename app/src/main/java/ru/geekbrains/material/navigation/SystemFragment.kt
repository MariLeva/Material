package ru.geekbrains.material.navigation

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.geekbrains.material.R
import ru.geekbrains.material.databinding.FragmentEarthBinding
import ru.geekbrains.material.databinding.FragmentSystemBinding

class SystemFragment : Fragment() {

    private var _binding: FragmentSystemBinding? = null
    private val binding: FragmentSystemBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = RenderEffect.createBlurEffect(15f, 0f, Shader.TileMode.MIRROR)
            view.findViewById<ImageView>(R.id.image_view).setRenderEffect(blurEffect)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SystemFragment()
    }
}