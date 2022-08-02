package com.example.tianaserver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tianaserver.data.model.Server
import com.example.tianaserver.databinding.LayoutServerBottomSheetItemBinding

class ServerAdapter(val serversList: MutableList<Server>,val serverEventListener: ServerEventListener) : RecyclerView.Adapter<ServerAdapter.ViewHolder>()  {


    inner class ViewHolder(val binding:LayoutServerBottomSheetItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindingServer(server: Server){
            binding.layoutServerDefaultServerRb.text = server.name
            if (server.isDefault)
                binding.layoutServerDefaultServerRb.isChecked=true

            binding.layoutServerDeleteServerBtn.setOnClickListener {
                serversList.remove(server)
                notifyItemRemoved(adapterPosition)
                serverEventListener.deleteServer(server)
            }
            binding.layoutServerDefaultServerRb.setOnClickListener {
                serverEventListener.updateServer(server)
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_server_bottom_sheet_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindingServer(serversList[position])
    }

    override fun getItemCount(): Int =serversList.size

    interface ServerEventListener{
        fun deleteServer(server: Server)
        fun addServer(server: Server)
        fun updateServer(server: Server)

    }
}