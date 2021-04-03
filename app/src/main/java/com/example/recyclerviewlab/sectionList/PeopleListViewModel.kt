package com.example.recyclerviewlab.sectionList

import androidx.lifecycle.*
import kotlinx.coroutines.flow.onEach

class PeopleListViewModel(
    private val repository: PeopleListRepository
): ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val peoples = liveData {
        loader.postValue(true)

        emitSource(repository.getPeopleLists()
            .onEach {
                loader.postValue(false)
            }.asLiveData())
    }
}
