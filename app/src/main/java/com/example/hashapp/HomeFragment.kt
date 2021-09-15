package com.example.hashapp

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashapp.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    //We created a view binding variable of type FragmentHomeBinding, which is auto generated as we used viewBinding in gradle files.
    //This is a var and is initialized as null. But whatever changes are reflected in this var are permanently stored in the val type "binding" object.
    //That is why it gets everything from _binding only.

    //Later we inflated the _binding with it's layout and use val type "binding" to return root layout.

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        binding.generateHashButton.setOnClickListener {
            onGenerateClicked()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        //AutoCompleteTextView removes the process of using/making Adapters. We can directly create an arrayAdapter and just give it to its setAdapter() methods.
        val hashAlgos = resources.getStringArray(R.array.hash_algos)
        val arrayAdapter =  ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgos)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    //------------------ Menu Related Functions -----------------------------------//

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.clear_menu)
        {
            binding.editTextMessage.text.clear();
            showSnackBar("Cleared.")
            return true
        }

        return true
    }



    //----------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------User defined functions--------------------------------------------------------//


    private fun onGenerateClicked()
    {
        if(binding.editTextMessage.text.isEmpty())
        {
            showSnackBar("Text is empty!")
        }
        else
        {
            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess(getHashData())
            }
        }
    }

    //Making a function for animations
    private suspend fun applyAnimations()
    {
        //Disables the button as soon as animations start so that no one can click on it multiple times again and crash the app.
        //Because the animation are just gonna make them invisible and don't actually remove them till we shift to the next fragment.
        binding.generateHashButton.isClickable = false

        binding.tvTitleHashAppGen.animate().alpha(0f).duration = 400L
        binding.generateHashButton.animate().alpha(0f).duration = 400L
        binding.textInputLayout.animate().alpha(0f).translationXBy(1200f).duration = 400L
        binding.editTextMessage.animate().alpha(0f).translationXBy(-1200f).duration = 400L

        //delay actually is just like Thread.postDelayed in Java. That is why it's a thread type method for which we use Coroutines here.
        //And to use Coroutines we use suspend functions.
        delay(300)

        binding.successBg.animate().alpha(1f).duration = 600L
        binding.successBg.animate().rotationBy(720f).duration = 600L
        binding.successBg.animate().scaleXBy(900f).duration = 800L
        binding.successBg.animate().scaleYBy(900f).duration = 800L

        delay(500)

        binding.successImageView.animate().alpha(1f).duration = 1000L

        delay(1500)
    }


    private fun getHashData(): String
    {
        val algorithm = binding.autoCompleteTextView.text.toString()
        val text = binding.editTextMessage.text.toString()

        return homeViewModel.getHash(text, algorithm)
    }

    private fun navigateToSuccess(hash: String)
    {
        //Earlier we didn't need to pass any value here. So we just used findNavController to navigate to the Success Fragment using the action property created.
        //findNavController().navigate(R.id.action_homeFragment_to_successFragment)

        //Now we have a String hashValue generated to pass as well.
        //So we make a direction(which is nothing but the arrow/action along with an arg to pass with it as well).
        //This is then provided to the findNavController().navigate() to navigate to the SuccessFragment along with the arg value.

        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hash)
        findNavController().navigate(directions)
    }

    private fun showSnackBar(message: String)
    {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

        //setAction is used to make a button on the snackBar and set it's functionality.
        // Here, we don't want to give any functionality, that's why we left it empty.
        snackBar.setAction("Okay"){}
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.greenish))
        snackBar.show()
    }


} //HomeFragment close.