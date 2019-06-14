package com.example.android.pilgrim.signUp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.responses.CategoryNationalityResponse
import com.example.android.pilgrim.nationality.NationalityActivity
import com.example.android.pilgrim.utils.Permissions
import com.example.android.pilgrim.utils.Validation.Companion.isFieldValid
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_user_data.*

class UserDataFragment : Fragment(), BlockingStep {
    val PICK_IMAGE_REQUEST = 1
    val PICK_NATIONALITY_REQUEST = 2
    var filePath: Uri? = null
    var nationality: String? = null

    val isImageUploaded = MutableLiveData<Boolean>()

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback!!.goToPrevStep()
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        if (isUserDataValid()) {
            val parentActivity = (activity as SignUpActivity)
            parentActivity.pilgrim.firstName = first_name.text.toString()
            parentActivity.pilgrim.lastName = last_name.text.toString()
            parentActivity.pilgrim.phoneNumber = phone.text.toString().toInt()
            parentActivity.pilgrim.gender = getGender()
            parentActivity.pilgrim.nationality = nationality
            if (filePath != null) {
                uploadImage()

                isImageUploaded.observe(this, androidx.lifecycle.Observer {
                    when (it) {
                        true -> callback!!.complete()
                        false -> Toast.makeText(
                            context,
                            getString(R.string.image_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else
                callback!!.complete()
        }
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
    }

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user_data, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_nationality.setOnClickListener {
            startActivityForResult(
                Intent(context, NationalityActivity::class.java),
                PICK_NATIONALITY_REQUEST
            )
        }

        btn_choose_image.setOnClickListener {
            Permissions.isStoragePermissionGranted(context!!)
            chooseImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_NATIONALITY_REQUEST -> {
                if (data != null) {
                    nationality =
                        (data.getSerializableExtra("nationality") as CategoryNationalityResponse).name
                    tv_nationality.text = nationality
                }
            }
            PICK_IMAGE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK
                    && data != null && data.data != null
                ) {
                    filePath = data.data
                    Glide.with(context!!).load(filePath).into(image)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun isUserDataValid()
            : Boolean {

        return isFieldValid(context!!, first_name) && isFieldValid(
            context!!,
            last_name
        ) && isFieldValid(context!!, phone) && hasNationality()
    }


    private fun getGender()
            : String {
        return when (segmented.checkedRadioButtonId) {
            male_radio_button.id -> "M"

            female_radio_button.id -> "F"

            else -> ""
        }
    }

    fun hasNationality(): Boolean {
        if (nationality == null) {
            tv_nationality.error = getString(R.string.required_field)
            return false
        } else
            return true
    }


    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle(getString(R.string.uploading))
        progressDialog.show()

        val parentActivity = (activity as SignUpActivity)

        val ref = FirebaseStorage.getInstance()
            .reference.child("images/${parentActivity.pilgrim.username}")
        var uploadTask = ref.putFile(filePath!!)

        val urlTask =
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                    isImageUploaded.value = false
                    progressDialog.dismiss()
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val parentActivity = (activity as SignUpActivity)
                    parentActivity.pilgrim.image = downloadUri.toString()
                    isImageUploaded.value = true

                } else {
                    isImageUploaded.value = false
                }
                progressDialog.dismiss()
            }
    }
}
