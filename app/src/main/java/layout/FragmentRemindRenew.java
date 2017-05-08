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
import java.util.Random;

import th.co.smk.smkagent.Item2RemindRenew;
import th.co.smk.smkagent.Item2RemindRenewAdapter;
import th.co.smk.smkagent.OnLoadMoreListener;
import th.co.smk.smkagent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRemindRenew extends Fragment {
    RecyclerView rvItem;
    CardView cvItem;
    private OnLoadMoreListener onLoadMoreListener;
    Integer maxValueOfSize = 35;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public FragmentRemindRenew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2_remind_renew, container, false);
        rvItem = (RecyclerView) rootView.findViewById(R.id.rv_RemindRenew);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvItem.setLayoutManager(manager);
        cvItem = (CardView) rootView.findViewById(R.id.cv_item_remindRenew);
        final ArrayList<Item2RemindRenew> itemList = new ArrayList<>();
        generatedDummy(itemList);

        final Item2RemindRenewAdapter adapter = new Item2RemindRenewAdapter(rvItem,this.getActivity().getApplicationContext(), itemList);
        rvItem.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (itemList.size() <= maxValueOfSize) {
                    itemList.add(null);
                    adapter.notifyItemInserted(itemList.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
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

        return rootView;
    }

    private ArrayList<Item2RemindRenew> generatedDummy(ArrayList<Item2RemindRenew> arrayList) {
        Integer maxRows;
        Integer currentRows = arrayList.size() + 20;

        if (currentRows < maxValueOfSize) {
            maxRows = currentRows;
        }else {
            maxRows = maxValueOfSize;
        }
        for (int i = arrayList.size();i < maxRows; i++) {
            Item2RemindRenew item = new Item2RemindRenew();
            item.id = randomId();
            item.name = "นายแจ้งเตือน โปรดจดจำ";
            item.status_process = "ปกติ";
            item.license_no = randomLicensePlate();
            arrayList.add(item);
        }
        return arrayList;
    }

    public String randomLicensePlate() {
        int passwordSize = 7;
        char[] charsFirst = "กขคงตจฉซอรจ".toCharArray();
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < passwordSize; i++) {
            if (i <= 1) {
                char c = charsFirst[random.nextInt(chars.length)];
                sb.append(c);
            }else if (i == 2) {
                sb.append("-");
            }else {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public String randomId() {
        int passwordSize = 18;
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < passwordSize; i++) {
            char c = chars[random.nextInt(chars.length)];
            if (i==2 || i==4 || i==12) {
                sb.append("-");
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
