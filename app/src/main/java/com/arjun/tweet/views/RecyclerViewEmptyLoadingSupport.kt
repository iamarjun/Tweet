package com.arjun.tweet.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewEmptyLoadingSupport : RecyclerView {

    var stateView: RecyclerViewEnum? = RecyclerViewEnum.LOADING
        set(value) {
            field = value
            setState()
        }
    var emptyStateView: View? = null
    var loadingStateView: View? = null


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}


    private val dataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            onChangeState()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            onChangeState()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            onChangeState()
        }
    }


    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(dataObserver)
        dataObserver.onChanged()
    }


    fun onChangeState() {
        if (adapter?.itemCount == 0) {
            emptyStateView?.visibility = View.VISIBLE
            loadingStateView?.visibility = View.GONE
            this@RecyclerViewEmptyLoadingSupport.visibility = View.GONE
        } else {
            emptyStateView?.visibility = View.GONE
            loadingStateView?.visibility = View.GONE
            this@RecyclerViewEmptyLoadingSupport.visibility = View.VISIBLE
        }
    }

    private fun setState() {

        when (this.stateView) {
            RecyclerViewEnum.LOADING -> {
                loadingStateView?.visibility = View.VISIBLE
                this@RecyclerViewEmptyLoadingSupport.visibility = View.GONE
                emptyStateView?.visibility = View.GONE
            }

            RecyclerViewEnum.NORMAL -> {
                loadingStateView?.visibility = View.GONE
                this@RecyclerViewEmptyLoadingSupport.visibility = View.VISIBLE
                emptyStateView?.visibility = View.GONE
            }
            RecyclerViewEnum.EMPTY_STATE -> {
                loadingStateView?.visibility = View.GONE
                this@RecyclerViewEmptyLoadingSupport.visibility = View.GONE
                emptyStateView?.visibility = View.VISIBLE
            }
        }
    }

    enum class RecyclerViewEnum {
        LOADING,
        NORMAL,
        EMPTY_STATE
    }
}