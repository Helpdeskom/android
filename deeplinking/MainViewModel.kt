package com.orange.flowchecklist.ui.activity.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.work.*
import com.orange.flowchecklist.network.model.CommonResponse
import com.orange.flowchecklist.network.remote.repository.ApiRepository
import com.orange.flowchecklist.network.remoteUtils.NetworkResult
import com.orange.flowchecklist.utils.helpers.workManagerHelpers.WorkerHelperClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val response = Channel<NetworkResult<CommonResponse>>(Channel.BUFFERED)
    val callResponse = response.receiveAsFlow()
    val dogImageUrl = ObservableField<String>()
    val showShimmer = ObservableField(true)
    private lateinit var workerId: UUID

    /**
     * Method for fetch dogs images
     **/
    fun fetchDogsImagesResponse() = viewModelScope.launch {
        apiRepository.getDog()
            .onStart {
                response.send(NetworkResult.Loading())
            }
            .catch { e ->
                response.send(NetworkResult.Error(e.message ?: e.toString()))
            }
            .collect {
                response.send(it)
            }
    }

    /**
     * Method for get users data
     **/
//    fun getUsersData() = apiRepository.getUsers()
//        .cachedIn(viewModelScope)


    fun startWorker(): LiveData<WorkInfo> {
        val data = workDataOf("Name" to "String")
        val workRequest: WorkRequest =
            OneTimeWorkRequestBuilder<WorkerHelperClass>().setInputData(data).build()
        workManager.enqueue(workRequest)
        workerId = workRequest.id
        return workManager.getWorkInfoByIdLiveData(workRequest.id)
    }

    fun cancelWorker() {
        workManager.cancelWorkById(workerId)
    }
}
