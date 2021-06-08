package com.sedavnyh.foody

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Перезапись класса приложения для включения Hilt
@HiltAndroidApp
class MyApplication: Application() {
}