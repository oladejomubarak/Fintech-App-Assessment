package oladejo.mubarak.fintechappassessment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import oladejo.mubarak.fintechappassessment.model.Account
import oladejo.mubarak.fintechappassessment.model.MainRepository
import oladejo.mubarak.fintechappassessment.model.Transaction
import oladejo.mubarak.fintechappassessment.model.TransactionRepository

class TransferViewModel(application: Application) : AndroidViewModel(application){
    private val repository = MainRepository()
    private val transactionRepository = TransactionRepository(application)

    val transferSummary = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun reviewTransfer(source: Account, destination: Account, amount: Double) {
        if (amount <= 0.0) {
            errorMessage.value = "Invalid transfer amount."
            return
        }
        if (amount > source.balance) {
            errorMessage.value = "Amount exceeds source account balance."
            return
        }
        val summary = "Transferring \$${amount} from ${source.accountName} to ${destination.accountName}."
        transferSummary.value = summary
    }

    fun confirmTransfer(source: Account, destination: Account, amount: Double, status: String) {
        repository.updateBalance(source.accountId, source.balance - amount)
        repository.updateBalance(destination.accountId, destination.balance + amount)

        val transaction = Transaction(
            sourceAccountName = source.accountName,
            destinationAccountName = destination.accountName,
            amount = amount,
            timestamp = System.currentTimeMillis(),
            status = status
        )
        transactionRepository.insertTransaction(transaction)
    }
}