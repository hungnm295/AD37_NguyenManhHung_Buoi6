package com.example.ad37_nguyenmanhhung_buoi6;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String strUsername, strPassword;
    TextView edtUsername, edtPassword;
    Button btnLogin;
    CheckBox cbRemember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        sharedPreferences = getSharedPreferences("DataLogin", MODE_PRIVATE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    public void initView(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        cbRemember = findViewById(R.id.cbRemember);
    }

    public void checkLogin(){
        strUsername = edtUsername.getText().toString().trim();
        strPassword = edtPassword.getText().toString().trim();

        if (strUsername.isEmpty()){
            Toast.makeText(MainActivity.this, "Không được để trống Username", Toast.LENGTH_SHORT).show();
        }else if(strPassword.isEmpty()){
            Toast.makeText(MainActivity.this, "Không được để trống Password", Toast.LENGTH_SHORT).show();
        }else{
            if (strUsername.equals(Data.username) && strPassword.equals(Data.password)) {
                if (!Data.PASSWORD_PATTERN.matcher(strPassword).matches()){
                    Toast.makeText(this, "Password bao gồm 6~14 ký tự (bao gồm chữ hoa, chữ thường, chữ số, ký tự đặc biệt) và không bao gồm khoảng trắng ",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), CreateNewNote.class);
                    startActivity(intent);
                    rememberLogin();
                }

            } else {
                Toast.makeText(this, "Sai Username hoặc Password", Toast.LENGTH_SHORT).show();
                /*if (!Data.PASSWORD_PATTERN.matcher(strPassword).matches()) {
                    Toast.makeText(MainActivity.this,
                            "Sai Username hoặc Password\nPassword phải có ít nhất 6 ký tự: bao gồm chữ hoa, chữ thường, chữ số, ký tự đặc biệt ",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Sai Username hoặc Password", Toast.LENGTH_SHORT).show();
                }*/
            }
        }
    }
    public void rememberLogin(){
        if (cbRemember.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", strUsername);
            editor.putString("password", strPassword);
            editor.putBoolean("checked", true);
            editor.commit();
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.remove("checked");
            editor.commit();
        }
    }

}
