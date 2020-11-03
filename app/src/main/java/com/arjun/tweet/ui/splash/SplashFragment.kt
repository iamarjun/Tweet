package com.arjun.tweet.ui.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arjun.tweet.R
import com.arjun.tweet.databinding.FragmentSplashBinding
import com.arjun.tweet.util.viewBinding
import timber.log.Timber


class SplashFragment : Fragment() {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.splash.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Timber.d("onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Timber.d("onAnimationEnd")
                val action = SplashFragmentDirections.actionSplashFragmentToTweetsFragment()
                findNavController().navigate(action)
            }

            override fun onAnimationCancel(animation: Animator?) {
                Timber.d("onAnimationCancel")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Timber.d("onAnimationRepeat")
            }

        })
    }

}