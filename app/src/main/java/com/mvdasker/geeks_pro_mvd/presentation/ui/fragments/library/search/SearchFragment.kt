package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.labrary.NotePro
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.adapter.NotesAdapterLibrary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var adapter = NotesAdapterLibrary(::onCLick)

    private val notesList = listOf(
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = "Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип",
            image = R.drawable.photo_asker
        ),
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = "Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип",
            image = R.drawable.photo_asker
        ),
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = "Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип",
            image = R.drawable.photo_asker
        ),
        NotePro(
            title = "Руководство войсковой части 7705",
            description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
            text = "Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип Руководство войсковой части 7705 регламентирует организацию, управление и функционирование воинского подразделения. Основные аспекты включают цели и задачи части (обеспечение боевой готовности, охрана государственной границы, участие в миротворческих операциях), структуру и численность (описание подразделений и их функций), а также командование и управление (права, обязанности и ответственность командира части, организация работы штаба и командиров подразделений). Важным элементом является организация службы, включая распорядок дня, дисцип",
            image = R.drawable.photo_asker
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        deleteClearBtn()
        initialize()
        searchCharacterListener()
    }

    private fun initialize() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
        adapter.submitList(notesList)
    }

    private fun searchCharacterListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    searchCharacter(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchCharacter(query: String) {
        val filteredList = notesList.filter { note ->
            note.title?.contains(query, ignoreCase = true) == true ||
                    note.description?.contains(query, ignoreCase = true) == true
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

    private fun deleteClearBtn() {
        binding.deleteClearBtn.setOnClickListener {
            if (binding.etSearch.text != null) {
                binding.etSearch.text = null
                binding.etSearch.clearFocus()
            }
        }
    }

}