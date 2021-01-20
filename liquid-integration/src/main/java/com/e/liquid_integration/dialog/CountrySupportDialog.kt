package com.e.liquid_integration.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.e.liquid_integration.R
import com.e.liquid_integration.data.LiquidConstants
import com.e.liquid_integration.ui.LiquidLoginActivity

class CountrySupportDialog(val _context: Context) : Dialog(_context, R.style.Theme_Dialog) {

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_crypto_support)

        findViewById<Button>(R.id.btnOkay).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                dismiss()
            }
        })

        findViewById<TextView>(R.id.txtCountrySupported).setOnClickListener {
            dismiss()

            val intent = Intent(_context, LiquidLoginActivity::class.java)
            intent.putExtra("url", LiquidConstants.COUNTRY_NOT_SUPPORTED)
            intent.putExtra("title", "Liquid")
            _context.startActivity(intent)

        }
    }

}