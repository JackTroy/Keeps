package com.example.zhong.keeps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private TextView register;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        //Create DB
        Connector.getDatabase();

        /*
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                //Query
                List<UserInfo> userInfos = DataSupport.select("user","password")
                        .where("user = ?",account)
                        .where("password = ?",password)
                        .find(UserInfo.class);

                if (!(userInfos.isEmpty())){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "account or password is invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                UtilKt.userLogin(account, password, LoginActivity.this);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        // for test
        Button bt_test = findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, TestActivity.class));
            }
        });

    }

    public void onUserLoginReturn(final Boolean ok) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ok) {
                    UserInfo user = new UserInfo();
                    user.setUser(accountEdit.getText().toString());
                    user.setPassword(passwordEdit.getText().toString());
                    user.setOnline(1);
                    user.save();
                    // if login succeeded
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                } else {
                    // if failed
                    Toast.makeText(LoginActivity.this,
                            "account or password is invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}