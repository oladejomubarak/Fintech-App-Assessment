package oladejo.mubarak.fintechappassessment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    fun loginUser(email: String, password: String) {
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val existingMethods = task.result?.signInMethods

                    if (existingMethods.isNullOrEmpty()) {
                        registerUser(email, password)
                    } else {
                        signInUser(email, password)
                    }
                } else {
                    _loginError.value = "Error checking user existence: ${task.exception?.message}"
                    _loginResult.value = false
                }
            }
    }

    private fun registerUser(email: String, password: String) {
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signInUser(email, password)
                } else {
                    // If registration fails because credentials exist, user login is follows because there is no actual registration screen
                   // _loginError.value = "Registration failed: ${task.exception?.message}"
                    //_loginResult.value = false
                    signInUser(email, password)
                }
            }
    }

    private fun signInUser(email: String, password: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginResult.value = true
                } else {
                    _loginError.value = task.exception?.message ?: "Login failed due to an unknown error"
                    _loginResult.value = false
                }
            }
    }
}