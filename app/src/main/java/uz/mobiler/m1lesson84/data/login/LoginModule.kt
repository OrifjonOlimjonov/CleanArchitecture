package uz.mobiler.m1lesson84.data.login

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.mobiler.m1lesson84.data.common.module.NetworkModule
import uz.mobiler.m1lesson84.data.login.remote.api.LoginService
import uz.mobiler.m1lesson84.data.login.repository.LoginRepositoryImpl
import uz.mobiler.m1lesson84.domain.login.LoginRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: LoginService, gson: Gson): LoginRepository {
        return LoginRepositoryImpl(loginService, gson)
    }
}