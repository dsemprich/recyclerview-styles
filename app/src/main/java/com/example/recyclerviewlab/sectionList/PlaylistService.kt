package com.example.recyclerviewlab.sectionList

import com.example.recyclerviewlab.backend.Backend
import com.example.recyclerviewlab.backend.sendAwait
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class PeopleListService @Inject constructor(
    private val api: Backend
) {
    suspend fun fetchPeopleLists(): Flow<Result<PeopleResult>> {
        return flow {
            emit(Result.success(api.sendAwait()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}