package com.example.recyclerviewlab.sectionList

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PeopleListRepository @Inject constructor(
    private val service: PeopleListService,
    private val mapper: PeopleListMapper
) {
    suspend fun getPeopleLists(): Flow<Result<List<People>>> =
        service.fetchPeopleLists().map {
            if (it.isSuccess) {
                Result.success(mapper(it.getOrNull()!!))
            }
            else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
}
