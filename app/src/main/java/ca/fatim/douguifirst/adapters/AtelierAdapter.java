package ca.fatim.douguifirst.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import ca.fatim.douguifirst.R;
import ca.fatim.douguifirst.models.Atelier;

public class AtelierAdapter extends BaseAdapter {
    // Les champs

    private LayoutInflater inflater;
    private Context context;
    private List<Atelier> listeAtelier;
    //Accès à FirebaseStorage
    private FirebaseStorage storage;
    private StorageReference gsRef;

    //constuctor
    public AtelierAdapter(Context c, List<Atelier> ateliers){
        this.context=c;
        this.listeAtelier=ateliers;
        this.inflater=LayoutInflater.from(c);
        // permet de recuperer l'image
        this.storage=FirebaseStorage.getInstance();
    }

    @Override
    public int getCount() {
        return listeAtelier.size();
    }

    @Override
    public Atelier getItem(int position) {
        return listeAtelier.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    convertView=inflater.inflate(R.layout.adapter_atelier, null);
    Atelier ateliercourant=getItem(position);
    String nomAtelier=ateliercourant.getNomAtelier();
    String adresseAtelier=ateliercourant.getAdresseAtelier();
    Double noteAtelier=0.0;

    // Affectation des valeurs
        TextView tvNomAtelier=convertView.findViewById(R.id.tv_nom_atelier);
        tvNomAtelier.setText(nomAtelier);
        TextView tvAdresseAtelier=convertView.findViewById(R.id.tv_adresse_atelier);
        tvAdresseAtelier.setText(adresseAtelier);
        TextView tvNoteAtelier=convertView.findViewById(R.id.tv_contenu_note_atelier);
        tvNoteAtelier.setText(noteAtelier.toString());
        //recuperation de l'image
       if (ateliercourant.getUrlLogo() != "") {

           Log.d("Information",  "logo " + ateliercourant.getUrlLogo());
            gsRef = storage.getReferenceFromUrl(ateliercourant.getUrlLogo());
            ImageView photo = convertView.findViewById(R.id.imLogo);
            Glide.with(convertView.getContext())
                    .load(gsRef)
                    .into(photo);
        }
        return convertView;
    }
}
