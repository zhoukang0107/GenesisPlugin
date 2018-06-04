package com.example.hotfix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hotfix.hotfix.CodeFixActivity;
import com.example.hotfix.hotfix.ResFixActivity;
import com.example.hotfix.hotfix.SoFixActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_code_fix).setOnClickListener(this);
        findViewById(R.id.btn_so_fix).setOnClickListener(this);
        findViewById(R.id.btn_res_fix).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_code_fix:
                fixCode();
                break;
            case R.id.btn_so_fix:
                fixSo();
                break;
            case R.id.btn_res_fix:
                fixRes();
                break;
                default:
        }
    }

    private void fixRes() {
        Intent intent = new Intent(this, ResFixActivity.class);
        startActivity(intent);
    }

    private void fixSo() {
        Intent intent = new Intent(this, SoFixActivity.class);
        startActivity(intent);
    }

    private void fixCode() {
        Intent intent = new Intent(this, CodeFixActivity.class);
        startActivity(intent);
    }
}
