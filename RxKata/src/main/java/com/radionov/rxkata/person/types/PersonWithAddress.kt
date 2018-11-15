package com.radionov.rxkata.person.types

import com.radionov.rxkata.person.types.Address
import com.radionov.rxkata.person.types.Person

data class PersonWithAddress(
        val person: Person,
        val address: Address
)