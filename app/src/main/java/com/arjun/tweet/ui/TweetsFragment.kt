package com.arjun.tweet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arjun.tweet.R

class TweetsFragment : Fragment() {

    companion object {
        fun newInstance() = TweetsFragment()
    }

    private lateinit var viewModel: TweetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tweets_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TweetsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}