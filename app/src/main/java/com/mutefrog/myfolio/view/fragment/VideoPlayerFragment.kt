package com.mutefrog.myfolio.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mutefrog.myfolio.databinding.FragmentVideoPlayerBinding
import com.potyvideo.library.globalEnums.EnumResizeMode
import com.potyvideo.library.globalInterfaces.AndExoPlayerListener

class VideoPlayerFragment : Fragment(), AndExoPlayerListener {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private var videoUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (arguments != null) {
            videoUrl = VideoPlayerFragmentArgs.fromBundle(requireArguments()).videoUrl
        }

        binding.videoPlayerView.setResizeMode(EnumResizeMode.ZOOM) // sync with attrs
        binding.videoPlayerView.setAndExoPlayerListener(this)
        binding.videoPlayerView.setPlayWhenReady(true)
        binding.videoPlayerView.enterFullScreen.visibility = View.GONE
        binding.videoPlayerView.setSource(videoUrl)
//     binding.videoPlayerView.setSource("https://jsoncompare.org/LearningContainer/SampleFiles/Video/MP4/Sample-MP4-Video-File-for-Testing.mp4")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}