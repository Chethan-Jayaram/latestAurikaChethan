package com.mobisprint.aurika.coorg.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.ProfileController;
import com.mobisprint.aurika.coorg.modle.ProfileModle;
import com.mobisprint.aurika.coorg.pojo.profile.Profile;
import com.mobisprint.aurika.coorg.pojo.profile.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.ProfileApiListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements ProfileApiListener {

    private TextView toolbar_title,tv_my_birthday,tv_dog_birthday,tv_my_anniversary,my_birthday,my_anniversary,my_dog_bday;
    private EditText mobile_num,email_id,first_name,last_name,lyt_birthday,lyt_anniversary,lyt_dog_birthday;
    private ImageView img_back,img_ph_num_clear,img_email_clear;
    private String[] gender = {"Male","Female","Other"};
    private String[] maritual_status = {"Single","Married"};
    private Spinner marital_status_spinner,gender_spinner;
    private static final int PICK_IMAGE = 1;
    private CircleImageView img_profile;
    private Context mContext;
    private RelativeLayout lyt_img_profile;
    private ProfileController profileController;
    private Button btn_update_profile;
    private Bitmap selectedImage1, bitmap;
    private ProfileModle profileModle = new ProfileModle();
    private DatePicker datePicker;
    private Calendar myCalendar;
    private int year, month, day;
    private int SELECT_PICTURE = 200;
    private Uri uri;
    private String stringUri;
    private ArrayAdapter arrayAdapter,arrayAdapter1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        tv_my_birthday = view.findViewById(R.id.txt_date);
        tv_dog_birthday = view.findViewById(R.id.txt_date2);
        tv_my_anniversary = view.findViewById(R.id.txt_date1);
        btn_update_profile = view.findViewById(R.id.btn_update_profile);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Profile");
        lyt_birthday = view.findViewById(R.id.lyt_birthday);
        lyt_anniversary = view.findViewById(R.id.lyt_anniversary);
        lyt_dog_birthday = view.findViewById(R.id.lyt_dog_birthday);
        my_birthday = view.findViewById(R.id.txt_date);
        my_anniversary = view.findViewById(R.id.txt_date1);
        my_dog_bday = view.findViewById(R.id.txt_date2);

        first_name = view.findViewById(R.id.first_name);
        last_name = view.findViewById(R.id.last_name);

        myCalendar = Calendar.getInstance();
        year = myCalendar.get(Calendar.YEAR);
        month = myCalendar.get(Calendar.MONTH);
        day = myCalendar.get(Calendar.DAY_OF_MONTH);


        img_profile = view.findViewById(R.id.img_profile);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        /*img_email_clear = view.findViewById(R.id.img_clear_email);
        img_ph_num_clear = view.findViewById(R.id.img_clear_mobile);*/
        mContext = getContext();
        lyt_img_profile = view.findViewById(R.id.lyt_img_profile);

        gender_spinner = view.findViewById(R.id.gender_spinner);
        marital_status_spinner = view.findViewById(R.id.marital_status_spinner);

        email_id = view.findViewById(R.id.email_id);
        mobile_num = view.findViewById(R.id.mobile_num);

        profileController = new ProfileController(this);


        email_id.setEnabled(false);
        mobile_num.setEnabled(false);

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
            //selectImage(mContext);

            imageChooser();
        });






        arrayAdapter = new ArrayAdapter(getContext(),R.layout.profile_spinner_drop_down,gender);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender_spinner.setAdapter(arrayAdapter);


        arrayAdapter1 = new ArrayAdapter(getContext(),R.layout.profile_spinner_drop_down,maritual_status);
        arrayAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
       // ViewCompat.setBackground(marital_status_spinner, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        marital_status_spinner.setAdapter(arrayAdapter1);

        profileController.getProfile();





        lyt_birthday.setOnClickListener(v -> {
            openDatePickerDialog(v,"my_bday");
        });

        lyt_anniversary.setOnClickListener(v -> {
            openDatePickerDialog(v,"my_anniversary");
        });

        lyt_dog_birthday.setOnClickListener(v -> {
            openDatePickerDialog(v,"my_dog_bday");
        });

        btn_update_profile.setOnClickListener(v -> {

            profileModle.setFirstName(first_name.getText().toString().trim());
            profileModle.setLastName(last_name.getText().toString().trim());
            profileModle.setGender(gender_spinner.getSelectedItem().toString());
            profileModle.setMaritalStatus(marital_status_spinner.getSelectedItem().toString());
            profileModle.setDateOfBirth(my_birthday.getText().toString());
            profileModle.setDateOfAnniversary(my_anniversary.getText().toString());
            profileModle.setDateOfDogsBirth(my_dog_bday.getText().toString());
            profileModle.setEmail(email_id.getText().toString().trim());
            profileModle.setContactNumber(mobile_num.getText().toString().trim());
            if (stringUri!=null && !stringUri.isEmpty()){
                profileModle.setImage(stringUri);
                Log.d("sent_image",stringUri);
            }
            profileController.updateProfile(profileModle);


            /*if (selectedImage1!=null){
                profileController.updateProfile(
                        first_name.getText().toString(),
                        last_name.getText().toString(),
                        gender_spinner.getSelectedItem().toString(),
                        marital_status_spinner.getSelectedItem().toString(),
                        tv_my_birthday.getText().toString(),
                        tv_my_anniversary.getText().toString(),
                        tv_dog_birthday.getText().toString(),
                        BitMapToString(selectedImage1)
                        );
            }
*/



        });

        return  view;
    }

    private void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    private void openDatePickerDialog(View v, String my_bday) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    switch (my_bday){
                        case "my_bday":
                            my_birthday.setText(selectedDate);
                            break;
                        case "my_anniversary":
                            my_anniversary.setText(selectedDate);
                            break;
                        case "my_dog_bday":
                            my_dog_bday.setText(selectedDate);
                            break;
                    }

                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();
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


    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                 uri = data.getData();
                if (null != uri) {
                    // update the preview image in the layout
                    img_profile.setImageURI(uri);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringUri = GlobalClass.encodeTobase64(bitmap);

                }
            }
        }
    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        selectedImage1 = selectedImage;
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
                                            selectedImage1 = bitmap;
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
    }*/


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response, String responseType) {
        if (response.body()!=null){

            if (responseType.equalsIgnoreCase("getProfile")){
                getProfile(response);
            }else if (responseType.equalsIgnoreCase("updateProfile")){
                getProfile(response);
                GlobalClass.ShowAlert(getContext(),"Alert","Profile updated");
                /*Fragment fragment1 = new HomeFragment();
                Bundle bundle = new Bundle();
                fragment1.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment1).addToBackStack(null).commit();
*/
            }



        }
    }



    private <ResponseType> void getProfile(Response<ResponseType> response) {

        Profile profile = (Profile) response.body();
        Data list = profile.getData();
        first_name.setText(list.getFirstName());
        last_name.setText(list.getLastName());
        email_id.setText(list.getEmail());
        mobile_num.setText(list.getContactNumber());

        Log.d("recieved_image",list.getImage());



        if (list.getImage()!=null && !list.getImage().isEmpty()){
            Glide.with(this).load(list.getImage()).into(img_profile);

        }

        if (list.getGender()!=null && !list.getGender().isEmpty()){
            int spinnerPosition = arrayAdapter.getPosition(list.getGender());
            gender_spinner.setSelection(spinnerPosition);
        }


        if (list.getMaritalStatus()!=null && !list.getMaritalStatus().isEmpty()){
            int spinnerPosition1 = arrayAdapter1.getPosition(list.getMaritalStatus());
            marital_status_spinner.setSelection(spinnerPosition1);
        }

        if (list.getDateOfBirth()!=null && !list.getDateOfBirth().isEmpty()){
            tv_my_birthday.setText(list.getDateOfBirth());
        }

        if (list.getDateOfAnniversary()!=null && !list.getDateOfAnniversary().isEmpty()){
            tv_my_anniversary.setText(list.getDateOfAnniversary());
        }

        if (list.getDateOfDogsBirth()!=null && !list.getDateOfDogsBirth().isEmpty()){
            tv_dog_birthday.setText(list.getDateOfDogsBirth());
        }
    }



    @Override
    public void onFetchError(String error) {

    }

    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}