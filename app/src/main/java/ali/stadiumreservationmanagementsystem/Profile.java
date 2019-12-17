package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Profile extends AppCompatActivity implements View.OnClickListener{
    private static final String url = "jdbc:mysql://192.168.184.2/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";
    private TextView txt2,txt4, txt6, txt8, txt10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txt2 = (TextView)findViewById(R.id.txt2);
        txt4 = (TextView)findViewById(R.id.txt5);
        txt6 = (TextView)findViewById(R.id.txt7);
        txt8 = (TextView)findViewById(R.id.txt9);
        txt10 = (TextView)findViewById(R.id.txt4);
        txt2.setOnClickListener(this);
        txt4.setOnClickListener(this);
        txt6.setOnClickListener(this);
        txt8.setOnClickListener(this);
        txt10.setOnClickListener(this);
    }
    public void onClick(View v){
        new Send().execute();

    }
    private class Send extends AsyncTask<String, String, String> {
        String msg="";
        Intent startingIntent = getIntent();
        String tt1 = startingIntent.getStringExtra("ad");
        protected String doInBackground(String... strings) {
            String rs1=null,rs2=null,rs3=null,rs4=null,rs5=null;

            ResultSet rs=null;
            try{
                System.out.println(tt1);

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);

                if (cc != null) {
                    String query = "select FirstName,UAddress,DOB,UAge from `User` where Email =? ";
                    PreparedStatement pst=cc.prepareStatement(query);
                    pst.setString(1,tt1);

                    rs=pst.executeQuery();


                    System.out.println("connected");

                    while(rs.next())
                    {
                        rs1=  rs.getString("FirstName");
                        rs3=  rs.getString("UAddress");
                        rs4=  rs.getString("DOB");
                        rs5=rs.getString("UAge");
                    }
                    msg = "updated";
                    if(msg.equals("updated"))
                    {
                        Activity(rs1,tt1,rs3,rs4,rs5);

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
            Toast.makeText(Profile.this, s, Toast.LENGTH_LONG).show();

        }
    }
    public void Activity(final String aa, final String bb, final String cc, final String dd, final String ee) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                txt2.setText(aa);
                txt4.setText(bb);
                txt6.setText(cc);
                txt8.setText(dd);
                txt10.setText(ee);

            }
        });
    }
}