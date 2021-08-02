package com.krupa.trendroid.ui.readme

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.krupa.trendroid.MyApplication
import com.krupa.trendroid.R
import com.krupa.trendroid.databinding.ActivityRepoReadmeBinding
import com.krupa.trendroid.viewmodels.RepositoryViewModel
import com.krupa.trendroid.util.Status
import com.krupa.trendroid.viewmodels.ViewModelFactory
import com.squareup.picasso.Picasso
import io.noties.markwon.Markwon
import io.noties.markwon.image.picasso.PicassoImagesPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class RepositoryReadmeActivity : AppCompatActivity() {

    lateinit var binding: ActivityRepoReadmeBinding
    lateinit var viewModel: RepositoryViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var repoID: Long = 0
    private var avatar: String? = ""
    private var name: String? = ""
    private var loginID: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_readme)
        (applicationContext as MyApplication).getComponent().inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RepositoryViewModel::class.java)

        repoID = intent.getLongExtra("repoID", 0)
        avatar = intent.getStringExtra("avatar")
        name = intent.getStringExtra("name")
        loginID = intent.getStringExtra("loginID")

        initialize()
        setUpObservers()
    }

    private fun initialize() {
        val toolbar = binding.toolbarReadme
        binding.toolbarReadme.title = name
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        Picasso.get().load(avatar).resize(100, 0)
            .into(binding.ivReadmeAvatar)
        binding.tvReadmeName.text = name

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getRepositoryReadme(repoID!!, loginID!!, name!!)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpObservers() {
        viewModel.repoReadmeData.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.readmeLoader.visibility = View.GONE
                    binding.readmeScrollView.visibility = View.VISIBLE
                    binding.readmeNotLoaded.visibility = View.GONE

                    it.data?.run {
                       setUpMarkTextView(this)
                    }
                }

                Status.LOADING -> {
                    binding.readmeLoader.visibility = View.VISIBLE
                    binding.readmeScrollView.visibility = View.GONE
                    binding.readmeNotLoaded.visibility = View.GONE
                }

                Status.ERROR -> {
                    binding.readmeLoader.visibility = View.GONE
                    binding.readmeScrollView.visibility = View.GONE
                    binding.readmeNotLoaded.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setUpMarkTextView(readMeData: String) {
        val markWon =
            Markwon.builder(this)
                .usePlugin(PicassoImagesPlugin.create(this))
                .build()

        markWon.setMarkdown(binding.tvReadmeDescription, readMeData);
    }
}