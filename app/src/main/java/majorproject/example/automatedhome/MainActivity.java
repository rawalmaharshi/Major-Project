package majorproject.example.automatedhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnViewDevices;
    Button btnAddDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnViewDevices = (Button) findViewById(R.id.btnViewDevices);
        btnAddDevice = (Button) findViewById(R.id.btnAddDevice);


        btnViewDevices.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All Devices Activity
                Intent i = new Intent(getApplicationContext(), AllDevicesActivity.class);
                startActivity(i);

            }
        });

        btnAddDevice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), NewDevicesActivity.class);
                startActivity(i);

            }
        });

    }
}
