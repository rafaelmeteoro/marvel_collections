package br.com.rafael.marvelcollections

import android.arch.lifecycle.Observer
import android.util.Log

class ChangeHistoryObserver<T> : Observer<T> {

    private val changeHistory = mutableListOf<T?>()

    override fun onChanged(t: T?) {
        changeHistory.add(t)
    }

    fun get(index: Int): T? {
        return changeHistory[index]
    }

    fun log() {
        changeHistory.map {
            Log.d(javaClass.simpleName, it.toString())
        }
    }
}