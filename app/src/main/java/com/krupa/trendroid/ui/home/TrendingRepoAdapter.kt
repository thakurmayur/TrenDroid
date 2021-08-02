package com.krupa.trendroid.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krupa.trendroid.databinding.InflateTrendingRepoBinding
import com.krupa.trendroid.model.Item
import com.krupa.trendroid.ui.readme.RepositoryReadmeActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class TrendingRepoAdapter @Inject constructor():
    RecyclerView.Adapter<TrendingRepoAdapter.TrendingRepoHolder>() {

    private var list = arrayListOf<Item>()

    inner class TrendingRepoHolder(var binding: InflateTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingRepoHolder {
        var binding =
            InflateTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = TrendingRepoHolder(binding)
        holder.itemView.setOnClickListener {
            Intent(
                parent.context,
                RepositoryReadmeActivity::class.java
            ).apply {
                var data = list[holder.adapterPosition]
                putExtra("repoID", data.id)
                putExtra("avatar", data.owner.avatarUrl)
                putExtra("name", data.name)
                putExtra("loginID", data.owner.login)
                parent.context.startActivity(this)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: TrendingRepoHolder, position: Int) {
        Picasso.get().load(list[position].owner.avatarUrl).resize(100, 0)
            .into(holder.binding.infHomeLogo)
        holder.binding.infHomeName.text = list[position].name
        holder.binding.infHomeDesc.text = list[position].description
        holder.binding.infTvHomeLanguage.text = getLanguageName(list[position].language)
        holder.binding.infTvHomeStars.text = getString(list[position].stargazersCount)
        holder.binding.infTvHomeForks.text = getString(list[position].forksCount)
    }

    private fun getLanguageName(name: String?): String {
        return name ?: "-"
    }

    private fun getString(count: Long): String {
        return try {
            val num = (count / 1000.0)
            "${"%.2f".format(num)}k"
        } catch (e: Exception) {
            count.toString()
        }
    }

    fun updateList(postList: ArrayList<Item>) {
        this.list = postList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}