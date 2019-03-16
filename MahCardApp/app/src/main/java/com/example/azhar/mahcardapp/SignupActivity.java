package com.example.azhar.mahcardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignupActivity extends AppCompatActivity {
    EditText fullName, email, userName, phoneNo, password;
    Spinner countryCode, countryName, cityName;
    TextView signinTv;
    Button register;
    String Fullname, Email, Username, Phoneno, Password, Countrycode, Countryname, Cityname;
    String URL="https://mahcard.org/api/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullName=(EditText)findViewById(R.id.edt_txt_as_fullname);
        email=(EditText)findViewById(R.id.edt_txt_as_email);
        userName=(EditText)findViewById(R.id.edt_txt_as_username);
        phoneNo=(EditText)findViewById(R.id.edt_txt_as_phone);
        password=(EditText)findViewById(R.id.edt_txt_password);
        countryCode=(Spinner)findViewById(R.id.spinner_country_code);
        countryName=(Spinner)findViewById(R.id.spinner_country_name);
        cityName=(Spinner)findViewById(R.id.spinner_city_name);
        signinTv=(TextView)findViewById(R.id.signin_tv);
        register=(Button)findViewById(R.id.register_btn);

        authrization();

        signinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getValues(){
        Fullname=fullName.getText().toString();
        Email=email.getText().toString();
        Username=userName.getText().toString();
        Phoneno=phoneNo.getText().toString();
        Password=password.getText().toString();
        Countrycode=countryCode.getSelectedItem().toString();
        Countryname=countryName.getSelectedItem().toString();
        Cityname=cityName.getSelectedItem().toString();
    }

    private void authrization(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                if (Fullname.isEmpty()){
                    fullName.setError("Please enter fullname");
                }else if (Email.isEmpty()){
                    email.setError("Please enter email");
                }else if (!Email.contains("@")){
                    email.setError("Please enter valid email");
                }else if (Username.isEmpty()){
                    userName.setError("Please enter username");
                }else if (Phoneno.isEmpty()){
                    phoneNo.setError("Please enter phone no");
                }else if (Password.isEmpty()){
                    password.setError("Please enter password");
                }else if (Password.length()<6){
                    password.setError("Password must be atleast 6 characters.");
                } else{

                    StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                           try {
                               JSONObject object=new JSONObject(response);
                               int status=object.getInt("status");
                               if (status==1){
                                   Toast.makeText(SignupActivity.this,"Register Success",Toast.LENGTH_SHORT).show();
                               }
                               if (status==0){
                                   String message=object.getString("message");
                                   Toast.makeText(SignupActivity.this,message,Toast.LENGTH_SHORT).show();
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error","Some error occured");
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param=new HashMap<>();
                            param.put("name",Fullname);
                            param.put("username",Username);
                            param.put("email",Email);
                            param.put("password",Password);
                            param.put("password_confirmation",Password);
                            param.put("country",Countryname);
                            param.put("city",Cityname);
                            param.put("mobile",Countrycode+Phoneno);
                            return param;
                        }

                    };

                    RequestQueue requestQueue= Volley.newRequestQueue(SignupActivity.this);
                    requestQueue.add(request);
                }
            }
        });


    }
}
