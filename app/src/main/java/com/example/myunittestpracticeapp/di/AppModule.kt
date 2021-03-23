package com.example.myunittestpracticeapp.di

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myunittestpracticeapp.R
import com.example.myunittestpracticeapp.data.local.ShoppingDao
import com.example.myunittestpracticeapp.data.local.ShoppingItemDatabase
import com.example.myunittestpracticeapp.data.remote.PixabayApi
import com.example.myunittestpracticeapp.repositories.DefaultShoppingRepository
import com.example.myunittestpracticeapp.repositories.ShoppingRepository
import com.example.myunittestpracticeapp.util.Constants.BASE_URL
import com.example.myunittestpracticeapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShoppingItemDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun providesShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providesPixabayApi(): PixabayApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(PixabayApi::class.java)

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayApi
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository
}