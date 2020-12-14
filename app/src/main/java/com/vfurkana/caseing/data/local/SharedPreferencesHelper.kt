package com.vfurkana.caseing.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vfurkana.caseing.data.model.RepositoryAppData
import io.reactivex.Completable
import io.reactivex.Single

class SharedPreferencesHelper(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    companion object {
        private const val FAVOURITES_LIST = "FAVOURITES_LIST"
    }

    fun getFavourites(): Single<List<Int>> {
        return Single.just(
            gson.fromJson(
                sharedPreferences.getString(FAVOURITES_LIST, "[]"), object : TypeToken<List<Int>>() {}.type
            )
        )
    }

    fun putFavourite(id: Int): Completable {
        return getFavourites().flatMapCompletable { oldList ->
            Completable.fromAction {
                if (!oldList.contains(id)) {
                    sharedPreferences.edit {
                        putString(FAVOURITES_LIST, gson.toJson(oldList.toMutableList().apply { add(id) })).commit()
                    }
                }
            }
        }
    }

    fun removeFavourite(id: Int): Completable {
        return getFavourites().flatMapCompletable { oldList ->
            Completable.fromAction {
                if (oldList.contains(id)) {
                    sharedPreferences.edit {
                        putString(FAVOURITES_LIST, gson.toJson(oldList.toMutableList().apply { remove(id) })).commit()
                    }
                }
            }
        }
    }
}