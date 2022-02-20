package com.babakmhz.githubuserfindercompose.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber


fun String?.validString() = this != null && this.isNotEmpty()


fun CoroutineScope.launchWithException(
    errorLiveData: MutableLiveData<Throwable?>,
    loading: MutableLiveData<Boolean>,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(CoroutineExceptionHandler { _, throwable ->
        Timber.e("error in coroutineScope. reason $throwable")
        errorLiveData.postValue(throwable)
        loading.postValue(false)
    }, block = block)
}


