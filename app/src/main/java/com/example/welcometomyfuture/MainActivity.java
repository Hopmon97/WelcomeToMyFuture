package com.example.welcometomyfuture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //BottomNavigationView bottom_navigation;


    EditText pas, usr,typ;

    public static String ip="192.168.10.9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usr = findViewById(R.id.etUsername);
        pas = findViewById(R.id.etPassword);

        /*MaterialButton loginBtn = (MaterialButton) findViewById(R.id.loginBtn);
*/


        }
    public void loginBtn(View view){
        String user= usr.getText().toString();
        String pass= pas.getText().toString();

    background bg = new background(this);
    bg.execute(user,pass);
    }

    public void registerBtn(View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
        finish();
    }
}/*bottom_navigation=findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:if (type.equals("0"))
                {

                    startActivity(new Intent(getApplicationContext(), admin.class));
                        return true;

                }
                else if (type.equals("1"))
                {

                    startActivity(new Intent(getApplicationContext(), Geoponos.class));
                        return true;

                }
                else if (type.equals("2"))
                {

                    startActivity(new Intent(getApplicationContext(), Georgosss.class));
                        return true;

                }
                else if (type.equals("3"))
                {

                    startActivity(new Intent(getApplicationContext(), SellerActivity.class));
                        return true;

                }

                        startActivity(new Intent(getApplicationContext(), activity.class));
                        return true;
                    case R.id.kx_cart:
                        startActivity(new Intent(getApplicationContext(), activity.class));
                        return true;
                }
                return false;
            }
        });*/
