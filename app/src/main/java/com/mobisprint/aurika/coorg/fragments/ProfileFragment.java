package com.mobisprint.aurika.coorg.fragments;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobisprint.aurika.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    private TextView toolbar_title;
    private EditText mobile_num,email_id;
    private ImageView img_back,img_ph_num_clear,img_email_clear;
    private String[] gender = {"Male","Female","Other"};
    private String[] maritual_status = {"Single","Married"};
    private Spinner maritual_ststus_spinner,gender_spinner;
    private static final int PICK_IMAGE = 1;
    private CircleImageView img_profile;
    private Context mContext;
    private RelativeLayout lyt_img_profile;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Profile");

        img_profile = view.findViewById(R.id.img_profile);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        /*img_email_clear = view.findViewById(R.id.img_clear_email);
        img_ph_num_clear = view.findViewById(R.id.img_clear_mobile);*/
        mContext = getContext();
        lyt_img_profile = view.findViewById(R.id.lyt_img_profile);

        gender_spinner = view.findViewById(R.id.gender_spinner);
        maritual_ststus_spinner = view.findViewById(R.id.maritual_ststus_spinner);

        email_id = view.findViewById(R.id.email_id);
        mobile_num = view.findViewById(R.id.mobile_num);


//        mobile_num.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (!mobile_num.getText().toString().isEmpty()){
//                    img_ph_num_clear.setVisibility(View.VISIBLE);
//                }
//                else{
//                    img_ph_num_clear.setVisibility(View.GONE);
//                }
//                return false;
//            }
//        });
//
//        img_ph_num_clear.setOnClickListener(v -> {
//            mobile_num.getText().clear();
//        });
//
        lyt_img_profile.setOnClickListener(v -> {
            selectImage(mContext);
        });


        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.profile_spinner_drop_down,gender);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender_spinner.setAdapter(arrayAdapter);


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(),R.layout.profile_spinner_drop_down,maritual_status);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        maritual_ststus_spinner.setAdapter(arrayAdapter1);

        return  view;
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        img_profile.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                if (Build.VERSION.SDK_INT >= 29){
                                    Uri imageUri= ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0));

                                    try (ParcelFileDescriptor pfd =getContext().getContentResolver().openFileDescriptor(selectedImage, "r")) {
                                        if (pfd != null) {
                                            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                                            img_profile.setImageBitmap(bitmap);
                                            cursor.close();
                                        }
                                    } catch (IOException ex) {

                                    }
                                }else{

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    String picturePath = cursor.getString(columnIndex);
                                    img_profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                    cursor.close();
                                }

                            }
                        }

                    }
                    break;
            }
        }
    }
}