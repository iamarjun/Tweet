package com.arjun.tweet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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

        binding.loading.setAnimationFromUrl("https://gist.githubusercontent.com/nirav-tukadiya/ee89ad79fd92b3bafd6cab100effd0c8/raw/3a757e35be40b5dd52d540fbef1a0768ee385358/loading.json")

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

        binding.search.setOnClickListener {
            val query = binding.searchView.text.toString()
            tweetsAdapter.filter.filter(query)
        }

        lifecycleScope.launchWhenStarted {
            
            viewModel.tweets.collect {
                binding.loading.isVisible = it is Resource.Loading

                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Timber.d("${it.data}")
                        binding.loading.cancelAnimation()
                        binding.loading.clearAnimation()
                        tweetsAdapter.submitList(it.data)
                    }
                    is Resource.Error -> {
                        Timber.e(it.exception)
                        binding.loading.cancelAnimation()
                        binding.loading.clearAnimation()
                    }

                }
            }
        }

    }

}