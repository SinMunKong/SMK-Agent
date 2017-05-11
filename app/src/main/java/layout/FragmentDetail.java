package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import th.co.smk.smkagent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetail extends Fragment {
    CalendarView calendarView;

    public FragmentDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1_insurance_data, container, false);

        calendarSetting(rootView);
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    private void calendarSetting(View view) {
        calendarView = (CalendarView) view.findViewById(R.id.calendarDetail);

        /*
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity(),"Year: " + year + "\n" +"Month: " + month + "\n" +
                                "Day of Month: " + dayOfMonth,
                        Toast.LENGTH_LONG).show();
            }
        });
        */
    }

}
