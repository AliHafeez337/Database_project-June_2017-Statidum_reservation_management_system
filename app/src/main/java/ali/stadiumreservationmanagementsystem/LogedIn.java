package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogedIn extends AppCompatActivity implements View.OnClickListener{

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_in);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn1);
        btn4 = (Button)findViewById(R.id.btn);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn99);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
    }
    public void onClick(View V){

        if(V.getId()== R.id.btn1) {
            Intent startingIntent = getIntent();
            String tt = startingIntent.getStringExtra("ad");

            Intent sendStuff = new Intent(this, Profile.class);
            sendStuff.putExtra("ad", tt);
            startActivity(sendStuff);
        }
        if(V.getId()== R.id.btn2){
            onBackPressed();
        }
        if(V.getId()== R.id.btn99){
            Intent in2 = new Intent("ali.stadiumreservationmanagementsystem.Stadium");
            startActivity(in2);
        }
        if(V.getId()== R.id.btn){
            Intent in = new Intent("ali.stadiumreservationmanagementsystem.scheduleForNonUser");
            startActivity(in);
        }
        if(V.getId()== R.id.btn5){
            Intent startingIntent = getIntent();
            String tt = startingIntent.getStringExtra("ad");

            Intent sendStuff = new Intent(this, Reserve.class);
            sendStuff.putExtra("ad", tt);
            startActivity(sendStuff);
            //Intent in = new Intent("ali.stadiumreservationmanagementsystem.Reserve");
            // startActivity(in);
        }

        if(V.getId()== R.id.btn6){
            Intent startingIntent = getIntent();
            String tt = startingIntent.getStringExtra("ad");

            Intent in= new Intent(this, Credit.class);
            in.putExtra("ad", tt);
            startActivity(in);
        }
    }
}
