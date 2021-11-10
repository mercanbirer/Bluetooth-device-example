package com.example.bluetoothdeviceexample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : Fragment() {

    private var _binding: T? = null
    val binding: T get() = _binding!!
    val scope: LifecycleCoroutineScope = lifecycleScope

    open fun T.initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.initialize()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        return _binding!!.root

    }

    open fun onPauseExtra() {}

    override fun onPause() {
        super.onPause()
        onPauseExtra()
    }

    open fun onKeyDown(event: Lifecycle.Event) {}

    @Subscribe
    fun baseOnKeyDown(event: Lifecycle.Event) {
        onKeyDown(event)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
        _binding = null
    }

}
