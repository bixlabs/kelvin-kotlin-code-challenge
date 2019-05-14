package com.safyah.realtimemap.mvp.views

interface BaseView<T> {
    fun createPresenter() : T
}