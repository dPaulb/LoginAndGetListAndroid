package com.dpaulb.paul.loginandgetlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button chooseButton, uploadButton, cameraButton;
    private ProgressBar progressBar;

    public static String URL = "http://10.0.2.2:3000/upload";
    static final int REQ_SELECT = 1;
    static final int PICK_FROM_CAMERA = 0;
    static final int CROP_FROM_IMAGE = 2;

    private Uri imageCaptureUri;
    private String absolutePath;
    private Intent intent;
    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        imageView = (ImageView) findViewById(R.id.imageView);
        chooseButton = (Button) findViewById(R.id.chooseButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);


        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://media/external/images/media");
                //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                intent = new Intent(Intent.ACTION_VIEW, uri);
                //인텐트에 요청을 덧붙인다.
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //모든 이미지
                intent.setType("image/*");
                //결과값을 받아오는 액티비티를 실행한다.
                startActivityForResult(intent, REQ_SELECT);

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                        "http://10.0.2.2:3000/upload", new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                        builder.setMessage("업로드에 성공했습니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                        builder.setMessage("업로드에 실패했습니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                        params.put("name", "Angga");
                        params.put("location", "Indonesia");
                        params.put("about", "UI/UX Designer");
                        params.put("contact", "angga@email.com");
                        return params;
                    }

                    @Override
                    protected Map<String, DataPart> getByteData() throws AuthFailureError {
                        Map<String, DataPart> params = new HashMap<>();
                        // file name could found file base or direct access from real path
                        // for now just get bitmap data from ImageView
                        params.put("thumbnail", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), imageView.getDrawable()), "image/jpeg"));
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(UploadActivity.this);
                queue.add(volleyMultipartRequest);
            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) return;
        try {
            //인텐트에 데이터가 담겨 왔다면
            if (intent.getData() != null) {
                //해당경로의 이미지를 intent에 담긴 이미지 uri를 이용해서 Bitmap형태로 읽어온다.
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                //이미지의 크기 조절하기.
                selPhoto = Bitmap.createScaledBitmap(selPhoto, 100, 100, true);
                //image_bt.setImageBitmap(selPhoto);//썸네일
                //화면에 출력해본다.
                imageView.setImageBitmap(selPhoto);
                Log.e("선택 된 이미지 ", "selPhoto : " + selPhoto);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
