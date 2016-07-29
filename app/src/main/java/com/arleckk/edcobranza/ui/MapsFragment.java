package com.arleckk.edcobranza.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.controller.DirectionsJSONParser;
import com.arleckk.edcobranza.model.TrabajadorFonacot;
import com.arleckk.edcobranza.task.UpdateMyLocationTask;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by SISTEMAS on 02/06/2016.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private View view;
    private SupportMapFragment fragment;
    private GoogleMap map;
    private Context fragmentContext;
    private EditText editCurrentLocation;
    private EditText editDestinyLocation;
    private RelativeLayout searchForm;
    private FrameLayout mapsLayout;
    private ImageButton buttonSearch;
    private LocationManager locationManager;
    private Geocoder geocoder;
    private Address address;
    private LatLng latLngCurrentLocation;
    private List<Address> addressList;
    private ArrayList<LatLng> markerPoints;
    private TrabajadorFonacot trabajador;
    private UpdateMyLocationTask locationTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fragmentContext = getActivity().getApplicationContext();
        locationManager = (LocationManager) getActivity().getSystemService(fragmentContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(fragmentContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragmentContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 10, this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(trabajador != null) {
            editDestinyLocation.setText(trabajador.getDireccion());
        } else {
            Log.v("maps_debug","onStart trabajador null");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_maps, container, false);
        }

        searchForm = (RelativeLayout) view.findViewById(R.id.search_form);
        mapsLayout = (FrameLayout) view.findViewById(R.id.frame_maps);
        editCurrentLocation = (EditText) view.findViewById(R.id.edit_current_location);
        editDestinyLocation = (EditText) view.findViewById(R.id.edit_destiny_location);
        buttonSearch = (ImageButton) view.findViewById(R.id.button_search_maps);

        GestorActivity.onChangeSpinnerMaps = new GestorActivity.OnChangeSpinnerMaps() {
            @Override
            public void onChangeSpinner(Object object) {
                trabajador = (TrabajadorFonacot) object;
                if(trabajador != null) {
                    setDestinyLocation(trabajador.getDireccion());
                } else {
                    Log.v("maps_debug","trabajador null");
                    if(map != null) {
                        map.clear();
                        if (!markerPoints.isEmpty()) {
                            markerPoints.clear();
                        }
                    }
                }
            }
        };

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRoute(v);
            }
        });

        return view;
    }

    public void setDestinyLocation(String destinyLocation) {
        if(destinyLocation != null) {
            if(!destinyLocation.equals("")) {
                editDestinyLocation.setText(destinyLocation);
            } else {
                Toast.makeText(view.getContext(),"No se tiene la dirección del trabajador",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(view.getContext(),"No se tiene la dirección del trabajador",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
        fragment.getMapAsync(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            setUpMap();
        }
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(fragmentContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragmentContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            map.setMyLocationEnabled(true);
            return;
        }
        map.setMyLocationEnabled(true);
    }

    public void setRoute(View view) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if(geocoder == null) {
            geocoder = new Geocoder(fragmentContext);
        }

        markerPoints = new ArrayList<LatLng>();

        if(map != null) {
            map.clear();
            if(!markerPoints.isEmpty()) {
                markerPoints.clear();
            }

            if(editCurrentLocation.getText().toString().equals("")) {
                markerPoints.add(latLngCurrentLocation);
            } else {
                String currentLocation = "";
                currentLocation = editCurrentLocation.getText().toString();
                addressList = null;
                address = null;
                try {
                    addressList = geocoder.getFromLocationName(currentLocation, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!(addressList == null) ) {
                    if (!addressList.isEmpty()) {
                        address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        markerPoints.add(latLng);
                    }
                }
            }

            String destinyLocation = "";
            destinyLocation = editDestinyLocation.getText().toString();
            addressList = null;
            address = null;
            try {
                addressList = geocoder.getFromLocationName(destinyLocation, 1);
            } catch (IOException e) {
                Toast.makeText(getContext(), "El servicio de maps no esta disponible por el momento",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            if (!(addressList == null) ) {
                if (!addressList.isEmpty()) {
                    address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    markerPoints.add(latLng);
                }
            }

            // existen al menos 2 puntos
            if(markerPoints.size()>1) {
                //colocar marcadores
                for(int i = 0; i < markerPoints.size(); i++) {
                    switch (i){
                        case 0:
                            Log.v("maps_debug","current location: "+markerPoints.get(i).toString());
                            map.addMarker(new MarkerOptions().position(markerPoints.get(i)).title("Current Location"));
                            break;
                        case 1:
                            Log.v("maps_debug","destiny location: "+markerPoints.get(i).toString());
                            map.addMarker(new MarkerOptions().position(markerPoints.get(i)).title("Destiny Location"));
                            break;
                    }
                    builder.include(markerPoints.get(i));
                }

                LatLng origin = markerPoints.get(0);
                LatLng dest = markerPoints.get(1);

                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(origin, dest);

                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);

                //posicionar marcadores y animacion
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,50);
                //map.moveCamera(cu);
                map.animateCamera(cu);
            } else {
                Toast.makeText(fragmentContext,getString(R.string.error_location),Toast.LENGTH_SHORT).show();
            }
        }
    }

    //parsin & download json from maps api
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // mode
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.v("maps_debug", "exception while downloading "+e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.BLUE);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                map.addPolyline(lineOptions);
            }
        }
    }

    private String setAddress(String text) {
        if(text != null) {
            if(!text.equals("")){
                return text;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        latLngCurrentLocation = new LatLng(location.getLatitude(),location.getLongitude());

        locationTask = new UpdateMyLocationTask(getContext());

        locationTask.execute(location);

//        Toast.makeText(fragmentContext,"Location changed: Lat "+location.getLatitude()+
//                " Lng: "+location.getLongitude(),Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent("UPDATE_MY_LOCATION");
//        intent.putExtra("location",location);
//        view.getContext().sendBroadcast(intent);

//        if(map != null) {
//            if(geocoder == null) {
//                geocoder = new Geocoder(fragmentContext);
//            }
//            String sCurrentLocation = "";
//            editCurrentLocation.setText("");
//            Toast.makeText(fragmentContext,"Location changed: Lat "+location.getLatitude()+
//                    " Lng: "+location.getLongitude(),Toast.LENGTH_SHORT).show();
//            Log.v("maps_debug","Location changed: Lat "+location.getLatitude()+
//                    " Lng: "+location.getLongitude());
//            LatLng currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
//            map.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
//            map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
//
//            //change lat & long into address
//            try {
//                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if(addressList != null) {
//                if(!addressList.isEmpty()) {
//                    String address = addressList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                    String city = addressList.get(0).getLocality();
//                    String state = addressList.get(0).getAdminArea();
//                    String country = addressList.get(0).getCountryName();
//                    String postalCode = addressList.get(0).getPostalCode();
//                    String knownName = addressList.get(0).getFeatureName();
////                    sCurrentLocation = address + " " + city + " " + state + " " + country + " " + postalCode + " " + knownName+".";
//
//                    sCurrentLocation += setAddress(address) + " " + setAddress(city) + " " + setAddress(state) + " " +
//                            setAddress(country) + " " + setAddress(postalCode) + " " + setAddress(knownName) + ".";
//
//                } else {
//                    Log.v("maps_debug","error al convertir las coordenadas en direccion");
//                }
//            }
//
//            UpdateLocationTask task = new UpdateLocationTask();
//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferencias",getActivity().MODE_PRIVATE);
//            String user = sharedPreferences.getString("user","null");
//            try {
//                user = new Cifrado().decrypt(user);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            String [] params = {
//                    user,
//                    String.valueOf(location.getLatitude()),
//                    String.valueOf(location.getLongitude()),
//                    Utilities.getDate() + " " + Utilities.getHour()
//            };
//            task.execute(params);
//            editCurrentLocation.setText(sCurrentLocation);
//        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
