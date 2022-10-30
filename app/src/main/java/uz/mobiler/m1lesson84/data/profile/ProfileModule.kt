package uz.mobiler.m1lesson84.data.profile

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.mobiler.m1lesson84.data.common.module.NetworkModule
import uz.mobiler.m1lesson84.data.profile.remote.api.ProfileService
import uz.mobiler.m1lesson84.data.profile.repository.ProfileRepositoryImpl
import uz.mobiler.m1lesson84.data.register.remote.api.RegisterService
import uz.mobiler.m1lesson84.domain.profile.ProfileRepository
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Singleton
    @Provides
    fun provideGetProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetProfileRepository(
        profileService:ProfileService,
        gson: Gson
    ):ProfileRepository{
        return ProfileRepositoryImpl(profileService,gson)
    }
}