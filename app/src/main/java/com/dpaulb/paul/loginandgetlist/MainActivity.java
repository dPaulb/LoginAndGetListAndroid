package com.dpaulb.paul.loginandgetlist;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button joinButton = (Button) findViewById(R.id.joinButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        Button imageButton = (Button) findViewById(R.id.imageButton);
        Button uploadButton = (Button) findViewById(R.id.uploadButton);



        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadIntent = new Intent(MainActivity.this, UploadActivity.class);
                MainActivity.this.startActivity(uploadIntent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(MainActivity.this, Image.class);
                MainActivity.this.startActivity(imageIntent);
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Join.class);
                MainActivity.this.startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                StringRequest jsonRequest = new StringRequest(Request.Method.POST,
                        "http://10.0.2.2:3000/login",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    //boolean isLogin = jsonResponse.getBoolean("isLogin");
                                    if(success){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("로그인 성공")
                                                .setPositiveButton("확인", null)
                                                .create()
                                                .show();
                                        Intent uploadIntent = new Intent(MainActivity.this, UploadActivity.class);
                                        MainActivity.this.startActivity(uploadIntent);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("로그인 실패")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("userID", userID);
                        parameters.put("userPassword", userPassword);
                        return parameters;
                    };
                };

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(jsonRequest);
            }
        });




    }
}
