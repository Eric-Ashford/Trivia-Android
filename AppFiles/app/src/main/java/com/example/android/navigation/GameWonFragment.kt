/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        //Set Next Match Button onClickListener to Game Fragment
        //Method used is NavDirection Classes
        binding.nextMatchButton.setOnClickListener{view : View ->
            view.findNavController()
                    .navigate(GameWonFragmentDirections
                            .actionGameWonFragmentToGameFragment())
        }

        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()

        setHasOptionsMenu(true)

        return binding.root
    }

    //Creating our share Intent
    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        return shareIntent
    }

    //Starting an Activity with our new Intent
    private fun shareSuccess(){
        startActivity(getShareIntent())
    }

    /*  Showing the Share Menu Dynamically:
        Use getShareIntent() to get the shareIntent. To make sure the shareIntent resolves to an Activity, check
        with the Android package manager ( PackageManager), which keeps track of the apps and activities installed on the
        device. Use the Activity's packageManager property to gain access to the package manager, and call
        resolveActivity(). If the result equals null, which means that the shareIntent doesn't resolve, find the sharing
        menu item from the inflated menu and make the menu item invisible.
    */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        if(getShareIntent().resolveActivity(requireActivity().packageManager)==null){
            menu.findItem(R.id.share).isVisible = false
        }
    }

    //Sharing from the Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }

        return super.onOptionsItemSelected(item)
    }
}
