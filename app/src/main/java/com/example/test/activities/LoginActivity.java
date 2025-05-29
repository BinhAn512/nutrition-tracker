package com.example.test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test.HomeScreen;
import com.example.test.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set status bar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));

        setContentView(R.layout.activity_login);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_password);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }

    private void handleLogin() {
        String username = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Reset previous errors
        tilEmail.setError(null);
        tilPassword.setError(null);

        // Validate input
        if (TextUtils.isEmpty(username)) {
            tilEmail.setError("Vui lòng nhập username");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Vui lòng nhập mật khẩu");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            tilPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            etPassword.requestFocus();
            return;
        }

        // Show loading state
        btnLogin.setEnabled(false);
        btnLogin.setText("Đang đăng nhập...");

        // TODO: Implement actual login logic here
        // For now, simulate login process
        simulateLogin(username, password);
    }

    private void simulateLogin(String email, String password) {
        // Simulate network delay
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reset button state
                btnLogin.setEnabled(true);
                btnLogin.setText("Đăng nhập");

                // TODO: Replace with actual authentication
                if (email.equals("test@example.com") && password.equals("123456")) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Navigate to main activity
                    Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1500);
    }

    private void handleRegister() {
        // TODO: Navigate to register activity
        Intent intent = new Intent(LoginActivity.this, StartScreenActivity.class);
        startActivity(intent);
    }
}
