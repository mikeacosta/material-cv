package net.acosta.mike.resume.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.acosta.mike.resume.data.ProfileItem
import net.acosta.mike.resume.data.Repository
import javax.inject.Inject


class ProfileViewModel @Inject constructor(repo: Repository<List<ProfileItem>>) : ViewModel() {

    private var profile: LiveData<List<ProfileItem>> = repo.getData()

    fun getProfile() : LiveData<List<ProfileItem>> {
        return profile
    }
}