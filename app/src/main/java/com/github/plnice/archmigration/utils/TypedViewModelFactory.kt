package com.github.plnice.archmigration.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

interface TypedViewModelFactory<out S : ViewModel> : ViewModelProvider.Factory {

    fun create(): S

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = create() as T

}
