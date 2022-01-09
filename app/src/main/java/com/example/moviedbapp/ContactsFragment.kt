package com.example.moviedbapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.databinding.FragmentContactsBinding
import com.example.moviedbapp.model.entities.Contact
import com.example.moviedbapp.ui.rvadapter.ContactsAdapter
import com.example.moviedbapp.utils.callPhone
import com.example.moviedbapp.utils.dialPhone
import com.example.moviedbapp.utils.showAlertDialog

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding: FragmentContactsBinding get() = _binding!!

    private var contactList: List<Contact>? = null

    private val makeCall: (String) -> Unit = {
        callPhone(it)
    }
    
    private val dialNumber: (String) -> Unit = {
        context?.dialPhone(it)
    }

    private val adapter by lazy {
        ContactsAdapter(makeCall, dialNumber)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false).apply {
            initViews()
            checkPermissions()
            setData()
        }
        return binding.root
    }

    private fun checkPermissions() {
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                contactList = getContacts()
            } else {
                context?.let {
                    showAlertDialog(
                        it,
                        title = getString(R.string.dialog_contact_access_denied_title),
                        message = getString(R.string.dialog_contact_access_denied_message)
                    )
                }
            }
        }
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                    contactList = getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showAlertDialog(
                        context = it,
                        title = getString(R.string.dialog_contact_access_title),
                        message = getString(R.string.dialog_contact_access_message),
                        negativeButtonText = getString(R.string.dialog_contact_access_negative_button),
                        positiveButtonText = getString(R.string.dialog_contact_access_positive_button),
                        positiveButtonAction = { requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
                    )
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }
            }

        }
    }

    private fun FragmentContactsBinding.initViews() {
        rvContacts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvContacts.adapter = adapter
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    contactList = getContacts()
                } else {
                    context?.let {
                        showAlertDialog(
                            it,
                            title = getString(R.string.dialog_contact_access_denied_title),
                            message = getString(R.string.dialog_contact_access_denied_message)
                        )
                    }
                }
            }
        }
        return
    }

    @SuppressLint("Range")
    private fun getContacts(): List<Contact> {
        val con: MutableList<Contact> = mutableListOf()
        context?.let {
            val contentResolver: ContentResolver = it.contentResolver
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            cursorWithContacts?.let { cursor ->
                while (cursor.moveToNext()) {
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val phone =
                            try {
                                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            } catch (e: Throwable) {
                                null
                            }
                        val contact = Contact(name, phone)
                        con.add(contact)
                }
            }
            cursorWithContacts?.close()
        }
        return con.toList()
    }

    private fun setData() {
        adapter.setData(contactList)
    }

    companion object {

        const val REQUEST_CODE_CONTACTS = 42
        const val REQUEST_CODE_CALL = 43

        fun newInstance() = ContactsFragment()

    }
}