package com.vfurkana.caseing.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryAppData(
    val id: Int,
    val repoName: String,
    val userName: String,
    val avatarUrl: String,
    val repoStarCount: Int,
    val openIssueCount: Int,
    var isInFavourites: Boolean = false
) : Parcelable