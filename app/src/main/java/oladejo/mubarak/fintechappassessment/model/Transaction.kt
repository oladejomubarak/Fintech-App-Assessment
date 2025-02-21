package oladejo.mubarak.fintechappassessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sourceAccountName: String,
    val destinationAccountName: String,
    val amount: Double,
    val timestamp: Long,
    val status:String
)