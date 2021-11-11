package com.example.bluetoothdeviceexample.ui.menu

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluetoothdeviceexample.R
import com.example.bluetoothdeviceexample.base.BaseFragment
import com.example.bluetoothdeviceexample.databinding.FragmentBluetoothBinding
import com.github.ajalt.timberkt.e


class BluetoothFragment : BaseFragment<FragmentBluetoothBinding>(R.layout.fragment_bluetooth) {
    private var deviceItemList = ArrayList<BluetoothDevice>()
    var bTAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var blAdapter: com.example.bluetoothdeviceexample.ui.menu.BluetoothAdapter? = null
    private val filters = IntentFilter(BluetoothDevice.ACTION_FOUND)
    private var bReciever: BroadcastReceiver? = null
    private val mDeviceList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        requireActivity().registerReceiver(bReciever, filter)
        val pairedDevices = bTAdapter.bondedDevices
        if (pairedDevices.size > 0) {
            e { "geldi $pairedDevices" }
            for (device: BluetoothDevice in pairedDevices) {
                deviceItemList.add(device)
                e { "listele $deviceItemList" }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        blAdapter = BluetoothAdapter(deviceItemList, requireContext())
        binding.lvBle.adapter = blAdapter

        bReciever = object : BroadcastReceiver() {
            @SuppressLint("LogNotTimber")
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (BluetoothDevice.ACTION_FOUND == action) {

                    Log.d("DEVICELIST", "Bluetooth device found\n")
                    val device =
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        deviceItemList.add(device)
                        Log.e("dekmlf", deviceItemList.toString())
                    }
                    blAdapter!!.notifyDataSetChanged()
                }
            }
        }


        binding.lvBle.setOnItemClickListener { _, _, position, _ ->
            bTAdapter.cancelDiscovery()
            val value: String = deviceItemList.toList()[position].address
            Log.e("blsjddknwfşml", deviceItemList.toList()[position].toString())
            val device: BluetoothDevice = bTAdapter.getRemoteDevice(value)
            blAdapter!!.notifyDataSetChanged()
            if (device.bondState == BluetoothDevice.BOND_BONDED) {
                pair(device)
            } else {
                Toast.makeText(requireContext(), "not connected", 500).show()
            }
        }
    }

    private fun pair(device: BluetoothDevice) {
        device.createBond()
    }


    override fun onStart() {
        super.onStart()
        requireActivity().registerReceiver(bReciever, filters)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            requireActivity().unregisterReceiver(bReciever)
        } catch (exception: ReceiverCallNotAllowedException) {
        }
    }

}
