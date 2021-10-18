package com.example.ravish.pothole_detection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class GenerateFragment extends Fragment {

    private Button buttonPath;
    private Button stopService;
    private EditText enterOrigin;
    private EditText enterDestination;
    private Button get_map;

    public GenerateFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_generate, container, false);
        buttonPath = (Button) view.findViewById(R.id.start);
        get_map = (Button) view.findViewById(R.id.get_map);
        //enterOrigin = (EditText) view.findViewById(R.id.enterOrigin);
        //enterDestination = (EditText) view.findViewById(R.id.enterDestination);

        stopService = (Button) view.findViewById(R.id.stop);

        buttonPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getActivity().startService(new Intent(getActivity(), service.class));
                Intent in = new Intent(getActivity(), service.class );
                getActivity().startService(in);
                Toast.makeText(getActivity(), "Service Started", Toast.LENGTH_SHORT).show();
            }
        });
        get_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getActivity().stopService(new Intent(getActivity(), service.class));
                Intent in = new Intent(getActivity(), Maps_Services.class );
                startActivity(in);
            }
        });

        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getActivity().stopService(new Intent(getActivity(), service.class));
                //Intent in = new Intent(getActivity(), service.class );
                //getActivity().stopService(in);
                Toast.makeText(getActivity(), "Service Stopped", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
