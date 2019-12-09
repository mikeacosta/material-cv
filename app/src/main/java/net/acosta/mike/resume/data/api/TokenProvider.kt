package net.acosta.mike.resume.data.api

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.acosta.mike.resume.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor(private val context: Context, private val apiClient: ApiClient)
{
    private var result: MutableLiveData<Boolean> = MutableLiveData()

    fun setToken() : LiveData<Boolean> {
        val token= PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.tokenKey), null)

        token?.let {
            apiClient.token = it
            result.value = true
            return result
        }

        val call = apiClient.login()

        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() != 200){
                    result.value = false
                    return
                }

                val responseToken = response.body()
                result.value = responseToken != null
                responseToken?.let { apiClient.token = it }

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
                with (sharedPref.edit()) {
                    putString(context.getString(R.string.tokenKey), responseToken)
                    commit()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                result.value = false
            }
        })

        return result
    }
}