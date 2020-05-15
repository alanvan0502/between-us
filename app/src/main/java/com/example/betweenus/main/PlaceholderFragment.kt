package com.example.betweenus.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.betweenus.R
import kotlinx.android.synthetic.main.fragment_main.*

class PlaceholderFragment : Fragment() {

    companion object {
        private const val ARG_SECTION_NUMBER = "Section number"

        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        section_label.text = "Hello world from section ${arguments?.getInt(ARG_SECTION_NUMBER)}"
    }
}