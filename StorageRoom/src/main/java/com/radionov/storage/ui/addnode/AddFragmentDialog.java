package com.radionov.storage.ui.addnode;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.radionov.storage.R;
import com.radionov.storage.app.App;
import com.radionov.storage.viewmodels.AddNodeViewModel;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragmentDialog extends AppCompatDialogFragment {

    public static final String TAG = AddFragmentDialog.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AddNodeViewModel addNodeViewModel;
    private EditText inputView;
    private Button addNodeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        inputView = rootView.findViewById(R.id.et_value);
        addNodeBtn = rootView.findViewById(R.id.btn_add);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onAttach(Context context) {
        App.from(context).getAppComponent().inject(this);
        super.onAttach(context);
    }

    private void init() {
        addNodeViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(AddNodeViewModel.class);

        addNodeBtn.setOnClickListener(v -> {
            String stringValue = inputView.getText().toString();
            try {
                int value = Integer.parseInt(stringValue);
                addNodeViewModel.addNode(value);
                dismiss();
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(),
                        "Wrong input! Only decimal numbers!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
