package ali.stadiumreservationmanagementsystem;

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

public class Stadium extends AppCompatActivity implements View.OnClickListener{

    private TextView txt4, txt6, txt8, txt10;
    private EditText txt2;
    private static final String url = "jdbc:mysql://169.254.37.198/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";
    String sch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium);

        txt2 = (EditText) findViewById(R.id.txt2);
        txt4 = (TextView)findViewById(R.id.txt5);
        txt6 = (TextView)findViewById(R.id.txt7);
        txt8 = (TextView)findViewById(R.id.txt9);
        txt10 = (TextView)findViewById(R.id.txt4);
        Button button = (Button)findViewById(R.id.button2);
        txt2.setOnClickListener(this);
        txt4.setOnClickListener(this);
        txt6.setOnClickListener(this);
        txt8.setOnClickListener(this);
        txt10.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    public void onClick(View v){
        System.out.println("wow");
        sch=txt2.getText().toString();
        new Send().execute();
        //  if(v.getId() == R.id.button){
        // System.out.println("wow");
        //  new Send().execute();

        // }

    }

    private class Send extends AsyncTask<String, String, String> {
        // Intent in=getIntent();
        // int cd=parseInt(in.getStringExtra("k2"));
        String msg="";
        protected String doInBackground(String... strings) {
            String rs1=null,rs2=null,rs3=null,rs4=null;


            ResultSet rs=null;
            try{
                System.out.println("just got");

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);


                //String query = "select stdName,stdAddress,No_of_seats,No_of_parking from stadium where stdID=?";
                //String query2 = "select MName,MPrice from `Mach`";
                //  String query3 = "select TeamName from team";
                //System.out.println("mow1");
                // PreparedStatement pst=cc.prepareStatement(query);
                // PreparedStatement pst1=cc.prepareStatement(query2);
                // PreparedStatement pst2=cc.prepareStatement(query3);


                //rs=pst.executeQuery();
                if (cc != null) {
                    String query = "select stdName,stdAddress,No_of_seats,No_of_parking from stadium where stdID=? ";
                    PreparedStatement pst=cc.prepareStatement(query);

                    pst.setString(1,sch);
                    rs=pst.executeQuery();


                    System.out.println("connected");
                    // pst.setInt(1,1);
                    while(rs.next())
                    {

                        rs1=  rs.getString("stdName");
                        rs2=  rs.getString("stdAddress");
                        rs3=  rs.getString("No_of_seats");
                        rs4=  rs.getString("No_of_parking");


                    }

                    msg = "updated";
                    if(msg.equals("updated"))
                    {
                        Activity(rs1,rs2,rs3,rs4);

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
            Toast.makeText(Stadium.this, s, Toast.LENGTH_LONG).show();

        }
    }
    public void Activity(final String aa, final String bb, final String cc, final String dd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                txt4.setText(aa);
                txt6.setText(bb);
                txt8.setText(cc);
                txt10.setText(dd);

            }
        });





    }


}

