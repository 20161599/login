package android.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences spref;
    int number=0;
    String namen;
    String emailn;
    String passwordn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        spref=getSharedPreferences("data", Context.MODE_PRIVATE);
        boolean isFirstRun = spref.getBoolean("isFirstRun", true);
        if(isFirstRun){
            SharedPreferences.Editor editor=spref.edit();
            editor.putBoolean("isFirstRun", false);
            editor.putString("number","0");
            editor.commit();
            Toast.makeText(this,"isFirstRun",Toast.LENGTH_LONG).show();

        }
        else{
            number=Integer.parseInt(spref.getString("number",""));

        }

        //setContentView(R.layout.layout_login);
        //setContentView(R.layout.activity_main);
    }
    private class logintask extends AsyncTask<String,Object,String>{
        protected void onPreExecute(){
            super.onPreExecute();
            ((ProgressBar)findViewById(R.id.progress)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.btnRegister)).setVisibility(View.INVISIBLE);
        }
        protected  String doInBackground(String... params){
            String email=params[0];
            String text;
            String password=((TextView)findViewById(R.id.password)).getText().toString();
            String username=((TextView)findViewById(R.id.name)).getText().toString();
            boolean have=false;

            if(email.equals("")|password.equals("")|username.equals("")){
                text="empty input";
            }
            else{
                te:
                for(int temp=0;temp<=number;temp++) {
                    if(email.equals(spref.getString("email"+temp,""))){
                        have=true;
                        break te;
                    }
                }
                if(have){
                    text="email exist";
                }
                else{
                    text="succed";
                }
            }
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        return  text;
        }
        @Override

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("succed")){
                SharedPreferences.Editor editor=spref.edit();
                String username=((TextView)findViewById(R.id.name)).getText().toString();
                String email=((TextView)findViewById(R.id.email)).getText().toString();
                String password=((TextView)findViewById(R.id.password)).getText().toString();
               String namen="name"+number;
                String emailn="email"+number;
                String passwordn="password"+number;
                editor.putString(namen,username);
                editor.putString(emailn,email);
                editor.putString(passwordn,password);
                String num=String.valueOf(number);
                editor.putString("number",num);
                editor.commit();
                number++;
                setContentView(R.layout.layout_login);

            }
            if(s.equals("email exist")|s.equals("empty input")){
                ((TextView)findViewById(R.id.btnLinkToLoginScreen)).setText(s);
                ((Button)findViewById(R.id.btnRegister)).setVisibility(View.VISIBLE);
                ((ProgressBar)findViewById(R.id.progress)).setVisibility(View.INVISIBLE);

                Handler handler = new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        ((TextView)findViewById(R.id.btnLinkToLoginScreen)).setText("log into your acount");
                    }
                };
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    }

    public void btnLogin(View view) {

        String inputemail = ((TextView) findViewById(R.id.email)).getText().toString();
        String inputpassword = ((TextView) findViewById(R.id.password)).getText().toString();
        boolean have = false;
        boolean match = false;
        if(inputemail.equals("")){
            Toast.makeText(this,"please input your email",Toast.LENGTH_LONG).show();
        }else {
            ok:
            for (int temp = 0; temp <= number; temp++) {

                if (inputemail.equals(spref.getString("email" + temp, ""))) {
                    have = true;

                    if (inputpassword.equals(spref.getString("password" + temp, ""))) {
                        match = true;
                        break ok;
                    }
                }
            }
            if(have&match){
                setContentView(R.layout.activity_main);
            }
            if(have&match==false){
                Toast.makeText(this,"do not match",Toast.LENGTH_LONG).show();
            }
            if(have==false){
                Toast.makeText(this,"count do not exist",Toast.LENGTH_LONG).show();
            }
        }


    }






    public void btnLogout(View view){
        setContentView(R.layout.layout_login);
    }
    public void btnsignup(View view){

        String email=((TextView)findViewById(R.id.email)).getText().toString();

       // setContentView(R.layout.layout_login);
       new logintask().execute(email);
    }
    public void btnchange(View view){


        setContentView(R.layout.layout_login);

    }
    public void btnRegister(View view)
    {

        setContentView(R.layout.layout_register);
    }
}
