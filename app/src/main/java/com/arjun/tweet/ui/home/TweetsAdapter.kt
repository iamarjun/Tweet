package com.arjun.tweet.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arjun.tweet.R
import com.arjun.tweet.databinding.LayoutTweetItemBinding
import com.arjun.tweet.models.Data
import com.arjun.tweet.util.GlideApp

class TweetsAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val originalTweets = arrayListOf<Data>()

    private val itemCallback = object : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.handle == newItem.handle
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, itemCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TweetsViewHolder(
            LayoutTweetItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TweetsViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Data>) {
        originalTweets.addAll(list)
        differ.submitList(originalTweets)
    }

    class TweetsViewHolder
    constructor(
        private val binding: LayoutTweetItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {

            binding.apply {
                GlideApp.with(itemView)
                    .load(item.profileImageUrl)
                    .placeholder(R.drawable.ic_person)
                    .into(profilePicture)
                userName.text = item.name
                userHandle.text = item.handle
                likeCount.text = "${item.favoriteCount}"
                retweetCount.text = "${item.retweetCount}"
                tweet.text = item.text
                root.setOnClickListener {
                    interaction?.onItemSelected(adapterPosition, item)
                }
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Data)
    }

    override fun getFilter(): Filter = TweetFiler()

    private inner class TweetFiler : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result = arrayListOf<Data>()
            constraint?.let { query ->
                if (query.isNotEmpty()) {
                    result.addAll(originalTweets.filter { it.contains(query) })
                } else
                    result.addAll(originalTweets)
            }

            return FilterResults().apply {
                count = result.size
                values = result
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {
                val list = it.values as List<Data>
                differ.submitList(list)
                notifyDataSetChanged()
            }
        }

    }
}

