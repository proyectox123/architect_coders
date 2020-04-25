package com.mho.training.features.persondetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Person
import com.example.android.testshared.defaultFakedPerson
import com.example.android.testshared.mockedPerson
import com.mho.training.di.DaggerTestComponent
import com.mho.training.di.TestComponent
import com.mho.training.rules.CoroutinesTestRule
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PersonDetailIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerInfoPerson: Observer<Person>

    private lateinit var viewModel: PersonDetailViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        viewModel = component.plus(PersonDetailActivityModule(mockedPerson.id)).personDetailViewModel
    }

    @Test
    fun `onPersonDetailInformation should display person detail information`() {
        runBlocking {

            //GIVEN
            viewModel.infoPerson.observeForever(observerInfoPerson)

            //WHEN
            viewModel.onPersonDetailInformation()

            //THEN
            verify(observerInfoPerson).onChanged(defaultFakedPerson)
        }
    }
}