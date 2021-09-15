package com.example.hashapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    //******************************//

    //We first of all created a navigation graph for our fragments. So we used "Resource Manager" to quickly create a NavGraph XML, and created two fragments there.
    //Then we joined them in the desired manner (using the comfortable "Design" way) and thus their actions are made automatically.

    //Now we have to give this graph to our Main Activity's XML file, and thus we make a "fragment" type in it again. There we drop-make it as a NavHostFragment.
    //Clearly this means that our Activity now has a fragment that is the Host for all the other fragments or basically, host for our NavGraph.

    //Finally we also have to create a NavController that will actually help to manage the app navigation within a NavHost.
    //This is generally created along with a NavHost and thus we call it here using the "findNavController(R.id.fragment)", where the NavHost is defined.

    //Also, to pass data between the fragments, we don't need to create Intent variables or Parcelables. We can do this using SafeArgs directly.
    //We define them in the NavGraph itself.

    //******************************//

    //Finally we set up the "setupActionBarWithNavController(navController)" to coordinate between the ActionBar and the hosted Fragments.
    //This actually helps to directly change the title of the ActionBar as we go through different fragments. Titles are defined in the "label" of every fragment
    //in the NavGraph.

    //Also, "onSupportNavigateUp()" actually helps you to navigate between not only the fragments, but also between the fragment
    //and the activity hosted by the fragment. For e.g.- When we open a link in Gmail, we are directed to that Activity/App/WebPage, but we are still inside
    //the Gmail app as well. It is just hosting the activity. Thus when we click "Back" button, we may be taken to Home.
    //But using this we just NavigateUp the graph created, and thus we will return back to our Gmail app(and thus
    // to the same Activity/Fragment from where we opened the link).

    //The default back button on the ActionBar is also known as the "Up" button (even though it is a left arrow...).

    //******************************//

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragment)
        setupActionBarWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}