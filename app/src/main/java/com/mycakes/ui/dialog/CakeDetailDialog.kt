package com.mycakes.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.mycakes.R
import com.mycakes.data.model.Cake
import kotlinx.android.synthetic.main.dialog_cake_detail.*

class CakeDetailDialog : DialogFragment() {
    companion object {
        const val ARG_CAKE = "arg_cake"
        fun instance(cake: Cake): CakeDetailDialog {
            val cakeDetailDialog = CakeDetailDialog()
            val arguments = Bundle()
            arguments.putParcelable(ARG_CAKE, cake)
            cakeDetailDialog.arguments = arguments
            return cakeDetailDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_cake_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cake = arguments?.getParcelable<Cake>(ARG_CAKE)
        cake?.apply {
            tvCakeDetail.text = this.desc
        }
        btnOk.setOnClickListener { dismiss() }

    }
}