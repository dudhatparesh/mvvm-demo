package com.mycakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mycakes.data.model.Cake
import com.mycakes.data.repository.CakeRepositoryImpl
import com.mycakes.ui.activity.MainActivity
import com.mycakes.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.net.UnknownHostException


@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var cakeRepository: CakeRepositoryImpl
    lateinit var mainViewModel: MainViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
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
        every {cakeRepository.fetchCakes()} returns (Single.just(cakes))

        mainViewModel.fetchCakes()

        Assert.assertEquals(expectedCakes, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.SUCCESS, mainViewModel.loadingState.value)
        Assert.assertEquals(null, mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchCake_successEmptyCakeList() {
        val cakes = listOf<Cake>()
        every {cakeRepository.fetchCakes()} returns (Single.just(cakes))

        mainViewModel.fetchCakes()

        Assert.assertEquals(null, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("No Cake found", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchCake_networkError() {

        every {cakeRepository.fetchCakes()} returns (Single.error(UnknownHostException("Abc")))

        mainViewModel.fetchCakes()

        Assert.assertEquals(null, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("No Network", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchCake_otherError() {
        every{cakeRepository.fetchCakes()} returns Single.error(RuntimeException("Abc"))

        mainViewModel.fetchCakes()

        Assert.assertEquals(null, mainViewModel.cakes.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("Abc", mainViewModel.errorMessage.value)
    }

    @Test
    fun getActivityTest(){
        val activityClass = mainViewModel.getActivity()
        assertTrue(activityClass == MainActivity::class.java)
    }
}