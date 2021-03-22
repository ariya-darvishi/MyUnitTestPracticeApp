package com.example.myunittestpracticeapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.myunittestpracticeapp.getOrAwaitValue
import com.example.myunittestpracticeapp.launchFragmentInHiltContainer
import com.example.myunittestpracticeapp.ui.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertShoppingItemTest() = runBlockingTest {

        val shoppingItem =
            ShoppingItem("book", 2, 80f, "url", 1)

        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItem().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)

    }

    @Test
    fun deleteShoppingItemTest() = runBlockingTest {

        val shoppingItem =
            ShoppingItem("book", 2, 80f, "url", 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)

    }

    @Test
    fun observeTotalPriceTest() = runBlockingTest {


        val shoppingItem1 =
            ShoppingItem("book", 2, 80f, "url", 1)
        val shoppingItem2 =
            ShoppingItem("book", 1, 40.8f, "url", 2)
        val shoppingItem3 =
            ShoppingItem("book", 3, 49f, "url", 3)


        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 80f + 1 * 40.8f + 3 * 49f)
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }



}