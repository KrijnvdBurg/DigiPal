package gsc.DigiPal.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gsc.DigiPal.R;
import gsc.DigiPal.app.AppConfig;
import gsc.DigiPal.app.AppController;
import gsc.DigiPal.helper.SQLiteHandler;
import gsc.DigiPal.helper.SessionManager;


public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText firstName_editText;
    private EditText lastName_editText;
    private EditText username_editText;
    private EditText password_editText;
    private EditText passwordRE_editText;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName_editText = (EditText) findViewById(R.id.firstName_editText);
        lastName_editText = (EditText) findViewById(R.id.lastName_editText);
        username_editText = (EditText) findViewById(R.id.username_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String firstName_editText_string = firstName_editText.getText().toString().trim();
                String lastName_editText_string = firstName_editText.getText().toString().trim();
                String username_editText_string = firstName_editText.getText().toString().trim();
                String password_editText_string = firstName_editText.getText().toString().trim();

                if (!firstName_editText_string.isEmpty() || !lastName_editText_string.isEmpty() || !username_editText_string.isEmpty() || password_editText_string.isEmpty()) {
                    registerUser(firstName_editText_string, lastName_editText_string, username_editText_string, password_editText_string);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details correctly!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void registerUser(final String firstName, final String lastName, final String username, final String password) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONObject user = jObj.getJSONObject("user");
                        int user_id = user.getInt("user_id");
                        String username = user.getString("username");
                        String firsNname = user.getString("firstname");
                        String lasNname = user.getString("lastname");

                        db.addUser(user_id, username, firsNname, lasNname);
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
