package net.acosta.mike.resume

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import net.acosta.mike.resume.data.*
import net.acosta.mike.resume.viewmodel.ProfileViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.*

class ProfileViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repo = mock<Repository<List<ProfileItem>>>()

    private val viewModel by lazy { ProfileViewModel(repo) }

    @Test
    fun getProfileTest() {
        val profiles = listOf(ProfileItem(1, ProfileType.Summary, "I write code", Date()),
                ProfileItem(2, ProfileType.Skill, "Kotlin", Date()))
        val data = MediatorLiveData<List<ProfileItem>>()

        data.value = profiles

        whenever(repo.getData()).thenReturn(data)

        viewModel.getProfile().observeForever { it ->
            Assert.assertEquals(2, it.size)
            Assert.assertTrue(it[0].type == ProfileType.Summary)
            Assert.assertTrue(it[1].text == "Kotlin")
        }

    }
}