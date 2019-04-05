package net.acosta.mike.resume

import android.app.Application
import net.acosta.mike.resume.di.*

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: App): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .roomModule(RoomModule(app))
                    .utilsModule(UtilsModule(app))
                    .build()
}