package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.library

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.NotePro
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchBinding
import com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.library.adapter.NotesAdapterLibrary

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var adapter = NotesAdapterLibrary(::onCLick)


    // Sample data
    private val notesList = listOf(
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = R.string.text_asker.toString(),
            image = R.drawable.photo_asker
        ),
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = R.string.text_asker.toString(),
            image = R.drawable.photo_asker
        ),
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = R.string.text_asker.toString(),
            image = R.drawable.photo_asker
        ),
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = R.string.text_asker.toString(),
            image = R.drawable.photo_asker
        )
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        initialize()
        searchCharacterListener()
    }

    private fun initialize() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
        // Initially show all notes
        adapter.submitList(notesList)
    }

    private fun searchCharacterListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    searchCharacter(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })
    }

    private fun searchCharacter(query: String) {
        val filteredList = notesList.filter { note ->
            note.title.contains(query, ignoreCase = true) ||
                    note.description.contains(query, ignoreCase = true)
        }
        adapter.updateSearchQuery(query)
        adapter.submitList(filteredList)
    }

    private fun onCLick(model: NotePro) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                model
            )
        )
    }
}
