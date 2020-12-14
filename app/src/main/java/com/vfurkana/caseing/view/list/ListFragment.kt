package com.vfurkana.caseing.view.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vfurkana.caseing.R
import com.vfurkana.caseing.data.model.RepositoryAppData
import com.vfurkana.caseing.databinding.FragmentListBinding
import com.vfurkana.caseing.view.binding.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()

    private var repositoryClickListener: ((RepositoryAppData) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater).apply { lifecycleOwner = this@ListFragment }
        binding.viewModel = viewModel
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (et_username.text.toString().isNullOrEmpty()) {
                binding.tilUsername.error = getString(R.string.error_invalid_username)
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                viewModel.onUsernameSubmit(et_username.text.toString())
            }
        }
        binding.btnSearch.setOnClickListener {
            hideKeyboard()
            if (et_username.text.toString().isNullOrEmpty()) {
                binding.tilUsername.error = getString(R.string.error_invalid_username)
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                viewModel.onUsernameSubmit(et_username.text.toString())
            }
        }
        binding.repositoryClickListener = object : ItemClickListener<RepositoryAppData> {
            override fun onItemClick(viewId: Int, modelList: List<RepositoryAppData>, clickedItem: RepositoryAppData, position: Int) {
                repositoryClickListener?.invoke(clickedItem)
            }
        }
        binding.etUsername.doOnTextChanged { text, start, before, count -> binding.tilUsername.error = null }
        return binding.root
    }

    fun setOnRepositorySelectedListener(listener: (RepositoryAppData) -> Unit) {
        this.repositoryClickListener = listener
    }

    fun hideKeyboard() {
        activity?.run {
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    fun refreshFavourites() {
        if (binding.etUsername.text?.isNotEmpty() == true){
            viewModel.onUsernameSubmit(et_username.text.toString())
        }
    }
}