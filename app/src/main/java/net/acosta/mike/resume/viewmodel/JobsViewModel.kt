package net.acosta.mike.resume.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.acosta.mike.resume.data.Job
import net.acosta.mike.resume.data.Repository
import javax.inject.Inject

class JobsViewModel @Inject constructor(repo: Repository<List<Job>>) : ViewModel() {

    private var jobs: LiveData<List<Job>> = repo.getData()

    fun getJobs(): LiveData<List<Job>> {
        return jobs
    }
}