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
     public Canvas ca;//�����������ṩ��ͼ�ķ�����
     public Paint p=new Paint();//����
     public Bitmap bi;//λͼ
     public ImageView image;//��ʾͼƬ�����
     public int qicolor=-1;//1Ϊ�ڣ�-1Ϊ��
     public qiquality qi;
     public samelist<qiquality> qilist=new samelist<qiquality>();
     public int[][] qiexist=new int[21][21];//1Ϊ�ڣ�-1Ϊ��
     public tishikuanglisten tsklis;
     public boolean stopflag=false;//��ͣ���
     public int vwidth,vheight;
     public boolean AIflag=false;//�˻��������
     public int AIaction=-1;//-1δ����1�Ѷ�
     public Random ra=new Random();
     public int[][] weightArray = new int[21][21];//��άȨֵ����
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
	
    public boolean win(){//�ж�ʤ����
    	boolean flag=false;
    	for(int i=qilist.length();i>0;i--){
    	if(win2(qilist.get(qilist.length()-i),1, 1,0)){flag=true;System.out.println("");;break;}//�ұ�
    	if(win2(qilist.get(qilist.length()-i),1, 1,-1)){flag=true;System.out.println("");break;}//����
    	if(win2(qilist.get(qilist.length()-i),1, 0,-1)){flag=true;System.out.println("");break;}//�ϱ�
    	if(win2(qilist.get(qilist.length()-i),1, -1,-1)){flag=true;System.out.println("");break;}//����
    	if(win2(qilist.get(qilist.length()-i),1, -1,0)){flag=true;System.out.println("");break;}//���
    	if(win2(qilist.get(qilist.length()-i),1, -1,1)){flag=true;System.out.println("");break;}//����
    	if(win2(qilist.get(qilist.length()-i),1, 0,1)){flag=true;System.out.println("");break;}//�·�
    	if(win2(qilist.get(qilist.length()-i),1, 1,1)){flag=true;System.out.println("");break;}//����
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
    
    public boolean AImove(){           //AI�㷨
    	 this.AIaction=1;
    	 for(int r=0;r<qiexist.length;r++){
				for(int c=0;c<qiexist[r].length;c++){
					
					if(AImove2( r,c,0,-1)){return true;}//���뷴��
					if(AImove2( r,c,1,-1)){return true;}//�����뷴��
					if(AImove2( r,c,1,0)){return true;}//���뷴��
					if(AImove2( r,c,1,1)){return true;}//�����뷴��
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
		//�жϸ�λ���Ƿ��ǿ�λ
    	Integer weight1;
    	Integer weight;
		if(this.qiexist[r][c]==0){
			//vx��vy������ͳ��
			
			int count = 0;//����洢��λ�����ı���
			int chess = 0;//����洢��һ�γ��ֵ����ӵı���
			String chessCode="0";//���������洢������������ı���
		
			for(int r1=r+vx,c1 = c+vy;r1!=(r+5*vx)||c1!=(c+5*vy);r1=r1+vx,c1=c1+vy){//vx��vy������ͳ��
			
			  if(r1<0||r1>20||c1<0||c1>20){break;}//��ֹ����
			  else{	
			    if(this.qiexist[r1][c1]==this.qiexist[r][c]){//�ж�c1λ���Ƿ��ǿ�λ
					if(c1 == (c+vy)&&r1==(r+vx)){//�ж��Ƿ�����
						break;
					}else if(count==0){//�ж��Ƿ��ǵ�һ�γ��ֿ�λ
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
						count++;
					}else if(count==1){//�ж��Ƿ��ǵڶ��γ��ֿ�λ
//						chessCode = this.qiexist[r][c1]+chessCode;//��¼�������������
						break;
					}
				}else {//�ж�c1λ���Ƿ�������
					
					
					if(chess==0){//�ж��Ƿ��ǵ�һ�γ�������
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
						chess = this.qiexist[r1][c1];//����һ�γ��ֵ����Ӵ��뵽chess�У�����֮����ж�
						
						if(this.qiexist[r1][c1]!=this.AIcolor){weightArray[r][c]++;}
						
					}else if(chess==this.qiexist[r1][c1]){//�ж�֮����ֵ������Ƿ�͵�һ���ֵ���������ͬ��ɫ��
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
						
					}else {//�жϲ�����ͬ��ɫ������
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
						break;
					}
			
			      }	
			    
					
			    }
			
			}
//			System.out.println("�����a"+chessCode);
			//����chessCode��HashMap�л�ȡ��Ӧ��Ȩֵ
			weight = map.get(chessCode);
			weight1=map.get(chessCode);
			if(weight!=null){//�ж�chessCode����������Ƿ����
				weightArray[r][c]+=weight;//��Ȩֵ�ۼ�����
			   
			    if((int)weight>=770){weightArray[r][c]=9000;}//��Ӯ���
			}
			
			
			count = 0;//����洢��λ�����ı���
			chess = 0;//����洢��һ�γ��ֵ����ӵı���
			chessCode="0";//���������洢������������ı���
		
			for(int r1=r-vx,c1 = c-vy;r1!=(r-5*vx)||c1!=(c-5*vy);r1=r1-vx,c1=c1-vy){//vx��vy �� ������ͳ��
			  
			if(r1<0||r1>20||c1<0||c1>20){break;}//��ֹ����
			else{	
				if(this.qiexist[r1][c1]==this.qiexist[r][c]){//�ж�c1λ���Ƿ��ǿ�λ
					if(c1 == (c-vy)&&r1==(r-vx)){//�ж��Ƿ�����
						break;
					}else if(count==0){//�ж��Ƿ��ǵ�һ�γ��ֿ�λ
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
						count++;
					}else if(count==1){//�ж��Ƿ��ǵڶ��γ��ֿ�λ
//						chessCode = this.qiexist[r][c1]+chessCode;//��¼�������������
						break;
					}
				}else {//�ж�c1λ���Ƿ�������
					if(chess==0){//�ж��Ƿ��ǵ�һ�γ�������
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
						chess = this.qiexist[r1][c1];//����һ�γ��ֵ����Ӵ��뵽chess�У�����֮����ж�
						
						if(this.qiexist[r1][c1]!=this.AIcolor){weightArray[r][c]++;}
						
					}else if(chess==this.qiexist[r1][c1]){//�ж�֮����ֵ������Ƿ�͵�һ���ֵ���������ͬ��ɫ��
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
					}else {//�жϲ�����ͬ��ɫ������
						chessCode = chessCode+this.qiexist[r1][c1];//��¼�������������
						break;
					}
					
			  }
				
			}
			
			}
//			System.out.println("�����b"+chessCode);
			weight = map.get(chessCode);
			Integer weight2=map.get(chessCode);
			
			if(weight!=null){//�ж�chessCode����������Ƿ����
				weightArray[r][c]+=weight;//��Ȩֵ�ۼ�����
			  if(weight1!=null&&weight2!=null)
			    if(((int)weight1+(int)weight2)>=770){weightArray[r][c]=9000;}//��Ӯ���
			
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
				 ca.drawLine(v.getWidth()/22+v.getWidth()*i/22, v.getHeight()/22, v.getWidth()/22+v.getWidth()*i/22, v.getHeight()*21/22, p);//����
				 ca.drawLine(v.getWidth()/22,v.getHeight()/22+v.getHeight()*i/22, v.getWidth()*21/22, v.getHeight()/22+v.getHeight()*i/22, p);//���� 

			 }
			 image.setImageBitmap(bi);
			 }
		else{
		 switch (event.getAction()) {     //�����Ļ��Ӧ
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

			int qix=(int)((this.x-v.getWidth()/44.00)/(v.getWidth()/22.00));//ȷ������λ��
			int qiy=(int)((this.y-v.getHeight()/44.00)/(v.getHeight()/22.00));// float/int ����ת��Ϊint�͵Ŀ���
			 
			 if(qix>20){qix=20;}  if(qix<0){qix=0;} 
			 if(qiy>20){qiy=20;}  if(qiy<0){qiy=0;}
			 qi.qix=qix;qi.qiy=qiy;
			
			 if(qiexist[qix][qiy]!=1&&qiexist[qix][qiy]!=-1){//��������
			 qiexist[qix][qiy]=qicolor;	
			 qilist.add(qi); 
			 ca.drawCircle((qix+1)*v.getWidth()/22, (qiy+1)*v.getHeight()/22, v.getWidth()/44, p);
//			System.out.println(qix+"  "+qiy);
	//		 System.out.println(qiexist[0][0]+"  "+qiexist[1][0]); 
			image.setImageBitmap(bi);
			this.AIaction=-1;       //���AIΪδ��
			}
			 else{this.qicolor=-this.qicolor;}
			 			 
		 	
		 	
			  if(win()&&this.stopflag==false)
			   {           //�����ʾ��
				  this.stopflag=true;
					AlertDialog.Builder builder= new AlertDialog.Builder(ma);//maΪ���
				    builder.setIcon(android.R.drawable.ic_dialog_info);
				    builder.setTitle("��ʾ");
				    if(this.qicolor==1)
				    {  
				    	builder.setMessage("����ʤ");
				     
				    }
				    else
				    {
			    	 builder.setMessage("����ʤ");
				      }
				    tsklis=new tishikuanglisten(ca, bi, image, qilist, qiexist,this);
				    builder.setPositiveButton("�鿴���", tsklis).setNegativeButton("����һ��", tsklis);
				    builder.create().show();//
			    }
			 if(this.AIflag){//AI���в���
                if(this.AIaction==-1){
                	
                	this.qicolor=-this.qicolor;
        			qi=new qiquality();
        			qi.color=this.qicolor;
        			this.AIcolor=qi.color;
        			
                	AImove();
                	AIgo();
                	
//                	//���������Ȩֵ
//                	for(int x=0;x<21;x++){
//                		for(int y=0;y<21;y++)
//                		{ 
//                			System.out.print(weightArray[x][y]+"��");
//                		}System.out.println();
//                	}
                	
        			if(this.qicolor==1)
        			 {this.p.setColor(Color.BLACK);}
        			else
        			 {this.p.setColor(Color.GRAY);}
        			qi.qix=AIx;qi.qiy=AIy;
        			weightArray = new int[21][21];
        			if(qiexist[qi.qix][qi.qiy]!=1&&qiexist[qi.qix][qi.qiy]!=-1){//��������
        				 qiexist[qi.qix][qi.qiy]=this.AIcolor;
        				 qilist.add(qi); 
        				 ca.drawCircle((qi.qix+1)*v.getWidth()/22, (qi.qiy+1)*v.getHeight()/22, v.getWidth()/44, p);
        				image.setImageBitmap(bi);
        			}
                }
		 	}
			 
			  if(win()&&this.stopflag==false)
			   {                                              //�����ʾ��
				   this.stopflag=true;
					AlertDialog.Builder builder= new AlertDialog.Builder(ma);//maΪ���
				    builder.setIcon(android.R.drawable.ic_dialog_info);
				    builder.setTitle("��ʾ");
				    if(this.qicolor==1)
				    {  
				    	builder.setMessage("����ʤ");
				     
				    }
				    else
				    {
			    	 builder.setMessage("����ʤ");
				      }
				    tsklis=new tishikuanglisten(ca, bi, image, qilist, qiexist,this);
				    builder.setPositiveButton("�鿴���", tsklis).setNegativeButton("����һ��", tsklis);
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


