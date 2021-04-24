package com.example.hashapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashapp.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment() {

    //This is used to get all the args values received in this Fragment using SafeArgs and Navigate.
    private val args: SuccessFragmentArgs by navArgs()

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)

        binding.successHashTextView.text = args.hashValue

        binding.successCopyButton.setOnClickListener {
            onCopyClicked()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //----------------------------------------------User defined functions--------------------------------------------------------//
    //----------------------------------------------------------------------------------------------------------------------------//

    private fun onCopyClicked() {
        lifecycleScope.launch {
            copyToClipboard(args.hashValue)
            applyAnimations()
        }
    }

    private fun copyToClipboard(hashValue: String) {

        //ClipBoardManager is used to get the system clipboard service for copying the data and storing it on the clipboard.
        //We have to pass that data here for which we create a ClipData value.//
        // (where first thing is the description of the data and the second thing is the data itself).

        val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted text", hashValue)
        clipboardManager.setPrimaryClip(clipData)
    }

    private suspend fun applyAnimations()
    {
        binding.include.messageBg.animate().translationY(80f).duration = 200L
        binding.include.messageTv.animate().translationY(80f).duration = 200L

        delay(2000L)

        binding.include.messageBg.animate().translationY(-80f).duration = 500L
        binding.include.messageTv.animate().translationY(-80f).duration = 500L
    }

}