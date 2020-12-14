package com.vfurkana.caseing.data

import com.vfurkana.caseing.data.local.SharedPreferencesHelper
import com.vfurkana.caseing.data.model.RepositoryAppData
import com.vfurkana.caseing.data.remote.GithubService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val githubService: GithubService,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {

    fun getGithubRepositories(userName: String): Single<List<RepositoryAppData>> {
        return Single.zip(githubService.fetchRepositories(userName), sharedPreferencesHelper.getFavourites()) { repositories, favourites ->
            repositories.map {
                RepositoryAppData(
                    it.id,
                    it.name,
                    it.owner.login,
                    it.owner.avatar_url,
                    it.stargazers_count,
                    it.open_issues_count,
                    favourites.contains(it.id)
                )
            }
        }
    }

    fun putFavourite(id: Int): Completable {
        return sharedPreferencesHelper.putFavourite(id)
    }

    fun removeFavourite(id: Int): Completable{
        return sharedPreferencesHelper.removeFavourite(id)
    }
}