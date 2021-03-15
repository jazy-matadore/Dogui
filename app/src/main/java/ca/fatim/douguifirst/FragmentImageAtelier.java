package ca.fatim.douguifirst;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentImageAtelier#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentImageAtelier extends Fragment {

    // Initialisation de variables
    ImageView ivAtelier;
    Button btnCamera;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentImageAtelier() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragmentimageatelier.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentImageAtelier newInstance(String param1, String param2) {
        FragmentImageAtelier fragment = new FragmentImageAtelier();
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
        View root = inflater.inflate(R.layout.fragment_image_atelier, container, false);


        //Affectation des variables
        ivAtelier = root.findViewById(R.id.iv_image_atelier);
        btnCamera = root.findViewById(R.id.btnCamera);

        // Requete pour l'octroi de permission de la camera

        if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            int requestCode;
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.CAMERA},
                    100);
        }
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            // get capture image
            Bitmap captureimage = (Bitmap) data.getExtras().get("data");

            // Set capture image to ImageView
            ivAtelier.setImageBitmap(captureimage);
        }
    }
}