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

import th.co.smk.smkagent.Item1InsuranceProcess;
import th.co.smk.smkagent.Item1InsuranceProcessAdapter;
import th.co.smk.smkagent.OnLoadMoreListener;
import th.co.smk.smkagent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInsuranceProcess extends Fragment {
    RecyclerView rvItem;
    CardView cvItem;
    Integer maxValueOfSize = 35;

    public FragmentInsuranceProcess() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1_insurance_data, container, false);
        rvItem = (RecyclerView) rootView.findViewById(R.id.rv_InsuranceProcess);
        rvItem.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvItem.setLayoutManager(manager);
        cvItem = (CardView) rootView.findViewById(R.id.cv_item_InsuranceProcess);
        final ArrayList<Item1InsuranceProcess> itemList = new ArrayList<>();
        generatedDummy(itemList);

        final Item1InsuranceProcessAdapter adapter = new Item1InsuranceProcessAdapter(rvItem,this.getActivity().getApplicationContext(), itemList);
        rvItem.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (itemList.size() <= maxValueOfSize) {
                    Toast.makeText(getActivity(), "On Load More", Toast.LENGTH_SHORT).show();
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

    private ArrayList<Item1InsuranceProcess> generatedDummy(ArrayList<Item1InsuranceProcess> arrayList) {
        Integer maxRows;
        Integer currentRows = arrayList.size() + 20;

        if (currentRows < maxValueOfSize) {
            maxRows = currentRows;
        }else {
            maxRows = maxValueOfSize;
        }

        for (int i = arrayList.size(); i < maxRows; i++) {
            Item1InsuranceProcess item = new Item1InsuranceProcess();
            item.id = randomId();
            item.name = "นายดำรง บริการดี";
            item.status_process = randomLicensePlate();
            item.day_left = i*2;
            item.day_left_unit = "วัน";
            if (getRandomBoolean()) {
                item.time_alert = "";
            }else {
                Random r = new Random();
                int iRandom = r.nextInt(24) + 1;
                item.time_alert = String.valueOf(iRandom)+" ช.ม.";
            }
            arrayList.add(item);
        }
        return arrayList;
    }

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
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
