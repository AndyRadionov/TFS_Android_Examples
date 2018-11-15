package com.radionov.rxkata.person.search

import io.reactivex.Observable
import com.radionov.rxkata.person.types.Person
import java.util.concurrent.TimeUnit

class SearchRepository(private val searchView: SearchView, private val personBackend: PersonBackend) {


    fun search(): Observable<List<Person>> {

        /**
         * Implement the search call according the following criteria:
         * - The search query string must contain at least 3 characters
         * - To save backend traffic: only search if search query hasn't changed within the last 300 ms
         * - If the user is typing fast "Hannes" and than deletes and types "Hannes" again (exceeding 300 ms) the search should not execute twice.
         */

        return searchView.onSearchTextchanged()
                .filter { it.length >= 3 }
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { personBackend.searchfor(it) }
                .filter { it.isNotEmpty() }
    }
}
