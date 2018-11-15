package com.radionov.rxkata.person.favorites

import com.radionov.rxkata.person.types.PersonWithAddress
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import org.junit.Test

class FavoritesRepositoryTest {

    @Test
    fun loadPersons() {

        val favs = PublishSubject.create<Set<Int>>()

        val db = object : FavoritesDatabase {

            override fun favoriteContacts() =
                    favs.doOnNext { println("Favorite contacts changed: $it") }
        }


        val subscriber = TestObserver<List<PersonWithAddress>>()

        val repo = FavoritesRepository(TestPersonBackend(), db)
        repo.loadFavorites().subscribeWith(subscriber)
        favs.onNext(emptySet())
        Thread.sleep(500)
        favs.onNext(setOf(1))
        Thread.sleep(200)
        favs.onNext(emptySet())
        favs.onNext(setOf(1,2))
        favs.onComplete()

        subscriber.awaitTerminalEvent()

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        Assert.assertEquals(listOf(
                emptyList(),
                listOf(PersonWithAddress(TestPersonBackend.PERSON_DATA[0], TestPersonBackend.ADDRESS_DATA[1]!!)),
                emptyList(),
                listOf(
                        PersonWithAddress(TestPersonBackend.PERSON_DATA[0], TestPersonBackend.ADDRESS_DATA[1]!!),
                        PersonWithAddress(TestPersonBackend.PERSON_DATA[1], TestPersonBackend.ADDRESS_DATA[2]!!)
        )),
                subscriber.values())

    }

}