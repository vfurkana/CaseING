package com.vfurkana.caseing.data.remote

import com.vfurkana.caseing.data.model.RepositoryResponseData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{userId}/repos")
    fun fetchRepositories(@Path("userId") userName: String): Single<List<RepositoryResponseData>>
}