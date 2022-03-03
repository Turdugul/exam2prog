package com.example.progexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), Click{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
    .add(R.id.frag_cont, FragmentMain())
    .commit()
}


    override fun onClick(id: Long) {
    val fragment = FragmentDetail()
    val bundle = Bundle()
    bundle.putLong("KEY_ID", id)
    fragment.arguments = bundle
    supportFragmentManager.beginTransaction()
        .replace(R.id.frag_cont, fragment)
        .addToBackStack(null)
        .commit()
}
}