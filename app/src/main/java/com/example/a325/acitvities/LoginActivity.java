package com.example.a325.acitvities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a325.R;
import com.example.a325.bases.BaseActivity;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.bt_register)
    Button btRegister;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.pass)
    EditText pass;



    private String uname;
    private String password;
    final private String TAG = "LoginActivity";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_login, R.id.bt_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                if (name.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    uname = name.getText().toString();
                    password = pass.getText().toString();
                    new LoginAsyncTask().execute();
                    break;
                }
            case R.id.bt_register:
                if (name.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    uname = name.getText().toString();
                    password = pass.getText().toString();
                    new RegisterAsyncTask().execute();
                    break;
                }
        }
    }

    private class LoginAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getLoginUrl();

            Map<String, String> params = HttpUrl.getInstance().getUserParams(uname, password);

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("true")) {
                setPreferences(uname);
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                //返回Result
                Intent intent = new Intent();
                intent.putExtra("uname", uname);
                LoginActivity.this.setResult(RESULT_OK, intent);
                LoginActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                name.setText("");
                pass.setText("");
                // setPreferences("");
            }
            //analysisCourseListJsonData(s);
        }
    }

    private void setPreferences(String name) {
        SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("user", name);
        edit.commit();
    }

    private class RegisterAsyncTask extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getRegisterUrl();

            Map<String, String> params = HttpUrl.getInstance().getUserParams(uname,password);

            return HttpRequest.getInstance().POST(url,params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.e(TAG,"Register得到的回复："+s);
            if (s.equals("true")) {
                setPreferences(uname);
                Toast.makeText(getApplicationContext(), "注册成功，自动登录", Toast.LENGTH_SHORT).show();
                //返回Result
                Intent intent = new Intent();
                intent.putExtra("uname", uname);
                LoginActivity.this.setResult(RESULT_OK, intent);
                LoginActivity.this.finish();
            }else {
                Toast.makeText(getApplicationContext(), "用户名已被占用", Toast.LENGTH_SHORT).show();
                name.setText("");
            }

        }
    }
}
