package com.example.tianaserver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tianaserver.data.model.Server
import com.example.tianaserver.databinding.FragmentServerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.greenrobot.eventbus.util.ErrorDialogManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServerBottomSheetDialogFragment : BottomSheetDialogFragment(),ServerAdapter.ServerEventListener {

    val viewModel: ServerViewModel by inject()
    lateinit var binding: FragmentServerBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.serversLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.activityMainServersBottomSheetRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                binding.activityMainServersBottomSheetRv.adapter =
                    ServerAdapter(it  as MutableList<Server> ,this)
            }
        }

        binding.activityMainAddServerBtn.setOnClickListener {
            var addServerDialog = AddServerDialogFragment()
            addServerDialog.show(requireActivity().supportFragmentManager, null)
            dismiss()

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_server_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun deleteServer(server: Server) {
        viewModel.deleteServer(server)
    }

    override fun addServer(server: Server) {
        viewModel.addServer(server)
    }

    override fun updateServer(server: Server) {
        viewModel.updateServer(server)

    }
}