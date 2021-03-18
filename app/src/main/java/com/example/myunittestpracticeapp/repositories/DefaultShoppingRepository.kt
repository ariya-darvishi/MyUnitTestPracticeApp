package com.example.myunittestpracticeapp.repositories

import androidx.lifecycle.LiveData
import com.example.myunittestpracticeapp.data.local.ShoppingDao
import com.example.myunittestpracticeapp.data.local.ShoppingItem
import com.example.myunittestpracticeapp.data.remote.PixabayApi
import com.example.myunittestpracticeapp.data.remote.responses.ImageResponse
import com.example.myunittestpracticeapp.util.Resource
import retrofit2.Response.error
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val dao: ShoppingDao,
    private val api: PixabayApi
) : ShoppingRepository {


    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return dao.observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = api.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(
                    "Couldn't reach the server. Check your internet connection",
                    null
                )
            }else{
                Resource.error(
                    "Couldn't reach the server. Check your internet connection",
                    null
                )
            }
        }catch (e:Exception){
            Resource.error(
                "Couldn't reach the server. Check your internet connection",
                null
            )
        }
    }
}