package com.example.portosmartguide

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.portosmartguide.data.local.AppDatabase
import com.example.portosmartguide.data.repository.PoiRepository
import com.example.portosmartguide.viewmodel.MapViewModel
import com.example.portosmartguide.viewmodel.MapViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Declaramos o nosso ViewModel
    private lateinit var mapViewModel: MapViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                ativarLocalizacao()
            } else {
                Toast.makeText(this, "Permissão de localização negada.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Configurar a arquitetura (Ligar a Base de Dados ao ViewModel)
        val database = AppDatabase.getDatabase(this)
        val repository = PoiRepository(database.poiDao())
        val factory = MapViewModelFactory(repository)
        mapViewModel = ViewModelProvider(this, factory)[MapViewModel::class.java]

        // 2. Inserir dados de teste na Base de Dados (Para efeitos de desenvolvimento)
        mapViewModel.inserirPOIsIniciais()

        // 3. Preparar a localização e o mapa
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        verificarPermissoesLocalizacao()

        // NOVO: Liga os botões visíveis de Zoom (+ e -) no canto inferior direito
        mMap.uiSettings.isZoomControlsEnabled = true

        // NOVO: O que acontece quando clicamos no "balão" de informação de um pino
        mMap.setOnInfoWindowClickListener { marker ->
            // Cria a intenção de viajar para o novo ecrã de Detalhes
            val intent = Intent(this, PoiDetailsActivity::class.java)
            // Leva o nome e a categoria do monumento na "mochila" para o próximo ecrã
            intent.putExtra("NOME_POI", marker.title)
            intent.putExtra("CATEGORIA_POI", marker.snippet)
            startActivity(intent)
        }

        lifecycleScope.launch {
            mapViewModel.pois.collect { listaDeLocais ->
                mMap.clear()
                for (local in listaDeLocais) {
                    val posicao = LatLng(local.latitude, local.longitude)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(posicao)
                            .title(local.nome)
                            .snippet(local.categoria)
                    )
                }
            }
        }
    }

    private fun verificarPermissoesLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            ativarLocalizacao()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @Suppress("MissingPermission")
    private fun ativarLocalizacao() {
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val minhaPosicao = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(minhaPosicao, 15f))
            }
        }
    }
}