package com.example.lab.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private TextView resT;
    MyAverage avg = new MyAverage();
    MyMinimum min = new MyMinimum();

    int [] arr = {1, 3, 10, 18, 14};

    abstract class MyValues {
        abstract int getResult(int [] arr);
    }

    class MyMinimum extends MyValues{
        int getResult(int [] arr) {
                int res = arr[0];
                for (int i = 0 ; i < arr.length ; i ++ ) {
                    if ( arr[i] < res )
                        res = arr[i];
                }
            return res;
        }
    }

    class MyAverage extends MyValues{
        int getResult(int [] arr) {
            int total = 0;
            for ( int i = 0 ; i < arr.length ; i ++ )
                total += arr[i];

            total /= arr.length;
            return total;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button)findViewById(R.id.min);
        btn2 = (Button)findViewById(R.id.avg);
        resT = (TextView)findViewById(R.id.res);


        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                resT.setText("Minimum number :" + min.getResult(arr));

            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                resT.setText("Average : " + avg.getResult(arr));
            }
        });


    }
}
