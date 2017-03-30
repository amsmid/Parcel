package com.whitebird.parcel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by girish on 8/3/17.
 */

class ConnectToServer {
    private Activity activity;
    ConnectToServer(Activity activity){
        this.activity=activity;
    }

    String GetResult(String getData, String onlineKey) throws UnsupportedEncodingException {
        String data;
        switch (onlineKey){
            case "userLogin.php":
                data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[1], "UTF-8");
                break;
            case "userRegister.php":
                data = URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[1], "UTF-8");
                data += "&" +URLEncoder.encode("mobileNo", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[2], "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[3], "UTF-8");
                data += "&" +URLEncoder.encode("address", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[4], "UTF-8");
                data += "&" + URLEncoder.encode("pincode", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[5], "UTF-8");
                data += "&" +URLEncoder.encode("stateName", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[6], "UTF-8");
                data += "&" + URLEncoder.encode("cityName", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[7], "UTF-8");
                data += "&" +URLEncoder.encode("type", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[8], "UTF-8");
                data += "&" + URLEncoder.encode("interCity", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[9], "UTF-8");
                data += "&" +URLEncoder.encode("vehicle", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[10], "UTF-8");
                break;

            case "userUpdate.php":
                data = URLEncoder.encode("uid", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[1], "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[2], "UTF-8");
                data += "&" +URLEncoder.encode("mobileNo", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[3], "UTF-8");
                data += "&" +URLEncoder.encode("address", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[4], "UTF-8");
                data += "&" + URLEncoder.encode("pincode", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[5], "UTF-8");
                data += "&" +URLEncoder.encode("stateName", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[6], "UTF-8");
                data += "&" + URLEncoder.encode("cityName", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[7], "UTF-8");
                break;
            case "userPassword.php":
                data = URLEncoder.encode("uid", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                data += "&" + URLEncoder.encode("oldPassword", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[1], "UTF-8");
                data += "&" + URLEncoder.encode("newPassword", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[2], "UTF-8");
                break;
            case "list.php":
                data = URLEncoder.encode("uid", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                break;
            case "fetchHub.php":
                data = URLEncoder.encode("uid", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                break;
            case "addParcel.php":
                data = URLEncoder.encode("sender", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                data += "&" + URLEncoder.encode("receiver", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[1], "UTF-8");
                data += "&" + URLEncoder.encode("address", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[2], "UTF-8");
                data += "&" +URLEncoder.encode("type", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[3], "UTF-8");
                data += "&" +URLEncoder.encode("pincode", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[4], "UTF-8");
                data += "&" + URLEncoder.encode("landmark", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[5], "UTF-8");
                data += "&" +URLEncoder.encode("size", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[6], "UTF-8");
                data += "&" + URLEncoder.encode("weight", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[7], "UTF-8");
                data += "&" + URLEncoder.encode("image1", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[8], "UTF-8");
                break;
            case "try.php":
                data = URLEncoder.encode("data", "UTF-8")
                        + "=" + URLEncoder.encode(getData.split("\\|")[0], "UTF-8");
                break;
            default:
                data = "Check Input";
        }


        String result = "";
        String accessLink = activity.getResources().getString(R.string.url)+onlineKey;
        BufferedReader reader=null;
        URL url = null;
        try
        {

            // Defined URL  where to send data
            url = new URL(accessLink);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
            result = sb.toString();
            Log.d("Res",result);
        }
        catch(Exception ex)
        {
            Log.d("Resex",ex.getMessage());
        }
        finally
        {
            try
            {
                reader.close();
            }

            catch(Exception ex) {
                Log.d("ResFinally",ex.getMessage());
            }
        }
        if (result.isEmpty()){
            result ="Data Not Found";
        }
        return result;
    }
}
