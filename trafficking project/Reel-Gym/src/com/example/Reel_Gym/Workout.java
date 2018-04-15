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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Workout extends Activity {

    private Button btn_Goback;
    final Context context = this;
    private Button btn;
    int call;
    int k = 0;
    int pause = 1;   //original
    String baseFolder;

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = new Date();

    Button btnstart, btnstop;
    TextView sensorView0;
    TextView sensorView1;
    TextView sensorView2;
    Handler bluetoothIn;
    boolean clicked = true;

    byte[] Test_send_to_HC05 = {-18,-79,16,56,56,-1,-4,-1,-1,-86};     //Test_send_to_HC05[20]={0xEE,0xB1,0x10,0x38,0x38,0xFF,0xFC,0xFF,0xFF,0xaa};
    byte[] Start_Recording = {-18,-79,17,0,6,0,5,-1,-4,-1,-1};	      //��ʼ��¼�������� EE B1 11 00 06 00 05 ... FF FC FF FF
    byte[] Stop_Recording = {-18,-79,17,0,7,0,5,-1,-4,-1,-1};	      //��ͣ��¼�������� EE B1 11 00 07 00 05 ... FF FC FF FF 
    	          
    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
//    private StringBuilder recDataString = new StringBuilder();
    private byte[] readMessage = new byte[20];		//receiving buffer
    private ConnectedThread mConnectedThread;
     InputStreamReader isr;
     BufferedReader br;
     String response; 

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.workout);

        btnstart = (Button) findViewById(R.id.button20);
        btnstop = (Button) findViewById(R.id.button30);
        sensorView0 = (TextView) findViewById(R.id.textView50);
        sensorView1 = (TextView) findViewById(R.id.textView7);
        sensorView2 = (TextView) findViewById(R.id.textView11);
        btn_Goback=(Button)findViewById(R.id.button10);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PhoneCallListener phoneCallListener = new PhoneCallListener();
        TelephonyManager telManager = (TelephonyManager)
        this.getSystemService(Context.TELEPHONY_SERVICE);
        telManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            baseFolder = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        }
        // revert to using internal storage (not sure if there's an equivalent to the above)
        else {
            baseFolder = context.getFilesDir().getAbsolutePath();
        }

/*        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
               if (msg.what == handlerState && pause == 0) {					//if message is what we want
                    String readMessage = (String) msg.obj;                      // msg.arg1 = bytes from connect thread                   
                    recDataString.append(readMessage);      
                	String AX = recDataString.substring(0,6); 
                	sensorView0.setText(" " + AX );     
                    int endOfLineIndex = recDataString.indexOf("8");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~                     
                    	String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();							//get length of data received
//                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));
                        if (recDataString.charAt(0) == '1')								//if it starts with # we know it is what we are looking for
                        {

//                            String AcX = recDataString.substring(recDataString.indexOf("2")+2, recDataString.indexOf("7")-1);             //get sensor value from string between indices 1-5
                        	String AcX = recDataString.substring(1,7);                      
//                            call = Integer.parseInt(ledval);
                            sensorView0.setText(" Accel(x,y,z) = " + AcX );	//update the textviews with sensor values

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
                                fout.write(linea+"accel(x,y,z): "+AcX);
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
        };*/
        
      //��ʼ��¼�������� EE B1 11 00 06 00 05 ... FF FC FF FF
      //��ͣ��¼�������� EE B1 11 00 07 00 05 ... FF FC FF FF 
      //���ͱ�����ֵ 10 01 10 02....(30) ...FF FC 0A aa  GBK(-17 -65 -67) (-17 -65 -67)
      //����repֵ 10 01 10 03....(30) ...FF FC 0A aa
      //����caloriesֵ 10 01 10 04....(30) ...FF FC 0A aa
      //Test_send_to_HC05[20]={0xEE,0xB1,0x10,0x38,0x38,0xFF,0xFC,0xFF,0xFF,0xaa}; 
        
		bluetoothIn = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == handlerState && pause == 0) {
					readMessage = (byte[]) msg.obj;	
//					for(int kk=0; kk<readMessage.length; kk++)  //readMessage.length
//						Log.d("sssss",readMessage[kk]+" ");
//					String readMessage2 = new String(readMessage, 0, msg.arg1);
//					sensorView0.setText(" "+readMessage2);
//					sensorView1.setText(" " + readMessage[0]+" " + readMessage[1]+" " + readMessage[2]+" " + readMessage[3]+" " + readMessage[4]+" " + readMessage[5]+" " + readMessage[6]+" " + readMessage[7]+" " + readMessage[8]+" " + readMessage[9]+" " + readMessage[10]+" " + readMessage[11]+" " + readMessage[12]+" " + readMessage[13]);
					int j = 0;
					int flag = 0;
					int flag2 = 0;
					int Index_start=0;
					int Index_end=0;
					int change=0;
					while (readMessage[j] != 0 && change==0) {						
						if (flag == 2) {                           
							if(readMessage[j] == -17 ){    
								flag2 = 2;
								Index_end=j;									
							}		
						}
						else if (flag == 3) {                           
							if(readMessage[j] == -17 ){    
								flag2 = 3;
								Index_end=j;									
							}		
						}
						else if (flag == 4) {                           
							if(readMessage[j] == -17 ){    
								flag2 = 4;
								Index_end=j;									
							}		
						}
						//***********pack start and end****************
						if(readMessage[j] == 2 && flag==0 ){   //2 stands for encoder value
							flag = 2;
							Index_start=j;
						}
						else if(readMessage[j] == 3 && flag==0 ){   //3 stands for rep value
							flag = 3;
							Index_start=j;
						}
						else if(readMessage[j] == 4 && flag==0 ){   //4 stands for calories value
							flag = 4;
							Index_start=j;
						}
						//*****process********
						if (flag == 2 && flag2 == 2 ) { // extract	data																
							change=1;
							String readMessageDataPiece = new String(readMessage, 0, j + 1);   
							String Distance = readMessageDataPiece.substring(Index_start, Index_end);
							sensorView0.setText("  " + Distance);
							break;											
						}
						else if (flag == 3 && flag2 == 3 ) { // extract	data																
							change=1;
							String readMessageDataPiece = new String(readMessage, 0, j + 1);   
							String Rep = readMessageDataPiece.substring(Index_start, Index_end);
							sensorView1.setText(" " + Rep);
							break;											
						}
						else if (flag == 4 && flag2 == 4 ) { // extract	data																
							change=1;
							String readMessageDataPiece = new String(readMessage, 0, j + 1);   
							String Calories = readMessageDataPiece.substring(Index_start, Index_end);
							sensorView2.setText(" " + Calories);
							break;											
						}
						j++;
					} //while end
					j = 0;
					flag = 0;
					flag2 = 0;
					Index_start=0;
					Index_end=0;
					change=0;
				}
			}
		};
        
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        btn_Goback.setOnClickListener(new OnClickListener() {		
//			@Override
			public void onClick(View arg0) {
				Intent intentbutton=new Intent();
				intentbutton.setClass(Workout.this, Activity02.class);
				startActivity(intentbutton);
				finish();
			}
		});
        
        btnstart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pause = 0;
                k = 0;
                Toast.makeText(getBaseContext(), "started recording", Toast.LENGTH_SHORT).show();
                clicked = false;
                mConnectedThread.write(Start_Recording);
            }
        });

        btnstop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pause = 1;
                Toast.makeText(getBaseContext(), "stopped recorfing", Toast.LENGTH_SHORT).show();
                clicked = false;
                mConnectedThread.write(Stop_Recording);
                sensorView0.setText(" 0" );
                sensorView1.setText(" 0" );
                sensorView2.setText(" 0" );
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
//        mConnectedThread.write("x");

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
                isr = new InputStreamReader(tmpIn);
                br = new BufferedReader(isr);
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


/*        public void run() {
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
        }*/
        public void run() {
            byte[] buffer = new byte[20];
            int bytes;
            while (true) {
                try {    
                	while((response = br.readLine())!=null){              		
                	buffer = response.getBytes();  
                	bytes = response.length();
//					for(int kk=0; kk<buffer.length; kk++)  //readMessage.length
//						Log.d("ee b1 10",buffer[kk]+" ");
//                	Log.d("ee b1 10",bytes+"");
//                  bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, buffer).sendToTarget();
                	}
                } catch (IOException e) {Log.d("abort","66");
                    break;
                }
            }
        }
        
        //write method
/*        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }*/
        public void write(byte[] msgBuffer) {
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