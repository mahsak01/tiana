package com.example.tianaserver

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.tianaserver.data.model.Server
import com.example.tianaserver.databinding.LayoutServerDialogBinding

class ServerDialogFragment(val deleteEventListener: DeleteEventListener,val server: Server):DialogFragment(),ServerAdapter.ServerEventListener {

    lateinit var binding:LayoutServerDialogBinding

    override fun onResume() {
        super.onResume()
        this.setListeners()
    }

    private fun setListeners() {
        this.binding.layoutServerDeleteServerNoBtn.setOnClickListener {
            dismiss()
        }
        this.binding.layoutServerDeleteServerYesBtn.setOnClickListener {
            deleteEventListener.delete(server)
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.layout_server_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

    override fun deleteServer(server: Server) {
        TODO("Not yet implemented")
    }

    override fun addServer(server: Server) {
        TODO("Not yet implemented")
    }

    override fun updateServer(server: Server) {
        TODO("Not yet implemented")
    }

    interface DeleteEventListener{
        fun delete(server: Server)
    }

}