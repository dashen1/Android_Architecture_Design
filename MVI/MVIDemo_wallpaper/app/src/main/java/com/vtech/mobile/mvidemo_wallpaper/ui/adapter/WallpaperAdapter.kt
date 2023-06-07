package com.vtech.mobile.mvidemo_wallpaper.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vtech.mobile.mvidemo_wallpaper.data.model.Vertical
import com.vtech.mobile.mvidemo_wallpaper.databinding.ItemWallpaperRvBinding

class WallpaperAdapter(private val verticals:ArrayList<Vertical>) :
    RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    fun addData(data:List<Vertical>){
        verticals.addAll(data)
    }

    class ViewHolder(itemWallpaperRvBinding: ItemWallpaperRvBinding):
            RecyclerView.ViewHolder(itemWallpaperRvBinding.root){

                var binding:ItemWallpaperRvBinding

                init {
                    binding = itemWallpaperRvBinding
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemWallpaperRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        verticals[position].preview.let {
            Glide.with(holder.itemView.context).load(it).into(holder.binding.ivWallPaper)
        }
    }

    override fun getItemCount(): Int = verticals.size
}