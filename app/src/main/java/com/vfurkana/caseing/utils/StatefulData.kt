package com.vfurkana.caseing.utils


sealed class StatefulData<out T> {
    class Success<T>(val data: T) : StatefulData<T>()
    class Error(val errorMessage: String? = null) : StatefulData<Nothing>()
    class Progress() : StatefulData<Nothing>()

    fun getSuccessData() : T? {
        return (this as? Success)?.data
    }
    fun isLoading(): Boolean {
        return this is Progress
    }
}