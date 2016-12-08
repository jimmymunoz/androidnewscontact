package com.example.jimmymunoz.testappspanel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class TabContact extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        Button btn_send = ((Button) view.findViewById(R.id.btn_send));

        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et_name = (EditText) view.findViewById(R.id.et_name);
                String name = et_name.getText().toString();

                EditText et_email = (EditText) view.findViewById(R.id.et_email);
                String email = et_email.getText().toString();

                EditText et_phone = (EditText) view.findViewById(R.id.et_phone);
                String phone = et_phone.getText().toString();

                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new HttpRequestTask().execute(name, email, phone);
                } else {
                    Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspos = new HashMap<String,String>();

        @Override
        protected String doInBackground(String... params) {
            String urladress ="http://lagenda.apnl.ws/index.php?key=N25QS3JnOWg=&deviceuid=MTIzNDU2&action=Z2V0ZWx0cw==&nom=&prenom=&email=&tel=&msg=";
            paramspos.put("nom",params[0]);
            paramspos.put("email",params[1]);
            paramspos.put("tel",params[2]);


            Log.d("Post", "request  :" + urladress);
            String responseServer = RestHelper.executePOST(urladress, paramspos);
            Log.d("Post", "Response  :" + responseServer);
            return responseServer;
        }

        protected String decodeBase64(String base64){
            String text = "";
            byte[] data = Base64.decode(base64, Base64.DEFAULT);
            try {
                text = new String(data, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String responseUrl) {
            Log.d("Post", "Response  :" + responseUrl );
            Toast toast = Toast.makeText(getContext(), "Sent Info.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
