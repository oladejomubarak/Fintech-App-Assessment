package oladejo.mubarak.fintechappassessment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import oladejo.mubarak.fintechappassessment.R
import oladejo.mubarak.fintechappassessment.model.Account

class AccountListAdapter(
    private var accounts: List<Account>,
    private val onAccountClick: (Account) -> Unit
) : RecyclerView.Adapter<AccountListAdapter.AccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(accounts[position])
    }

    override fun getItemCount(): Int = accounts.size

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvAccountId: TextView = itemView.findViewById(R.id.tvAccountId)
        private val tvAccountName: TextView = itemView.findViewById(R.id.tvAccountName)
        private val tvAccountBalance: TextView = itemView.findViewById(R.id.tvAccountBalance)

        fun bind(account: Account) {
            tvAccountId.text = "Account ID: ${account.accountId}"
            tvAccountName.text = account.accountName
            tvAccountBalance.text = "Balance: \$${account.balance}"
            itemView.setOnClickListener {
                onAccountClick(account)
            }
        }
    }
}