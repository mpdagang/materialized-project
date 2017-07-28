/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

package com.example.mpdagang.kotselog.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mpdagang.kotselog.DeviceListAdapter;
import com.example.mpdagang.kotselog.MainActivity;
import com.example.mpdagang.kotselog.OBDManager;
import com.example.mpdagang.kotselog.R;

import java.util.ArrayList;
import java.util.UUID;


public class StartConFrag extends Fragment implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{
    private static final String TAG = "StartConFragment";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothDevice mBTDevice;
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;

    public StringBuilder messages;
    public StringBuilder wholeResponse;

    public Button btnEnableDisable_Discoverable;
    public Button btnDiscoverDev;
    public Button btnStartConnection;
    public Button btnSend;
    public Button backBtn;
    public Button btnONOFF;
    public EditText etSend;
    public ListView lvNewDevices;
    public TextView incomingMessages;
    public byte[] bytes;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);

            }
        }
    };

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String text = intent.getStringExtra("theMessage");

            messages.append(text);
            wholeResponse.append(text);
            if(wholeResponse.toString().contains(">") && !wholeResponse.toString().contains("S")){
                Log.d(TAG,"this " + wholeResponse.toString()+ " is the whole reply not needed");
                wholeResponse = new StringBuilder();
            }

            //incomingMessages.setText(messages);
            else if (wholeResponse.toString().contains(">") && wholeResponse.toString().contains("00") && wholeResponse.toString().contains("S")){
                Log.d(TAG,"this " + wholeResponse.toString().substring(12)+ " is the whole reply Needed");
                ((MainActivity)getActivity()).setupPids(OBDManager.decodeAvailable(wholeResponse.toString().substring(12)));
                Log.d(TAG,"this " + ((MainActivity)getActivity()).pidList.toString() + " is the whole reply Needed");

                wholeResponse = new StringBuilder();
                //incomingMessages.setText(wholeResponse);
                AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
                a.setMessage("Kotselog connected to adapter");
                a.setCancelable(false);
                a.setPositiveButton("Ok", StartConFrag.this);
                AlertDialog alert = a.create();
                alert.setTitle("Success");
                alert.show();
            }



            Log.d(TAG,"Data written on field");
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: called.");
        View view =  inflater.inflate(R.layout.fragment_start_con, container, false);

        //backBtn = (Button) view.findViewById(R.id.backButton);
        //btnONOFF = (Button) view.findViewById(R.id.btnONOFF);
        //btnSend = (Button) view.findViewById(R.id.btnSend);
        //btnEnableDisable_Discoverable = (Button) view.findViewById(R.id.btnDiscoverable_on_off);
        btnDiscoverDev = (Button) view.findViewById(R.id.btnFindUnpairedDevices);
        //btnStartConnection = (Button) view.findViewById(R.id.btnStartConnection);

        lvNewDevices = (ListView) view.findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();
        //etSend = (EditText) view.findViewById(R.id.editText);
        //incomingMessages = (TextView) view.findViewById(R.id.incomingMessage);
        messages = new StringBuilder();
        wholeResponse = new StringBuilder();

        //setup inputStream listener


        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        getActivity().registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(StartConFrag.this);

        btnDiscoverDev.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

                if(mBluetoothAdapter.isDiscovering()){
                    mBluetoothAdapter.cancelDiscovery();
                    Log.d(TAG, "btnDiscover: Canceling discovery.");

                    //check BT permissions in manifest
                    checkBTPermissions();

                    mBluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    getActivity().registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                }
                if(!mBluetoothAdapter.isDiscovering()){

                    //check BT permissions in manifest
                    checkBTPermissions();

                    mBluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    getActivity().registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter("incomingMessage"));
            Log.d(TAG, "Registered receiver");
        }catch(Exception e){
            Log.d(TAG, "Unable to register predefined Receivers");
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: called.");
        super.onPause();
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
            Log.d(TAG, "Unregistered receiver");
        }catch(Exception e){
            Log.d(TAG, "Unable to unregister predefined Receivers");
        }
    }



    //create method for starting connection
    //***connection will fail and app will crash if not paired
    public void startConnection(){
        //start connection service
        ((MainActivity)getActivity()).startBluetoothConnection(mBTDevice, MY_UUID_INSECURE);
    }

    public void enableDisableBT(){
        if(mBluetoothAdapter == null){
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if(!mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            getActivity().registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if(mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: disabling BT.");
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            getActivity().registerReceiver(mBroadcastReceiver1, BTIntent);
        }

    }


    /**
     * Method required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = getActivity().checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += getActivity().checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //first cancel discovery, memory intensive.
        mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "onItemClick: You Clicked on a device.");
        String deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d(TAG, "Trying to pair with " + deviceName);
            mBTDevices.get(i).createBond();
            //get paired device to be given later do bluetoothConnectionService instance
            mBTDevice = mBTDevices.get(i);
            ((MainActivity)getActivity()).setBluetoothConnectionService();
            startConnection();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ((MainActivity)getActivity()).setViewPager(3);
    }
}
