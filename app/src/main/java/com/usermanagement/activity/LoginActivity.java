package com.usermanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.usermanagement.R;
import com.usermanagement.model.LoginUser;
import com.usermanagement.presenter.LoginPresenter;
import com.usermanagement.viewcontractor.LoginContract;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {
    private LoginContract.Presenter presenter;
    private EditText email, password;
    private Button login;
    private TextView locate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        presenter = new LoginPresenter(this,getApplicationContext());
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        login = findViewById(R.id.btnLogin);
        locate = findViewById(R.id.txtSignup);
        login.setOnClickListener(this);
        locate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnLogin:
                    String Email = email.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    LoginUser user = new LoginUser(Email, Password);
                    if (isValidate(user)==true) {
                        presenter.onLogin(user);
                    }
                    break;
                case R.id.txtSignup:
                    presenter.onLocateToSignup();
                    break;
            }
    }

    @Override
    public void onSuccess(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }

    public boolean isValidate(LoginUser user) {
        boolean status = false;
        if (TextUtils.isEmpty(user.getEmail())){
            email.requestFocus();
            email.setError("Please Enter email address");
            status = false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()){
            email.requestFocus();
            email.setError("Please Enter valid email address");
            status = false;
        }else if (TextUtils.isEmpty(user.getPassword())){
            password.requestFocus();
            password.setError("Please Enter password");
            status = false;
        }else if (user.getPassword().length()<7){
            password.requestFocus();
            password.setError("Please Enter strong password of minimum 8 digits");
            status = false;
        }else {
            status = true;
        }
        return status;
    }

}