package teamm.cs442_project;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketAsync extends AsyncTask<Void, Void, String>{

    private String server_resp;
    private String client_request;

    private TextView teamNumText;

    SocketAsync(String request, TextView teamEdit){
        this.client_request = request;
        this.teamNumText = teamEdit;
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

            dataOut.writeUTF(client_request);

            int amt_rec = 0;
            int amt_exp = 1;

            while(amt_rec < amt_exp) {
                server_resp = dataIn.readUTF();

                if(server_resp.equals("err")){
                    dataOut.writeUTF("close");
                    break;
                }

                amt_rec += server_resp.length();
            }

            dataOut.writeUTF("close");

        }catch(IOException ioe) {
            server_resp = "read err";
        } finally {
            if(client_sock != null){
                try {
                    dataOut.writeUTF("close");
                    client_sock.close();
                }catch(IOException cl){
                    //
                }
            }
        }

        return server_resp;
    }

    @Override
    protected void onPostExecute(String result){
        this.teamNumText.setText(server_resp);
        super.onPostExecute(result);
    }
}
