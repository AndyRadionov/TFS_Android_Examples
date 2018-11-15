package com.radionov.rxkata.person.favorites

import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import com.radionov.rxkata.person.types.PersonWithAddress

internal class FavoritesRepository(private val personBackend: PersonBackend, private val favoritesDatabase: FavoritesDatabase) {


    fun loadFavorites(): Observable<List<PersonWithAddress>> {

        /**
         * Provide an observable that only emits a list of PersonWithAddress if they are marked as favorite ones.
         */

        return favoritesDatabase.favoriteContacts()
                .withLatestFrom(personBackend.loadAllPersons()) { t1, t2 ->
                    t2.filter { t1.contains(it.person.id) }
                }
    }
}
