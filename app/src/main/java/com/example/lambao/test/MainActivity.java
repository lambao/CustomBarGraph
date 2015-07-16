package com.example.lambao.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//グラフビューを作成
		HsGraphBar graph = new HsGraphBar(this);
		
		
		//グラフの値
		List<Integer>values = new ArrayList<Integer>();

		//グラフのxラベル
		List<String>xLabels = new ArrayList<String>();
		
		//偽のデータを用意します
		Random rnd = new Random(); 
		for(int i=0;i<5;i++){
			values.add(rnd.nextInt(100));
			xLabels.add(String.valueOf(i) + "番目");
		}
		
		//値をグラフに渡す
		graph.setValues(values);
		
		//xラベルをグラフに渡す
		graph.setXLabels(xLabels);
		
		
		
		//グラフを挿入
		//追加できるのはLinearLayoutのみ！
		((LinearLayout)findViewById(R.id.graphTarget)).addView(graph);
	}
	
	
	@Override
	protected void onStart(){
		super.onStart();
		
		

		
	}
	
}
