package ru.grandi.bambino.changeablerecycler

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { MainViewModel() }
}