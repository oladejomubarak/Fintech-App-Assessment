package oladejo.mubarak.fintechappassessment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import oladejo.mubarak.fintechappassessment.model.Account
import oladejo.mubarak.fintechappassessment.model.MainRepository

class HomeViewModel : ViewModel() {
    private val repository = MainRepository()

    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> = _accounts

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        _accounts.value = repository.getAccounts()
    }
}