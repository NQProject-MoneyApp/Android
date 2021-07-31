package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.MoneyAppClient.icons
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.*

class AddGroupViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    init {}

    suspend fun addGroup(name: String, icon: MoneyAppIcon): SimpleResult<String> {
        _loading.value = true
        val result = GroupRepository.addGroup(name=name, icon=icon.id)
        _loading.value = false
        return result
    }

    suspend fun icons(): SimpleResult<List<MoneyAppIcon>> {
        val result = IconsRepository.icons()
        return result
    }
}