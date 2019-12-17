package ali.stadiumreservationmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddCustomer extends AppCompatActivity implements View.OnClickListener {


    private Button btn;
    private TextView txtV1;
    private EditText txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10;
    private CheckBox chk1, chk2;
    private Boolean b;

    private static final String url = "jdbc:mysql://192.168.184.2/stadium";
    private static final String User = "toor";
    private static final String Password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btn = (Button) findViewById(R.id.btn1);
        txt1 = (EditText) findViewById(R.id.txt1);
        txt1.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        txt2 = (EditText) findViewById(R.id.txt2);
        txt2.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        txt3 = (EditText) findViewById(R.id.txt3);
        txt3.setInputType(InputType.TYPE_CLASS_NUMBER);
        txt4 = (EditText) findViewById(R.id.txt4);
        txt4.setInputType(InputType.TYPE_CLASS_NUMBER);
        txt5 = (EditText) findViewById(R.id.txt5);

        txt6 = (EditText) findViewById(R.id.txt6);
        txt7 = (EditText) findViewById(R.id.txt7);
        txt7.setInputType(InputType.TYPE_CLASS_NUMBER);
        txt8 = (EditText) findViewById(R.id.txt8);

        btn.setOnClickListener(this);
    }

    public void onClic(View v) {
        if (v.getId() == R.id.btn1) {
            System.out.println("finally");
            new Send().execute();

        }
    }

    private class Send extends AsyncTask<String, String, String> {
        String a = txt1.getText().toString();
        String b = txt2.getText().toString();
        String c = txt3.getText().toString();
        String d = txt4.getText().toString();
        String e = txt5.getText().toString();
        String f = txt6.getText().toString();
        String g = txt7.getText().toString();
        String h = txt8.getText().toString();
        String i = txt9.getText().toString();
        String j = txt10.getText().toString();
        String check;

        String msg = "";

        protected String doInBackground(String... strings) {
            PreparedStatement pst = null;


            try {

                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("tfg");
                Connection cc = DriverManager.getConnection(url, User, Password);


                String query = "insert into `User`  values(?,?,?,?,?,?,?,?,?,?) ";
                //System.out.println("mow1");

                pst = cc.prepareStatement(query);
                System.out.println("connected");
                pst.setString(1, null);


                pst.setString(3, a);

                pst.setString(4, b);
                pst.setString(2, c);
                pst.setString(5, d);
                pst.setString(6, e);
                pst.setString(8, f);
                pst.setString(9, g);
                pst.setString(10, h);
                // if(i.equals(j))

                pst.setString(7, check);

                //pst.setString(4, j); confrm password

                int i = pst.executeUpdate();
                if (i > 0) {
                    msg = "updated";
                }

                System.out.println("Connected to the database test1");
                Intent in = new Intent(this, Reserve.class);
                in.putExtra("ad", e);
                startActivity(in);
                pst.close();

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();

            }
            return msg;
        }
    }
}