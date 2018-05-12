package com.example.wuziqijiemian;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Buttonlisten implements OnClickListener{
	public Paint p=new Paint();//画笔
    public mainlisten ml;
//    public ImageView image;//显示图片的组件
    
    public Buttonlisten(mainlisten ml){this.ml=ml;}
    
	public void onClick(View v) {
		Button but = (Button) v;
	  String s=	but.getText().toString();
      if(s.equalsIgnoreCase("新棋局")){
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
		 ml.stopflag=false;
      }
      
      if(s.equalsIgnoreCase("悔棋")){
//System.out.println(">>>>>>>>>>>>>>>>>");    	  
    	  qiquality qh=ml.qilist.remove();
    	  ml.qiexist[qh.qix][qh.qiy]=0;
    	  ml.p.setColor(Color.BLACK);//重置画布
 		 ml.p.setAntiAlias(true);
 		 ml.bi=Bitmap.createBitmap(ml.vwidth, ml.vheight, Bitmap.Config.ARGB_8888);
 		 ml.ca=new Canvas(ml.bi);
 		 for(int i=0;i<21;i++){//画棋盘
 			 ml.ca.drawLine(ml.vwidth/22+ml.vwidth*i/22,ml.vheight/22, ml.vwidth/22+ml.vwidth*i/22, ml.vheight*21/22, ml.p);//竖线
 			 ml.ca.drawLine(ml.vwidth/22,ml.vheight/22+ml.vheight*i/22, ml.vwidth*21/22, ml.vheight/22+ml.vheight*i/22, ml.p);//横线 
 		 }int j=0;
 		 for(;j<ml.qilist.length();j++)
 		{    if(ml.qilist.get(j).color==1){ml.p.setColor(Color.BLACK);}else{ml.p.setColor(Color.GRAY);}
 			 ml.ca.drawCircle((ml.qilist.get(j).qix+1)*ml.vwidth/22, (ml.qilist.get(j).qiy+1)*ml.vheight/22, ml.vwidth/44, ml.p);
 		} 
 		     if(ml.qilist.get(j-1).color==1){ml.qicolor=1;}
 		     else{ml.qicolor=-1;}
 		     
 		 ml.stopflag=false;
 		 ml.image.setImageBitmap(ml.bi);
      }
      
      if(s.equalsIgnoreCase("人机对战/人人对战")){
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
	   
		 
		 ml.AIflag=!ml.AIflag;
		 if(ml.AIflag)
		 { Toast.makeText(ml.ma, "人机模式已启动" , Toast.LENGTH_SHORT).show();
		    if(ml.AIaction==-1){      //AI先走
		    	ml.qi=new qiquality();
		      ml.qicolor=-ml.qicolor;
		      ml.qi.color=ml.qicolor;
		        if(ml.qicolor==1){ml.p.setColor(Color.BLACK);}
		        else {ml.p.setColor(Color.GRAY);}
		      ml.qi.qix=7+ml.ra.nextInt(8);ml.qi.qiy=7+ml.ra.nextInt(8);
		      ml.qilist.add(ml.qi);
		      ml.ca.drawCircle((ml.qi.qix+1)*ml.vwidth/22, (ml.qi.qiy+1)*ml.vheight/22, ml.vwidth/44, ml.p);
		      ml.AIaction=-ml.AIaction;
	        }
		 }
		 else{Toast.makeText(ml.ma, "人机模式已关闭" , Toast.LENGTH_SHORT).show();}
		
		 ml.stopflag=false;
          ml.image.setImageBitmap(ml.bi);
      }   
      
      
      
	}

	

}
