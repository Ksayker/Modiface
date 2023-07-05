package com.ksayker.modiface.presentation.di

import android.content.Context
import com.ksayker.modiface.core.annotation.AppContext
import com.ksayker.modiface.data.di.DatabaseModule
import com.ksayker.modiface.data.di.ProviderModule
import com.ksayker.modiface.data.di.RetrofitModule
import com.ksayker.modiface.data.di.SourceModule
import com.ksayker.modiface.domain.di.InteractorModule
import com.ksayker.modiface.presentation.fragment.editimage.EditImageFragment
import com.ksayker.modiface.presentation.fragment.editimage.EditImageViewModel
import com.ksayker.modiface.presentation.fragment.editphoto.EditPhotoFragment
import com.ksayker.modiface.presentation.fragment.editphoto.EditPhotoViewModel
import com.ksayker.modiface.presentation.fragment.imagelist.ImageListFragment
import com.ksayker.modiface.presentation.fragment.imagelist.ImageListViewModel
import com.ksayker.modiface.presentation.fragment.randomimage.RandomImageFragment
import com.ksayker.modiface.presentation.fragment.randomimage.RandomImageViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        SourceModule::class,
        DatabaseModule::class,
        ProviderModule::class,
        RetrofitModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: ImageListFragment)

    fun inject(fragment: EditImageFragment)

    fun inject(fragment: EditPhotoFragment)

    fun inject(fragment: RandomImageFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@AppContext context: Context): Builder

        fun build(): ApplicationComponent
    }
}