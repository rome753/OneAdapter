package cc.rome753.archdemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chao on 18-6-19.
 */

public class DataRepository {

    public static LiveData<List<Person>> getPersonLiveData(){
        final MutableLiveData<List<Person>> liveData = new MutableLiveData<>();
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<Person> data = new ArrayList<>();
                for(int i = 0; i < 5; i++) {
                    data.add(new Person("Alice", 18));
                    data.add(new Person("Bob", 22));
                    data.add(new Person("Chris", 12));
                    data.add(new Person("David", 16));
                }

//                liveData.setValue(data);
                liveData.postValue(data);
            }
        }.start();
        return liveData;
    }
}
