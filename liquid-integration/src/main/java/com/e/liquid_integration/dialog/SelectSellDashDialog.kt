package com.e.liquid_integration.dialog

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import com.e.liquid_integration.R
import com.e.liquid_integration.`interface`.ValueSelectListner
import com.google.android.material.bottomsheet.BottomSheetDialog


class SelectSellDashDialog(val _context: Context, val listner: ValueSelectListner) : BottomSheetDialog(_context) {

    init {
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        create()
    }

    override fun create() {

        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_sell_dash, null)
        val dialog = BottomSheetDialog(_context, R.style.BottomSheetDialog) // Style here
        dialog.setContentView(bottomSheetView);
        dialog.show()

        /* val bottomSheetView = layoutInflater.inflate(R.layout.dialog_sell_dash, null)
         setContentView(bottomSheetView)*/

        val llCreditCard = bottomSheetView.findViewById(R.id.ll_credit_card) as LinearLayout
        val llCryptocurrency = bottomSheetView.findViewById(R.id.ll_cryptocurrency) as LinearLayout
        val collapseButton = bottomSheetView.findViewById(R.id.collapse_button) as ImageView

        llCreditCard.setOnClickListener {
            dialog.dismiss()
            listner.onItemSelected(1)
        }

        llCryptocurrency.setOnClickListener {
            dialog.dismiss()
            listner.onItemSelected(2)
        }

        collapseButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}