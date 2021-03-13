package ca.fatim.douguifirst.ui.politique;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PolitiqueViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PolitiqueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is politique fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}