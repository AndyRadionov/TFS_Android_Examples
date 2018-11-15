package com.radionov.rxkata.person.favorites

import io.reactivex.Observable

internal interface FavoritesDatabase {

    /**
     * Returns the ids of the favorite contacts of the users.
     * Since it is an observable, it can change over time.
     *
     * @return
     */
    fun favoriteContacts(): Observable<Set<Int>>
}
