package uz.mobiler.m1lesson84.data.product

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.mobiler.m1lesson84.data.common.module.NetworkModule
import uz.mobiler.m1lesson84.data.product.remote.api.ProductService
import uz.mobiler.m1lesson84.data.product.repository.ProductRepositoryImpl
import uz.mobiler.m1lesson84.domain.product.ProductRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(productService: ProductService, gson: Gson): ProductRepository {
        return ProductRepositoryImpl(productService, gson)
    }
}