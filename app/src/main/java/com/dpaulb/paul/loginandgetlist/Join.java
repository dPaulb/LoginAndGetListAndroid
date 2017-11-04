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

public class Join extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final EditText idText = (EditText) findViewById(R.id.userID);
        final EditText passwordText = (EditText) findViewById(R.id.userPassword);
        final EditText nameText = (EditText) findViewById(R.id.userName);
        final EditText ageText = (EditText) findViewById(R.id.userAge);
        final Button joinButton = (Button) findViewById(R.id.joinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                final String userName = nameText.getText().toString();
                final String userAge = ageText.getText().toString();

                StringRequest jsonRequest = new StringRequest(Request.Method.POST,
                        "http://10.0.2.2:3000/join", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                        builder.setMessage("회원 가입에 성공했습니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                        builder.setMessage("회원 가입에 실패했습니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("userID", userID);
                        parameters.put("userPassword", userPassword);
                        parameters.put("userName", userName);
                        parameters.put("userAge", userAge);
                        return parameters;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(Join.this);
                queue.add(jsonRequest);
            }
        });

    }
}
