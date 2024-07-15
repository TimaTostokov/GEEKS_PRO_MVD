package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.NotePro
import com.mvdasker.geeks_pro_mvd.databinding.FragmentLibraryBinding
import com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.library.adapter.NotesAdapterLibrary

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private val adapter = NotesAdapterLibrary(::onClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        goToSearch()
        setupListeners()
    }

    private fun initialize() {
        binding.recyclerView.adapter = adapter
    }

    private fun goToSearch() {
        binding.etSearch.setOnClickListener {
            findNavController().navigate(LibraryFragmentDirections.actionLibraryFragmentToSearchFragment())
        }
    }

    private fun setupListeners() {
        val notesList = listOf(
            NotePro(
                title = "Руководство войсковой части 7705",
                description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
                text = "С другой стороны, сплочённость команды профессионалов способствует повышению качества первоочередных требований. Принимая во внимание показатели успешности, реализация намеченных плановых заданий предоставляет широкие возможности для переосмысления внешнеэкономических политик.\n" +
                        "Задача организации, в особенности же сложившаяся структура организации напрямую зависит от кластеризации усилий. Таким образом, перспективное планирование создаёт необходимость включения в производственный план целого ряда внеочередных мероприятий с учётом комплекса поэтапного и последовательного развития общества. Равным образом, консультация с широким активом способствует повышению качества направлений прогрессивного развития. Лишь диаграммы связей формируют глобальную экономическую сеть и при этом —  обнародованы.\n" +
                        "Имеется спорная точка зрения, гласящая примерно следующее: независимые государства, инициированные исключительно синтетически, обнародованы. Безусловно, социально-экономическое развитие выявляет срочную потребность экономической целесообразности принимаемых решений. Сложно сказать, почему стремящиеся вытеснить традиционное производство, нанотехнологии и по сей день остаются уделом либералов, которые жаждут быть ассоциативно распределены по отраслям.\n" +
                        "С другой стороны, сплочённость команды профессионалов способствует повышению качества первоочередных требований. ",
                image = R.drawable.photo_asker
            ),
            NotePro(
                title = "Руководство войсковой части 7705",
                description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
                text = "yuwfuuwgfwefyowfiuhewufhweiuhfewhufhewiufuewihfrgfuirewvuh",
                image = R.drawable.photo_asker
            ),
            NotePro(
                title = "Руководство войсковой части 7705",
                description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
                text = "yuwfuuwgfwefyowfiuhewufhweiuhfewhufhewiufuewihfrgfuirewvuh",
                image = R.drawable.photo_asker
            ),
            NotePro(
                title = "Руководство войсковой части 7705",
                description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
                text = "yuwfuuwgfwefyowfiuhewufhweiuhfewhufhewiufuewihfrgfuirewvuh",
                image = R.drawable.photo_asker
            ),
            NotePro(
                title = "Руководство войсковой части 7705",
                description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
                text = "yuwfuuwgfwefyowfiuhewufhweiuhfewhufhewiufuewihfrgfuirewvuh",
                image = R.drawable.photo_asker
            ),
            NotePro(
                title = "Руководство войсковой части 7705",
                description = "Lorem ipsum dolor sit amet consectetur. Lorem ipsum...",
                text = "yuwfuuwgfwefyowfiuhewufhweiuhfewhufhewiufuewihfrgfuirewvuh",
                image = R.drawable.photo_asker
            )
        )
        adapter.submitList(notesList)
    }

    private fun onClick(model: NotePro) {
        findNavController().navigate(
            LibraryFragmentDirections.actionLibraryFragmentToDetailFragment(
                model
            )
        )
    }
}