package com.atakanakar.challenge.ui.fragment.splash

import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.atakanakar.challenge.R
import com.atakanakar.challenge.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


/**
 * Created by atakanakar on 10.02.2021.
 */
@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashVM by hiltNavGraphViewModels(R.id.splashFragment)
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initListener()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                binding.motionBase.transitionToEnd()
            }
        }
    }

    private fun initListener() {
        binding.motionBase.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        delay(1000)
                        findNavController().navigate(R.id.action_splashFragment_to_searchFragment)
                    }
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}