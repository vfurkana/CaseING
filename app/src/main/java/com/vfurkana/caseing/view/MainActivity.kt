package com.vfurkana.caseing.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.vfurkana.caseing.R
import com.vfurkana.caseing.databinding.ActivityMainBinding
import com.vfurkana.caseing.view.detail.DetailFragment
import com.vfurkana.caseing.view.list.ListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpToolbar()
        (supportFragmentManager.findFragmentById(R.id.fr_list) as? ListFragment)?.setOnRepositorySelectedListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl_detailContainer, DetailFragment.newInstance(it)).addToBackStack(null).commit()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            supportFragmentManager.addOnBackStackChangedListener {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    setDisplayHomeAsUpEnabled(true)
                } else {
                    setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    fun refreshFavourites() {
        (supportFragmentManager.findFragmentById(R.id.fr_list) as? ListFragment)?.refreshFavourites()
    }
}