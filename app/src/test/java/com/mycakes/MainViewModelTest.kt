package com.mycakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mycakes.data.model.Cake
import com.mycakes.data.repository.CakeRepositoryImpl
import com.mycakes.viewmodel.MainViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException
import java.net.UnknownHostException


@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var cakeRepository: CakeRepositoryImpl
    lateinit var mainViewModel: MainViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(cakeRepository)
    }

    @Test
    fun fetchCake_successWithOrderedAndDistinct() {
        val cakes = listOf(
            Cake("Demo 1", "Demo 1", "Demo 1"), Cake("Demo 1", "Demo 1", "Demo 1"),
            Cake("Demo", "Demo", "Demo")
        )

        //Here in expected output we have removed duplicate entry and based on title sorting
        //the last entry should come first

        val expectedCakes = listOf(Cake("Demo", "Demo", "Demo"), Cake("Demo 1", "Demo 1", "Demo 1"))
        `when`(cakeRepository.fetchCakes()).thenReturn(Single.just(cakes))

        mainViewModel.fetchCakes()

        Assert.assertEquals(expectedCakes, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.SUCCESS, mainViewModel.loadingState.value)
        Assert.assertEquals(null, mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchCake_successEmptyCakeList() {
        val cakes = listOf<Cake>()
        `when`(cakeRepository.fetchCakes()).thenReturn(Single.just(cakes))

        mainViewModel.fetchCakes()

        Assert.assertEquals(null, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("No Cake found", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchCake_networkError() {
        `when`(cakeRepository.fetchCakes()).thenReturn(Single.error(UnknownHostException("Abc")))

        mainViewModel.fetchCakes()

        Assert.assertEquals(null, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("No Network", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchCake_otherError() {
        `when`(cakeRepository.fetchCakes()).thenReturn(Single.error(RuntimeException("Abc")))

        mainViewModel.fetchCakes()

        Assert.assertEquals(null, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("Abc", mainViewModel.errorMessage.value)
    }
}