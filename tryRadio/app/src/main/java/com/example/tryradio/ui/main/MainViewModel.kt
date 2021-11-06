package com.example.tryradio.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var season: MutableLiveData<String> = MutableLiveData()
    private var checkBox1: MutableLiveData<Boolean> = MutableLiveData(false)
    private var checkBox2: MutableLiveData<Boolean> = MutableLiveData(false)
    private var checkBox3: MutableLiveData<Boolean> = MutableLiveData(false)
    private var checkBox4: MutableLiveData<Boolean> = MutableLiveData(false)

    private val itemsChecked = mutableListOf<String>()

    fun setSeason(text: String) {
        season.value = text
    }

    fun getSeason() : MutableLiveData<String> {
        return season;
    }


    fun setCheck(value: Boolean, name: String) {
        Log.i("VIEWMODEL2", value.toString() + " " + name)
        when (name) {
            "CheckBox1" -> checkBox1.value = value
            "CheckBox2" -> checkBox2.value = value
            "CheckBox3" -> checkBox3.value = value
            "CheckBox4" -> checkBox4.value = value
        }
        if (value) {addToList(name)}
    }

    fun getCheckValue(name: String) : Boolean {

        var out: Boolean?
        when (name) {
            "checkBox1" -> out = checkBox1.value
            "checkBox2" -> out =  checkBox2.value
            "checkBox3" -> out =  checkBox3.value
            else -> out =  checkBox4.value
        }

        return out ?: false
    }

    fun addToList(boxName: String) {
        itemsChecked.add(boxName)
    }

    fun getFullList() : String {
        var out = ""
        for (item in itemsChecked)
            out += item + " "
        return out
    }


}