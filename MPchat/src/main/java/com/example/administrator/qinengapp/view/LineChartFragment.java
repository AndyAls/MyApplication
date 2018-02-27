package com.example.administrator.qinengapp.view;
;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.qinengapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/12.
 */
public class LineChartFragment extends Fragment {

    private LineData lineData=null;
    private LineChart lcLineChart;
    private ArrayList<String> x =null;//x轴数据集合
    private ArrayList<Entry> data=null;
    private LineData resultLineDate;

    public LineChartFragment(){

        for (int i=0;i<10;i++){
            Entry entry = new Entry( 10 * i,i);
            if (x==null){
                x=new ArrayList<String>();
            }
            if (data==null){
                data=new ArrayList<Entry>();
            }
            x.add(i+"");
            data.add(entry);
        }


    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        LineData resultLineDate=getLineDate();
//        showChart();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.line_chart, container, false);
        lcLineChart = (LineChart) view.findViewById(R.id.lc_line_chart);
        resultLineDate = getLineDate();
        showChart();
        return view;

    }

    /**
     * 设置数据
     * @return
     */
    public LineData getLineDate() {

        ArrayList<LineDataSet> lineDataSets=new ArrayList<LineDataSet>();

        LineDataSet lineDataSet = new LineDataSet(data, "折线图");//y轴数据集合
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawCubic(true);//设置平滑曲线
        lineDataSet.setCubicIntensity(0.6f);//设置曲线的平滑度
        lineDataSet.setLineWidth(1f);//线宽
        lineDataSet.setCircleSize(2f);//现实圆形大小
        lineDataSet.setColor(Color.RED);//现实颜色
        lineDataSet.setCircleSize(Color.BLUE);//圆形颜色
        lineDataSet.setHighLightColor(Color.WHITE);//高度线的颜色
        lineDataSets.add(lineDataSet);
        lineData = new LineData(x, lineDataSets);
        return lineData;
    }

    /**
     * 设置样式
     */
    private void showChart() {
        lcLineChart.setDrawBorders(true);
        lcLineChart.setDescription("风险数据");
        lcLineChart.getAxisRight().setEnabled(false);
        lcLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lcLineChart.getXAxis().setGridColor(getResources().getColor(R.color.transparent));
        lcLineChart.setData(lineData);//设置数据
        Legend legend = lcLineChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(6f);
        legend.setTextColor(Color.YELLOW);
        lcLineChart.animateX(2000);
        lcLineChart.showContextMenu();
        lcLineChart.setData(resultLineDate);
        lcLineChart.setNoDataText("怎么没数据呢");
    }

}
