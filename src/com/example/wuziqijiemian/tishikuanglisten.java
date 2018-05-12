package com.example.wuziqijiemian;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class tishikuanglisten implements android.content.DialogInterface.OnClickListener{
	 public Canvas ca;//画布（用来提供画图的方法）
     public Paint p=new Paint();//画笔
     public Bitmap bi;//位图
     public ImageView image;//显示图片的组件
     public samelist<qiquality> qilist;
     public int[][] qiexist;//1为黑，-1为白,0为空
	 public mainlisten ml;
	
	public tishikuanglisten(Canvas ca, Bitmap bi,ImageView image,samelist<qiquality> qilist,int[][] qiexist,mainlisten ml){
		this.ca=ca;this.bi=bi;this.image=image;this.qilist=qilist;this.qiexist=qiexist;this.ml=ml;
	}


	public void onClick(DialogInterface dialog, int which) {
		// which 左-2 右-1
		if(which==-1){ml.stopflag=true;}
		else{ml.stopflag=false;
			ml.qiexist=new int[21][21];//重置记录
		     ml.qilist=new samelist<qiquality>();
		 ml.p.setColor(Color.BLACK);//重置画布
		 ml.p.setAntiAlias(true);
		 ml.bi=Bitmap.createBitmap(ml.vwidth, ml.vheight, Bitmap.Config.ARGB_8888);
		 ml.ca=new Canvas(ml.bi);
		 for(int i=0;i<21;i++){
			 ml.ca.drawLine(ml.vwidth/22+ml.vwidth*i/22,ml.vheight/22, ml.vwidth/22+ml.vwidth*i/22, ml.vheight*21/22, ml.p);//竖线
			 ml.ca.drawLine(ml.vwidth/22,ml.vheight/22+ml.vheight*i/22, ml.vwidth*21/22, ml.vheight/22+ml.vheight*i/22, ml.p);//横线 

		 }
		 ml.image.setImageBitmap(ml.bi);
		 ml.image=this.image;
		}
	}


}
