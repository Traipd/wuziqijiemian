package com.example.wuziqijiemian;



public class samelist<E>{ //2016·ºÐÍ
	private Object[] ob=new Object[0];
	
	public void add(E obi){
		 Object[] ob1=new Object[ob.length+1];
			ob1[ob.length]=obi;
			System.arraycopy(ob, 0, ob1, 0, ob.length);
			ob=ob1;
	}
	public int length(){return ob.length;}
	public E remove(){
		Object[] ob1=new Object[ob.length-1];
		E ob2=(E)ob[ob.length-1];
		System.arraycopy(ob, 0, ob1, 0, ob.length-1);
		ob=ob1;
		return ob2;
	}
	public E get(int i){return (E)ob[i];}
    public void clear(){ob=new Object[0];}
}
