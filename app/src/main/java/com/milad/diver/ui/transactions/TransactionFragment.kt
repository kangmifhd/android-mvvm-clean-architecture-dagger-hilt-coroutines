package com.milad.diver.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.milad.diver.R
import com.milad.diver.data.model.Item
import com.milad.diver.data.model.User
import com.milad.diver.data.model.common.Status
import com.milad.diver.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_transaction.*

@AndroidEntryPoint
class TransactionFragment : BaseFragment() {

    private val mTransactionViewModel: TransactionViewModel by viewModels()
    private lateinit var mTransactionsList: List<Item>
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private var mAdapter = TransactionListAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun initComponents() {
        setUpRecyclerView()
        mTransactionViewModel.getTransactions()
    }

    override fun initUiListeners() {

    }

    override fun initObservers() {
        mTransactionViewModel.mGetInformationLiveData.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    mTransactionsList = it.data?.mItems!!
                    mAdapter.updateData(mTransactionsList)
                }
            }
        })
    }

    override fun updateToolbar() {

    }

   private fun setUpRecyclerView() {

        mLinearLayoutManager = LinearLayoutManager(context)
        recyclerview_transaction_transactionsList.layoutManager = mLinearLayoutManager
        recyclerview_transaction_transactionsList.adapter = mAdapter
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        context?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.drawable_all_dividerforrecyclerview)
                ?.let { drawable ->
                    dividerItemDecoration.setDrawable(
                        drawable
                    )
                }
        }
        recyclerview_transaction_transactionsList.addItemDecoration(dividerItemDecoration)
    }
}