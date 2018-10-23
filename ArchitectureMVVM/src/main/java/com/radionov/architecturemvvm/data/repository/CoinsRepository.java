package com.radionov.architecturemvvm.data.repository;

import android.arch.lifecycle.MutableLiveData;

import com.radionov.architecturemvvm.data.dto.CoinInfo;
import com.radionov.architecturemvvm.data.remote.Api;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author Andrey Radionov
 */
public class CoinsRepository {

    private Api apiService;
    private Call<List<CoinInfo>> apiSubscription;
    private MutableLiveData<List<CoinInfo>> coinsLiveData;

    public CoinsRepository(Api apiService) {
        this.apiService = apiService;
        coinsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<CoinInfo>> fetchCoinRates() {
        updateCoinRates();
        return coinsLiveData;
    }

    public void updateCoinRates() {
        cancelSubscription();
        apiSubscription = apiService.getCoinsList();
        apiSubscription
                .enqueue(new Callback<List<CoinInfo>>() {
                    @Override
                    public void onResponse(Call<List<CoinInfo>> call, Response<List<CoinInfo>> response) {
                        coinsLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<CoinInfo>> call, Throwable t) {
                        Timber.d(t);
                        coinsLiveData.setValue(Collections.emptyList());
                    }
                });
    }

    public void cancelSubscription() {
        if (apiSubscription != null && apiSubscription.isExecuted()) {
            apiSubscription.cancel();
        }
    }
}
