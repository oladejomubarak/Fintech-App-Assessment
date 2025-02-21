package oladejo.mubarak.fintechappassessment.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import oladejo.mubarak.fintechappassessment.R
import oladejo.mubarak.fintechappassessment.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: AccountListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val rvAccounts = findViewById<RecyclerView>(R.id.rvAccounts)
        rvAccounts.layoutManager = LinearLayoutManager(this)

        viewModel.accounts.observe(this) { accountList ->
            adapter = AccountListAdapter(accountList) { account ->
                Toast.makeText(this, "Selected ${account.accountName}", Toast.LENGTH_SHORT).show()
            }
            rvAccounts.adapter = adapter
        }

        val btnTransfer = findViewById<Button>(R.id.btnTransfer)
        val btnHistory = findViewById<Button>(R.id.btnHistory)

        btnTransfer.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        btnHistory.setOnClickListener {
            val intent = Intent(this, TransactionHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}