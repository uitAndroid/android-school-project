package com.school.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class exercise extends Fragment {
    Button bt;
    Exercise_view exeView;
    public exercise() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    View.OnClickListener myClickLIstener= new View.OnClickListener() {
        public void onClick(View v) {
            String tag = (String) v.getTag();
            // your stuff
            Intent myIntent = new Intent(getActivity(), Exercise_view.class);
            myIntent.putExtra("key",tag); //Optional parameters
            getActivity().startActivity(myIntent);
        }
    };

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {

        bt = (Button) view.findViewById(R.id.fb_button1);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("fb_btn1");

        bt = (Button) view.findViewById(R.id.fb_button2);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("fb_btn2");

        bt = (Button) view.findViewById(R.id.fb_button3);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("fb_btn3");

        bt = (Button) view.findViewById(R.id.lb_button1);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("lb_btn1");

        bt = (Button) view.findViewById(R.id.lb_button2);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("lb_btn2");

        bt = (Button) view.findViewById(R.id.lb_button3);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("lb_btn3");

        bt = (Button) view.findViewById(R.id.ub_button1);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("ub_btn1");

        bt = (Button) view.findViewById(R.id.ub_button2);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("ub_btn2");

        bt = (Button) view.findViewById(R.id.ub_button3);
        bt.setOnClickListener(myClickLIstener);
        bt.setTag("ub_btn3");
    }

}
