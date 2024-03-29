package com.example.ezmoneybettingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OffersDialogFragment(
    private val offer_titles: ArrayList<String>,
    private val offer_names: ArrayList<String>
) : DialogFragment() {


    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView: View =
            inflater.inflate(R.layout.dialog_fragment_with_recycler_view, container, false)

        val recyclerViewAdapter = RecyclerViewAdapter(offer_names)
        val offersList = rootView.findViewById<RecyclerView>(R.id.itemsList)
        val mSocket = SocketHandler.getSocket()

        offersList.apply {
            // vertical layout
            layoutManager = LinearLayoutManager(requireContext())
            // set adapter
            adapter = recyclerViewAdapter

            // Touch handling
            offersList.addOnItemTouchListener(
                RecyclerTouchListener(
                    requireContext(),
                    offersList
                ) { _, position ->
                    mSocket.emit("offer_chosen", offer_titles[position], offer_names[position])
                    dismiss()
                })
        }

        return rootView
    }
}

