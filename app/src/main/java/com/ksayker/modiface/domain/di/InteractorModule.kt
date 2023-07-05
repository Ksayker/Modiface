package com.ksayker.modiface.domain.di

import com.ksayker.modiface.domain.interactor.editimage.EditImageInteractor
import com.ksayker.modiface.domain.interactor.editimage.EditImageInteractorImpl
import com.ksayker.modiface.domain.interactor.imagelist.ImageListInteractor
import com.ksayker.modiface.domain.interactor.imagelist.ImageListInteractorImpl
import com.ksayker.modiface.domain.interactor.randominmage.RandomImageInteractor
import com.ksayker.modiface.domain.interactor.randominmage.RandomImageInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun bindImageListInteractor(interactor: ImageListInteractorImpl): ImageListInteractor

    @Binds
    fun bindEditImageInteractor(interactor: EditImageInteractorImpl): EditImageInteractor

    @Binds
    fun bindRandomImageInteractor(interactor: RandomImageInteractorImpl): RandomImageInteractor
}