package layout;



import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import th.co.smk.smkagent.Item0Main;
import th.co.smk.smkagent.Item0MainAdapter;
import th.co.smk.smkagent.OnLoadMoreListener;
import th.co.smk.smkagent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {
    RecyclerView rvItem;
    CardView cvItem;
    Integer maxValueOfSize = 35;


    public FragmentMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment0_main, container, false);
        rvItem = (RecyclerView) rootView.findViewById(R.id.rv_Main);
        rvItem.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvItem.setLayoutManager(manager);
        cvItem = (CardView) rootView.findViewById(R.id.cv_item_mainMenu);
        final ArrayList<Item0Main> itemList = new ArrayList<>();
        generatedDummy(itemList);

        final Item0MainAdapter adapter = new Item0MainAdapter(rvItem,this.getActivity().getApplicationContext(), itemList);
        rvItem.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getActivity(), "on Load more", Toast.LENGTH_SHORT).show();
                if (itemList.size() <= maxValueOfSize) {
                    itemList.add(null);
                    adapter.notifyItemInserted(itemList.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "ItemList - "+itemList.size(), Toast.LENGTH_SHORT).show();
                            itemList.remove(itemList.size() - 1);
                            adapter.notifyItemRemoved(itemList.size());
                            generatedDummy(itemList);
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    }, 2500);
                } else {
                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return inflater.inflate(R.layout.fragment0_main, container, false);
    }

    private ArrayList<Item0Main> generatedDummy(ArrayList<Item0Main> arrayList) {
        Integer maxRows;
        Integer currentRows = arrayList.size() + 20;

        if (currentRows < maxValueOfSize) {
            maxRows = currentRows;
        }else {
            maxRows = maxValueOfSize;
        }

        for (int i = arrayList.size(); i < maxRows; i++) {
            Item0Main item = new Item0Main();
            item.menu_id = i;
            item.menu_name = "Sample menu name";
            item.menu_path_icon = R.drawable.ic_menu_gallery;
            arrayList.add(item);
        }
        return arrayList;
    }



}
