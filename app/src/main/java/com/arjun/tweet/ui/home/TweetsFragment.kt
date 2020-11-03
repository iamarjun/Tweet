package com.arjun.tweet.ui.home

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
import com.arjun.tweet.views.RecyclerViewEmptyLoadingSupport
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
            emptyStateView = binding.empty
            loadingStateView = binding.loading
            adapter = tweetsAdapter
        }

        binding.search.setOnClickListener {
            val query = binding.searchView.text.toString()
            tweetsAdapter.filter.filter(query)
        }

        lifecycleScope.launchWhenStarted {
            
            viewModel.tweets.collect {

                when (it) {
                    is Resource.Loading -> {
                        binding.tweetList.stateView = RecyclerViewEmptyLoadingSupport.RecyclerViewEnum.LOADING
                    }
                    is Resource.Success -> {
                        Timber.d("${it.data}")
                        binding.tweetList.stateView = RecyclerViewEmptyLoadingSupport.RecyclerViewEnum.NORMAL
                        tweetsAdapter.submitList(it.data)
                    }
                    is Resource.Error -> {
                        binding.tweetList.stateView = RecyclerViewEmptyLoadingSupport.RecyclerViewEnum.NORMAL
                        Timber.e(it.exception)
                    }

                }
            }
        }

    }

}