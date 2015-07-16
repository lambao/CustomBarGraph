package com.example.lambao.test;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.widget.LinearLayout;

public class HsGraphBar extends LinearLayout{
	
	//グラフの高さ
	int graphHeight = 300;
	
	//グラフの値
	List<Integer>values = new ArrayList<Integer>();
	
	//グラフのxラベル
	List<String>xLabels = new ArrayList<String>();
	List<String>yLabels = new ArrayList<String>();
	
	
	//valuesの中の最大値
	Integer maxValue = 0;

	//ヨコの線の数を指定します。５個くらい線を描こうかと思います・・・。
	Integer yLineCount = 5;
	
	
	//ラベルの高さを省いたグラフのサイズ
	Integer plotHeight = 0;
	
	//ラベルの幅を省いたグラフのサイズ
	Integer plotWidth = 0;
	
	/**
	 * コンストラクター
	 * Viewのクラス内では、getContext()でcontextの取得ができる
	 * 
	 * @param context
	 */
	public HsGraphBar(Context context) {
		super(context);
		
		setWillNotDraw(false);
	}
	
	
	/**
	 * 棒グラフの値を一度にセットする
	 * 
	 * 
	 */
	public void setValues(List<Integer>values){
		this.values = values;
	}
	
	
	/**
	 * x軸のラベルを一度に全部セットする
	 * 
	 * 
	 */
	public void setXLabels(List<String>xLabels){
		this.xLabels = xLabels;
	}	
	
	
	/**
	 * AddViewなんかで実際にviewが追加されたときに呼び出される
	 * 
	 */
	@Override
	protected void onAttachedToWindow(){
		super.onAttachedToWindow();

	}
	
	/**
	 * タテヨコの大きさを決める時に呼び出される
	 * Viewの大きさを指定したい場合は、ここで指定
	 * 
	 */
	@Override
	protected void onMeasure(int width, int height) {
		super.onMeasure(width, height);
		
		//グラフのサイズを指定
		setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, graphHeight));
	}
	
	
	/**
	 * レイアウトを決める時に呼び出される
	 * 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	/**
	 * 描画する！
	 * 
	 */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//valuesの中の最大値を見つけ出す
		for(int i=0;i<values.size();i++){
			if(values.get(i)>maxValue){
				maxValue = values.get(i);
			}
		}
		
		//## 修正 ### yのライン数がmaxValueを下回る場合は、maxValueをyのライン数にする
		if(maxValue<yLineCount){
			yLineCount= maxValue;
		}
		
		
		//## 修正 ### グラフの上に余白を生み出すためにちょっとだけかけてやる
		maxValue = ((Float)(((Integer)maxValue).floatValue()*1.3F)).intValue();
		
		//ラベルのペイントを取る
		Paint txtPaint = getLabelPaint();
		
		//高さを出したいので、仮に一番最初のラベルの文字列を取得します
		String txt = xLabels.get(0); 
		
		//四角形
		Rect bounds = new Rect();
		
		//指定した文字列を入れる四角形を作成
		txtPaint.getTextBounds(txt,0,txt.length(),bounds);
		
		//四角形の高さを取得 = 文字列の高さ
		int xHeight = bounds.height();
		
		//グラフだけの高さを計算
		//## 修正 ### ちょっとだけ余白を入れる
		plotHeight = getMeasuredHeight()-(xHeight*2);
		
		
		//### Y軸ラベルを作成 ###

		
		//それぞれのYの値を計算。誤差を少なくする為に一旦floatに変えてます
		Float eachY = maxValue.floatValue()/yLineCount.floatValue();
		Float tmpY = 0F;
		
		//先にフィールドとして宣言したyLabelsを初期化
		yLabels = new ArrayList<String>();
		
		for(int i=0; i<yLineCount; i++){
			yLabels.add( String.valueOf(tmpY.intValue()));
			tmpY = tmpY + eachY;
		}
		
		//y軸の幅を取得します。変数はxのを流用。
		//一番最後の値がでかそうなので一番最後の文字列を取得
		txt = yLabels.get(yLabels.size()-1);
		txtPaint.getTextBounds(txt,0,txt.length(),bounds);
		int yWidth = bounds.width();
		
		//グラフだけの幅を計算
		//## 修正 ### ちょっとだけ余白を入れる
		plotWidth = getMeasuredWidth() - (yWidth*2);
		
		
		
		//描画する！
		//drawLabels(canvas);
		//drawBackground(canvas);
		drawBars(canvas);
		
	}
	
	/**
	 * ラベルの色・サイズはY軸、X軸ともに共通のを使用するので一つにまとめます
	 * Paintは、色やサイズなんかを保持するオブジェクトです。
	 * 
	 * 
	 */
	private Paint getLabelPaint(){

		Paint paint = new Paint();
		//アンチエイリアス
		paint.setAntiAlias(true);
		//文字の色
		paint.setColor(Color.parseColor("#777777"));
		//文字のサイズ
		paint.setTextSize(15);		
		
		
		return paint;		
	}
	
	
	/**
	 * ラベルを書く
	 * 
	 * @param canvas
	 */
	private void drawLabels(Canvas canvas){
		
		Paint paint = getLabelPaint();
		//右揃えにする
		paint.setTextAlign(Align.RIGHT);
		
		
		//## Yのラベルを描く！
		//線のそれぞれの高さを計算
		Float yEach = plotHeight.floatValue()/yLineCount.floatValue();
		
		
		
		//yLabelsは、0から入ってるのでグラフの下の部分から描画します。
		Float yCurrent = plotHeight.floatValue();
		
		//微調整。文字の大きさの半分だけずらす		
		Rect bounds = new Rect();
		String txt = yLabels.get(yLabels.size()-1);
		paint.getTextBounds(txt, 0, txt.length(), bounds);
		yCurrent = yCurrent + (bounds.height()/2);
		//##修正## 開始位置も文字半分ずらす
		int halfWidth = bounds.width()/2;
		
		
		//前に計算した文字列の幅をもっかい計算するという愚行
		int xPlotStart = getMeasuredWidth() - plotWidth;
		
		for(int i=0; i< yLabels.size();i++){
			//テキストを描画します。
			//xの位置は、右側を指定
			canvas.drawText(yLabels.get(i), xPlotStart - halfWidth, yCurrent, paint);
			
			//次の位置を計算
			yCurrent = yCurrent - yEach;
		}
		
		
		//## Xのラベルを描く！
		
		//中央ぞろえ
		paint.setTextAlign(Align.CENTER);
		
		//それぞれの幅を計算
		Float xEach = plotWidth.floatValue()/((Integer)xLabels.size()).floatValue();
		
		//さらにそれぞれの真ん中の位置を計算
		Float center = xEach/2;
		
		//yで計算したxの位置を流用。
		Float tmpX = xPlotStart + center;
		
		for(int i=0;i<xLabels.size();i++){
			canvas.drawText(xLabels.get(i), tmpX, getMeasuredHeight(), paint);
			tmpX = tmpX + xEach;
		}
	}
	
	
	/**
	 * 背景を書く
	 * 
	 * @param canvas
	 */
	private void drawBackground(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		int plotStartX = getMeasuredWidth() - plotWidth;
		int plotStartY = 0;
		
		
		//background
		paint.setColor(Color.parseColor("#EFEFEF"));
		Rect rect = new Rect(plotStartX, 0, getMeasuredWidth(), plotHeight);
	    canvas.drawRect(rect, paint);			
	    
	    
	    //## yの線を描く！
		Float yEach = plotHeight.floatValue()/yLineCount.floatValue();

		//yLabelsは、0から入ってるのでグラフの下の部分から描画します。
		Float yCurrent = plotHeight.floatValue();
		
		//前に計算した文字列の幅をもっかい計算するという愚行
		int xPlotStart = getMeasuredWidth() - plotWidth;
		
		for(int i=0; i< yLabels.size();i++){
			
			if(i==0){
				paint.setStrokeWidth(2);
				paint.setColor(Color.parseColor("#777777"));
			}else{
				paint.setStrokeWidth(1);
				paint.setColor(Color.parseColor("#AAAAAA"));
			}
			
			//線を描きます
			canvas.drawLine(xPlotStart, yCurrent, getMeasuredWidth(), yCurrent, paint);
			
			//次の位置を計算
			yCurrent = yCurrent - yEach;
		}	
		
		
		
		
	}	
	
	
	/**
	 * バーを書く
	 * 
	 * @param canvas
	 */
	private void drawBars(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);		
		
		paint.setColor(Color.parseColor("#777777"));
		
		//それぞれの幅を計算
		Float xEach = plotWidth.floatValue()/((Integer)xLabels.size()).floatValue();
		
		//棒グラフに適度な余白を追加します。
		Float barSpace = 20F;
		
		//前に計算した文字列の幅をもっかい計算するという愚行
		Integer xPlotStart = getMeasuredWidth() - plotWidth;	
		
		//最初のxの位置を計算
		//xの開始位置
		Float tmpXLeft = xPlotStart.floatValue() + barSpace ;
		//xの終わり
		Float tmpXRight = xPlotStart + xEach - barSpace ;
		
		
		//valuesの値1に対する高さを計算
		Float eachHeight = plotHeight.floatValue()/maxValue.floatValue();
		
		for(int i=0;i<values.size();i++){
			canvas.drawRect(tmpXLeft, plotHeight -(eachHeight*values.get(i)), tmpXRight, plotHeight, paint);
			tmpXLeft = tmpXLeft + xEach;
			tmpXRight = tmpXRight + xEach;
		}		
	}		
}
