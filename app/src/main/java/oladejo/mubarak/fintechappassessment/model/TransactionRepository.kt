package oladejo.mubarak.fintechappassessment.model

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TransactionRepository (context: Context) {
    private val transactionDao = AppDatabase.getDatabase(context).transactionDao()

    fun insertTransaction(transaction: Transaction) {
        GlobalScope.launch {
            transactionDao.insertTransaction(transaction)
        }
    }

    fun getAllTransactions(): LiveData<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }
}