package ali.stadiumreservationmanagementsystem;

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

public class UpdateSchedule extends AppCompatActivity implements View.OnClickListener{

    private Button btn;
    private EditText txt1, txt2, txt3, txt4, txt5;
    private CheckBox chk;
    private boolean ccc;
    private static final String url = "jdbc:mysql://169.254.37.198/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

        txt1=(EditText)findViewById(R.id.txt1);
        txt2=(EditText)findViewById(R.id.txt2);
        txt3=(EditText)findViewById(R.id.txt3);
        txt4=(EditText)findViewById(R.id.txt4);
        txt5=(EditText)findViewById(R.id.txt5);
        chk = (CheckBox)findViewById(R.id.chk);
        btn= (Button)findViewById(R.id.btn);

        txt1.setOnClickListener(this);
        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);
        txt4.setOnClickListener(this);
        txt5.setOnClickListener(this);
        btn.setOnClickListener(this);
        chk.setOnClickListener(this);
    }
    public  void onClick(View v){
        ccc=chk.isChecked();

        if(v.getId() == R.id.btn){
            ccc=chk.isChecked();
            new Send().execute();

        }
    }
    private class Send extends AsyncTask<String, String, String> {
        String a = txt1.getText().toString();
        String b = txt2.getText().toString();
        String c = txt3.getText().toString();
        String d = txt4.getText().toString();
        String e = txt5.getText().toString();
        String rs1=null,rs2=null,rs3=null,rs4=null,rs5=null;

        String check;

        String msg = "";

        protected String doInBackground(String... strings) {
            PreparedStatement pst = null;
            PreparedStatement pst2= null,pst3=null;



            try {

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);


                //String queryi = "insert into `Schedule` (Duration,Dayy,Date,Time)  values(?,?,?,?) ";
                String queryi= "update `Schedule` set Duration=?,Dayy=?,Date=?,Time=? Where schID=?";
                String querys = "select Duration,Dayy,Date,Time from Schedule where schID=? ";
                String query1 = "Select schID from `Match` where MID=? ";
                pst = cc.prepareStatement(queryi);
                pst3 = cc.prepareStatement(querys);
                pst2 = cc.prepareStatement(query1);
                if (cc != null) {
                    if (ccc == false) {


                        System.out.println("connected");

                        pst2.setString(1, a);
                        ResultSet rs = pst2.executeQuery();
                        while (rs.next()) {
                            rs1 = rs.getString("schID");
                        }


                        pst3.setString(1, rs1);
                        ResultSet rss = pst3.executeQuery();
                        while (rss.next()) {
                            rs2 = rss.getString("Duration");
                            rs3 = rss.getString("Dayy");
                            rs4 = rss.getString("Date");
                            rs5 = rss.getString("Time");

                        }
                        System.out.println(rs2 + "" + rs3 + "" + rs4 + rs5);
                        Activity(rs2, rs3, rs4, rs5);


                        System.out.println("Connected to the database test1");

                        pst.close();
                    }
                    else if(ccc==true)
                    {
                        pst2.setString(1, a);
                        ResultSet rs = pst2.executeQuery();
                        while (rs.next()) {
                            rs1 = rs.getString("schID");
                        }
                        pst.setString(1,b);
                        pst.setString(2, c);
                        pst.setString(3, d);
                        pst.setString(4, e);
                        pst.setString(5,rs1);
                        int i = pst.executeUpdate();

                        if (i > 0) {
                            msg = "updated";
                        }
                    }
                } else {
                    System.out.println("notconnected");
                }

            } catch(Exception e){
                System.out.println(e);
                e.printStackTrace();
            }

            return msg;


        }
        protected void onPostExecute(String s) {
            Toast.makeText(UpdateSchedule.this, s, Toast.LENGTH_LONG).show();
        }
    }
    public void Activity(final String aa, final String bb, final String cc, final String dd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                txt2.setText(aa);
                txt3.setText(bb);

                txt4.setText(cc);
                txt5.setText(dd);

            }
        });





    }




}