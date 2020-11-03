package com.arjun.tweet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjun.tweet.R
import com.arjun.tweet.databinding.TweetsFragmentBinding
import com.arjun.tweet.util.Resource
import com.arjun.tweet.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class TweetsFragment : Fragment() {

    private val viewModel by viewModels<TweetsViewModel>()
    private val binding by viewBinding(TweetsFragmentBinding::bind)
    private val tweetsAdapter by lazy { TweetsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tweets_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tweetList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    binding.tweetList.context,
                    (binding.tweetList.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = tweetsAdapter
        }

        lifecycleScope.launchWhenStarted {
            viewModel.tweets.collect {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Timber.d("${it.data}")
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }

    }

}