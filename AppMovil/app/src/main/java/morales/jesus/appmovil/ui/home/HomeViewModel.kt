package morales.jesus.appmovil.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import morales.jesus.appmovil.dtos.ProductoDTO

class HomeViewModel : ViewModel() {

    private val _productos = MutableLiveData<List<ProductoDTO>>()
    val productos: LiveData<List<ProductoDTO>> = _productos

}