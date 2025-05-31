package com.example.test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test.R;
import com.example.test.api.ApiService;
import com.example.test.models.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilUsername, tilEmail, tilPassword, tilConfirmPassword;
    private TextInputEditText etUsername, etEmail, etPassword, etConfirmPassword;
    private CheckBox cbTerms;
    private MaterialButton btnRegister;
    private ImageView ivBack;
    private TextView tvLoginLink;

    // Password pattern: At least 8 characters, contains uppercase, lowercase, and number
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set status bar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));

        setContentView(R.layout.activity_register);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        tilUsername = findViewById(R.id.til_username);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_password);
        tilConfirmPassword = findViewById(R.id.til_confirm_password);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        cbTerms = findViewById(R.id.cb_terms);
        btnRegister = findViewById(R.id.btn_register);
        ivBack = findViewById(R.id.iv_back);
        tvLoginLink = findViewById(R.id.tv_login_link);
    }

    private void setupClickListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to login
            }
        });
    }

    private void handleRegister() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Reset all error messages
        clearErrors();

        // Validate all fields
        if (!validateInputs(username, email, password, confirmPassword)) {
            return;
        }

        // Check terms acceptance
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Vui lòng đồng ý với điều khoản sử dụng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading state
        btnRegister.setEnabled(false);
        btnRegister.setText("Đang đăng ký...");

        // TODO: Implement actual registration logic here
        simulateRegistration(username, email, password);
    }

    private void clearErrors() {
        tilUsername.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);
    }

    private boolean validateInputs(String username, String email,
                                   String password, String confirmPassword) {
        boolean isValid = true;

        // Validate username
        if (TextUtils.isEmpty(username)) {
            tilUsername.setError("Vui lòng nhập tên đăng nhập");
            if (isValid) etUsername.requestFocus();
            isValid = false;
        } else if (username.length() < 1) {
            tilUsername.setError("Tên đăng nhập phải có ít nhất 1 ký tự");
            if (isValid) etUsername.requestFocus();
            isValid = false;
        } else if (!isValidUsername(username)) {
            tilUsername.setError("Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới");
            if (isValid) etUsername.requestFocus();
            isValid = false;
        }

        // Validate email
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Vui lòng nhập email");
            if (isValid) etEmail.requestFocus();
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Email không hợp lệ");
            if (isValid) etEmail.requestFocus();
            isValid = false;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Vui lòng nhập mật khẩu");
            if (isValid) etPassword.requestFocus();
            isValid = false;
        } else if (!isValidPassword(password)) {
            tilPassword.setError("Mật khẩu không đáp ứng yêu cầu bảo mật");
            if (isValid) etPassword.requestFocus();
            isValid = false;
        }

        // Validate confirm password
        if (TextUtils.isEmpty(confirmPassword)) {
            tilConfirmPassword.setError("Vui lòng xác nhận mật khẩu");
            if (isValid) etConfirmPassword.requestFocus();
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            if (isValid) etConfirmPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidUsername(String username) {
        // Username can only contain letters, numbers, and underscores
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    private boolean isValidPassword(String password) {
        return pattern.matcher(password).matches();
    }

    private void simulateRegistration(String username, String email, String password) {
        // Simulate network delay
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reset button state
                btnRegister.setEnabled(true);
                btnRegister.setText("Đăng ký");
                Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                // add to database
                // Navigate to login screen or main activity
                Intent intent = new Intent(RegisterActivity.this, StartScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", username);
                bundle.putString("EMAIL", email);
                bundle.putString("PASSWORD", password);

                User user = new User(username, email, password);
                ApiService.apiService.createUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}