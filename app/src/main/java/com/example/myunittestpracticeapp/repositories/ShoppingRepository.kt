package com.example.myunittestpracticeapp.repositories

import androidx.lifecycle.LiveData
import com.example.myunittestpracticeapp.data.local.ShoppingItem
import com.example.myunittestpracticeapp.data.remote.responses.ImageResponse
import com.example.myunittestpracticeapp.util.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}