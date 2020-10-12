package cc.rome753.oneadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.demo.model.Person;
import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneTemplate;
import cc.rome753.oneadapter.base.OneViewHolder;
import cc.rome753.oneadapter.databinding.OneViewHolderWrapper;
import cc.rome753.oneadapter.demo.databinding.ItemPersonBinding;

public class DataBindingActivity extends AppCompatActivity {

    OneAdapter oneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneAdapter = new OneAdapter().register(new OneTemplate() {
            @Override
            public boolean isMatch(int position, Object o) {
                return true;
            }

            @Override
            public OneViewHolder getViewHolder(ViewGroup parent) {
                return new OneViewHolderWrapper<Person, ItemPersonBinding>(parent, R.layout.item_person) {
                    @Override
                    protected void bindViewCasted(int position, Person person) {
                        binding.setPerson(person);
                        binding.executePendingBindings();
                    }
                }.asOneViewHolder();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(oneAdapter);

        requestData();
    }

    private void requestData() {
        List<Object> data = new ArrayList<>();
        for(int i = 0; i <= 10; i++) {
            data.add(new Person("Bill", 22));
            data.add(new Person("Chris", 10));
            data.add(new Person("David", 36));
        }
        oneAdapter.setData(data);
        oneAdapter.notifyDataSetChanged();
    }
}
