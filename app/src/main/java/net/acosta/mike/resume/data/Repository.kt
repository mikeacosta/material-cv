package net.acosta.mike.resume.data

import androidx.lifecycle.LiveData

interface Repository<T> {
    fun getData(): LiveData<T>
}
