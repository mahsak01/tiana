package com.example.tianaserver

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.tianaserver.data.model.Server
import com.example.tianaserver.databinding.LayoutServerAddServerDialogBinding
import com.google.android.material.snackbar.Snackbar
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.util.ArrayList


class AddServerDialogFragment(val addServerDialogEventListener: AddServerDialogEventListener) : DialogFragment(), PickiTCallbacks {
    private lateinit var binding: LayoutServerAddServerDialogBinding
    private var pickiT: PickiT? = null
    private var serverAddress: String? = null
    private var reportingServer: String? = null
    private var serverName: String? = null



    private val filePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    it.data?.let { it1 ->
                        pickiT?.getPath(it1, Build.VERSION.SDK_INT)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickiT = PickiT(requireContext(), this, requireActivity())
    }


    override fun onResume() {
        super.onResume()
        this@AddServerDialogFragment.binding.layoutServerFileUriTv.isSelected=true
        this.setListeners()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.layout_server_add_server_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

    private fun setListeners() {
        this.binding.layoutServerChooseFileBtn.setOnClickListener {
            filePickerLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).also {
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                it.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                it.putExtra("type1", "txt")
                it.type = "text/*"
            })
        }
        this.binding.layoutServerAddServerBtn.setOnClickListener {
            serverName = this.binding.layoutServerServerNameTI.editText?.text.toString()
            if (serverName!=null && serverName!="") {
                if (serverAddress != null && reportingServer != null) {
                    val server = Server(0, serverName!!, serverAddress!!, reportingServer!!, false)
                    addServerDialogEventListener.add(server)
                    showToast("سرور اضافه شد")
                    dismiss()
                } else
                    showToast("فایل خود را به درستی انتخاب کنید")
            } else
                showToast("نام سرور را وارد کنید")
        }
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun PickiTonUriReturned() {
    }

    override fun PickiTonStartListener() {
    }

    override fun PickiTonProgressUpdate(progress: Int) {
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        try {
            if (wasSuccessful) {
                path?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            val file = File(path)
                            val bufferedReader = file.bufferedReader()
                            serverAddress = bufferedReader.readLine()
                            reportingServer = bufferedReader.readLine()
                            this@AddServerDialogFragment.binding.layoutServerFileUriTv.text=path
                        } catch (e: Exception) {
                            Timber.e(e.message)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e)


        }
    }

    override fun PickiTonMultipleCompleteListener(
        paths: ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
    }

    interface AddServerDialogEventListener{
        fun add(server: Server)
    }
}