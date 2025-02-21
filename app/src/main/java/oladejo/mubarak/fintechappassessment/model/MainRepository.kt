package oladejo.mubarak.fintechappassessment.model

class MainRepository {

    private val _accounts = mutableListOf(
        Account("1000000000", "Checking", 1000.0),
        Account("2000000000", "Savings", 2500.0),
        Account("3000000000", "Business", 5000.0),
    )
    fun getAccounts(): List<Account> = _accounts
    fun updateBalance(accountId: String, newBalance: Double) {
        _accounts.find { it.accountId == accountId }?.balance = newBalance
    }
}