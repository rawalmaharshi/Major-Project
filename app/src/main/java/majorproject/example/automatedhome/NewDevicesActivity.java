package majorproject.example.automatedhome;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.data;
import static android.R.attr.name;
/**
 * Created by Admin on 24-03-2017.
 */

public class NewDevicesActivity extends Activity {
    // Progress Dialog
    private ProgressDialog pDialog;
    EditText inputName;
    EditText inputState;

    // url to create new product
    private static String url_create_product = "http://192.168.1.100/new_device.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);

        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputState = (EditText) findViewById(R.id.inputState);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateDevice);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewDevice().execute();
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewDevice extends AsyncTask<String, String, String> {
        String dev_name = inputName.getText().toString();
        String dev_state = inputState.getText().toString();

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewDevicesActivity.this);
            pDialog.setMessage("Creating Device..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", dev_name));
            params.add(new BasicNameValuePair("state", dev_state));

    try {


        ContentValues values = new ContentValues();
        values.put("name", dev_name);
        values.put("state", dev_state);
        String data  = URLEncoder.encode("dev_name", "UTF-8") + "=" +
                        URLEncoder.encode(dev_name, "UTF-8");
                data += "&" + URLEncoder.encode("dev_state", "UTF-8") + "=" +
                        URLEncoder.encode(dev_state, "UTF-8");

        URL url = new URL(url_create_product);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

        wr.write(data);
        wr.flush();

        BufferedReader reader = new BufferedReader(new
                InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line = null;

        // Read Server Response
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            break;
        }

        return sb.toString();
    }catch(Exception e){
        return new String("Exception: " + e.getMessage());
    }}
//
//
//            // check log cat fro response
//            Log.d("Create Response", json.toString());
//
//            // check for success tag
//            try {
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//                    // successfully created product
//                    Intent i = new Intent(getApplicationContext(), AllDevicesActivity.class);
//                    startActivity(i);
//
//                    // closing this screen
//                    finish();
//                } else {
//                    finish();
//                    // failed to create product
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            Intent i = new Intent(getApplicationContext(), AllDevicesActivity.class);
            startActivity(i);
        }

    }
}


