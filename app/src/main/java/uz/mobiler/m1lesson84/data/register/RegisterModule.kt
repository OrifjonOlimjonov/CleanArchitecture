package uz.mobiler.m1lesson84.data.register

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.mobiler.m1lesson84.data.common.module.NetworkModule
import uz.mobiler.m1lesson84.data.register.remote.api.RegisterService
import uz.mobiler.m1lesson84.data.register.repository.RegisterRepositoryImpl
import uz.mobiler.m1lesson84.domain.register.RegisterRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RegisterModule {

    @Singleton
    @Provides
    fun provideRegisterService(retrofit: Retrofit): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(
        registerService: RegisterService,
        gson: Gson
    ): RegisterRepository {
        return RegisterRepositoryImpl(registerService, gson)
    }
}