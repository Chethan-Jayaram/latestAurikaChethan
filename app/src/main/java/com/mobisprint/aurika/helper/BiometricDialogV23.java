package com.mobisprint.aurika.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;

import androidx.annotation.NonNull;

public class BiometricDialogV23 extends BottomSheetDialog implements View.OnClickListener {

    private Context context;

    private Button btnCancel;
    private ImageView imgLogo;
    private CancellationSignal cancellationSignal;
    private TextView itemTitle, itemDescription, itemSubtitle, itemStatus;



    public BiometricDialogV23(@NonNull Context context,   CancellationSignal cancellationSignal) {
        super(context, R.style.BottomSheetDialogTheme);
        this.context = context.getApplicationContext();
        this.cancellationSignal=cancellationSignal;
        setDialogView();
    }


    public BiometricDialogV23(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BiometricDialogV23(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setDialogView() {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.view_bottom_sheet, null);
        setContentView(bottomSheetView);

        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        imgLogo = findViewById(R.id.img_logo);
        itemTitle = findViewById(R.id.item_title);
        itemSubtitle = findViewById(R.id.item_subtitle);
        itemDescription = findViewById(R.id.item_description);

        updateLogo();
    }

    public void setTitle(String title) {
        itemTitle.setText(title);
    }

    public void updateStatus(String status) {
        itemStatus.setText(status);
    }

    public void setSubtitle(String subtitle) {
        itemSubtitle.setText(subtitle);
    }

    public void setDescription(String description) {
        itemDescription.setText(description);
    }

    public void setButtonText(String negativeButtonText) {
        btnCancel.setText(negativeButtonText);
    }

    private void updateLogo() {
        try {
            Drawable drawable = getContext().getPackageManager().getApplicationIcon(context.getPackageName());
            imgLogo.setImageDrawable(drawable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        dismiss();
        cancellationSignal.cancel();
    }
}
