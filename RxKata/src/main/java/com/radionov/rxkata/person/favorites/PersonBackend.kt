package com.radionov.rxkata.person.favorites

import com.radionov.rxkata.person.types.PersonWithAddress
import io.reactivex.Observable

internal interface PersonBackend {
    fun loadAllPersons(): Observable<List<PersonWithAddress>>
}
