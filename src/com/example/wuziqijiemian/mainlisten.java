package com.example.wuziqijiemian;

import java.util.HashMap;
import java.util.Random;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class mainlisten implements OnTouchListener{
	 public MainActivity ma;
     public float x,y;
     public Canvas ca;//画布（用来提供画图的方法）
     public Paint p=new Paint();//画笔
     public Bitmap bi;//位图
     public ImageView image;//显示图片的组件
     public int qicolor=-1;//1为黑，-1为白
     public qiquality qi;
     public samelist<qiquality> qilist=new samelist<qiquality>();
     public int[][] qiexist=new int[21][21];//1为黑，-1为白
     public tishikuanglisten tsklis;
     public boolean stopflag=false;//暂停标记
     public int vwidth,vheight;
     public boolean AIflag=false;//人机启动标记
     public int AIaction=-1;//-1未动；1已动
     public Random ra=new Random();
     public int[][] weightArray = new int[21][21];//二维权值数组
     public int AIx=0,AIy=0;
     public int AIcolor;
     
     
     static HashMap<String,Integer>  map = new HashMap<String,Integer>();
	 static {map.put("0",0);
	 map.put("01-1",200);map.put("0-11",200); 
	 map.put("010",250);map.put("0-10",250); 
	 
	 map.put("010-1",240);map.put("0-101",240); 
	 map.put("011-1",400);map.put("0-1-11",400); 
	 map.put("0101",450);map.put("0-10-1",450); 
	 map.put("0110",570);map.put("0-1-10",570); 
	 
	 map.put("0111-1",750);map.put("0-1-1-11",750); 
	 map.put("0110-1",560);map.put("0-1-101",560); 
	 map.put("01110",800);map.put("0-1-1-10",800); 
	 map.put("01111",10000);map.put("0-1-1-1-1",10000); 
	  }
     
     public mainlisten(ImageView image,MainActivity ma){
    	 this.image=image;this.ma=ma;
    	 p.setAntiAlias(true);
     }
	
    public boolean win(){//判断胜负、
    	boolean flag=false;
    	for(int i=qilist.length();i>0;i--){
    	if(win2(qilist.get(qilist.length()-i),1, 1,0)){flag=true;System.out.println("");;break;}//右边
    	if(win2(qilist.get(qilist.length()-i),1, 1,-1)){flag=true;System.out.println("");break;}//右上
    	if(win2(qilist.get(qilist.length()-i),1, 0,-1)){flag=true;System.out.println("");break;}//上边
    	if(win2(qilist.get(qilist.length()-i),1, -1,-1)){flag=true;System.out.println("");break;}//左上
    	if(win2(qilist.get(qilist.length()-i),1, -1,0)){flag=true;System.out.println("");break;}//左边
    	if(win2(qilist.get(qilist.length()-i),1, -1,1)){flag=true;System.out.println("");break;}//左下
    	if(win2(qilist.get(qilist.length()-i),1, 0,1)){flag=true;System.out.println("");break;}//下方
    	if(win2(qilist.get(qilist.length()-i),1, 1,1)){flag=true;System.out.println("");break;}//右下
    	}
    	return flag;
    }
    public boolean win2(qiquality qi,int i,int mx,int my){
    	boolean flag=false;
    	if(0<=(qi.qix+i*mx)&&20>=(qi.qix+i*mx)&&0<=(qi.qiy+i*my)&&20>=(qi.qiy+i*my)){
    	 if(qi.color==qiexist[qi.qix+i*mx][qi.qiy+i*my])
    	 {   if(i==4){return true;} 
    		i=i+1;
    		flag=win2(qi,i,mx,my);
    	 }
    	}
    	return flag;
    }
    
    public boolean AImove(){           //AI算法
    	 this.AIaction=1;
    	 for(int r=0;r<qiexist.length;r++){
				for(int c=0;c<qiexist[r].length;c++){
					
					if(AImove2( r,c,0,-1)){return true;}//上与反向
					if(AImove2( r,c,1,-1)){return true;}//右上与反向
					if(AImove2( r,c,1,0)){return true;}//右与反向
					if(AImove2( r,c,1,1)){return true;}//右下与反向
				}
			}
    	  
    	return false;
    	  
    }
    public void AIgo(){
    	for(int r=0;r<qiexist.length;r++){
				for(int c=0;c<qiexist[r].length;c++){
					if(weightArray[r][c]>weightArray[AIx][AIy])
					{AIx=r;AIy=c;}
				}
			}
    }
    public boolean AImove2(int r,int c,int vx,int vy){
		//判断该位置是否是空位
    	Integer weight1;
    	Integer weight;
		if(this.qiexist[r][c]==0){
			//vx，vy方向上统计
			
			int count = 0;//定义存储空位次数的变量
			int chess = 0;//定义存储第一次出现的棋子的变量
			String chessCode="0";//定义用来存储棋子相连情况的变量
		
			for(int r1=r+vx,c1 = c+vy;r1!=(r+5*vx)||c1!=(c+5*vy);r1=r1+vx,c1=c1+vy){//vx，vy方向上统计
			
			  if(r1<0||r1>20||c1<0||c1>20){break;}//防止出界
			  else{	
			    if(this.qiexist[r1][c1]==this.qiexist[r][c]){//判断c1位置是否是空位
					if(c1 == (c+vy)&&r1==(r+vx)){//判断是否相邻
						break;
					}else if(count==0){//判断是否是第一次出现空位
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
						count++;
					}else if(count==1){//判断是否是第二次出现空位
//						chessCode = this.qiexist[r][c1]+chessCode;//记录棋子相连的情况
						break;
					}
				}else {//判断c1位置是否是棋子
					
					
					if(chess==0){//判断是否是第一次出现棋子
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
						chess = this.qiexist[r1][c1];//将第一次出现的棋子存入到chess中，便于之后的判断
						
						if(this.qiexist[r1][c1]!=this.AIcolor){weightArray[r][c]++;}
						
					}else if(chess==this.qiexist[r1][c1]){//判断之后出现的棋子是否和第一出现的棋子是相同颜色的
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
						
					}else {//判断不是相同颜色的棋子
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
						break;
					}
			
			      }	
			    
					
			    }
			
			}
//			System.out.println("最后结果a"+chessCode);
			//根据chessCode从HashMap中获取对应的权值
			weight = map.get(chessCode);
			weight1=map.get(chessCode);
			if(weight!=null){//判断chessCode相连的情况是否存在
				weightArray[r][c]+=weight;//将权值累加起来
			   
			    if((int)weight>=770){weightArray[r][c]=9000;}//必赢情况
			}
			
			
			count = 0;//定义存储空位次数的变量
			chess = 0;//定义存储第一次出现的棋子的变量
			chessCode="0";//定义用来存储棋子相连情况的变量
		
			for(int r1=r-vx,c1 = c-vy;r1!=(r-5*vx)||c1!=(c-5*vy);r1=r1-vx,c1=c1-vy){//vx，vy 逆 方向上统计
			  
			if(r1<0||r1>20||c1<0||c1>20){break;}//防止出界
			else{	
				if(this.qiexist[r1][c1]==this.qiexist[r][c]){//判断c1位置是否是空位
					if(c1 == (c-vy)&&r1==(r-vx)){//判断是否相邻
						break;
					}else if(count==0){//判断是否是第一次出现空位
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
						count++;
					}else if(count==1){//判断是否是第二次出现空位
//						chessCode = this.qiexist[r][c1]+chessCode;//记录棋子相连的情况
						break;
					}
				}else {//判断c1位置是否是棋子
					if(chess==0){//判断是否是第一次出现棋子
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
						chess = this.qiexist[r1][c1];//将第一次出现的棋子存入到chess中，便于之后的判断
						
						if(this.qiexist[r1][c1]!=this.AIcolor){weightArray[r][c]++;}
						
					}else if(chess==this.qiexist[r1][c1]){//判断之后出现的棋子是否和第一出现的棋子是相同颜色的
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
					}else {//判断不是相同颜色的棋子
						chessCode = chessCode+this.qiexist[r1][c1];//记录棋子相连的情况
						break;
					}
					
			  }
				
			}
			
			}
//			System.out.println("最后结果b"+chessCode);
			weight = map.get(chessCode);
			Integer weight2=map.get(chessCode);
			
			if(weight!=null){//判断chessCode相连的情况是否存在
				weightArray[r][c]+=weight;//将权值累加起来
			  if(weight1!=null&&weight2!=null)
			    if(((int)weight1+(int)weight2)>=770){weightArray[r][c]=9000;}//必赢情况
			
			return false;
		 }
		}
		return false;
    }
    
	public boolean onTouch(View v, MotionEvent event) {
//		System.out.println(this.stopflag);
	if(this.stopflag==false)
	{	
		if(bi==null)
			{vwidth=v.getWidth();vheight=v.getHeight();
			 this.p.setColor(Color.BLACK);
			 bi=Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
			 ca=new Canvas(bi);
			 for(int i=0;i<21;i++){
				 ca.drawLine(v.getWidth()/22+v.getWidth()*i/22, v.getHeight()/22, v.getWidth()/22+v.getWidth()*i/22, v.getHeight()*21/22, p);//竖线
				 ca.drawLine(v.getWidth()/22,v.getHeight()/22+v.getHeight()*i/22, v.getWidth()*21/22, v.getHeight()/22+v.getHeight()*i/22, p);//横线 

			 }
			 image.setImageBitmap(bi);
			 }
		else{
		 switch (event.getAction()) {     //点击屏幕反应
		 case MotionEvent.ACTION_DOWN:

			 this.x=event.getX();
			this.y=event.getY();
			this.qicolor=-this.qicolor;
			qi=new qiquality();
			qi.color=this.qicolor;
			if(this.qicolor==1)
			 {this.p.setColor(Color.BLACK);}
			else
			 {this.p.setColor(Color.GRAY);}

			int qix=(int)((this.x-v.getWidth()/44.00)/(v.getWidth()/22.00));//确定棋子位置
			int qiy=(int)((this.y-v.getHeight()/44.00)/(v.getHeight()/22.00));// float/int 存在转换为int型的可能
			 
			 if(qix>20){qix=20;}  if(qix<0){qix=0;} 
			 if(qiy>20){qiy=20;}  if(qiy<0){qiy=0;}
			 qi.qix=qix;qi.qiy=qiy;
			
			 if(qiexist[qix][qiy]!=1&&qiexist[qix][qiy]!=-1){//保存棋子
			 qiexist[qix][qiy]=qicolor;	
			 qilist.add(qi); 
			 ca.drawCircle((qix+1)*v.getWidth()/22, (qiy+1)*v.getHeight()/22, v.getWidth()/44, p);
//			System.out.println(qix+"  "+qiy);
	//		 System.out.println(qiexist[0][0]+"  "+qiexist[1][0]); 
			image.setImageBitmap(bi);
			this.AIaction=-1;       //标记AI为未动
			}
			 else{this.qicolor=-this.qicolor;}
			 			 
		 	
		 	
			  if(win()&&this.stopflag==false)
			   {           //添加提示框
				  this.stopflag=true;
					AlertDialog.Builder builder= new AlertDialog.Builder(ma);//ma为活动名
				    builder.setIcon(android.R.drawable.ic_dialog_info);
				    builder.setTitle("提示");
				    if(this.qicolor==1)
				    {  
				    	builder.setMessage("黑棋胜");
				     
				    }
				    else
				    {
			    	 builder.setMessage("白棋胜");
				      }
				    tsklis=new tishikuanglisten(ca, bi, image, qilist, qiexist,this);
				    builder.setPositiveButton("查看棋局", tsklis).setNegativeButton("再来一局", tsklis);
				    builder.create().show();//
			    }
			 if(this.AIflag){//AI运行部分
                if(this.AIaction==-1){
                	
                	this.qicolor=-this.qicolor;
        			qi=new qiquality();
        			qi.color=this.qicolor;
        			this.AIcolor=qi.color;
        			
                	AImove();
                	AIgo();
                	
//                	//测试用输出权值
//                	for(int x=0;x<21;x++){
//                		for(int y=0;y<21;y++)
//                		{ 
//                			System.out.print(weightArray[x][y]+"　");
//                		}System.out.println();
//                	}
                	
        			if(this.qicolor==1)
        			 {this.p.setColor(Color.BLACK);}
        			else
        			 {this.p.setColor(Color.GRAY);}
        			qi.qix=AIx;qi.qiy=AIy;
        			weightArray = new int[21][21];
        			if(qiexist[qi.qix][qi.qiy]!=1&&qiexist[qi.qix][qi.qiy]!=-1){//保存棋子
        				 qiexist[qi.qix][qi.qiy]=this.AIcolor;
        				 qilist.add(qi); 
        				 ca.drawCircle((qi.qix+1)*v.getWidth()/22, (qi.qiy+1)*v.getHeight()/22, v.getWidth()/44, p);
        				image.setImageBitmap(bi);
        			}
                }
		 	}
			 
			  if(win()&&this.stopflag==false)
			   {                                              //添加提示框
				   this.stopflag=true;
					AlertDialog.Builder builder= new AlertDialog.Builder(ma);//ma为活动名
				    builder.setIcon(android.R.drawable.ic_dialog_info);
				    builder.setTitle("提示");
				    if(this.qicolor==1)
				    {  
				    	builder.setMessage("黑棋胜");
				     
				    }
				    else
				    {
			    	 builder.setMessage("白棋胜");
				      }
				    tsklis=new tishikuanglisten(ca, bi, image, qilist, qiexist,this);
				    builder.setPositiveButton("查看棋局", tsklis).setNegativeButton("再来一局", tsklis);
				    builder.create().show();//
			    } 
			 
			break;

		 default:
			
			 break;
		 }
		}
		
		}
	  return false;
	 }
	
	
	
	
 }


