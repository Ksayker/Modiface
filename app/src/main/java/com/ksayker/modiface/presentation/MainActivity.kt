package com.ksayker.modiface.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.ksayker.modiface.R
import com.ksayker.modiface.presentation.fragment.imagelist.ImageListFragment
import com.ksayker.modiface.presentation.fragment.imagelist.ImageListState


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph, ImageListFragment.makeArgs(ImageListState()))
    }
}