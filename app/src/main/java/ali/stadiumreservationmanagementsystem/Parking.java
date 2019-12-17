package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Parking extends AppCompatActivity implements View.OnClickListener{

    private EditText txt1, txt2, txt3, txt4, txt5;
    private CheckBox chk1, chk2, chk3, chk4, chk5;
    private Button button;
    private static final String url = "jdbc:mysql://192.168.184.2/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";
    boolean c1,c2,c3,c4;
    String s1,s2,s3,s4,s5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        txt1 = (EditText) findViewById(R.id.txt1);
        txt2 = (EditText)findViewById(R.id.txt2);
        txt3 = (EditText)findViewById(R.id.txt3);

        button= (Button)findViewById(R.id.button2);

        txt1.setOnClickListener(this);
        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);

        button.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v.getId() == R.id.button2){
            s1=txt1.getText().toString();
            s2=txt2.getText().toString();
            s3=txt3.getText().toString();
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

                    String query2 = "Insert into Parking (ParkingNo , ParkingFloorNo,ParkingZone,RID) values (?,?,?,?) ";

                    PreparedStatement pst=cc.prepareStatement(query);
                    PreparedStatement pst1=cc.prepareStatement(query2);
                    // Set(rs1,rs5);

                    pst.setString(1,tt);
                    rs= pst.executeQuery();


                    System.out.println("connected");
                    while(rs.next())
                    {

                        rs2=  rs.getString("RID");

                    }
                    pst1.setString(2,s1);
                    pst1.setString(1,s2);
                    pst1.setString(3,s3);
                    pst1.setString(4,rs2);
                    System.out.println(s1+s2+s3+rs2);
                    int i = pst1.executeUpdate();
                    if (i > 0) {
                        msg = "updated";
                    }else
                    {
                        msg="seat already reserved";
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
            Toast.makeText(Parking.this, s, Toast.LENGTH_LONG).show();

        }
    }





}






