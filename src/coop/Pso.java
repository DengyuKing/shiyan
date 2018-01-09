package coop;

import java.util.Arrays;

public class Pso {
	//ѭ������
	int loop_num;
	//������Ŀ
	int pso_num;
	//ά��
	int part_num;
	float [][] pso;//������Ŀ �� �ж��ά��
	float [][] speed;//���ӵ��ٶ�
	float [] fit;//���ӵ�ǰֵ
	float [] pbest;//���ӵ���ʷ����ֵ
	
	float [][] xpbest;//���ӵľֲ�����λ��
	float    gbest=0;
	float [] xgbest;//��¼�ҵ�����õ�Ԫ��λ��
	float [][] utility;
	
	//Ȱֵ����
	final static double  w=0.8;
	final static double  c1=2;
	final static double  c2=2;
	final static double WMax=1;
	final static double WMin=0;
	final static double SMax=0.01;
	final static double SMin =-0.01;
	final  float [][] data;
	Nodelist obj = Nodelist.init();
	StringBuffer str = new StringBuffer();
	StringBuffer st = new StringBuffer();
	public Pso (int loop_num, int pso_num,int part_num,float[][] data){
		
		this.loop_num=loop_num;
		this.pso_num=pso_num;
		this.part_num = part_num;
		this.data=data; 
		pso_init();
		pso_process();
	}
	
	private void pso_init(){
		pso = new float [pso_num][part_num];
		speed = new float [pso_num][part_num];
		fit = new float [pso_num];
		pbest = new float[pso_num];
		
	    xpbest=new float[pso_num][part_num];
		
		xgbest= new float[part_num];
		utility= new float[part_num][5];
		for (int i=0;i<pso_num;i++)
			for (int j=0;j<part_num;j++){
				pso[i][j]=(float)Math.random();
				speed[i][j]= (float)(Math.random()/10);
			}
		
		System.out.println("pso init successful!!!");
	}
	private void pso_process(){
		//������Ӧֵ������¼��λ��
		
		for (int num =1;num<=loop_num;++num){
			str.append("��"+num+"�ε���\r\n");
		for (int i=0;i<pso_num;i++){
			fit [i] = fitness(pso[i]);
			if (fit[i]>pbest[i]){
				pbest[i] = fit[i];
				for (int j = 0; j<part_num;++j){
					
					xpbest[i][j] = pso[i][j];
				}
			}
			if ((pbest[i]-gbest)>1e-7){
				gbest = pbest[i];
				for (int j=0;j<part_num;++j){
					xgbest[j]=xpbest[i][j];
					 Node node =obj.getNode(j);
					 utility[j][0]=node.coop_utility;
					 utility[j][1]=node.cpu_utility;
					 utility[j][2]=node.energy_utility;
					 utility[j][3]=node.memory_utility;
					 utility[j][4]=node.getUtility(); 
				}			 
			}
		}
		str.append("gbest "+gbest+"\r\n");
		str.append("pbest"+"\r\n");
		 for (int i=0;i<pbest.length;i++)
			 //System.out.print(pbest[i]+" ");
		 str.append(pbest[i]+" ");
		 str.append("\r\n");
		// System.out.println();
		 
		// System.out.println("xgbest");
		 str.append("xgbest"+"\r\n");
		 for (int i=0;i<xgbest.length;i++)
			 //System.out.print(xgbest[i]+" "); 
		 str.append(xgbest[i]+" ");
		
		 
		 str.append("\r\n");
		 
		 for (int i=0;i<xgbest.length;i++){
			 str.append( utility[i][0]+" "+ utility[i][1]+" "+ utility[i][2]+" "+ utility[i][3]+" "+ utility[i][4]);
			 str.append("\r\n");
		 }
		
		for (int i=0;i<pso_num;++i){
			for (int j=0;j<part_num;++j){
				speed[i][j]=(float)(w*speed[i][j]+c1*Math.random()*(xpbest[i][j]-pso[i][j])+c2*Math.random()*(xgbest[j]-pso[i][j]));
			
				if ((speed[i][j]<SMin))
					speed[i][j] = (float)SMin;
				if (speed[i][j]>SMax)
					speed[i][j]=(float)SMax;
				  pso[i][j]=(float)(pso[i][j]+speed[i][j]);
				if (pso[i][j]<WMin)
					pso[i][j] = (float)WMin;
				if (pso[i][j]>WMax||(Math.abs(pso[i][j]-1.)<1e-7))
					pso[i][j] =(float)(WMax-(1e-4));
				
				if (Math.abs(pso[i][j]-1)<1e-7)
					System.out.println(pso[i][j]+"pso");
					
			}
		}
		}
		//str.append("---------------------------------------------------------------\r\n");
		st.append("gbest "+gbest+" ");
		float average =0;
		float utility_cpu=0;
		float utility_memory=0;
		float utility_energy =0;
		for (int i=2;i<xgbest.length;i++){
			average+=xgbest[i];
			//st.append("node:"+(i+1)+" cpu : "+data[i][1]+" energy : "+data[i][2]+" memory : "+data[i][3]+" coop : "+xgbest[i]+"\r\n");
			utility_cpu+=data[i][1];
			utility_energy+=data[i][2];
			utility_memory+=data[i][3];
		}
		st.append("average_coop "+ average/(xgbest.length -2)+" cpu "+utility_cpu/(xgbest.length -2)+" energy "+utility_energy/(xgbest.length -2)+" memory "+utility_memory/(xgbest.length -2)+"\r\n");
		//st.append("---------------------------------------------------------------\r\n");
		
		
	}
	
	private float  fitness(float [] i){
		double fitness=0.;
		fitness=obj.uaverage(i);
		return (float)fitness;
	}
	

}
  