package com.example.minhaoca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhaoca.R;
import com.example.minhaoca.util.ConcreteInputValidator;
import com.example.minhaoca.util.InputValidator;

import static com.example.minhaoca.constants.InputValidationCode.FIELD_OK;
import static com.example.minhaoca.constants.InputValidationCode.LOGIN_CONTAINS_SPACES;
import static com.example.minhaoca.constants.InputValidationCode.LOGIN_IS_EMPTY;
import static com.example.minhaoca.constants.InputValidationCode.PASSWORD_CONTAINS_SPACES;
import static com.example.minhaoca.constants.InputValidationCode.PASSWORD_IS_EMPTY;
import static com.example.minhaoca.constants.InputValidationCode.PASSWORD_TOO_SHORT;

public class LoginActivity extends BaseActivity {

    private InputValidator inputValidator;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView loginWarning;
    private EditText loginField;
    private TextView passwordWarning;
    private EditText passwordField;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivityInitProcess();
    }

    @Override
    protected void onCreateViewInstances() {
        inputValidator = new ConcreteInputValidator();

        String title = getString(R.string.login);
        TextView activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText(title);

        progressBar = findViewById(R.id.progress_bar);

        toolbar = findViewById(R.id.toolbar);

        loginWarning = findViewById(R.id.login_warning);
        loginField = findViewById(R.id.login_field);

        passwordWarning = findViewById(R.id.password_warning);
        passwordField = findViewById(R.id.password_field);

        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);
    }

    @Override
    protected void onViewInit() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onBindViewMode() {
        toolbar.inflateMenu(R.menu.menu_user);
    }

    @Override
    protected void onActivityInitFinished() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = loginField.getText().toString();
                String password = passwordField.getText().toString();

                Integer loginResultCode = inputValidator.verifyLogin(login);
                Integer passwordResultCode = inputValidator.verifyPassword(password);

                updateWarningMessages(loginResultCode, passwordResultCode);

                if (loginResultCode.equals(FIELD_OK) && passwordResultCode.equals(FIELD_OK)) {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "OkBuddyRetard", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void clearWarningMessages() {
        loginWarning.setText("");
        passwordWarning.setText("");
    }

    private void updateWarningMessages(Integer loginResultCode, Integer passwordResultCode) {
        clearWarningMessages();

        if (loginResultCode.equals(LOGIN_CONTAINS_SPACES)) {
            loginWarning.setText(R.string.login_contains_spaces_warning);
        } else if (loginResultCode.equals(LOGIN_IS_EMPTY)) {
            loginWarning.setText(R.string.login_is_empty_warning);
        }

        if (passwordResultCode.equals(PASSWORD_CONTAINS_SPACES)) {
            passwordWarning.setText(R.string.password_contains_spaces_warning);
        } else if (passwordResultCode.equals(PASSWORD_IS_EMPTY)) {
            passwordWarning.setText(R.string.password_is_empty_warning);
        } else if (passwordResultCode.equals(PASSWORD_TOO_SHORT)) {
            passwordWarning.setText(R.string.password_too_short_warning);
        }
    }

}
