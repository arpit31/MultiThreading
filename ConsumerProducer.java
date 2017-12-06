package com.apple;

class Inventory{
	int goods=0;
	boolean available=false;
	synchronized void get() {
		
		while(available==false)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		available=false;
		notify();
		
	}
	
	
	synchronized void put(int number) {
		while (available==true)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		available=true;
		goods= number;
		notify();
	}
	
}
class Producer extends Thread{
	Inventory t;
	Producer(Inventory t){
		this.t=t;
	}
	public void run() {
		for(int i=1;i<10;i++) {
		t.put(i);
		System.out.println("put "+t.goods);
		}
		
	}
}

class Consumer extends Thread{
	Inventory t;
	Consumer(Inventory t){
		this.t=t;
	}
	public void run() {
		for(int i=1;i<10;i++) {
		t.get();
		System.out.println("get "+ t.goods);
		}
		
	}
}
public class ConsumerProducer {
public static void main(String[] args) {
	Inventory t=new Inventory();
	Producer p= new Producer(t);
	Consumer c= new Consumer(t);
	p.start();
	c.start();
}
}
/*Without synchronizing the get() and put(), java.lang.IllegalMonitorStateException is thrown as  
 to indicate that a thread has attempted to wait on an object's monitor or to notify other threads waiting on an object's monitor without owning the specified monitor.*/
/* 
Output- 
put 1
get 1
put 2
get 2
put 3
get 3
put 4
get 4
put 5
get 5
put 6
get 6
put 7
get 7
put 8
get 8
put 9
get 9
*/

/*If exception comes in one thread then also second thread works as desirable

synchronized void get() {
	
	while(available==false)
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	available=false;
	notify();
	if(goods==5)
		throw new ArithmeticException();
	//return goods;
	
}


put 1
put 2
get 1
get 2
put 3
get 3
put 4
get 4
put 5
Exception in thread "Thread-1" java.lang.ArithmeticException
	at com.apple.Inventory.get(ConsumerProducer.java:17)
	at com.apple.Consumer.run(ConsumerProducer.java:57)
put 6*/