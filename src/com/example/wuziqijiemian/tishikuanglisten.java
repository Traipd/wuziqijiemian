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
	 public Canvas ca;//�����������ṩ��ͼ�ķ�����
     public Paint p=new Paint();//����
     public Bitmap bi;//λͼ
     public ImageView image;//��ʾͼƬ�����
     public samelist<qiquality> qilist;
     public int[][] qiexist;//1Ϊ�ڣ�-1Ϊ��,0Ϊ��
	 public mainlisten ml;
	
	public tishikuanglisten(Canvas ca, Bitmap bi,ImageView image,samelist<qiquality> qilist,int[][] qiexist,mainlisten ml){
		this.ca=ca;this.bi=bi;this.image=image;this.qilist=qilist;this.qiexist=qiexist;this.ml=ml;
	}


	public void onClick(DialogInterface dialog, int which) {
		// which ��-2 ��-1
		if(which==-1){ml.stopflag=true;}
		else{ml.stopflag=false;
			ml.qiexist=new int[21][21];//���ü�¼
		     ml.qilist=new samelist<qiquality>();
		 ml.p.setColor(Color.BLACK);//���û���
		 ml.p.setAntiAlias(true);
		 ml.bi=Bitmap.createBitmap(ml.vwidth, ml.vheight, Bitmap.Config.ARGB_8888);
		 ml.ca=new Canvas(ml.bi);
		 for(int i=0;i<21;i++){
			 ml.ca.drawLine(ml.vwidth/22+ml.vwidth*i/22,ml.vheight/22, ml.vwidth/22+ml.vwidth*i/22, ml.vheight*21/22, ml.p);//����
			 ml.ca.drawLine(ml.vwidth/22,ml.vheight/22+ml.vheight*i/22, ml.vwidth*21/22, ml.vheight/22+ml.vheight*i/22, ml.p);//���� 

		 }
		 ml.image.setImageBitmap(ml.bi);
		 ml.image=this.image;
		}
	}


}
