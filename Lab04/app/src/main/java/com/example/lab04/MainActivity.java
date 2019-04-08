package com.example.lab04;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // Main activity objects
    EditText mEditText;
    Button mSendTxtBtn;
    Button mChoosePhotoBtn;
    Button mSendPhotoBtn;
    Button mTakePhotoBtn;
    TextView mImageTitle;
    ImageView mDisplayImage;

    // Image things
    ArrayList<Uri> imageUris = new ArrayList<>();
    Uri imageUri;
    final int CAMERA_REQUEST  = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Assign main activity objects
        mEditText = (EditText) findViewById(R.id.main_EditText);
        mSendTxtBtn = (Button) findViewById(R.id.main_sndTxtBtn);
        mChoosePhotoBtn = (Button) findViewById(R.id.main_chooseBtn);
        mSendPhotoBtn = (Button) findViewById(R.id.main_sendPhotoBtn);
        mTakePhotoBtn = (Button) findViewById(R.id.main_takePhotoBtn);
        mImageTitle = (TextView) findViewById(R.id.main_imageTitle);
        mImageTitle.setVisibility(View.INVISIBLE);
        mDisplayImage = (ImageView) findViewById(R.id.main_displayImage);

        // Button actions
        mChoosePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        mSendPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(imageUris == (null))
                    Toast.makeText(MainActivity.this, "No picture selected!", Toast.LENGTH_LONG).show();
                else
                    sendPicture();
            }
        });

        mSendTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendTxt = mEditText.getText().toString();
                if(sendTxt.equals(""))
                    Toast.makeText(MainActivity.this, "Text field cannot be empty.", Toast.LENGTH_LONG).show();
                else
                    sendText(sendTxt);
            }
        });
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
    }

    private void sendPicture()
    {
        if(imageUris.size() == 1)
        {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUris.get(0));
            shareIntent.setType("image/jpeg");
            startActivity(shareIntent);
        }
        else if(imageUris.size() > 1)
        {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            shareIntent.setType("image/*");
            startActivity(shareIntent);
        }

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImageTitle.setVisibility(View.VISIBLE);
            mDisplayImage.setImageBitmap(photo);
        }

        else if(resultCode == RESULT_OK)
        {
            try
            {
                imageUris.add(data.getData());
                final InputStream imageStream = getContentResolver().openInputStream(imageUris.get(0));
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mDisplayImage.setImageBitmap(selectedImage);
                mImageTitle.setVisibility(View.VISIBLE);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void sendText(String sendText)
    {
        Intent sendTxtIntent = new Intent();
        sendTxtIntent.setAction(Intent.ACTION_SEND);
        sendTxtIntent.putExtra(Intent.EXTRA_TEXT, sendText);
        sendTxtIntent.setType("text/plain");
        startActivity(sendTxtIntent);

        Log.i("SND-PHOTO-BTN", "started sendTxtIntent activity.");
    }
}
