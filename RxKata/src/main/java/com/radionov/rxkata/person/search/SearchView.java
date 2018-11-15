package com.radionov.rxkata.person.search;

import io.reactivex.Observable;

public interface SearchView {

    Observable<String> onSearchTextchanged();
}
