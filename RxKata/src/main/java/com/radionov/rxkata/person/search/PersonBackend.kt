package com.radionov.rxkata.person.search

import com.radionov.rxkata.person.types.Person
import io.reactivex.Observable

interface PersonBackend {

    fun searchfor(searchFor: String): Observable<List<Person>>
}
