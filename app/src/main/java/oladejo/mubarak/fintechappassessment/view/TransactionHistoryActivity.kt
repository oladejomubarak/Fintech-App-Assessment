package oladejo.mubarak.fintechappassessment.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import oladejo.mubarak.fintechappassessment.R
import oladejo.mubarak.fintechappassessment.viewModel.TransactionHistoryViewModel

class TransactionHistoryActivity : AppCompatActivity() {
    private lateinit var viewModel: TransactionHistoryViewModel
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        val toolbar = findViewById<MaterialToolbar>(R.id.historyToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Transaction History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this)[TransactionHistoryViewModel::class.java]
        val rvTransactions = findViewById<RecyclerView>(R.id.rvTransactions)
        rvTransactions.layoutManager = LinearLayoutManager(this)
        adapter = TransactionAdapter()
        rvTransactions.adapter = adapter
        viewModel.allTransactions.observe(this) { transactionList ->
            adapter.setData(transactionList)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}