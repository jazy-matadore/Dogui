package ca.fatim.douguifirst.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ca.fatim.douguifirst.AjoutAtelierActivity;
import ca.fatim.douguifirst.DetailsAtelierActivity;
import ca.fatim.douguifirst.R;
import ca.fatim.douguifirst.adapters.AtelierAdapter;
import ca.fatim.douguifirst.models.Atelier;

public class AtelierFragment extends Fragment {

    private AtelierViewModel atelierViewModel;
    FirebaseFirestore db;
    static  List<Atelier> listeAtelier;
    static Context context;

    void getAllAtelier(FirebaseFirestore dbs,
                       View vw){
        dbs.collection("Atelier")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listeAtelier=new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Atelier atelier = document.toObject(Atelier.class);
                                boolean add = listeAtelier.add(atelier);
                                Log.d("Information", document.getId() + " => " + document.getData());

                            }
                            Log.d("Information",  "atelier Size  " + listeAtelier.size());
                            ListView lvlisteAtelier = vw.findViewById(R.id.listeatelier);

                            lvlisteAtelier.setAdapter(new AtelierAdapter(context,listeAtelier));

                            // Ajout du processus d'écoute sur le clic

                            lvlisteAtelier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent visiodetail =new Intent(view.getContext(), DetailsAtelierActivity.class);
                                    startActivity(visiodetail);
                                }
                            });
                        } else {
                            Log.d("Erreur Data", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        atelierViewModel =
                new ViewModelProvider(this).get(AtelierViewModel.class);
        View root = inflater.inflate(R.layout.fragment_atelier, container, false);

        // chargement de la liste
        context=root.getContext();
        db=FirebaseFirestore.getInstance();
        getAllAtelier(db, root);


        atelierViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);

                }
        });
        FloatingActionButton fab = root.findViewById(R.id.btnAjoutAtelier);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ajoutAtelier=new Intent(root.getContext(), AjoutAtelierActivity.class);
                startActivity(ajoutAtelier);

            }
        });
        return root;
    }

}