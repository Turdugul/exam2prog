package com.example.progexam

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.progexam.databinding.FragmentDetailBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FragmentDetail: Fragment(R.layout.fragment_detail) {
    private val api get() = Injector.rickandmortyapi
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: Click

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Click
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        val id = arguments?.getLong("KEY_ID") ?: -1L

        binding.apply {
            api.getSingleChar(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    val img = imgItem
                    Glide.with(requireContext())
                        .load(it.image)
                        .into(img)
                    nameItem.text = it.name
                    statusItem.text = it.status
                    speciesItem.text = it.species
                    typeItem.text = it.type
                    genderItem.text = it.gender
                    originItem.text = it.origin.name
                    locationNameItem.text = it.location.url
                    episodeItem.text = it.episode.toString()
                    urlItem.text = it.url
                    createdItem.text = it.created

                }
                .doOnError {
                    Toast.makeText(requireContext(), " detail error!", Toast.LENGTH_SHORT)
                        .show()
                }
                .subscribe()
        }
    }
}