package oladejo.mubarak.fintechappassessment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import oladejo.mubarak.fintechappassessment.model.Transaction
import oladejo.mubarak.fintechappassessment.model.TransactionRepository

class TransactionHistoryViewModel (application: Application) : AndroidViewModel(application)  {
    private val transactionRepository = TransactionRepository(application)
    val allTransactions: LiveData<List<Transaction>> = transactionRepository.getAllTransactions()
}