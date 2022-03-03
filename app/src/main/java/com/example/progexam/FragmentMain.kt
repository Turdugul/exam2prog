package com.example.progexam

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progexam.databinding.FragmentMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FragmentMain: Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val api get() = Injector.rickandmortyapi
    private lateinit var listener: Click

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Click
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        refreshApp()

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = Adapter {
            listener.onClick(it.id!!)
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        api.getAllCharacters()
            .subscribeOn(Schedulers.io())
            .map {
                val listChar = mutableListOf<Character>()
                it.results.forEach { it ->
                    val char = Character(
                        id = it.id,
                        name = it.name,
                        status = it.status,
                        species = it.species,
                        type = it.type,
                        gender = it.gender,
                        origin = it.origin,
                        location = it.location,
                        image = it.image,
                        episode = it.episode,
                        url = it.url,
                        created = it.created
                    )
                    listChar.add(char)
                }
                listChar.toList()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                adapter.setData(it)
            }
            .doOnError {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
            .subscribe()
    }

    private fun refreshApp() {
        binding.swipeToRefresh.setOnRefreshListener {
            Toast.makeText(requireContext(), "page refreshed!", Toast.LENGTH_SHORT).show()
            binding.swipeToRefresh.isRefreshing = false
        }
    }
}

