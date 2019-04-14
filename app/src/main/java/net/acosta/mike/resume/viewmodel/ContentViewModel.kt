package net.acosta.mike.resume.viewmodel

import android.text.Spanned
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.acosta.mike.resume.BuildConfig
import net.acosta.mike.resume.data.Content
import net.acosta.mike.resume.data.Repository
import net.acosta.mike.resume.ui.support.toSpanned
import javax.inject.Inject

class ContentViewModel @Inject constructor(val repo: Repository<Content>) : ViewModel() {

    private var content: LiveData<Content> = repo.getData()

    var codeSpanned: Spanned? = null
        get() = content.value?.code?.toSpanned()

    var isCodeVisible: Boolean = false
        get() = content.value?.code != null

    var isAboutVisible: Boolean = false
        get() = content.value?.email != null

    var versionName: String = ""
        get() = BuildConfig.VERSION_NAME

    var email: String? = null
        get() = content.value?.email

    var web: String? = null
        get() = content.value?.web

    var github: String? = null
        get() = content.value?.github

    var apiSpanned: Spanned? = null
        get() = content.value?.api?.toSpanned()

    fun getContent(): LiveData<Content> {
        return content
    }
}