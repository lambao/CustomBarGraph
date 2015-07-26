package com.example.lambao.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements View.OnClickListener {

	private Button mLeftBtn;
	private Button mCenterBtn;
	private Button mRightBtn;

	private RadioGroup mRadioGrp;
	private RadioButton mLeftRadioBtn;
	private RadioButton mRightRadioBtn;
	private RadioButton mCenterRadioBtn;

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

		mLeftBtn = (Button) findViewById(R.id.left_button);
		mCenterBtn = (Button) findViewById(R.id.center_button);
		mRightBtn = (Button) findViewById(R.id.right_button);
		mLeftBtn.setOnClickListener(this);

		mRadioGrp = (RadioGroup) findViewById(R.id.radio_grp);
		mLeftRadioBtn = (RadioButton) findViewById(R.id.left);
		mRightRadioBtn = (RadioButton) findViewById(R.id.right);
		mCenterRadioBtn = (RadioButton) findViewById(R.id.center);
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
			case R.id.left_button:
				int selectedBtn = mRadioGrp.getCheckedRadioButtonId();
				switch (selectedBtn) {
					case R.id.left:
						Log.e("LamLB", "Left selected");
						break;
					case R.id.center:
						Log.e("LamLB", "Center selected");
						break;
					case R.id.right:
						Log.e("LamLB", "Right selected");
						break;
					default:
						break;
				}
				break;
			case R.id.center_button:
				break;
			case R.id.right_button:
				break;
			default:
				break;
		}
	}
}
