package net.acosta.mike.resume

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import net.acosta.mike.resume.data.Job
import net.acosta.mike.resume.data.JobRepository
import net.acosta.mike.resume.viewmodel.JobViewModel
import org.junit.*
import java.util.*


class JobViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repo = mock<JobRepository>()

    private val viewModel by lazy { JobViewModel(repo) }

    @Test
    fun getJobTest() {
        val data = MediatorLiveData<Job>()
        data.value = Job(1, "Acme Co.", "Coder", "July 2015", "present",
                        "www.acme.com", "acme", listOf("coding", "design"), Date())

        whenever(repo.getJob(1)).thenReturn(data)

        viewModel.getJob(1).observeForever { it ->
            Assert.assertEquals("Acme Co.", it.company)
            Assert.assertEquals("Coder", it.title)
            Assert.assertTrue(it.content.size == 2)
        }
    }
}

