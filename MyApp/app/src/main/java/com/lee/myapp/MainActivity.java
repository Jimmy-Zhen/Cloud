package com.lee.myapp;

import android.support.annotation.IdRes;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private static TextView tv_msg = null;
    private EditText ed_msg = null;
    private Button online = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_msg = (TextView)findViewById(R.id.tv_msg);
        ed_msg = (EditText)findViewById(R.id.ed_msg);
        online = (Button)findViewById(R.id.online);

        online.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String msg = ed_msg.getText().toString();
                int temp = Integer.valueOf(msg);
                int [] num = new int [temp];
                for(int i=0;i<temp;i++){
                    num[i] = i;
                }
                long startMili=System.currentTimeMillis();
                num = BubbleSort.bubbleSort(num);
                long endMili=System.currentTimeMillis();
                tv_msg.setText(String.valueOf(endMili - startMili)+"ms");
            }
        });

    }

}
