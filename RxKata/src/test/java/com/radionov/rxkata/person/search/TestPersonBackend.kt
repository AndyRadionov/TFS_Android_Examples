package com.radionov.rxkata.person.search

import com.radionov.rxkata.person.types.Person
import io.reactivex.Observable
import java.util.concurrent.CopyOnWriteArrayList

class TestPersonBackend : PersonBackend {

    private val PEOPLE = listOf(Person(1, "Franz", "Beckenbauer"),
            Person(2, "Franz", "Kafka"),
            Person(3, "Thomas", "Müller")
    )

    val queries = CopyOnWriteArrayList<String>()

    override fun searchfor(searchFor: String): Observable<List<Person>> = Observable.create { emmiter ->
        queries += searchFor
        println("backend: >> start searching for $searchFor")
        try {
            Thread.sleep(400)
        } catch (t : Throwable){
        }
        if (!emmiter.isDisposed) {
            println("backend << result for $searchFor")
            emmiter.onNext(search(searchFor))
            emmiter.onComplete()
        }
    }

    fun search(searchFor  : String) = PEOPLE.filter { it.firstname.startsWith(searchFor) }
}