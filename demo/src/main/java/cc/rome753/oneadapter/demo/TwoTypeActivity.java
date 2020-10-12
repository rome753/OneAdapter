package cc.rome753.oneadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneListener;
import cc.rome753.oneadapter.base.OneViewHolder;

public class TwoTypeActivity extends AppCompatActivity {

    OneAdapter oneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneAdapter = new OneAdapter(new StringListener(), new LongListener());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(oneAdapter);

        requestData();
    }

    private void requestData() {
        List<Object> data = new ArrayList<>();
        for(int i = 'A'; i <= 'z'; i++){
            data.add(String.valueOf((char)i));
        }
        for(int i = 3; i < 50; i += 6){
            data.add(i, System.nanoTime());
        }
        oneAdapter.setData(data);
        oneAdapter.notifyDataSetChanged();
    }

    static class StringViewHolder extends OneViewHolder<String>{
        TextView text1;
        ImageView icon;
        public StringViewHolder(ViewGroup parent, int layoutRes) {
            super(parent, layoutRes);
            text1 = itemView.findViewById(android.R.id.text1);
            icon = itemView.findViewById(android.R.id.icon);
        }

        @Override
        protected void bindViewCasted(int position, String s) {
            text1.setText(s);
            icon.setImageResource(R.mipmap.ic_launcher);
        }
    }

    static class StringListener implements OneListener{

        @Override
        public boolean isMyItemViewType(int position, Object o) {
            return o instanceof String;
        }

        @Override
        public OneViewHolder getMyViewHolder(ViewGroup parent) {
            return new StringViewHolder(parent, android.R.layout.activity_list_item);
        }
    }

    static class LongViewHolder extends OneViewHolder<Long>{

        public LongViewHolder(ViewGroup parent, int layoutRes) {
            super(parent, layoutRes);
        }

        @Override
        protected void bindViewCasted(int position, Long l) {
            TextView text = itemView.findViewById(R.id.text);
            text.setText("time: " + l);
        }
    }

    static class LongListener implements OneListener{

        @Override
        public boolean isMyItemViewType(int position, Object o) {
            return o instanceof Long;
        }

        @Override
        public OneViewHolder getMyViewHolder(ViewGroup parent) {
            return new LongViewHolder(parent, R.layout.item_text);
        }
    }



}
