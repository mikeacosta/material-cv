package net.acosta.mike.resume.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.acosta.mike.resume.data.api.ApiClient
import net.acosta.mike.resume.data.room.ContentDao
import net.acosta.mike.resume.utils.AppStatus
import net.acosta.mike.resume.utils.CustomExecutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ContentRepository @Inject constructor(private val apiClient: ApiClient,
                                            private val contentDao: ContentDao,
                                            private val executor: CustomExecutor,
                                            private val appStatus: AppStatus)
    : Repository<Content> {

    private lateinit var content: MutableLiveData<Content>
    private var isBusy = false

    override fun getData() : LiveData<Content>  {
        content = MutableLiveData()
        loadContent()

        return content
    }

    private fun loadContent() {
        if (isBusy)
            return

        executor.execute {
            val contentFromDb = contentDao.getAll()

            if (contentFromDb.isNotEmpty()) {
                content.postValue(contentFromDb.first())
            } else {
                isBusy = true
                apiClient.getContent().enqueue(object: Callback<Content> {
                    override fun onResponse(call: Call<Content>, response: Response<Content>?) {
                        content.value = response?.body()
                        if (response?.body() != null)
                            executor.execute {
                                val c = response.body()
                                c?.lastUpdate = Date()
                                contentDao.insert(response.body()!!)
                            }
                        isBusy = false
                    }

                    override fun onFailure(call: Call<Content>, t: Throwable?) {
                        content.value = null
                        isBusy = false
                    }
                })
            }
        }
    }
}