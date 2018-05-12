package com.example.wuziqijiemian;

import com.example.wuziqijiemian.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageView image=(ImageView)this.findViewById(id.qipan);
		Button xinqiju=(Button)this.findViewById(id.xinqiju);
		Button huiqi=(Button)this.findViewById(id.huiqi);
		Button duizhan=(Button)this.findViewById(id.duizhan);
		mainlisten ml=new mainlisten(image,this);
		image.setOnTouchListener(ml);
		Buttonlisten bl=new Buttonlisten(ml);
		xinqiju.setOnClickListener(bl);
		huiqi.setOnClickListener(bl);
		duizhan.setOnClickListener(bl);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
