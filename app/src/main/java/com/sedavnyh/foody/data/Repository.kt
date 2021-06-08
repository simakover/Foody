package com.sedavnyh.foody.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

// Репозиторий для работы с данными из апи и базы данных. Предоствляет данные для вью модели
// инжект дата сурсов и видимость на уровни жизни активити, переживая смену конфигурации
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {

    val remote = remoteDataSource
}