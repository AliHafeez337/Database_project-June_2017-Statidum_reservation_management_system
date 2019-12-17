package ali.stadiumreservationmanagementsystem;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class removeCustomer extends AppCompatActivity implements View.OnClickListener {

    private EditText txt1;
    private Button btn1;

    private static final String url = "jdbc:mysql://192.168.184.2/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_customer);

        txt1 = (EditText) findViewById(R.id.txt1);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            new Send().execute();
        }
    }

    private class Send extends AsyncTask<String, String, String> {

        String a = txt1.getText().toString();
        String msg = "";

        protected String doInBackground(String... strings) {
            PreparedStatement pst = null;

            try {

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);

                String query = "delete from user where UID = ?";

                pst = cc.prepareStatement(query);
                System.out.println("connected");
                pst.setString(1, a);
                pst.executeUpdate();

                return msg;
            } catch (Exception e) {

            }
            return msg;
        }
    }
}
