package org.fossasia.pslab.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.fossasia.pslab.R;
import org.fossasia.pslab.activity.OscilloscopeActivity;
import org.fossasia.pslab.others.FloatSeekBar;

public class TimebaseTiggerFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Spinner spinnerTiggerChannelSelect;
    private FloatSeekBar seekBarTimebase;
    private FloatSeekBar seekBarTigger;
    private TextView textViewTimeBase;
    private TextView textViewTigger;


    public static TimebaseTiggerFragment newInstance(String param1, String param2) {
        TimebaseTiggerFragment fragment = new TimebaseTiggerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_timebase_tigger, container, false);

        //seekBarTimebase = (SeekBar) v.findViewById(R.id.seekBar_timebase_tt);
        seekBarTimebase = (FloatSeekBar) v.findViewById(R.id.seekBar_timebase_tt);
        seekBarTigger = (FloatSeekBar) v.findViewById(R.id.seekBar_tigger);
        textViewTimeBase = (TextView) v.findViewById(R.id.tv_timebase_values_tt);
        textViewTigger = (TextView) v.findViewById(R.id.tv_tigger_values_tt);
        spinnerTiggerChannelSelect = (Spinner) v.findViewById(R.id.spinner_tigger_channel_tt);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if(tabletSize){
            textViewTigger.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textViewTimeBase.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }

        seekBarTimebase.setMax(8);
        seekBarTimebase.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                switch (progress){
                    case 0:
                        textViewTimeBase.setText("875.00 μs");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(875);
                        break;
                    case 1:
                        textViewTimeBase.setText("1.00 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(1);
                        break;
                    case 2:
                        textViewTimeBase.setText("2.00 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(2);
                        break;
                    case 3:
                        textViewTimeBase.setText("4.00 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(4);
                        break;
                    case 4:
                        textViewTimeBase.setText("8.00 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(8);
                        break;
                    case 5:
                        textViewTimeBase.setText("25.60 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(25.60);
                        break;
                    case 6:
                        textViewTimeBase.setText("38.40 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(38.40);
                        break;
                    case 7:
                        textViewTimeBase.setText("51.20 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(51.20);
                        break;
                    case 8:
                        textViewTimeBase.setText("102.40 ms");
                        ((OscilloscopeActivity)getActivity()).setXAxisScale(102.40);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarTimebase.setProgress(0);

        seekBarTigger.setters(-16.5, 16.5);
        seekBarTigger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewTigger.setText(seekBarTigger.getValue() + " V");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarTigger.setProgress(50);

        String [] channels = {"CH1", "CH2", "CH3", "MIC"};
        ArrayAdapter<String> channelsAdapter;
        if(tabletSize){
            channelsAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.custom_spinner_tablet, channels);
        }
        else {
            channelsAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.custom_spinner, channels);
        }

        channelsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerTiggerChannelSelect.setAdapter(channelsAdapter);
        spinnerTiggerChannelSelect.setSelection(channelsAdapter.getPosition("CH1"),true);
        spinnerTiggerChannelSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((OscilloscopeActivity)getActivity()).tiggerChannel = spinnerTiggerChannelSelect.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}