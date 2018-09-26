package br.com.rafael.marvelcollections.common

import android.app.Application
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.di.DaggerMainComponent
import br.com.rafael.marvelcollections.di.MainComponent
import br.com.rafael.marvelcollections.di.characters.CharactersModule
import br.com.rafael.marvelcollections.di.characters.CharactersSubComponent
import br.com.rafael.marvelcollections.di.modules.AppModule
import br.com.rafael.marvelcollections.di.modules.DataModule
import br.com.rafael.marvelcollections.di.modules.NetworkModule
import com.squareup.leakcanary.LeakCanary

class App : Application() {

    lateinit var mainComponent: MainComponent
    private var charactersSubComponent: CharactersSubComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule(getString(R.string.api_base_url), getString(R.string.public_api_key), getString(R.string.private_api_key)))
                .dataModule(DataModule())
                .build()
    }

    fun createCharactersComponent(): CharactersSubComponent {
        charactersSubComponent = mainComponent.plus(CharactersModule())
        return charactersSubComponent!!
    }

    fun releaseCharactersComponent() {
        charactersSubComponent = null
    }
}