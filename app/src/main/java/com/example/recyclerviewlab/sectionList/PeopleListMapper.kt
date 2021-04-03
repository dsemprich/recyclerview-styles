package com.example.recyclerviewlab.sectionList

import javax.inject.Inject

class PeopleListMapper @Inject constructor() : Function1<PeopleResult,List<People>> {
    override fun invoke(peopleRaw: PeopleResult): List<People> {
        return peopleRaw.results.map {
            People(
                it.login.uuid,
                it.name.first,
                it.name.last,
                it.dob.date,
                it.picture.medium,
                it.email,
                it.location.city,
                it.location.country
            )
        }
    }
}
