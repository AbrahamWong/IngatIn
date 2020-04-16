package com.example.ingatin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

// Credits: Coding In Flow -> https://youtu.be/IfpRL2K1hJk
public class AlertMode_BottomSheetDialog extends BottomSheetDialogFragment {
    private Listener listen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_alertmode, container, false);

        TextView isRing = v.findViewById(R.id.alertMode_ring);
        isRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen.onClick("Ring");
                dismiss();
            }
        });

        TextView isVibrate = v.findViewById(R.id.alertMode_Vibrate);
        isVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen.onClick("Vibrate");
                dismiss();
            }
        });

        TextView isBoth = v.findViewById(R.id.alertMode_ring_vibrate);
        isBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen.onClick("Ring & Vibrate");
                dismiss();
            }
        });

        return v;
    }

    public interface Listener{
        void onClick(String s);
    }

    // To attach bottom sheet to activity

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listen = (Listener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement Listener");
        }

    }
}
