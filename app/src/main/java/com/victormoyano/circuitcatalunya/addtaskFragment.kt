package com.victormoyano.circuitcatalunya

import android.Manifest
import android.R.attr
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.victormoyano.circuitcatalunya.adapters.AsignarAdapter
import com.victormoyano.circuitcatalunya.adapters.TipusMantenimentAdapter
import com.victormoyano.circuitcatalunya.adapters.ZonasAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Averias
import com.victormoyano.circuitcatalunya.models.TipoAverias
import com.victormoyano.circuitcatalunya.models.UsersLista
import com.victormoyano.circuitcatalunya.models.Zonas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.internal.wait
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.Handler


class addtaskFragment : Fragment() {

    private lateinit var imageButton: ImageButton
    private lateinit var problemEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var tipusMantenimentSpinner: Spinner
    private lateinit var zonesSpinner: Spinner
    private lateinit var asignarSpinner: Spinner
    private lateinit var PrioritatSpinner: Spinner
    private lateinit var ButtonEnviar: Button
    private val REQUEST_IMAGE_CAPTURE = 1
    private val CAMERA_PERMISSION_CODE = 101
    private val idLogat: Int = HomeActivity.IdLogatHolder.getIdLogat()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_addtask, container, false)

        imageButton = view.findViewById(R.id.imageButton)
        problemEditText = view.findViewById(R.id.problem)
        descriptionEditText = view.findViewById(R.id.description)
        tipusMantenimentSpinner = view.findViewById(R.id.TipusManteniment)
        zonesSpinner = view.findViewById(R.id.Zones)
        asignarSpinner = view.findViewById(R.id.Asignar)
        PrioritatSpinner = view.findViewById(R.id.Prioritat)
        ButtonEnviar = view.findViewById(R.id.btnEnviar)

        // Aquí puedes agregar la lógica para manejar los eventos de los elementos de la interfaz de usuario.
        // Por ejemplo, puedes agregar un OnClickListener al ImageButton para cambiar la imagen cuando se haga clic en él.
        imageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val responseZonas = RetrofitConnection.service.getZonas()
                val responseTipusManteniment = RetrofitConnection.service.getTipoAverias()
                val responseAsignar = RetrofitConnection.service.getUsers()
                val zonasObject = responseZonas.body()
                val tipusMantenimentObject = responseTipusManteniment.body()
                val asignarObject = responseAsignar.body()

                val prioridadOptions = arrayOf("alta", "media", "baja")
                val prioridadAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    prioridadOptions
                )
                PrioritatSpinner.adapter = prioridadAdapter

                // Verificamos si la respuesta no es nula y si contiene la lista de zonas
                if (zonasObject != null && zonasObject.isNotEmpty()) {
                    val zonasAdapter = ZonasAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        zonasObject
                    )
                    zonesSpinner.adapter = zonasAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e(
                        "addtaskFragment",
                        "La respuesta del servicio no contiene la lista de zonas esperada"
                    )
                }
                Log.d("addtaskFragmentTipus", tipusMantenimentObject.toString())
                if (tipusMantenimentObject != null && tipusMantenimentObject != null) {
                    val tipusMantenimentAdapter = TipusMantenimentAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        tipusMantenimentObject
                    )
                    tipusMantenimentSpinner.adapter = tipusMantenimentAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e(
                        "addtaskFragment",
                        "La respuesta del servicio no contiene la lista de zonas esperada"
                    )
                }
                Log.d("addtaskFragment", asignarObject.toString())
                if (asignarObject != null && asignarObject != null) {
                    val asignarAdapter = AsignarAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        asignarObject
                    )
                    asignarSpinner.adapter = asignarAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e(
                        "addtaskFrag",
                        "La respuesta del servicio no contiene la lista de zonas esperada"
                    )
                }
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir al obtener los datos de Retrofit
                Log.e("addtask", e.toString())
            }

        }
            return view
    }
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // Manejar excepción si la aplicación de cámara no está disponible
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                // Permiso denegado, puedes mostrar un mensaje al usuario informando que la funcionalidad de la cámara no está disponible.
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("addtaskFragment", idLogat.toString())
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageButton.setImageBitmap(imageBitmap)

            // Guardar la imagen con un nombre único
            val imageName = "image_${System.currentTimeMillis()}.jpg"
            val imagePath = saveImageToInternalStorage(imageBitmap, imageName)
            //pon un delayed al button enviar
            ButtonEnviar.setOnClickListener {
                sendImageToLaravel(imagePath, imageName);
                    val problem = problemEditText.text.toString()
                    val description = descriptionEditText.text.toString()
                    val tipusManteniment = tipusMantenimentSpinner.selectedItem as TipoAverias
                    val zona = zonesSpinner.selectedItem as Zonas
                    val asignar = asignarSpinner.selectedItem as UsersLista
                    val prioridad = PrioritatSpinner.selectedItem as String
                    val fecha_hoy = java.time.LocalDate.now().toString()
                    val averia = Averias(
                        Incidencia = problem,
                        descripcion = description,
                        data_inicio = fecha_hoy,
                        data_fin = null,
                        prioridad = prioridad,
                        imagen = imageName,
                        creator_id = idLogat,
                        tecnico_asignado_id = asignar.id,
                        asignador = 1,
                        zona_id = zona.id,
                        tipo_averias_id = tipusManteniment.id
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val response = RetrofitConnection.service.addAveria(averia)
                            if (response.isSuccessful) {
                                // Mostrar un toast de confirmación
                                MainActivity().mostrarToastPersonalizado(requireContext(), "Tarea añadida correctamente")
                                // Limpiar los campos de entrada
                                problemEditText.text.clear()
                                descriptionEditText.text.clear()
                            } else {
                                // Mostrar un toast de error
                                MainActivity().mostrarToastPersonalizado(requireContext(), "Error al añadir la tarea")
                            }
                        } catch (e: Exception) {
                            // Mostrar un toast de error si hay una excepción
                            MainActivity().mostrarToastPersonalizado(
                                requireContext(),
                                "Error al añadir la tarea"
                            )
                        }
                }
            }

        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap, fileName: String): String {
        val contextWrapper = ContextWrapper(requireContext())
        val directory = contextWrapper.getDir("images", Context.MODE_PRIVATE)
        val file = File(directory, fileName)

        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file.absolutePath
    }

    private fun sendImageToLaravel(imagePath: String, imageName: String) {
        val imageFile = File(imagePath)
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", imageName, requestFile)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = RetrofitConnection.service.uploadImage(body)
                if (response.isSuccessful) {
                    //toast personalizado
                    MainActivity().mostrarToastPersonalizado(requireContext(), "Imagen subida correctamente")
                } else {
                    // Manejar la respuesta fallida
                    MainActivity().mostrarToastPersonalizado(requireContext(), "Imagen no subida")

                }
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir al subir la imagen
                Log.e("addtaskFragment", e.toString())
            }
        }
    }
}