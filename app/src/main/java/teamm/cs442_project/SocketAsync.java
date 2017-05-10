package teamm.cs442_project;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketAsync extends AsyncTask<Void, Void, String>{

    private String server_resp;
    private String client_request;

    private ArrayList<TextView> teamNumText;

    SocketAsync(String request, ArrayList<TextView> teamEdit){
        this.client_request = request;
        this.teamNumText = teamEdit;
    }

    SocketAsync(String request){
        this.client_request = request;
    }

    @Override
    protected String doInBackground(Void... arg0){

        final String server_ip = "52.27.236.253";
        final int data_port = 65121;

        Socket client_sock = null;
        DataOutputStream dataOut = null;
        DataInputStream dataIn = null;

        try {
            client_sock = new Socket(server_ip, data_port);

            dataOut = new DataOutputStream(client_sock.getOutputStream());
            dataIn = new DataInputStream(client_sock.getInputStream());

            dataOut.write(client_request.getBytes());

            int amt_rec = 0;
            int amt_exp = 1;

            byte[] resp = new byte[1024];
            //byte[] send = new byte[1024];

            while(amt_rec < amt_exp) {
                dataIn.read(resp);

                if(resp.toString().equals("err")){
                    dataOut.write("close".getBytes());
                    break;
                }

                amt_rec += resp.toString().length();
            }

            server_resp = new String(resp, "US-ASCII");

        }catch(Exception e) {
            server_resp = "read err";
        }finally {
            if(client_sock != null){
                try {
                    dataOut.write("close".getBytes());
                }catch(Exception e){
                    //
                }
            }
        }

        return server_resp;
    }

    @Override
    protected void onPostExecute(String result){
        if(this.teamNumText != null){
            int count = 0;
            String[] parts = server_resp.split(",");

            for(TextView edit : teamNumText){
                edit.setText(parts[count]);
                count++;
            }
            super.onPostExecute(result);
        }
    }
}
