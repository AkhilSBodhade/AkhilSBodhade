package com.mutefrog.myfolio.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import com.mutefrog.myfolio.databinding.FragmentTutorialBinding

class TutorialFragment : Fragment() {

    private var imageGravity = 0
    private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = requireArguments().getString(ARG_PARAM1)!!
            description = requireArguments().getString(ARG_PARAM2)!!
            imageResource = requireArguments().getInt(ARG_PARAM3)
            imageGravity = requireArguments().getInt(ARG_PARAM4)
        }
    }

    val leftGravityParam = LinearLayoutCompat.LayoutParams(
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
    ).apply {
        gravity = Gravity.START
    }

    val rightGravityParam = LinearLayoutCompat.LayoutParams(
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
    ).apply {
        gravity = Gravity.END
    }

    private var _binding: FragmentTutorialBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTutorialBinding.inflate(inflater, container, false)
        val view = binding.root
        tvTitle = binding.textOnboardingTitle
        tvDescription = binding.textOnboardingDescription
        image = binding.imageOnboarding
        tvTitle.text = title
        tvDescription.text = description

        when (imageGravity) {
            0 -> image.layoutParams = leftGravityParam
            1 -> image.layoutParams = rightGravityParam
        }
        image.setImageResource(imageResource)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"

        fun newInstance(
            title: String?,
            description: String?,
            imageResource: Int,
            imageGravity: Int
        ): TutorialFragment {
            val fragment = TutorialFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, title)
            args.putString(ARG_PARAM2, description)
            args.putInt(ARG_PARAM3, imageResource)
            args.putInt(ARG_PARAM4, imageGravity)
            fragment.arguments = args
            return fragment
        }
    }
}
