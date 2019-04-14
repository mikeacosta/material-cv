package net.acosta.mike.resume.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.acosta.mike.resume.data.api.ApiClient
import net.acosta.mike.resume.data.room.ProfileDao
import net.acosta.mike.resume.utils.AppStatus
import net.acosta.mike.resume.utils.CustomExecutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProfileRepository @Inject constructor(private val apiClient: ApiClient,
                                            private val profileDao: ProfileDao,
                                            private val executor: CustomExecutor,
                                            private val appStatus: AppStatus)
    : Repository<List<ProfileItem>> {

    private lateinit var profile: MutableLiveData<List<ProfileItem>>

    override fun getData() : LiveData<List<ProfileItem>> {
        profile = MutableLiveData()
        loadProfile()

        return profile
    }

    private fun loadProfile() {
        executor.execute {
            val profileFromDb = profileDao.getAll()

            if (profileFromDb.isNotEmpty()) {
                profile.postValue(profileFromDb)
            } else {
                val call = apiClient.getProfile()

                call.enqueue(object: Callback<Profile> {
                    override fun onResponse(call: Call<Profile>?, response: Response<Profile>?) {
                        profile.value = response?.body()?.items
                        executor.execute {
                            response?.body()?.items?.forEach { it ->
                                it.lastUpdate = Date()
                                profileDao.insert(it)
                            }
                        }
                    }

                    override fun onFailure(call: Call<Profile>?, t: Throwable?) {
                        profile.value = null
                    }
                })
            }
        }
    }
}