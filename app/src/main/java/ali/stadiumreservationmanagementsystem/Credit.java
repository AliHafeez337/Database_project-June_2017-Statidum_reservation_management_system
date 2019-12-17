package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Credit extends AppCompatActivity implements View.OnClickListener {
    private TextView txtV2, txtV3, txtV4, txtV5;
    private EditText txt1, txt2, txt3, txt4;

    private static final String url = "jdbc:mysql://192.168.184.2/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        txtV2 = (TextView) findViewById(R.id.txtV2);
        txtV3 = (TextView) findViewById(R.id.txtV3);
        txtV4 = (TextView) findViewById(R.id.txtV4);
        txtV5 = (TextView) findViewById(R.id.txtV5);
        txt1 = (EditText) findViewById(R.id.txt1);
        txt2 = (EditText) findViewById(R.id.txt2);
        txt3 = (EditText) findViewById(R.id.txt3);
        txt4 = (EditText) findViewById(R.id.txt5);

        txt1.setInputType(InputType.TYPE_CLASS_NUMBER);
        txt2.setInputType(InputType.TYPE_CLASS_NUMBER);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.btn1) {
            System.out.println("wow");
            new Send().execute();
        }
    }

    private class Send extends AsyncTask<String, String, String> {

        String a = txt1.getText().toString();
        String b = txt2.getText().toString();
        String c = txt3.getText().toString();
        String d = txt4.getText().toString();

        String msg = "";

        Intent startingIntent = getIntent();
        String tt = startingIntent.getStringExtra("ad");
        protected String doInBackground(String... strings) {
            PreparedStatement pst = null;

            try {

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);


                String query = "insert into Credit (CID,CreditCardNo,BalanceReceived ,ExpireDate,CardType) values(?,?,?,?,?) ";

                pst = cc.prepareStatement(query);
                if (cc != null) {

                    System.out.println(tt);
                    System.out.println("connected");
                    pst.setString(1, tt);
                    pst.setString(2, a);
                    pst.setString(3, b);
                    pst.setString(4, d);
                    if (c.equals("Visa") || c.equals("Master")) {
                        pst.setString(5, c);
                    }
                    else{
                        msg="choose correct card type";
                    }
                    int i = pst.executeUpdate();
                    if (i > 0) {
                        msg = "updated";
                    }

                    System.out.println("Connected to the database test1");
                    pst.close();
                }
                else {
                    System.out.println("notconnected");
                }

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }

            return msg;
        }


        protected void onPostExecute(String s) {
            Toast.makeText(Credit.this, s, Toast.LENGTH_LONG).show();
        }
    }
}



