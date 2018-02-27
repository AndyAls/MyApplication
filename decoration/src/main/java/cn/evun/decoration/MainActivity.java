package cn.evun.decoration;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        initView();
        initData();
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {

            datas.add("中华人民共和国"+i);
        }
    }

    private final void initView() {

        mList = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mList.setLayoutManager(manager);
        mList.setHasFixedSize(true);
        mList.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.HORIZONTAL,10, ContextCompat.getColor(this,R.color.colorPrimary)));
        MyAdapter adapter = new MyAdapter();
        mList.setAdapter(adapter);

    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(View.inflate(parent.getContext(), R.layout.item,null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemHolder holder1 = (ItemHolder) holder;
            holder1.textView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }
    class ItemHolder extends RecyclerView.ViewHolder{

        private final TextView textView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
