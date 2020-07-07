package com.example.ass_quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edtID, edtPass;
    Button btnLogin, btnExit;
    Intent intent;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtID = findViewById(R.id.loginId);
        edtPass = findViewById(R.id.loginPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnExit = findViewById(R.id.btnExit);
        checkBox = findViewById(R.id.cbRemember);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = "admin";
                String userPass = "admin";

                if (edtID.getText().toString().equals(userName)
                        && edtPass.getText().toString().equals(userPass)){
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công",
                            Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else if (!edtID.getText().toString().equals(userName)){
                    Toast.makeText(getApplicationContext(), "Sai tên đăng nhập",
                            Toast.LENGTH_SHORT).show();

                }else if (!edtPass.getText().toString().equals(userPass)){
                    Toast.makeText(getApplicationContext(), "Sai mật khẩu",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                Intent close = new Intent(Intent.ACTION_MAIN);
                close.addCategory(Intent.CATEGORY_HOME);
                startActivity(close);
                finish();
            }
        });

    }
}
