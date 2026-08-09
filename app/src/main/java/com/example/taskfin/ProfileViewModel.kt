package com.example.taskfin

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ImageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    // Variabel untuk menyimpan kata sandi dinamis tiap akun
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status.asStateFlow()

    private val _university = MutableStateFlow("")
    val university: StateFlow<String> = _university.asStateFlow()

    private val _semester = MutableStateFlow("")
    val semester: StateFlow<String> = _semester.asStateFlow()

    private val _jurusan = MutableStateFlow("")
    val jurusan: StateFlow<String> = _jurusan.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    fun isEmailRegistered(userEmail: String): Boolean {
        return _email.value.isNotBlank() && _email.value.equals(userEmail, ignoreCase = true)
    }

    // Perbarui fungsi register data agar sekaligus bisa menyimpan sandi yang diinput user
    fun setRegisterData(name: String, userEmail: String, pass: String = "") {
        _fullName.value = name
        _email.value = userEmail
        if (pass.isNotBlank()) {
            _password.value = pass
        }
    }

    // Fungsi khusus untuk mengatur atau memperbarui sandi (misal: saat Register atau Ubah Sandi)
    fun setPassword(newPass: String) {
        _password.value = newPass
    }

    fun updatePassword(newPass: String) {
        _password.value = newPass
    }

    fun updateProfileData(
        name: String = _fullName.value,
        univ: String = _university.value,
        bDate: String = _birthDate.value,
        gen: String = _gender.value,
        addr: String = _address.value,
        phone: String = _phoneNumber.value,
        sem: String = _semester.value,
        jur: String = _jurusan.value,
        userStatus: String = _status.value
    ) {
        _fullName.value = name
        _university.value = univ
        _birthDate.value = bDate
        _gender.value = gen
        _address.value = addr
        _phoneNumber.value = phone
        _semester.value = sem
        _jurusan.value = jur
        _status.value = userStatus
    }

    fun updateProfileImage(context: Context, tempUri: Uri) {
        val appContext = context.applicationContext
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val permanentUri = ImageUtils.saveImageToInternalStorage(appContext, tempUri)
                val updatedUri = Uri.parse("$permanentUri?time=${System.currentTimeMillis()}")
                _profileImageUri.value = updatedUri
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}