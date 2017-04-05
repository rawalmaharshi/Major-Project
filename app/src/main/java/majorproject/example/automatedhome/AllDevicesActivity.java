package majorproject.example.automatedhome;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllDevicesActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> devList;

    // url to get all devices list
    private static String url_all_devices = "http://192.168.1.100/all_dev.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DEVICES = "devices";
    private static final String TAG_dev_id = "dev_id";
    private static final String TAG_NAME = "dev_name";
    private String TAG = MainActivity.class.getSimpleName();

    // devices JSONArray
   // JSONArray devices = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_attached);

        // Hashmap for ListView
        devList = new ArrayList<HashMap<String, String>>();

        // Loading devices in Background Thread
        new LoadAllDevices().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String dev_id = ((TextView) view.findViewById(R.id.dev_id)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        UpdateDeviceState.class);
                // sending dev_id to next activity
                in.putExtra(TAG_dev_id, dev_id);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    private class LoadAllDevices extends AsyncTask<String, Void, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllDevicesActivity.this);
            pDialog.setMessage("Loading devices. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All devices from url
         * */
        @Override
        protected String doInBackground(String... args) {
            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            ContentValues param = new ContentValues();
//            // getting JSON string from URL
//            JSONObject json = jParser.makeHttpRequest(url_all_devices, "GET", params);

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = url_all_devices;
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);


            // Check your log cat for JSON reponse
         //   Log.e("All devices: ", json.toString());


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node


                    // Checking for SUCCESS TAG
                    int success = jsonObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // devices found
                        // Getting Array of devices
                        JSONArray devices = jsonObj.getJSONArray("device");
                        //yeh naam (device) json array ka naam hai.
                        Log.e(TAG,"\n\nDevice array:" + devices);
                        // looping through All devices
                        for (int i = 0; i < devices.length(); i++) {
                            JSONObject c = devices.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString(TAG_dev_id);
                            String dev_name = c.getString(TAG_NAME);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_dev_id, id);
                            map.put(TAG_NAME, dev_name);

                            // adding HashList to ArrayList
                            devList.add(map);
                        }
                    } else {
                        // no devices found
                        // Launch Add New product Activity
                        Intent i = new Intent(getApplicationContext(),
                                NewDevicesActivity.class);
                        // Closing all previous activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
            return null;
        }

            /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all devices
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllDevicesActivity.this, devList,
                            R.layout.devicess, new String[] { TAG_dev_id,
                            TAG_NAME},
                            new int[] { R.id.dev_id, R.id.dev_name });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}

