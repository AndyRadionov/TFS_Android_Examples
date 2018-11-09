package com.radionov.asynchandlerlooper;

import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author Andrey Radionov
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tv_message);

        MyObservable.Callable<String> callable = new MyObservable.Callable<String>() {
            @Override
            public String call() {
                ThreadUtils.sleep(3000);
                return "Hello Tinkoff!";
            }
        };

        HandlerThread backgroundThread = new HandlerThread("backgroundThread");
        backgroundThread.start();

        MyObservable.from(callable)
                .observeOn(Looper.getMainLooper())
                .subscribeOn(backgroundThread.getLooper())
                .subscribe(new MyObservable.Callback<String>() {
                    @Override
                    public void onResult(String result) {
                        tvMessage.setText(result);
                    }
                });
    }
}