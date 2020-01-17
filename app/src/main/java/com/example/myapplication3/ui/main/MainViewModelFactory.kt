package com.example.myapplication3.ui.main

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.myapplication3.network.retrofit.Repository

class MainViewModelFactory(owner: SavedStateRegistryOwner, defaultArgs: Bundle?,
                          val repository : Repository) :
    AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return MainViewModel( handle, repository) as T
    }
}