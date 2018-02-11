package cc.rome753.oneadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.oneadapter.R;
import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneListener;
import cc.rome753.oneadapter.base.OneViewHolder;

public class SimpleListActivity extends AppCompatActivity {

    OneAdapter oneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneAdapter = new OneAdapter(new OneListener() {
            @Override
            public boolean isMyItemViewType(int position, Object o) {
                return true;
            }

            @Override
            public OneViewHolder getMyViewHolder(ViewGroup parent) {
                return new OneViewHolder<String>(parent, R.layout.item_text){

                    @Override
                    protected void bindViewCasted(int position, String s) {
                        TextView text = itemView.findViewById(R.id.text);
                        text.setText(s);
                    }
                };
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(oneAdapter);

        requestData();
    }

    private void requestData() {
        List<String> data = new ArrayList<>();
        for(int i = 'A'; i <= 'z'; i++) {
            data.add(" " + (char)i);
        }
        oneAdapter.setData(data);
    }
}
