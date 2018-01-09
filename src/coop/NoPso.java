package coop;

import java.util.Arrays;

public class NoPso {
	StringBuilder st = new StringBuilder();
	Nodelist obj = Nodelist.init();
	int nodenum;
	int co;
	NoPso(int nodenum,int co){
		this.nodenum = nodenum;
		coop = new float [nodenum];
		this.co=co;
		 NoPso_init();
		 NoPso_process();
		 
	}
	float [] coop;
       private void NoPso_init(){
    	   System.out.println("������ýڵ������");
    	   for (int i=0;i<nodenum;i++) {
			   float x =TestData.randnum(co,100);
			   coop[i] =x;
		   }
			System.out.println(Arrays.toString(coop));
       }
       
       private void NoPso_process(){
    	double fitness = fitness(coop);
    	double avg=0;
    	for (int i=2;i<nodenum;i++)
    		avg+=coop[i];
       
          avg/=(nodenum-2);
          st.append("fitness "+fitness+" avgcoop "+avg+"\r\n");
          
       }
       private double fitness (float [] i){
    	double fitness=0.;
   		fitness=obj.uaverage(i);
   		return fitness;
       }
}
