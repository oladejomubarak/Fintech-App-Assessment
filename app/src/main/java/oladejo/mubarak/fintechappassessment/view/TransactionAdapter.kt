package oladejo.mubarak.fintechappassessment.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import oladejo.mubarak.fintechappassessment.R
import oladejo.mubarak.fintechappassessment.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder>()  {

    private var transactions: List<Transaction> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Transaction>) {
        transactions = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSource: TextView = itemView.findViewById(R.id.tvSource)
        private val tvDestination: TextView = itemView.findViewById(R.id.tvDestination)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)

        fun bind(transaction: Transaction) {
            tvSource.text = "From: ${transaction.sourceAccountName}"
            tvDestination.text = "To: ${transaction.destinationAccountName}"
            tvAmount.text = "Amount: \$${transaction.amount}"
            tvStatus.text = "Status: ${transaction.status}"

            val date = Date(transaction.timestamp)
            val format = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault())
            val dateString = format.format(date)
            tvTimestamp.text = dateString
        }
    }
}