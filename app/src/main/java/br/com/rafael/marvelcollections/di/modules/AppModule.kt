package br.com.rafael.marvelcollections.di.modules

import android.content.Context
import br.com.rafael.marvelcollections.common.ImageLoader
import br.com.rafael.marvelcollections.common.PicassoImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(context: Context) {

    private val appContext = context.applicationContext

    @Singleton
    @Provides
    fun provideAppComponent(): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideImageLoader(context: Context): ImageLoader {
        return PicassoImageLoader(Picasso.with(context))
    }
}