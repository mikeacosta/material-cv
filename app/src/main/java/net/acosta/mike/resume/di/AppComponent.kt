package net.acosta.mike.resume.di

import dagger.Component
import net.acosta.mike.resume.ui.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class,
    ViewModelModule::class, RoomModule::class, UtilsModule::class])
interface AppComponent {
    fun inject(target: JobsFragment)
    fun inject(target: JobActivity)
    fun inject(target: CredsFragment)
    fun inject(target: AboutFragment)
    fun inject(target: CodeFragment)
    fun inject(target: ProfileFragment)
    fun inject(target: InfoFragment)
    fun inject(target: SplashActivity)
}