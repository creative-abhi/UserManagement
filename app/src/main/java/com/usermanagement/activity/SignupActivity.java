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
import com.usermanagement.model.SignupUser;
import com.usermanagement.modelresponse.LoginResponse;
import com.usermanagement.presenter.SignupPresenter;
import com.usermanagement.viewcontractor.SignupContract;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, SignupContract.View {
private EditText username, email, password;
private Button signup;
private TextView locate;
private SignupContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        presenter = new SignupPresenter(this, getApplicationContext());
        username = findViewById(R.id.edtUsername);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        signup = findViewById(R.id.btnSignup);
        locate = findViewById(R.id.txtlogin);
        signup.setOnClickListener(this);
        locate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignup:
            String Username = username.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();
            SignupUser user = new SignupUser(Username,Email,Password);
            if (isValidate(user)) {
                presenter.onRegister(user);
            }
            break;
            case R.id.txtlogin:
            presenter.onlocateToSignin();
            break;
        }
    }

    @Override
    public void onSuccess(String result) {
        Toast.makeText(this, result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidate(SignupUser user){
        boolean status = false;
        if (TextUtils.isEmpty(user.getUsername())){
            username.requestFocus();
            username.setError("Please Enter username");
            status = false;
        }else if (TextUtils.isEmpty(user.getEmail())){
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
            password.setError("Please Enter Strong password of minimum 8 digits");
            status = false;
        }else {
            status = true;
        }
        return status;
    }
}