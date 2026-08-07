package com.example.taskfin

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthHelper(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    suspend fun signInWithGoogle(
        webClientId: String,
        onSuccess: (email: String, name: String) -> Unit,
        onError: (String) -> Unit
    ) {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val email = googleIdTokenCredential.id
                val displayName = googleIdTokenCredential.displayName ?: "Pengguna Google"

                onSuccess(email, displayName)
            } else {
                onError("Tipe kredensial tidak valid")
            }
        } catch (e: GetCredentialException) {
            onError("Gagal Login Google: ${e.localizedMessage}")
        } catch (e: Exception) {
            onError("Terjadi kesalahan: ${e.localizedMessage}")
        }
    }
}