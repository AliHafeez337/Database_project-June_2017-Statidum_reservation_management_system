package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Meal extends AppCompatActivity implements View.OnClickListener{

    private Button btn2,btn3;
    private EditText txt1, txt2, txt3;
    private TextView view1;
    String t1,t2,t3;
    private static final String url = "jdbc:mysql://192.168.184.2/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        txt1 = (EditText)findViewById(R.id.txt1);
        txt2 = (EditText)findViewById(R.id.txt1);
        txt3 = (EditText)findViewById(R.id.txt1);

        btn3 = (Button)findViewById(R.id.btn2);
        btn2 = (Button)findViewById(R.id.btn3);

        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }
    public void onClick (View v){

        if(v.getId() == R.id.btn2 ){
            t1=txt1.getText().toString();
            t2=txt2.getText().toString();
            t3=txt3.getText().toString();
            new Send().execute();

        }
    }
    private class Send extends AsyncTask<String, String, String> {

        Intent statingIntent = getIntent();
        //String tt = startingIntent.getStringExtra("ad");

        String msg="";
        protected String doInBackground(String... strings) {
            String rs2=null,rs3=null,rs4=null,rs6=null;
            int rs1=0,rs5=0;
            ResultSet rs=null,rse=null;

            try{

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);

                if (cc != null) {

                    String query = "insert into Meal(MealDeal, MealType,MealPrice, RID) values(?, ?, ?, ?) ";
                    PreparedStatement pst=cc.prepareStatement(query);

                    pst.setString(2,t1);
                    pst.setString(3,t2);
                    pst.setString(4,t3);

                    rs= pst.executeQuery();

                    System.out.println("connected");
                    while(rs.next()) {

                        rs2 = rs.getString("MealPrice");
                        Activity(rs2);
                    }

                    System.out.println("Connected to the database test1");
                    pst.close();
                }
                else {
                    System.out.println("notconnected");
                }

            } catch (Exception e) {

                e.printStackTrace();
            }

            return msg;

        }
        protected void onPostExecute(String s) {
            Toast.makeText(Meal.this, s, Toast.LENGTH_LONG).show();

        }
    }
    public void Activity(final String aa) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                view1.setText(aa);
            }
        });
    }
}