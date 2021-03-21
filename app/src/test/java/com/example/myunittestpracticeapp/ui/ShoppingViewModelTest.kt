package com.example.myunittestpracticeapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myunittestpracticeapp.MainCoroutineRule
import com.example.myunittestpracticeapp.getOrAwaitValueTest
import com.example.myunittestpracticeapp.repositories.FakeShoppingRepository
import com.example.myunittestpracticeapp.util.Constants
import com.example.myunittestpracticeapp.util.Status
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    lateinit var viewModel: ShoppingViewModel


    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }


    @Test
    fun insertShoppingItemWithEmptyField_returnError() {

        viewModel.insertShoppingItem("name", "", "45.6")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun insertShoppingItemToLongName_returnError() {

        val longName = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.insertShoppingItem(longName, "8", "45.6")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun insertShoppingItemToLongPrice_returnError() {

        val longPrice = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.insertShoppingItem("name", "8", longPrice)
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun insertShoppingItemToHighAmount_returnError() {

        viewModel.insertShoppingItem(
            "name", "888888888888888888888888888888", "45.6"
        )
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun insertShoppingItemWithValidInput_returnSuccess() {

        viewModel.insertShoppingItem(
            "name", "3", "45.6"
        )
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }


}