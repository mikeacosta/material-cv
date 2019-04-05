package net.acosta.mike.resume.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.acosta.mike.resume.data.Job
import net.acosta.mike.resume.data.JobRepository
import javax.inject.Inject

class JobViewModel @Inject constructor(private val repo: JobRepository) : ViewModel() {

    fun getJob(id: Int): LiveData<Job> {
        return repo.getJob(id)
    }
}