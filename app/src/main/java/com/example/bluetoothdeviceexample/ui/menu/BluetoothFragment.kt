package com.example.bluetoothdeviceexample.ui.menu

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.View
import com.example.bluetoothdeviceexample.R
import com.example.bluetoothdeviceexample.base.BaseFragment
import com.example.bluetoothdeviceexample.databinding.FragmentBluetoothBinding
import com.github.ajalt.timberkt.e

class BluetoothFragment : BaseFragment<FragmentBluetoothBinding>(R.layout.fragment_bluetooth) {
    private var deviceItemList = ArrayList<BluetoothDevice>()
    var bTAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
}