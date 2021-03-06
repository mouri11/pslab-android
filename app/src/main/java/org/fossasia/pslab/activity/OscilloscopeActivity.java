package org.fossasia.pslab.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;

import org.fossasia.pslab.communication.ScienceLab;
import org.fossasia.pslab.fragment.ChannelParametersFragment;
import org.fossasia.pslab.fragment.DataAnalysisFragment;
import org.fossasia.pslab.fragment.TimebaseTiggerFragment;
import org.fossasia.pslab.fragment.XYPlotFragment;
import org.fossasia.pslab.others.ScienceLabCommon;
import org.fossasia.pslab.R;

/**
 * Created by viveksb007 on 10/5/17.
 */

public class OscilloscopeActivity extends AppCompatActivity implements
        ChannelParametersFragment.OnFragmentInteractionListener,
        TimebaseTiggerFragment.OnFragmentInteractionListener,
        DataAnalysisFragment.OnFragmentInteractionListener,
        XYPlotFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    private ScienceLab scienceLab;
    private LineChart mChart;
    private LinearLayout linearLayout;
    private FrameLayout frameLayout;
    private ImageButton channelParametersButton;
    private ImageButton timebaseButton;
    private ImageButton dataAnalysisButton;
    private ImageButton xyPlotButton;
    private RelativeLayout mChartLayout;
    private TextView channelParametersTextView;
    private TextView timebaseTiggerTextView;
    private TextView dataAnalysisTextView;
    private TextView xyPlotTextView;
    private TextView leftYAxisLabel;
    private TextView leftYAxisLabelUnit;
    private TextView rightYAxisLabelUnit;
    private TextView xAxisLabelUnit;
    private int height;
    private int width;
    private double timebase;
    private XAxis x1;
    private YAxis y1;
    private YAxis y2;
    public boolean isCH1Selected;
    public boolean isCH2Selected;
    public boolean isCH3Selected;
    public boolean isMICSelected;
    public boolean isFourierTransformSelected;
    public boolean isXYPlotSelected;
    public boolean sineFit;
    public boolean squareFit;
    private String leftYAxisInput;
    public String tiggerChannel;
    public String curveFittingChannel1;
    public String curveFittingChannel2;
    Fragment channelParametersFragment;
    Fragment timebasetiggerFragment;
    Fragment dataAnalysisFragment;
    Fragment xyPlotFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oscilloscope);

        scienceLab = ScienceLabCommon.getInstance().scienceLab;
        linearLayout = (LinearLayout) findViewById(R.id.layout_dock_os1);
        mChart = (LineChart) findViewById(R.id.chart_os);
        mChartLayout = (RelativeLayout) findViewById(R.id.layout_chart_os);
        frameLayout = (FrameLayout) findViewById(R.id.layout_dock_os2);
        channelParametersButton = (ImageButton) findViewById(R.id.button_channel_parameters_os);
        timebaseButton = (ImageButton) findViewById(R.id.button_timebase_os);
        dataAnalysisButton = (ImageButton) findViewById(R.id.button_data_analysis_os);
        xyPlotButton = (ImageButton) findViewById(R.id.button_xy_plot_os);
        leftYAxisLabel = (TextView) findViewById(R.id.tv_label_left_yaxis_os);
        leftYAxisLabelUnit = (TextView) findViewById(R.id.tv_unit_left_yaxis_os);
        rightYAxisLabelUnit = (TextView) findViewById(R.id.tv_unit_right_yaxis_os);
        xAxisLabelUnit = (TextView) findViewById(R.id.tv_unit_xaxis_os);
        channelParametersTextView = (TextView) findViewById(R.id.tv_channel_parameters_os);
        timebaseTiggerTextView = (TextView) findViewById(R.id.tv_timebase_tigger_os);
        dataAnalysisTextView = (TextView) findViewById(R.id.tv_data_analysis_os);
        xyPlotTextView = (TextView) findViewById(R.id.tv_xy_plot_os);
        x1 = mChart.getXAxis();
        y1 = mChart.getAxisLeft();
        y2 = mChart.getAxisRight();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        onWindowFocusChanged();

        channelParametersFragment = new ChannelParametersFragment();
        timebasetiggerFragment = new TimebaseTiggerFragment();
        dataAnalysisFragment = new DataAnalysisFragment();
        xyPlotFragment = new XYPlotFragment();

        if (findViewById(R.id.layout_dock_os2) != null) {
            addFragment(R.id.layout_dock_os2, channelParametersFragment, "ChannelParametersFragment");
        }
        channelParametersButton.setOnClickListener(this);
        timebaseButton.setOnClickListener(this);
        dataAnalysisButton.setOnClickListener(this);
        xyPlotButton.setOnClickListener(this);
        channelParametersTextView.setOnClickListener(this);
        timebaseTiggerTextView.setOnClickListener(this);
        dataAnalysisTextView.setOnClickListener(this);
        xyPlotTextView.setOnClickListener(this);

        chartInit();
        
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_channel_parameters_os:
                replaceFragment(R.id.layout_dock_os2, channelParametersFragment, "ChannelParametersFragment");
                break;

            case R.id.tv_channel_parameters_os:
                replaceFragment(R.id.layout_dock_os2, channelParametersFragment, "ChannelParametersFragment");
                break;

            case R.id.button_timebase_os:
                replaceFragment(R.id.layout_dock_os2, timebasetiggerFragment, "TimebaseTiggerFragment");
                break;

            case R.id.tv_timebase_tigger_os:
                replaceFragment(R.id.layout_dock_os2, timebasetiggerFragment, "TimebaseTiggerFragment");
                break;

            case R.id.button_data_analysis_os:
                replaceFragment(R.id.layout_dock_os2, dataAnalysisFragment, "DataAnalysisFragment");
                break;

            case R.id.tv_data_analysis_os:
                replaceFragment(R.id.layout_dock_os2, dataAnalysisFragment, "DataAnalysisFragment");
                break;

            case R.id.button_xy_plot_os:
                replaceFragment(R.id.layout_dock_os2, xyPlotFragment, "XYPlotFragment");
                break;

            case R.id.tv_xy_plot_os:
                replaceFragment(R.id.layout_dock_os2, xyPlotFragment, "XYPlotFragment");
                break;
        }
    }

    public void onWindowFocusChanged() {
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        //dynamic placing the layouts
        if(tabletSize){
            RelativeLayout.LayoutParams lineChartParams = (RelativeLayout.LayoutParams) mChartLayout.getLayoutParams();
            lineChartParams.height = height * 3 / 4;
            lineChartParams.width = width * 7 / 8;
            mChartLayout.setLayoutParams(lineChartParams);
            RelativeLayout.LayoutParams frameLayoutParams = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
            frameLayoutParams.height = height / 4;
            frameLayoutParams.width = width * 7 / 8;
            frameLayout.setLayoutParams(frameLayoutParams);
        }
        else{
            RelativeLayout.LayoutParams lineChartParams = (RelativeLayout.LayoutParams) mChartLayout.getLayoutParams();
            lineChartParams.height = height * 2 / 3;
            lineChartParams.width = width * 5 / 6;
            mChartLayout.setLayoutParams(lineChartParams);
            RelativeLayout.LayoutParams frameLayoutParams = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
            frameLayoutParams.height = height / 3;
            frameLayoutParams.width = width * 5 / 6;
            frameLayout.setLayoutParams(frameLayoutParams);
        }
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .commit();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Oscilloscope")
                .setMessage("Are you sure you want to close the Oscilloscope?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void chartInit(){
        mChart.setTouchEnabled(true);
        mChart.setHighlightPerDragEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setPinchZoom(true);
        mChart.setScaleYEnabled(false);
        mChart.setBackgroundColor(Color.BLACK);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        Legend l = new Legend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        x1.setTextColor(Color.WHITE);
        x1.setDrawGridLines(true);
        x1.setAvoidFirstLastClipping(true);
        x1.setAxisMinimum(0f);
        x1.setAxisMaximum(875f);

        y1.setTextColor(Color.WHITE);
        y1.setAxisMaximum(16f);
        y1.setAxisMinimum(-16f);
        y1.setDrawGridLines(true);

        y2.setAxisMaximum(16f);
        y2.setAxisMinimum(-16f);
        y2.setTextColor(Color.WHITE);
        y2.setEnabled(true);
    }

    public void setXAxisScale(double timebase){
        x1.setAxisMinimum(0);
        x1.setAxisMaximum((float) timebase);
        if(timebase == 875f)
            xAxisLabelUnit.setText("(μs)");
        else
            xAxisLabelUnit.setText("(ms)");

        this.timebase = timebase;
        mChart.fitScreen();
        mChart.invalidate();
    }

    public void setLeftYAxisScale(double upperLimit, double lowerLimit){
        y1.setAxisMaximum((float) upperLimit);
        y1.setAxisMinimum((float) lowerLimit);
        if(upperLimit == 500f)
            leftYAxisLabelUnit.setText("(mV)");
        else
            leftYAxisLabelUnit.setText("(V)");
        mChart.fitScreen();
        mChart.invalidate();
    }

    public void setRightYAxisScale(double upperLimit, double lowerLimit){
        y2.setAxisMaximum((float) upperLimit);
        y2.setAxisMinimum((float) lowerLimit);
        if(upperLimit == 500f)
            rightYAxisLabelUnit.setText("(mV)");
        else
            rightYAxisLabelUnit.setText("(V)");
        mChart.fitScreen();
        mChart.invalidate();
    }

    public void setLeftYAxisLabel(String leftYAxisInput){
        this.leftYAxisInput = leftYAxisInput;
        leftYAxisLabel.setText(leftYAxisInput);
    }
}
