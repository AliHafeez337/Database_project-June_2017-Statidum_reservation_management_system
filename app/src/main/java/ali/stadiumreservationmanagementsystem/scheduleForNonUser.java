package ali.stadiumreservationmanagementsystem;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import android.widget.*;

public class scheduleForNonUser extends AppCompatActivity implements View.OnClickListener{

private CheckBox chk1;
private Button btn1;
private EditText txt1;
private TextView tv1,tv2,tv3,tv4;
private static final String url = "jdbc:mysql://192.168.184.2/stadium";
private static final String User = "toor";
private static final String Password = "1234";
private String test;
@Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_for_non_user);

        chk1 = (CheckBox)findViewById(R.id.chk1);

        btn1 = (Button)findViewById(R.id.btn1);
        txt1=(EditText)findViewById(R.id.txt1);
        tv1=(TextView)findViewById(R.id.textView1) ;
        tv2=(TextView)findViewById(R.id.textView2) ;
        tv3=(TextView)findViewById(R.id.textView3) ;
        tv4=(TextView)findViewById(R.id.textView4) ;

        System.out.println(test);
        chk1.setOnClickListener(this);
        btn1.setOnClickListener(this);
        }
public void onClick (View v){
        if(v.getId() == R.id.btn1){
            System.out.println("work");

            test=txt1.getText().toString();
            System.out.println(test);

            if(chk1.isChecked() ){
            new Send().execute();
            }

            else{
            String notice = "Please, select matchID in check box";
            Toast.makeText(scheduleForNonUser.this, notice, Toast.LENGTH_LONG).show();
            }
        }
}

private class Send extends AsyncTask<String, String, String> {

    String msg="";
    protected String doInBackground(String... strings) {
        String rs2=null,rs3=null,rs4=null,rs6=null;
        int rs1=0,rs5=0;
        ResultSet rs=null, rse=null;

        try{
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("tfg");
            Connection cc = DriverManager.getConnection(url, User, Password);

            if (cc != null) {
                System.out.println("kl");
                System.out.println(test);
                String query = "select Duration,Date,Time from Schedule where schID=? ";
                String query2 = "select MName from `Match` where schID=? ";
                PreparedStatement pst=cc.prepareStatement(query);
                PreparedStatement pst1=cc.prepareStatement(query2);

                pst.setString(1,test);
                pst1.setString(1,test);

                rs= pst.executeQuery();
                rse=pst1.executeQuery();

                System.out.println("connected");
                while(rs.next())
                {
                    rs2=  rs.getString("Duration");
                    rs3=  rs.getString("Date");
                    rs4=rs.getString("Time");
                }
                while (rse.next())
                {
                    rs6=rse.getString("MName");
                }

                System.out.println(rs6 + "" + rs2 + " " + rs3 + rs4);

                if(rs6==null)
                {
                    msg = "error";
                }

                else
                {
                    Activity(rs2,rs3,rs4,rs6);

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
        Toast.makeText(scheduleForNonUser.this, s, Toast.LENGTH_LONG).show();

    }
}
    public void Activity(final String aa, final String bb, final String cc, final String dd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tv1.setText(aa);
                tv2.setText(bb);

                tv3.setText(cc);
                tv4.setText(dd);

            }
        });
    }
}



