package com.dpaulb.paul.loginandgetlist;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class Image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        final ImageView imageView = (ImageView)findViewById(R.id.imageView);

        Button imageButton = (Button) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageRequest imageRequest = new ImageRequest("http://aboki.net/shopimages/aboki/0040030018283.jpg?10311649",
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                imageView.setImageBitmap(response);
                            }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                RequestQueue queue = Volley.newRequestQueue(Image.this);
                queue.add(imageRequest);
            }
        });
    }
}
