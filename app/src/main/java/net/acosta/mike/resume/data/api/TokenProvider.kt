package net.acosta.mike.resume.data.api

import android.content.Context
import android.preference.PreferenceManager
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
        var token= PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.tokenKey), null)

        if (token != null){
            apiClient.token = token
            result.value = true
            return result
        }

        val call = apiClient.login()

        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                token = response.body()
                apiClient.token = token
                result.value = true

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
                with (sharedPref.edit()) {
                    putString(context.getString(R.string.tokenKey), token)
                    commit()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                token = null
                result.value = false
            }
        })

        return result
    }
}