package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Reserve extends AppCompatActivity implements View.OnClickListener {

    private String rs2 = null;
    private EditText txt1;
    private Button btn1, btn2, btn3, btn4;

    private static final String url = "jdbc:mysql://192.168.184.2/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        btn4 = (Button) findViewById(R.id.btn4);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn4) {
            new Send().execute();
        }
        if (v.getId() == R.id.btn1) {
            Intent sendStuff = new Intent(this, Meal.class);
            sendStuff.putExtra("ad", rs2);
            startActivity(sendStuff);
        }
        if (v.getId() == R.id.btn2) {
            Intent sendStuff = new Intent(this, Seat.class);
            sendStuff.putExtra("ad", rs2);
            startActivity(sendStuff);

        }
        if (v.getId() == R.id.btn3) {
            Intent sendStuff = new Intent(this, Parking.class);
            sendStuff.putExtra("ad", rs2);
            startActivity(sendStuff);
        }
    }

    private class Send extends AsyncTask<String, String, String> {

        String a = txt1.getText().toString();

        String msg = "";

        Intent startingIntent = getIntent();
        String tt = startingIntent.getStringExtra("ad");

        protected String doInBackground(String... strings) {
            ResultSet rs = null, rs3 = null;
            String rs1 = null;

            try {

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);
                String query1 = "select UID from user where rEMail = ?";
                String query2 = "insert into reserve (MID, UID) values (?, ?)";
                String query3 = "select RID from reserve where UID = ?";

                PreparedStatement pst = cc.prepareStatement(query1);
                pst.setString(1, tt);

                rs = pst.executeQuery();
                while (rs.next()) {
                    rs1 = rs.getString("UID");
                }

                PreparedStatement pst1 = cc.prepareStatement(query2);
                if (cc != null) {
                    pst1.setString(1, a);
                    pst1.setString(2, rs1);
                }
                pst1.executeQuery();
                PreparedStatement pst2 = cc.prepareStatement(query3);
                pst2.setString(1, tt);
                rs3= pst2.executeQuery();
                while (rs.next()) {
                    rs2 = rs3.getString("RID");
                }
                System.out.println("connected");

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }

            return msg;
        }
    }
}