package com.mho.training.features.persondetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Person
import com.example.android.testshared.mockedPerson
import com.example.android.usecases.GetPersonInformationUseCase
import com.mho.training.defaultMockedPerson
import com.mho.training.initMockedDi
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PersonDetailIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerInfoPerson: Observer<Person>

    private lateinit var viewModel: PersonDetailViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> PersonDetailViewModel(id, get(), get()) }
            factory { GetPersonInformationUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedPerson.id) }
    }

    @Test
    fun `onPersonDetailInformation should display person detail information`() {
        runBlocking {

            //GIVEN
            viewModel.infoPerson.observeForever(observerInfoPerson)

            //WHEN
            viewModel.onPersonDetailInformation()

            //THEN
            verify(observerInfoPerson).onChanged(defaultMockedPerson)
        }
    }
}