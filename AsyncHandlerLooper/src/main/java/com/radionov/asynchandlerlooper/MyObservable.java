package com.radionov.asynchandlerlooper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * @author Andrey Radionov
 */
public class MyObservable<T> {

    private static final String TAG = MyObservable.class.getSimpleName();

    private Looper subscribeOnLooper;
    private Looper observeOnLooper;
    private Callable<T> callable;
    private boolean needsToQuitSubscribeLooper;

    private MyObservable(Callable<T> callable) {
        this.callable = callable;
    }

    public interface Callback<T> {
        void onResult(T result);
    }

    public interface Callable<T> {
        T call();
    }

    public static <T> MyObservable<T> from(Callable<T> callable) {
        if (callable == null) {
            throw new IllegalArgumentException("Callable argument can't be null!");
        }
        return new MyObservable<>(callable);
    }

    public MyObservable<T> subscribeOn(Looper looper) {
        subscribeOnLooper = looper;
        return this;
    }

    public MyObservable<T> observeOn(Looper looper) {
        observeOnLooper = looper;
        return this;
    }

    public void subscribe(Callback<T> callback) {
        prepareLoopers();
        Handler observeOnHandler = prepareObserveOnHandler(callback);
        Handler subscribeOnHandler = prepareSubscribeOnHandler(observeOnHandler);
        subscribeOnHandler.sendEmptyMessage(0);
    }

    private void prepareLoopers() {
        if (subscribeOnLooper == null && Looper.myLooper() == null) {
            Looper.prepare();
            Looper.loop();
            needsToQuitSubscribeLooper = true;
            subscribeOnLooper = Looper.myLooper();
        }

        if (observeOnLooper == null) {
            observeOnLooper = subscribeOnLooper;
        }
    }

    private Handler prepareObserveOnHandler(final Callback<T> callback) {
        return new Handler(observeOnLooper, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "ObserveOn thread: " + Thread.currentThread().getName());
                T result = (T) msg.obj;
                callback.onResult(result);
                return true;
            }
        });
    }

    private Handler prepareSubscribeOnHandler(final Handler observeHandler) {
        return new Handler(subscribeOnLooper, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "SubscribeOn thread: " + Thread.currentThread().getName());
                try {
                    T result = callable.call();
                    Message resultMsg = new Message();
                    resultMsg.obj = result;
                    observeHandler.sendMessage(resultMsg);
                } catch (Exception e) {
                    Log.d(TAG, "SubscribeOn handleMessage error: " + e.getMessage());
                    return false;
                }
                quitSubscribeLooper();
                return true;
            }
        });
    }

    private boolean isNotMainLooper(Looper looper) {
        return looper != Looper.getMainLooper();
    }

    private void quitSubscribeLooper() {
        if (isNotMainLooper(subscribeOnLooper) && needsToQuitSubscribeLooper) {
            subscribeOnLooper.quit();
        }
    }
}
