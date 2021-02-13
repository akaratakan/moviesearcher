package com.atakanakar.challenge.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.atakanakar.challenge.R

class InfoDialog(
    private val titleText: String,
    private val messageText: String,
    private val functionOk: (() -> Unit)? = null
) : DialogFragment() {

    var txtTitle: TextView? = null
    var txtMessage: TextView? = null
    var txtOk: TextView? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val root = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_warning, null)
        txtTitle = root.findViewById(R.id.txtTitle)
        txtMessage = root.findViewById(R.id.txtContent)
        txtOk = root.findViewById(R.id.txtOk)
        isCancelable = false
        txtTitle?.text = if (titleText.isBlank()) getString(R.string.error) else titleText
        txtMessage?.text = if (messageText.isBlank()) getString(R.string.error_content) else messageText
        txtOk?.setOnClickListener {
            dismiss()
            functionOk?.let { it() }
        }
        return root
    }

}