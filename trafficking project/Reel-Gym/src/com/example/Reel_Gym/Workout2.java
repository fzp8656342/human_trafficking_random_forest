package com.example.Reel_Gym;

import com.example.Reel_Gym.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Workout2 extends Activity {

    final Context context = this;
    private Button btn;
    int call;
    int k = 0;
    int pause = 1;
    String baseFolder;

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = new Date();

    Button btnOn, btnOff, btnOn2, btnstart, btnstop, btnvibratoroff;
    TextView txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3, sensorView4, sensorView5, sensorView6,sensorView7;
    Handler bluetoothIn;
    boolean clicked = true;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.workout2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        btn = (Button) findViewById(R.id.call);
        PhoneCallListener phoneCallListener = new PhoneCallListener();
        TelephonyManager telManager = (TelephonyManager)
                this.getSystemService(Context.TELEPHONY_SERVICE);
        telManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

        // add button listener
//        btn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
//                phoneCallIntent.setData(Uri.parse("tel:3024653830"));
//                startActivity(phoneCallIntent);
//            }
//        });


        //Link the buttons and textViews to respective views
//        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        btnOn = (Button) findViewById(R.id.buttonOn);
        btnOn2 = (Button) findViewById(R.id.buttonOn2);
        btnvibratoroff = (Button) findViewById(R.id.vibratoroff);
        btnOff = (Button) findViewById(R.id.buttonOff);
        btnstart = (Button) findViewById(R.id.start);
        btnstop = (Button) findViewById(R.id.stop);
        txtString = (TextView) findViewById(R.id.txtString);
        txtStringLength = (TextView) findViewById(R.id.testView1);
        sensorView0 = (TextView) findViewById(R.id.sensorView0);
        sensorView1 = (TextView) findViewById(R.id.sensorView1);
        sensorView2 = (TextView) findViewById(R.id.sensorView2);
        sensorView3 = (TextView) findViewById(R.id.sensorView3);
        sensorView4 = (TextView) findViewById(R.id.sensorView4);
        sensorView5 = (TextView) findViewById(R.id.sensorView5);
        sensorView6 = (TextView) findViewById(R.id.sensorView6);
        sensorView7 = (TextView) findViewById(R.id.sensorView7);

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            baseFolder = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        }
        // revert to using internal storage (not sure if there's an equivalent to the above)
        else {
            baseFolder = context.getFilesDir().getAbsolutePath();
        }


        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState && pause == 0) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();							//get length of data received
                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));
                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {

                            String AcX = recDataString.substring(recDataString.indexOf("=")+2, recDataString.indexOf("|")-1);             //get sensor value from string between indices 1-5
                            String AcY = recDataString.substring(recDataString.indexOf("Y")+2, recDataString.indexOf("/")-1);            //same again...
                            String AcZ = recDataString.substring(recDataString.indexOf("Z")+2, recDataString.indexOf("!")-1);
                            String tmp = recDataString.substring(recDataString.indexOf("p")+2, recDataString.indexOf("@")-1);
                            String GyX = recDataString.substring(recDataString.indexOf("x")+2, recDataString.indexOf("$")-1);             //get sensor value from string between indices 1-5
                            String GyY = recDataString.substring(recDataString.indexOf("y")+2, recDataString.indexOf("%")-1);            //same again...
                            String GyZ = recDataString.substring(recDataString.indexOf("z")+2, recDataString.indexOf("*")-1);
                            String GyroMag =recDataString.substring(recDataString.indexOf("{")+2, recDataString.indexOf("+")-1);
                            String AccMag =recDataString.substring(recDataString.indexOf("n") + 2, recDataString.indexOf("(") - 1);
                            String distance =recDataString.substring(recDataString.indexOf("(") + 2, recDataString.indexOf("^") - 1);
                            String vibval = recDataString.substring(recDataString.indexOf("l")+2, recDataString.indexOf("`")-1);
                            String ir =recDataString.substring(recDataString.indexOf("r") + 2, recDataString.indexOf("~"));

//                            call = Integer.parseInt(ledval);

                            sensorView0.setText(" Accel(x,y,z) = " + AcX + ", " + AcY + ", " + AcZ );	//update the textviews with sensor values
                            sensorView1.setText(" temperature = " + tmp + " C");
                            sensorView2.setText(" gyro(x,y,z) = " + GyX + ", " + GyY + ", " + GyZ);
                            sensorView3.setText(" vibrator value = " + vibval);
                            sensorView4.setText(" Accel mag = " + AccMag);
                            sensorView5.setText(" Gyro mag = " + GyroMag);
                            sensorView6.setText(" dist = " +distance+" cm");
                            sensorView7.setText(" ir = " +ir+" cm");
                            txtString.setText(" vib val = " + vibval);

                            try
                            {
                                File file = new File(baseFolder + File.separator + "data.txt");
                                file.getParentFile().mkdirs();
                                FileOutputStream os = new FileOutputStream(file,true);
//                                OutputStreamWriter fout= new OutputStreamWriter(openFileOutput("data.txt", Context.MODE_APPEND));
                                OutputStreamWriter fout = new OutputStreamWriter(os);

                                String linea = System.getProperty("line.separator");
                                if(k== 0){
                                    fout.write(linea+"");
                                    fout.write(linea+"time and date: "+ dateFormat.format(date));
                                    fout.write(linea+"");
                                    k++;
                                }
                                fout.write(linea+"accel(x,y,z): "+AcX+","+AcY+","+AcZ+", gyro(x,y,z): "+GyX+","+GyY+","+GyZ+", temp(c): "+tmp+", Acc Mag: "+ AccMag+", gyro Mag: "+ GyroMag + ", prox dist: " + distance+ ", ir dist: " + ir );
                                fout.close();

                            }
                            catch (Exception ex)
                            {
                                Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                            }

                        }
//                        if ((call == 2)){
//                            btnOff.performClick();
//                            call = 0;
//                            btncall.performClick();
//                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();


        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        btnOff.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("0");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
                clicked = false;
            }
        });

        btnOn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
                clicked = false;
            }
        });

        btnOn2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("2");    // Send "2" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn on vibrator", Toast.LENGTH_SHORT).show();
                clicked = false;
            }
        });

        btnvibratoroff.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("3");    // Send "2" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn off vibrator", Toast.LENGTH_SHORT).show();
                clicked = false;
            }
        });

        btnstart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pause = 0;
                k = 0;
                Toast.makeText(getBaseContext(), "started recording", Toast.LENGTH_SHORT).show();
                clicked = false;
            }
        });

        btnstop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pause = 1;
                Toast.makeText(getBaseContext(), "stopped recorfing", Toast.LENGTH_SHORT).show();
                clicked = false;
            }
        });
    }

    // monitor phone call states
    private class PhoneCallListener extends PhoneStateListener {

        String TAG = "LOGGING PHONE CALL";

        private boolean phoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(TAG, "OFFHOOK");

                phoneCalling = true;
            }

            // When the call ends launch the main activity again
            if (TelephonyManager.CALL_STATE_IDLE == state) {

                Log.i(TAG, "IDLE");

                if (phoneCalling) {

                    Log.i(TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());

                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    phoneCalling = false;
                }

            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }
}