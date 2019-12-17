package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Seat extends AppCompatActivity implements View.OnClickListener {
    String usern;
    private EditText txt1, txt2, txt3, txt4, txt5;
    private Button btn;
    private static final String url = "jdbc:mysql://169.254.37.198/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";
    String t1, t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);


        txt2 = (EditText) findViewById(R.id.txt2);
        txt3 = (EditText) findViewById(R.id.txt3);

        btn = (Button) findViewById(R.id.btn);


        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);

        btn.setOnClickListener(this);


    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            System.out.println(usern);

            t2 = txt2.getText().toString();
            t3 = txt3.getText().toString();


            new Send().execute();


        }
    }

    private class Send extends AsyncTask<String, String, String> {
        Intent startingIntent = getIntent();
        String tt = startingIntent.getStringExtra("ad");



        String msg="";
        protected String doInBackground(String... strings) {
            String rs2=null,rs3=null,rs4=null,rs6=null;
            int rs1=0,rs5=0;
            // int test=txt1.getText();

            ResultSet rs=null,rse=null;

            try{
                System.out.println(tt);

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);


                if (cc != null) {
                    String query="Select RID from Reserve where UID = (Select UID from `User` where EMail=?)";

                    String query2 = "Insert into Seat (SeatNo , SeatRowNo,RID) values (?,?,?) ";

                    PreparedStatement pst=cc.prepareStatement(query);
                    PreparedStatement pst1=cc.prepareStatement(query2);
                    // Set(rs1,rs5);

                    pst.setString(1,tt);
                    rs= pst.executeQuery();
                    //rse=pst1.executeQuery();

                    System.out.println("connected");
                    while(rs.next())
                    {

                        rs2=  rs.getString("RID");

                    }
                    pst1.setString(1,t2);
                    pst1.setString(2,t3);
                    pst1.setString(3,rs2);
                    int i = pst1.executeUpdate();
                    if (i > 0) {
                        msg = "updated";
                    }else
                    {
                        msg="Parking already reserved or you have reserved your seat";
                    }

                    System.out.println("Connected to the database test1");
                    pst.close();
                }
                else {
                    System.out.println("notconnected");
                }

            } catch (Exception e) {
                msg="seat already reserved";

                e.printStackTrace();
            }

            return msg;

        }
        protected void onPostExecute(String s) {
            Toast.makeText(Seat.this, s, Toast.LENGTH_LONG).show();

        }
    }
}