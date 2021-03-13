package ca.fatim.douguifirst.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import ca.fatim.douguifirst.R;
import ca.fatim.douguifirst.adapters.AtelierAdapter;
import ca.fatim.douguifirst.models.Atelier;

import static androidx.core.content.ContextCompat.getSystemService;

public class AtelierFragment extends Fragment {

    private AtelierViewModel atelierViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        atelierViewModel =
                new ViewModelProvider(this).get(AtelierViewModel.class);
        View root = inflater.inflate(R.layout.fragment_atelier, container, false);
        // final TextView textView = root.findViewById(R.id.text_home);
        //list of items

        List<Atelier> atelierListe =new ArrayList<>();
        atelierListe.add(new Atelier("Taillor Salon", "Ka 017 rue sansfil B7A12", 8.5));
        atelierListe.add(new Atelier("Marian Couture", "Ma 009 rue saintRobert H7K12", 7.0 ));
        atelierListe.add(new Atelier("Couture d'ébène", "Ra 342 rue transit L8F56", 5.3));

        // get list view

        ListView atelierListView = root.findViewById(R.id.listeatelier);
        atelierListView.setAdapter(new AtelierAdapter(root.getContext(),atelierListe ));

        atelierViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);

                }
        });
        return root;
    }
}