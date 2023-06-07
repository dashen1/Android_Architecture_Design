package com.vtech.mobile.mvidemo_wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.vtech.mobile.mvidemo_wallpaper.data.intent.MainIntent
import com.vtech.mobile.mvidemo_wallpaper.data.state.MainState
import com.vtech.mobile.mvidemo_wallpaper.databinding.ActivityMainBinding
import com.vtech.mobile.mvidemo_wallpaper.network.NetworkUtils
import com.vtech.mobile.mvidemo_wallpaper.ui.adapter.WallpaperAdapter
import com.vtech.mobile.mvidemo_wallpaper.ui.viewmodel.MainViewModel
import com.vtech.mobile.mvidemo_wallpaper.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    private var wallpaperAdapter = WallpaperAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 绑定ViewModel
        mainViewModel = ViewModelProvider(this,ViewModelFactory(NetworkUtils.apiService))[MainViewModel::class.java]
        initView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            // 状态收集
            mainViewModel.state.collect{
                when(it){
                    is MainState.Idle ->{}
                    is MainState.Loading -> {
                        binding.btnGetWallpaper.visibility = View.GONE
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    is MainState.Wallpapers -> { // 数据返回
                        binding.btnGetWallpaper.visibility = View.GONE
                        binding.pbLoading.visibility = View.GONE

                        binding.rvWallpaper.visibility = View.VISIBLE
                        it.wallpaper.let { paper ->
                            wallpaperAdapter.addData(paper.res.vertical)
                        }
                        wallpaperAdapter.notifyDataSetChanged()
                    }
                    is MainState.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.btnGetWallpaper.visibility = View.GONE
                        Log.d("TAG","observeViewModel:${it.error}")
                        Toast.makeText(this@MainActivity, it.error,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initView() {
        // RV config.
        binding.rvWallpaper.apply {
            layoutManager = GridLayoutManager(this@MainActivity,2)
            adapter = wallpaperAdapter
        }
        // 按钮点击
        binding.btnGetWallpaper.setOnClickListener{
            lifecycleScope.launch {
                mainViewModel.mainIntentChannel.send(MainIntent.GetWallpaper)
            }
        }
    }
}