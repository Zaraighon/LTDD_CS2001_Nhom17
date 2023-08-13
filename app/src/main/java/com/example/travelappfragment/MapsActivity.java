package com.example.travelappfragment;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.travelappfragment.databinding.ActivityAddplaceBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.travelappfragment.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
//    private ActivityMapsBinding binding;

    private ActivityAddplaceBinding binding2;
    private PlacesClient placesClient;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private static LatLng mDefaultLocation = new LatLng(10.8231, 106.6297);

    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location lastKnownlocation = null;
    private Double selectedLatitude = null;
    private Double selectedLongtitude = null;
    private String selectedAddress = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        String apiKey = getString(R.string.APIKey);

        Button doneBtn = (Button) findViewById(R.id.donebBtn);
        TextView selectedPlaceTv = (TextView) findViewById(R.id.selectedPlaceTv);
        //get the selected location back to requesting activity/fragment class
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("latitude", selectedLatitude);
                intent.putExtra("longtitude", selectedLongtitude);
                intent.putExtra("address", selectedAddress);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        //Initialize the Places client
        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);

        autocompleteSupportFragment.setCountries("VN");
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));



        //Lấy thông tin ra gán địa chỉ vào ô Address
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String id = place.getId();
                String title = place.getName();
                LatLng latLng = place.getLatLng();
                selectedLatitude = latLng.latitude;
                selectedLongtitude = latLng.longitude;
                selectedAddress = place.getAddress();

                Log.i(TAG, "onPlaceSelected ID: " + id);
                Log.i(TAG, "onPlaceSelected Title: " + title);
                Log.i(TAG, "onPlaceSelected Latitude: " + selectedLatitude);
                Log.i(TAG, "onPlaceSelected Longtitude: " + selectedLongtitude);
                Log.i(TAG, "onPlaceSelected Address: " + selectedAddress);


                addMarkerCustom(latLng, title, selectedAddress);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "onError: stats: " + status);
            }
        });
//        detectAndShowDeviceLocationMap();
        getDeviceLocation();


    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Get the current location of the device, and position the map's camera
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener((Executor) this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownlocation= location;
                            Log.d(TAG, "Latitude: " + lastKnownlocation.getLatitude());
                            Log.d(TAG, "Longitude: " + lastKnownlocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownlocation.getLatitude(),
                                            lastKnownlocation.getLongitude()), 15));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, 15));
                        }

//                        getCurrentPlaceLikelihoods();
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Fetch a list of likely places, and show the current place on the map - provided the user
     * has granted location permission.
     */
    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.default_info_snippet)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in HCM and move the camera
        LatLng hcm = new LatLng(10.8231, 106.6297);
        mMap.addMarker(new MarkerOptions().position(hcm).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcm, 15));

        mMap.getUiSettings().setZoomControlsEnabled(true);
//        requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectedLatitude = latLng.latitude;
                selectedLongtitude = latLng.longitude;

                Log.d(TAG, "onMapClick: selectLatitude: " + selectedLatitude);
                Log.d(TAG, "onMapClick: selectLongtitude: " + selectedLongtitude);

                addressFromLatLng(latLng);
            }
        });

        // Prompt the user for permission.
        getLocationPermission();
    }


    private void addressFromLatLng(LatLng latLng) {
        Log.d(TAG, "addressFromLatLng: ");

        Geocoder geocoder = new Geocoder(this);

        try{
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            Address address = addressList.get(0);
            String addressLine = address.getAddressLine(0);
            String countryName = address.getCountryName();
            String adminArea = address.getAdminArea();
            String subAdminArea = address.getSubAdminArea();
            String locality = address.getLocality();
            String subLocality = address.getSubLocality();
            String postalCode = address.getPostalCode();

            selectedAddress = "" + addressLine;

            addMarkerCustom(latLng, "" + subLocality, "" + addressLine);
        }catch (Exception e) {
            Log.e(TAG, "addressFromLatLng: ", e);
        }
    }

    private void addMarkerCustom(LatLng latLng, String title, String selectedAddress) {
//        Log.d(TAG, "addMarker Latitude: " + latLng.latitude);
//        Log.d(TAG, "addMarker Longtitude: " + latLng.longitude);
//        Log.d(TAG, "addMarker title: " + title);
//        Log.d(TAG, "addMarker address: " + selectedAddress);
        mMap.clear();

        try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("" + title);
            markerOptions.snippet("" + selectedAddress);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            TextView selectedPlaceTv = (TextView) findViewById(R.id.selectedPlaceTv);
            selectedPlaceTv.setText(selectedAddress);
        } catch (Exception e) {
            Log.e(TAG, "addMarker: ", e);
        }
    }
}