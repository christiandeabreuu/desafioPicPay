package com.picpay.desafio.android.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel
    private val mockUseCase: GetUsersUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when fetchUsers is called, should call use case once`() = runTest {
        // Arrange
        coEvery { mockUseCase() } returns flowOf(emptyList())

        // Act
        viewModel.fetchUsers()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        verify(exactly = 1) { mockUseCase() }
    }


    @Test
    fun `should set loading state at start of fetchUserss`() = runTest {
        // Configura o dispatcher principal de teste
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // Simula uma execução vazia do UseCase
        coEvery { mockUseCase.invoke() } returns flow {
            emit(emptyList())
        }

        // Cria o ViewModel
        val viewModel = MainViewModel(mockUseCase)

        // Chama o método fetchUsers()
        viewModel.fetchUsers()

        // Avança manualmente o tempo do scheduler de teste
        testScheduler.advanceUntilIdle()

        // Captura o estado do LiveData
        val state = viewModel.state.getOrAwaitValue()
        assertTrue(state.isLoading) // Verifica que isLoading está true

        // Reseta o Dispatcher após o teste
        Dispatchers.resetMain()
    }

    @Test
    fun `should set loading state at start of fetchUsers`() = runTest {
        coEvery { mockUseCase.invoke() } returns flow { emit(emptyList()) }

        val viewModel = MainViewModel(mockUseCase)

        viewModel.fetchUsers()

        assertEquals(viewModel.state.value?.isLoading, null)
    }


    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2, unit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        if (!latch.await(time, unit)) {
            throw RuntimeException("LiveData value was not set within timeout.")
        }

        @Suppress("UNCHECKED_CAST") return data as T
    }
}