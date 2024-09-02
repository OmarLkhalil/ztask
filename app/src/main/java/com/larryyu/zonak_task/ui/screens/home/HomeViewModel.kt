package com.larryyu.zonak_task.ui.screens.home

import androidx.lifecycle.ViewModel
import com.larryyu.zonak_task.domain.usecase.GetAllNewsUseCase
import com.larryyu.zonak_task.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getAllCategoriesUseCase: GetCategoriesUseCase
) : ViewModel(){

    private fun getNews(){

    }
}