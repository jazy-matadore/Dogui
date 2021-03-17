package ca.fatim.douguifirst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.fatim.douguifirst.models.Atelier;
import ca.fatim.douguifirst.utilitaires.AfficheMessage;


public class AjoutAtelierActivity extends AppCompatActivity {

    //recuperation des champs
    private ImageView ivImageAtelier;
    private EditText etNomResponsableAtelier;
    private EditText etNomAtelier;
    private EditText etContactAtelier;
    private EditText etSiteAtelier;
    private EditText etAdresseAtelier;
    private EditText etSpecialiteAtelier;
    private EditText etLongitudeAtelier;
    private EditText etLatitudeAtelier;
    private Button btnEnregistrerAtelier;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_atelier);
        //Firebase

        db = FirebaseFirestore.getInstance();
        storage =FirebaseStorage.getInstance("gs://dougui-faaa1.appspot.com");
        reference = storage.getReference();

        //Recuperation du bouton Enregistrer

        btnEnregistrerAtelier = findViewById(R.id.btnEnregistrerAtelier);

        btnEnregistrerAtelier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GeoPoint gps;
                Atelier atelier;
                boolean insertion = true;
                ivImageAtelier = (ImageView) findViewById(R.id.iv_image_atelier);

                etNomResponsableAtelier = (EditText) findViewById(R.id.editTextNomResponsable);
                etNomAtelier = (EditText) findViewById(R.id.editTextNomAtelier);
                etContactAtelier = (EditText) findViewById(R.id.editTextContactAtelier);
                etSiteAtelier = (EditText) findViewById(R.id.editTextSiteAtelier);
                etAdresseAtelier = (EditText) findViewById(R.id.editTextAdresseAtelier);
                etSpecialiteAtelier = (EditText) findViewById(R.id.editTextSpecialiteAtelier);
                etLongitudeAtelier = (EditText) findViewById(R.id.editTextLongitudeAtelier);
                etLatitudeAtelier = (EditText) findViewById(R.id.editTextLatitudeAtelier);

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                String numAtelier = format.format(date);

                String nomResponsable = etNomResponsableAtelier.getText().toString();
                String nomAtelier = etNomAtelier.getText().toString();
                String contactAtelier = etContactAtelier.getText().toString();
                String siteAtelier = etSiteAtelier.getText().toString();
                String adresseAtelier = etAdresseAtelier.getText().toString();
                String specialiteAtelier = etSpecialiteAtelier.getText().toString();
                String url = "gs://dougui-faaa1.appspot.com/logos/A" + numAtelier + ".jpg";
                //
                Double longitudeAtelier = Double.valueOf(etLongitudeAtelier.getText().toString());
                Double latitudeAtelier = Double.valueOf(etLatitudeAtelier.getText().toString());
                gps = new GeoPoint(latitudeAtelier, longitudeAtelier);

                if (nomAtelier == "") {
                    // pour affichage du message
                    AfficheMessage.afficheAlert("Le nom de l'atelier est obligatoire",AjoutAtelierActivity.this);
                    insertion = false;
                }

                if (insertion) {
                    //ajout de photo
                    StorageReference logoReference = reference.child("logos/A" + numAtelier + ".jpg");
                    ivImageAtelier.setDrawingCacheEnabled(true);
                    ivImageAtelier.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) ivImageAtelier.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = logoReference.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                        }
                    });


                    atelier = new Atelier(numAtelier, nomResponsable, nomAtelier, siteAtelier, contactAtelier, adresseAtelier, gps, url);

                    // Ajout de l'atelier
                    db.collection("Atelier")
                            .add(atelier)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Insertion", "DocumentSnapshot successfully written!");
                                    // pour affichage du message
                                    AfficheMessage.afficheAlert("Insertion réussie",AjoutAtelierActivity.this);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("ErreurInsertion", "Error writing document", e);
                                }
                            });

                }
            }
        });

    }
}
