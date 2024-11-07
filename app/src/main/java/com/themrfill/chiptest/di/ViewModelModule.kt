package com.themrfill.chiptest.di

import com.themrfill.chiptest.vm.DogViewModel
import com.themrfill.chiptest.vm.ImagesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val vmModule = module {
    viewModelOf(::DogViewModel)
    viewModelOf(::ImagesViewModel)
}