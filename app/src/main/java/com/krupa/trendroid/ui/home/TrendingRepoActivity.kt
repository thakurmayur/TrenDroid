package com.krupa.trendroid.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krupa.trendroid.MyApplication
import com.krupa.trendroid.R
import com.krupa.trendroid.databinding.ActivityTrendingRepoBinding
import com.krupa.trendroid.model.Item
import com.krupa.trendroid.util.Status
import com.krupa.trendroid.viewmodels.RepositoryViewModel
import com.krupa.trendroid.viewmodels.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrendingRepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrendingRepoBinding
    private lateinit var viewModel: RepositoryViewModel

    @Inject
    lateinit var adapter: TrendingRepoAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var trendingList = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Not supporting night mode for now
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trending_repo)
        (applicationContext as MyApplication).getComponent().inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RepositoryViewModel::class.java)

        initialize()
        setUpObservers()
    }

    private fun initialize() {
        var toolbar = binding.toolbarHome
        setSupportActionBar(toolbar)

        val recyclerView = binding.recyclerHome
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getTrendingRepositories()
        }

        // Setup Work Manager
        viewModel.setUpWorkRequest()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_refresh) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getTrendingRepoFromInternet()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpObservers() {
        viewModel.repositoriesList.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loaderHome.visibility = View.GONE
                    binding.recyclerHome.visibility = View.VISIBLE

                    it.data?.let {
                        trendingList.clear()
                        trendingList.addAll(it)
                        adapter.updateList(trendingList)
                        binding.homeNoInternetLayout.visibility =
                            if (trendingList.isEmpty()) View.VISIBLE else View.GONE
                    }
                }

                Status.LOADING -> {
                    binding.loaderHome.visibility = View.VISIBLE
                    binding.recyclerHome.visibility = View.GONE
                    binding.homeNoInternetLayout.visibility = View.GONE
                }

                Status.ERROR -> {
                    binding.loaderHome.visibility = View.GONE
                    Toast.makeText(
                        this,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()

                    if (trendingList.isEmpty())
                        binding.homeNoInternetLayout.visibility = View.VISIBLE
                    else
                        binding.homeNoInternetLayout.visibility = View.GONE
                }
            }
        })
    }
}