package oladejo.mubarak.fintechappassessment.view

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import oladejo.mubarak.fintechappassessment.R
import oladejo.mubarak.fintechappassessment.model.Account
import oladejo.mubarak.fintechappassessment.model.MainRepository
import oladejo.mubarak.fintechappassessment.viewModel.TransferViewModel

class TransferActivity : AppCompatActivity() {
    private lateinit var viewModel: TransferViewModel
    private lateinit var spSourceAccount: Spinner
    private lateinit var spDestinationAccount: Spinner
    private lateinit var etTransferAmount: EditText
    private lateinit var btnReview: Button

    private lateinit var accounts: List<Account>
    private var selectedSource: Account? = null
    private var selectedDestination: Account? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        val toolbar = findViewById<MaterialToolbar>(R.id.transferToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Transfer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[TransferViewModel::class.java]

        spSourceAccount = findViewById(R.id.spSourceAccount)
        spDestinationAccount = findViewById(R.id.spDestinationAccount)
        etTransferAmount = findViewById(R.id.etTransferAmount)
        btnReview = findViewById(R.id.btnReview)
        accounts = MainRepository().getAccounts()
        val sourceNames = mutableListOf("Select Source Account")
        sourceNames.addAll(accounts.map { it.accountName })
        val destinationNames = mutableListOf("Select Destination Account")
        destinationNames.addAll(accounts.map { it.accountName })
        val sourceAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            sourceNames
        )
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val destinationAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            destinationNames
        )
        destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spSourceAccount.adapter = sourceAdapter
        spDestinationAccount.adapter = destinationAdapter
        viewModel.errorMessage.observe(this) { msg ->
            if (msg != null) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        viewModel.transferSummary.observe(this) { summary ->
            showTransferSummaryDialog(summary)
        }
        btnReview.setOnClickListener {
            val amount = etTransferAmount.text.toString().toDoubleOrNull() ?: 0.0
            if (spSourceAccount.selectedItemPosition == 0) {
                Toast.makeText(this, "Please select a Source Account.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (spDestinationAccount.selectedItemPosition == 0) {
                Toast.makeText(this, "Please select a Destination Account.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            selectedSource = accounts[spSourceAccount.selectedItemPosition - 1]
            selectedDestination = accounts[spDestinationAccount.selectedItemPosition - 1]
            if (selectedSource == selectedDestination) {
                Toast.makeText(this, "Select different accounts.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.reviewTransfer(selectedSource!!, selectedDestination!!, amount)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showTransferSummaryDialog(summary: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Review Transfer")
            .setMessage(summary)
            .setPositiveButton("Confirm") { _, _ ->
              //  confirmTransfer()
                confirmTransferWithDelay()
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()

        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        positiveButton.setTextColor(getColor(R.color.green))
        negativeButton.setTextColor(getColor(R.color.red))
    }
    private fun confirmTransferWithDelay() {
        val progressDialog = android.app.ProgressDialog(this).apply {
            setTitle("Processing Transfer")
            setMessage("Please wait...")
            setCancelable(false)
            show()
        }
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            progressDialog.dismiss()
            confirmTransfer()
        }, 3000)
    }
    private fun confirmTransfer() {
        val amount = etTransferAmount.text.toString().toDoubleOrNull() ?: 0.0
        viewModel.confirmTransfer(selectedSource!!, selectedDestination!!, amount, "Successful")
        Toast.makeText(this, "Transfer Successful", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}