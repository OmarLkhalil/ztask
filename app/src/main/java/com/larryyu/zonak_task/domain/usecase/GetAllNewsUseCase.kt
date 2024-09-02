package com.larryyu.zonak_task.domain.usecase

import com.larryyu.zonak_task.domain.repo.NewsRepo
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke() = newsRepo.onGetAllNews()
}