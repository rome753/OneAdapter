package cc.rome753.archdemo;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by chao on 18-6-19.
 */

public class PersonViewModel extends ViewModel {

    private final LiveData<List<Person>> mPersonLiveData;

    private final MutableLiveData<String> mReloadLiveData;

    public PersonViewModel() {
        mReloadLiveData = new MutableLiveData<>();
        mPersonLiveData = Transformations.switchMap(mReloadLiveData, new Function<String, LiveData<List<Person>>>() {
            @Override
            public LiveData<List<Person>> apply(String input) {
                return DataRepository.getPersonLiveData();
            }
        });

    }

    public LiveData<List<Person>> getLiveData(){
        return mPersonLiveData;
    }

    public void reload(){
        mReloadLiveData.setValue("");
    }
}
