package ca.fatim.douguifirst;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Import du GPS
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Criteria;
import android.location.LocationProvider;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAjoutAtelier#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAjoutAtelier extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Ajout parametre GPS

    LocationManager locationManager = null;
    private String fournisseur;
    private TextView latitude;
    private TextView longitude;

    Geocoder geocoder;

    LocationListener ecouteurGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location localisation)
        {
            Toast.makeText(getContext(), fournisseur + " localisation", Toast.LENGTH_SHORT).show();

            Log.d("GPS", "localisation : " + localisation.toString());
            String coordonnees = String.format("Latitude : %f - Longitude : %f\n", localisation.getLatitude(), localisation.getLongitude());
            Log.d("GPS", coordonnees);
            String autres = String.format("Vitesse : %f - Altitude : %f - Cap : %f\n", localisation.getSpeed(), localisation.getAltitude(), localisation.getBearing());
            Log.d("GPS", autres);
            //String timestamp = String.format("Timestamp : %d\n", localisation.getTime());
            //Log.d("GPS", "timestamp : " + timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date(localisation.getTime());
            Log.d("GPS", sdf.format(date));

            String strLatitude = String.format("%f", localisation.getLatitude());
            String strLongitude = String.format("%f", localisation.getLongitude());
            latitude.setText(strLatitude);
            longitude.setText(strLongitude);

            List<Address> adresses = null;
            try
            {
                adresses = geocoder.getFromLocation(localisation.getLatitude(), localisation.getLongitude(), 1);
            }
            catch (IOException ioException)
            {
                Log.e("GPS", "erreur", ioException);
            } catch (IllegalArgumentException illegalArgumentException)
            {
                Log.e("GPS", "erreur " + coordonnees, illegalArgumentException);
            }

            if (adresses == null || adresses.size()  == 0)
            {
                Log.e("GPS", "erreur aucune adresse !");
            } else {
                Address adresse = adresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                String strAdresse = adresse.getAddressLine(0) + ", " + adresse.getLocality();
                Log.d("GPS", "adresse : " + strAdresse);

                for(int i = 0; i <= adresse.getMaxAddressLineIndex(); i++)
                {
                    addressFragments.add(adresse.getAddressLine(i));
                }
                Log.d("GPS", TextUtils.join(System.getProperty("line.separator"), addressFragments));
               // Adresse.setText(TextUtils.join(System.getProperty("line.separator"), addressFragments));
            }
        }
        @Override
        public void onProviderDisabled(String fournisseur)
        {
            Toast.makeText(getContext(), fournisseur + " désactivé !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String fournisseur)
        {
            Toast.makeText(getContext(), fournisseur + " activé !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String fournisseur, int status, Bundle extras)
        {
            switch(status)
            {
                case LocationProvider.AVAILABLE:
                    Toast.makeText(getContext(), fournisseur + " état disponible", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(getContext(), fournisseur + " état indisponible", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(getContext(), fournisseur + " état temporairement indisponible", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getContext(), fournisseur + " état : " + status, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        arreterLocalisation();
    }

    private void initialiserLocalisation()
    {
        if (locationManager == null)
        {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteres = new Criteria();

            // la précision  : (ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision)
            criteres.setAccuracy(Criteria.ACCURACY_FINE);

            // l'altitude
            criteres.setAltitudeRequired(true);

            // la direction
            criteres.setBearingRequired(true);

            // la vitesse
            criteres.setSpeedRequired(true);

            // la consommation d'énergie demandée
            criteres.setCostAllowed(true);
            //criteres.setPowerRequirement(Criteria.POWER_HIGH);
            criteres.setPowerRequirement(Criteria.POWER_MEDIUM);

            fournisseur = locationManager.getBestProvider(criteres, true);
            Log.d("GPS", "fournisseur : " + fournisseur);
        }

        if (fournisseur != null)
        {
            // dernière position connue
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                Log.d("GPS", "no permissions !");
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        100);
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        101);

                //return;
            }

            Location localisation = locationManager.getLastKnownLocation(fournisseur);
            if(localisation != null)
            {
                // on notifie la localisation
                ecouteurGPS.onLocationChanged(localisation);
            }

            // on configure la mise à jour automatique : au moins 10 mètres et 15 secondes
            locationManager.requestLocationUpdates(fournisseur, 15000, 10, ecouteurGPS);
        }
    }

    private void arreterLocalisation()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(ecouteurGPS);
            ecouteurGPS = null;
        }
    }



    public FragmentAjoutAtelier() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAjoutAtelier.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAjoutAtelier newInstance(String param1, String param2) {
        FragmentAjoutAtelier fragment = new FragmentAjoutAtelier();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_ajout_atelier, container, false);
        geocoder= new Geocoder(getContext(), Locale.getDefault());
        latitude = (TextView) root.findViewById(R.id.editTextLatitudeAtelier);
        longitude = (TextView) root.findViewById(R.id.editTextLongitudeAtelier);

        Log.d("GPS", "onCreate");
        initialiserLocalisation();

        return root;
    }
}