package com.example.azhar.mahcardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText userName, userPassword;
    TextView register_txt;
    Button userLogin;
    String Username, Userpassword;
    String URL="https://mahcard.org/api/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName=(EditText)findViewById(R.id.edt_txt_as_name);
        userPassword=(EditText)findViewById(R.id.edt_txt_as_password);
        register_txt=(TextView)findViewById(R.id.signup_txt);
        userLogin=(Button)findViewById(R.id.signin_btn);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username=userName.getText().toString();
                Userpassword=userPassword.getText().toString();
                if (Username.isEmpty()){
                    userName.setError("Please enter username first.");
                }else if (Userpassword.isEmpty()){
                    userPassword.setError("Please enter password first.");
                }else {
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.getInt("status");
                                if (status == 1) {
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                }
                                if (status == 0) {
                                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", "Some error occured");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("username", userName.getText().toString());
                            param.put("password", userPassword.getText().toString());
                            return param;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    requestQueue.add(request);

                }
            }
        });

        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
