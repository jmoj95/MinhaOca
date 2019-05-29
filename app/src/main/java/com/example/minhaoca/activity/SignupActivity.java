package com.example.minhaoca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhaoca.R;
import com.example.minhaoca.service.FirebaseAuthService;
import com.example.minhaoca.service.FirebaseDatabaseService;
import com.example.minhaoca.util.ConcreteInputValidator;
import com.example.minhaoca.util.InputValidator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

import static com.example.minhaoca.constants.InputValidationCode.*;

public class SignupActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";

    private FirebaseAuthService firebaseAuthService;
    private FirebaseDatabaseService firebaseDatabaseService;
    private InputValidator inputValidator;

    private ProgressBar progressBar;
    private TextView emailWarning;
    private TextView loginWarning;
    private TextView phoneWarning;
    private TextView passwordWarning;
    private TextView passwordRepeatWarning;
    private EditText emailField;
    private EditText loginField;
    private EditText phoneField;
    private EditText passwordField;
    private EditText passwordRepeatField;
    private Button btnSignup;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        startActivityInitProcess();
    }

    @Override
    protected void onCreateViewInstances() {
        firebaseAuthService = new FirebaseAuthService();
        firebaseDatabaseService = new FirebaseDatabaseService();
        inputValidator = new ConcreteInputValidator();

        String title = getString(R.string.signup);

        TextView activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText(title);

        progressBar = findViewById(R.id.progress_bar);

        emailWarning = findViewById(R.id.email_warning);
        loginWarning = findViewById(R.id.login_warning);
        phoneWarning = findViewById(R.id.phone_warning);
        passwordWarning = findViewById(R.id.password_warning);
        passwordRepeatWarning = findViewById(R.id.password_repeat_warning);

        emailField = findViewById(R.id.email_field);
        loginField = findViewById(R.id.login_field);
        phoneField = findViewById(R.id.phone_field);
        passwordField = findViewById(R.id.password_field);
        passwordRepeatField = findViewById(R.id.password_repeat_field);

        btnSignup = findViewById(R.id.btn_signup);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    @Override
    protected void onActivityInitFinished() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                final String login = loginField.getText().toString();
                final String phone = phoneField.getText().toString();
                String password = passwordField.getText().toString();
                String passwordRepeat = passwordRepeatField.getText().toString();

                Integer emailResultCode = inputValidator.verifyEmail(email);
                Integer loginResultCode = inputValidator.verifyLogin(login);
                Integer phoneResultCode = inputValidator.verifyPhone(phone);
                Integer passwordResultCode = inputValidator.verifyPassword(password);
                Integer passwordRepeatResultCode = inputValidator
                        .verifyPasswordRepeat(passwordRepeat, password);

                updateWarningMessages(emailResultCode, loginResultCode, phoneResultCode,
                        passwordResultCode, passwordRepeatResultCode);

                if (emailResultCode.equals(FIELD_OK) && loginResultCode.equals(FIELD_OK) &&
                        passwordResultCode.equals(FIELD_OK) &&
                        passwordRepeatResultCode.equals(FIELD_OK)) {

                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuthService.signupWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                performOnSignupSuccess(login, phone);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                performOnSignupFailure();
                            }
                        });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void clearWarningMessages() {
        emailWarning.setText("");
        loginWarning.setText("");
        passwordWarning.setText("");
        passwordRepeatWarning.setText("");
    }

    private void updateWarningMessages(Integer emailResultCode, Integer loginResultCode,
                                       Integer phoneResultCode, Integer passwordResultCode,
                                       Integer passwordRepeatResultCode) {
        clearWarningMessages();

        if (emailResultCode.equals(EMAIL_CONTAINS_SPACES)) {
            emailWarning.setText(R.string.email_contains_spaces_warning);
        } else if (emailResultCode.equals(EMAIL_IS_EMPTY)) {
            emailWarning.setText(R.string.email_is_empty_warning);
        } else if (emailResultCode.equals(EMAIL_WRONG_FORMAT)) {
            emailWarning.setText(R.string.email_wrong_format_warning);
        }

        if (loginResultCode.equals(LOGIN_CONTAINS_SPACES)) {
            loginWarning.setText(R.string.login_contains_spaces_warning);
        } else if (loginResultCode.equals(LOGIN_IS_EMPTY)) {
            loginWarning.setText(R.string.login_is_empty_warning);
        }

        if (phoneResultCode.equals(PHONE_IS_EMPTY)) {
            phoneWarning.setText(R.string.phone_is_empty_warning);
        }

        if (passwordResultCode.equals(PASSWORD_CONTAINS_SPACES)) {
            passwordWarning.setText(R.string.password_contains_spaces_warning);
        } else if (passwordResultCode.equals(PASSWORD_IS_EMPTY)) {
            passwordWarning.setText(R.string.password_is_empty_warning);
        } else if (passwordResultCode.equals(PASSWORD_TOO_SHORT)) {
            passwordWarning.setText(R.string.password_too_short_warning);
        }

        if (passwordRepeatResultCode.equals(PASSWORD_REPEAT_DOESNT_MATCH)) {
            passwordRepeatWarning.setText(R.string.password_repeat_doesnt_match_warning);
        }
    }

    private void performOnSignupSuccess(String login, String phone) {
        Log.d(TAG, "onClick: Success on signing up");
        String uid = firebaseAuthService.getCurrentUser().getUid();
        firebaseDatabaseService.writeNewUser(uid, login, phone);
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        finish();
        startActivity(intent);
    }

    private void performOnSignupFailure() {
        Log.d(TAG, "onClick: Failure on signing up");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Failed to create new user.",
            Toast.LENGTH_SHORT).show();
    }

}
