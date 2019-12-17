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

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button btn1, btn2, btn3;
    private EditText txt1, txtP1;
    private TextView txtV1;
    private String pass, msg = " ";
    public String usern = null;
    //public String CNIC=null;

        private static final String url = "jdbc:mysql://192.168.0.104/stadium";
        private static final String User = "toor";
        private static final String Password = "1234";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            txtV1 = (TextView) findViewById(R.id.txtV1);
            btn1 = (Button) findViewById(R.id.btn1);
            txt1 = (EditText) findViewById(R.id.txt1);
            txtP1 = (EditText) findViewById(R.id.txtP1);
            btn2 = (Button) findViewById(R.id.btn2);
            btn3 = (Button) findViewById(R.id.btn3);

            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
            btn3.setOnClickListener(this);

        }

    public void onClick(View V) {


        if (V.getId() == R.id.btn1) {
            Intent in = new Intent("ali.stadiumreservationmanagementsystem.scheduleForNonUser");
            startActivity(in);
        } else if (V.getId() == R.id.btn2) {
            usern = txt1.getText().toString();


            pass = txtP1.getText().toString();

            new Send().execute();
            // String log = " successfull login";
            // Toast.makeText(MainActivity.this, log, Toast.LENGTH_LONG).show();
            //Intent in2 = new Intent("ali.stadiumreservationmanagementsystem.LogedIn");
            //startActivity(in2);
        } else if (V.getId() == R.id.btn3) {
            Intent in3 = new Intent("ali.stadiumreservationmanagementsystem.createAccount");
            startActivity(in3);
        }
    }



    private class Send extends AsyncTask<String, String, String> {
        Intent in = getIntent();
        Intent in2=getIntent();

        //String msg="";
        protected String doInBackground(String... strings) {
            String rs1 = null, rs2 = null, rs3 = null, rs4 = null;


            ResultSet rs = null,rss=null;
            try {
                System.out.println("just got");

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);
                //rs=pst.executeQuery();
                if (cc != null) {
                    String query = "select Password from `user` where   Email=? and isadmin is NULL";
                    String query2 = "select Password from `user` where   Email=? and isadmin is NOT NULL ";
                    // String query1 = "select CNIC from `user` where   Email=? ";

                    PreparedStatement pst = cc.prepareStatement(query);
                    PreparedStatement pst1 = cc.prepareStatement(query2);
                    pst.setString(1, usern);
                    pst1.setString(1,usern);

                    //pst.setString(2,pass);
                    System.out.println(usern + " " + pass);
                    rs = pst.executeQuery();
                    rss=pst1.executeQuery();
                    while (rs.next()) {
                        rs1 = rs.getString("Password");

                    }
                    while (rss.next()) {
                        rs2 = rss.getString("Password");

                    }


                    System.out.println(rs1 + " df  " + rs2);


                    Activity(rs1,usern,rs2);



                    pst.close();
                } else {
                    System.out.println("notconnected");
                }

            } catch (Exception e) {

                e.printStackTrace();
            }

            return msg;

        }

        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

        }
    }

    public String Activity(final String aa,final String bb,final String cc) {


        String u = txtP1.getText().toString();
        System.out.println("notconnected");

        if (u.equals(aa)) {
            System.out.println(aa);

            msg = " successfull login customer";

            //Toast.makeText(MainActivity.this, log, Toast.LENGTH_LONG).show();
            Intent sendStuff = new Intent(this, LogedIn.class);
            sendStuff.putExtra("ad", bb);
            startActivity(sendStuff);

        } else if (u.equals(cc)) {
            msg = " Admin";

            Intent ssendStuff = new Intent(this, admin.class);
            ssendStuff.putExtra("ad", bb);
            startActivity(ssendStuff);




        } else {
            System.out.println("lol");

            msg = "wrong email or password";
        }
        return msg;


    }

}



