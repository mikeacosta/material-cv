package net.acosta.mike.resume.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.acosta.mike.resume.data.api.ApiClient
import net.acosta.mike.resume.data.room.JobDao
import net.acosta.mike.resume.utils.AppStatus
import net.acosta.mike.resume.utils.CustomExecutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JobRepository @Inject constructor(private val apiClient: ApiClient,
                                        private val jobDao: JobDao,
                                        private val executor: CustomExecutor,
                                        private val appStatus: AppStatus)
    : Repository<List<Job>> {

    private lateinit var jobs: MutableLiveData<List<Job>>

    override fun getData() : LiveData<List<Job>> {
        jobs = MutableLiveData()
        loadJobs()

        return jobs
    }

    fun getJob(id: Int): LiveData<Job> {
        val job = MutableLiveData<Job>()

        if (this::jobs.isInitialized) {
            job.value = jobs.value?.single { j -> j.id == id }
        } else {
            executor.execute {
                val jobFromDb = jobDao.getbyId(id)
                job.postValue(jobFromDb)
            }
        }

        return job
    }

    private fun loadJobs() {
        executor.execute {
            val jobsFromDb = jobDao.getAll()

            if (jobsFromDb.isNotEmpty()) {
                jobs.postValue(jobsFromDb)
            } else {
                val call = apiClient.getJobs()

                call.enqueue(object: Callback<Jobs> {
                    override fun onResponse(call: Call<Jobs>, response: Response<Jobs>?) {
                        jobs.value = response?.body()?.jobs
                        executor.execute {
                            response?.body()?.jobs?.forEach { it ->
                                it.lastUpdate = Date()
                                jobDao.insert(it)
                            }
                        }
                    }

                    override fun onFailure(call: Call<Jobs>?, t: Throwable?) {
                        if (jobsFromDb.isNotEmpty())
                            jobs.value = jobsFromDb
                        else
                            jobs.value = null
                    }
                })
            }
        }
    }
}