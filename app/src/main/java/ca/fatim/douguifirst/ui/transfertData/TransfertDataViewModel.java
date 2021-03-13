package ca.fatim.douguifirst.ui.transfertData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransfertDataViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TransfertDataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is transfert Data fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}