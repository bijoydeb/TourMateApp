package com.summons.tourmateapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.summons.tourmateapp.Database.SignUpManager;
import com.summons.tourmateapp.Model.SignUp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    ImageView imageView;
    EditText fullNameEditText;
    EditText userNameEditText;
    EditText passwordEditText;
    EditText eNumberEditText;
    EditText addressEditText;

    SignUpManager signUpManager;
    SignUp signUp;

    TextView choseTextView;

    private String savedImageURL = "";
    private File file;
    private static File dir = null;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.context = this;
        idReference();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SignUp");

        signUpManager = new SignUpManager(context);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                android.app.FragmentManager manager =SignUpActivity.this.getFragmentManager();
//                ImageDialog dialog = new ImageDialog();
//                dialog.show(manager, "image_Dialog");
                imageResource();
            }
        });


    }

    private void idReference() {
        imageView = (ImageView) findViewById(R.id.imageView);
        fullNameEditText = (EditText) findViewById(R.id.fullNameEditText);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        eNumberEditText = (EditText) findViewById(R.id.eNumberEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        choseTextView = (TextView) findViewById(R.id.choseTextView);
    }

    public void signUp(View view) {
        String fullName = fullNameEditText.getText().toString().trim();
        String userName = userNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String eNumber = eNumberEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (fullName.equals("")) {
            Toast.makeText(context, "Enter Full Name !", Toast.LENGTH_SHORT).show();
        } else if (userName.equals("")) {
            Toast.makeText(context, "Enter User Name !", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(context, "Enter Password !", Toast.LENGTH_SHORT).show();
        } else {
            if (!signUpManager.userNameExits(userName).equals("0")) {
                Toast.makeText(context, "UserName Already Exits !", Toast.LENGTH_SHORT).show();
            } else {
                signUp = new SignUp(fullName, userName, password, eNumber, address, savedImageURL);
                long success = signUpManager.addRegInfo(signUp);
                if (success > 0) {
                    Toast.makeText(context, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, "SignUp Failed !", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //
//    @SuppressLint("ValidFragment")
//    private class ImageDialog extends DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
    public void imageResource() {
        AlertDialog alertDialog = null;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
        LayoutInflater inflater = SignUpActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.image_chose_layout, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        ImageView cameraButton = (ImageView) dialogView.findViewById(R.id.cameraButton);
        ImageView galleryButton = (ImageView) dialogView.findViewById(R.id.galleryButton);

        final AlertDialog finalAlertDialog = alertDialog;
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                finalAlertDialog.dismiss();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, GALLERY_REQUEST);
                finalAlertDialog.dismiss();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            final long time = System.currentTimeMillis();
            try {
                savedImageURL = saveBitmapIntoSdcard(bitmap, time + ".png");
                imageView.setImageBitmap(bitmap);
                choseTextView.setVisibility(View.GONE);
            } catch (final IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                final Uri selectedImageUri = data.getData();
                final String url__ = getPath(selectedImageUri);
                savedImageURL = url__;
                imageView.setImageURI(selectedImageUri);
                choseTextView.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public String getPath(Uri uri) {
        final String[] projection = {MediaStore.MediaColumns.DATA};
        final Cursor cursor = managedQuery(uri, projection, null, null, null);
        final int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String saveBitmapIntoSdcard(Bitmap bitmap22, String filename)
            throws IOException {

        createBaseDirectory();

        try {

            new Date();
            OutputStream out = null;
            file = new File(SignUpActivity.dir, "/" + filename);

            out = new FileOutputStream(file);

            bitmap22.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createBaseDirectory() {

        final String extStorageDirectory = Environment
                .getExternalStorageDirectory().toString();

        SignUpActivity.dir = new File(extStorageDirectory + "/TourMate");

        if (SignUpActivity.dir.mkdir()) {
            System.out.println("Directory created");
        } else {
            System.out.println("Directory is not created or exists");
        }
    }

}
