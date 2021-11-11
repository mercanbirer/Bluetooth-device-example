package com.example.bluetoothdeviceexample.ui.menu

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bluetoothdeviceexample.base.BaseAdapter
import com.example.bluetoothdeviceexample.databinding.ItemBluetoothBinding

class BluetoothAdapter(
    var items: ArrayList<BluetoothDevice>,
    private val ctx: Context,
) : BaseAdapter<BluetoothDevice>(ctx, items) {
    private val applicationInfos: List<BluetoothDevice> = items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemBluetoothBinding

        if (convertView == null) {
            binding = ItemBluetoothBinding.inflate(LayoutInflater.from(ctx), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemBluetoothBinding
        }

        val model: BluetoothDevice = applicationInfos[position]

        if (model.name != null){
            binding.tvApp.text = model.name
        }else{
            binding.tvApp.text = model.address
        }
        return binding.root
    }

    fun updateList(newItems: java.util.ArrayList<BluetoothDevice>) {
        items = newItems
        notifyDataSetChanged()
    }
}
