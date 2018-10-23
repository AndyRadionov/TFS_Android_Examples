package com.radionov.architecturemvvm.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.radionov.architecturemvvm.data.dto.CoinInfo;
import com.radionov.architecturemvvm.data.repository.CoinsRepository;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public class MainViewModel extends ViewModel {

    private CoinsRepository coinsRepository;
    private MutableLiveData<List<CoinInfo>> coinsLiveData;

    public MainViewModel(CoinsRepository repository) {
        coinsRepository = repository;
        coinsLiveData = coinsRepository.fetchCoinRates();
    }

    public MutableLiveData<List<CoinInfo>> getCoinsLiveData() {
        return coinsLiveData;
    }

    public void updateData() {
        coinsRepository.updateCoinRates();
    }

    @Override
    protected void onCleared() {
        coinsRepository.cancelSubscription();
    }
}
