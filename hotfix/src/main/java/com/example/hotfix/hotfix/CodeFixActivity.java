package com.example.hotfix.hotfix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotfix.R;

public class CodeFixActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_fix);
        findViewById(R.id.btn_calc).setOnClickListener(this);
        findViewById(R.id.btn_fixbug).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_calc:
                calc();
                break;
            case R.id.btn_fixbug:
                fixBug();
                break;
            default:
        }
    }

    private void fixBug() {
        DexUtil.init(this);
        //Toast.makeText(this,fileDir.getAbsolutePath()+" "+fileDir.getPath(),Toast.LENGTH_SHORT).show();
    }

    private void calc() {
        EditText dev1 = findViewById(R.id.edit_div1);
        EditText dev2 = findViewById(R.id.edit_div2);
        int ndev1 = Integer.valueOf(dev1.getText().toString());
        int ndev2 = Integer.valueOf(dev2.getText().toString());
        Toast.makeText(this,"除数："+ndev1+"  被除数："+ndev2,Toast.LENGTH_SHORT).show();
        int result = CalculateUtil.div(ndev1,ndev2);

        ((TextView)findViewById(R.id.tv_result)).setText(String.valueOf(result));

    }
}
