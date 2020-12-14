package com.vfurkana.caseing.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vfurkana.caseing.data.model.RepositoryAppData
import com.vfurkana.caseing.databinding.FragmentDetailBinding
import com.vfurkana.caseing.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater).apply { lifecycleOwner = this@DetailFragment }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<RepositoryAppData>(EXTRA_REPOSITORY).let {
            viewModel.onBundle(it)
            it?.repoName?.let { activity?.actionBar?.title = it }
        }
        viewModel.getRepository().observe(requireActivity(), Observer {
            (activity as? MainActivity)?.refreshFavourites()
        })
    }

    companion object {
        const val EXTRA_REPOSITORY = "EXTRA_REPOSITORY"
        fun newInstance(repositoryData: RepositoryAppData): DetailFragment {
            return DetailFragment().apply { arguments = bundleOf(EXTRA_REPOSITORY to repositoryData) }
        }
    }
}