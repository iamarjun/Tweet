package com.arjun.tweet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.arjun.tweet.R
import com.arjun.tweet.databinding.TweetsFragmentBinding
import com.arjun.tweet.util.viewBinding

class TweetsFragment : Fragment() {

    private val viewModel by viewModels<TweetsViewModel>()
    private val binding by viewBinding(TweetsFragmentBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tweets_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}