package coop;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Nodelist {
	private static  Nodelist obj;
	private double [] arg;
	public static Nodelist init(){
		if (obj == null){
		obj= new Nodelist();
		}
		return obj;
		
	}
	public static Nodelist init (float [][] data){
		if (obj == null){
			obj= new Nodelist();
			}
			obj.initdata(data);
			return obj;
	}
	private  ArrayList<Node> list= new ArrayList<>();
	private String filename ="data.txt";
	//static StringBuffer ss = new StringBuffer();
	//static StringBuffer src = new StringBuffer();
	{
		arg= new double [18];
		//�����Ȳ���
		arg[0]=0.01;//a
		arg[1]=8;//b
		arg[2]=0;//m
		//cpu
		arg[3]=20;//a
		arg[4]=5;//b
		arg[5]=0;//m
		//memory
		arg[6]=1;//m
		arg[7]=40;//b1
		arg[8]=10;//b2
		arg[9]=1/41;//b3
		arg[10]=20;//a
		arg[11]=5;//b
		//energy
		arg[12]=1;//m
		arg[13]=40;//b1
		arg[14]=5;//b2
		arg[15]=1/41;//b3
		arg[16]=20;//a
		arg[17]=5;//b
	}
	private Nodelist(){
		//��ʽ������ʼ��
		
		//RandWfile.readfile(filename,list);
		
	}
	private  void initdata(float [][] data){
		list.clear();
		System.out.println("��ʼ���ڵ�");
		for (int i =0;i<data.length;i++){
			Node node = new Node(data[i]);
			System.out.println("node"+(i+1)+":"+Arrays.toString(data[i]));
			list.add(node);
		}
		System.out.println("��ʼ���ڵ㡣����");
		
		
	}
	//�����ȼ���
	public float utility_coop(Node node){
		double utility =0;
	
		double m =0;
		double a1 =0.01;
		double a2=8;
		
		if (Math.abs(node.getCoop()-m)<1e-7)
			return 0;
		else {
			utility = 1./(1+a1*Math.pow(node.getCoop(), -a2));
		}
		node.coop_utility=(float)utility;
		return (float)utility;
		}
	
	//cpu��Ч�ü���
	public float utility_cpu(Node node){
		double utility = 0;
		arg[5]=node.getCpu()*0.4;
		if (0<node.getCoop() && node.getCoop()<arg[5]){
			utility = Math.sqrt(node.getCpu());
		}
		else if (arg[5]<node.getCoop()&& node.getCoop()<1) {
			utility = Math.sqrt(node.getCpu());
			utility = utility/(1+arg[3]*Math.pow(node.getCoop()-arg[5], arg[4]));
			
		}
		else 
			System.out.println("utility_cpu error!!! "+node.getCoop()+" "+arg[5]);
		node.cpu_utility=(float)utility;
		return (float)utility;
		}
	public float utility_memory(Node node){ 
		arg[6]=node.getMemory()*0.4;
		double utility = (1./(1+arg[7]*Math.pow(1-node.getMemory(),arg[8])))-arg[9];
		if (0<node.getCoop() && node.getCoop()<arg[6]){
			
		}
			
		else if (arg[6]<node.getCoop()&& node.getCoop()<1) {
			utility = utility/(1+arg[10]*Math.pow(node.getCoop()-arg[6], arg[11]));
			
		} 
		else 
			System.out.println("utility_memory error!!! "+node.getCoop()+" "+arg[6]);
		node.memory_utility=(float)utility;
		return (float)utility;
		}
	public float utility_energy(Node node) {
		arg[12]=1;//m
		arg[13]=40;//b1
		arg[14]=5;//b2
		arg[15]=1/41;//b3
		arg[16]=20;//a
		arg[17]=5;//b
		arg[12]=node.getEnergy()*0.4;
		double utility = (1./(1+arg[13]*Math.pow(1-node.getEnergy(),arg[14])))-arg[15];
		if (0<node.getCoop() && node.getCoop()<arg[12]){
			
		}
			
		else if (arg[12]<node.getCoop()&& node.getCoop()<1) {
			utility = utility/(1+arg[16]*Math.pow(node.getCoop()-arg[12], arg[17]));
			
		}
		else 
			System.out.println("utility_energy error!!! "+node.getCoop()+" "+arg[12]);
		
		node.energy_utility=(float)utility;
		return (float)utility;
		}
	
	
	//�����ڵ���Ч�ü���
	public float utility(Node node){
		double utility = 0;
		double cpu = node.getCpu();
		double memory = node.getMemory();
		double energy = node .getEnergy();
		double r = Math.sqrt(cpu*cpu+memory*memory+energy*energy);
		//utility = 1./4*(utility_cpu(node)+utility_memory(node)+utility_energy(node)+utility_coop(node));
		utility = 1./3*utility_cpu(node)+1./3*utility_memory(node)+2./3*utility_energy(node);
		utility = Math.pow(utility, node.getFriend());
		//utility -= node.getCoop()/(2.71828*(r+1));
		//utility/=4;
		node.setUtility((float)utility);
		return  (float)utility;
	}
	// modify 8/29 �µļ��㷽ʽ
	public double nutility_cpu(Node node){
		double coop = node.getCoop();
		double cpu = node.getCpu();
		double utility=0;
		//utility = normpdf(coop*10,(8*cpu+1),(cpu*0.1));
		utility = normpdf(coop*10,(8*cpu),(4+cpu))+0.7*cpu;
		return utility;
	}
	
	public double nutility_memory(Node node){
		double coop = node.getCoop();
		double memory = node.getMemory();
		double utility=0;
		
		//utility = normpdf(coop*10,(8*memory),(0.2*memory))+0.1*memory;
		utility = normpdf(coop*10,(8*memory),(5+memory))+0.7*memory;

		return utility;
	
	}
	public double nutility_energy(Node node){
		double coop = node.getCoop();
		double energy = node.getEnergy();
		double utility=0;
		//utility = normpdf(coop*10,(8*energy),(0.2*+energy))+0.2*energy;
		utility = normpdf(coop*10,(8*energy),(3+energy))+0.7*energy;
		return utility;
	}
	public double nutility(Node node){
		double utility =0;
		double cpu = nutility_cpu(node);
		double memory = nutility_memory(node);
		double energy =nutility_energy(node);
		double sum=cpu+memory+energy;
//		node.cpu_utility=(float)(cpu/sum);
//		node.energy_utility=(float)(energy/sum);
//		node.memory_utility=(float)(memory/sum);
		//modify 12/10
		node.cpu_utility=(float)(cpu);
		node.energy_utility=(float)(energy);
		node.memory_utility=(float)(memory);
		utility=1./4*node.cpu_utility+node.energy_utility*1./2+node.memory_utility*1./4;
		if (utility>1)
			System.out.println("utility"+utility+"  cpu_utility"+node.cpu_utility+"  energy_utility"+node.energy_utility+" memory_utility"+node.memory_utility);
		//utility = 1./3*nutility_cpu(node)+1./3*nutility_memory(node)+2./3*nutility_energy(node);
		utility = Math.pow(utility, node.getFriend());

		node.setUtility((float)utility);
		return utility;
	}
    public static double normpdf(double x,double u,double s){
	    	double cons = 1./Math.sqrt(2*Math.PI*s);
	    	double index = (x-u)*(x-u)/(2*s*s);
	    	return cons*Math.pow(Math.E, -index); 
	    } 
	
	
	
	
	
	public  float uaverage(float [] coop){
		
		double fitness =0.;
		double s=0.;
		double avg=0.;
		int node_sum=0;//�м�ڵ���Ŀ
		//�ڵ��ֵ
		int j=0;
		
		for (Node i:list){
			i.setCoop(coop[j++]);
			if (!i.issrc()){
			fitness += nutility(i);
			++node_sum;
				
		}
		}
		double utility_src =ss();
		for (Node i:list){
			if (i.issrc())
				i.setUtility((float)utility_src);
		}
		
		if (list.size()==0){
			System.out.println("�ڵ���ĿΪ0");
			return 0;
		}
		//fitness/=node_sum;
		//�ڵ㷽��
		
		//s/=node_sum;
		
		
		//fitness=(fitness*node_sum+utility_src(utility_src))/list.size();
		//目标函数为源节点效用和中间节点效用的平均值
		//两个端节点
		//utility_src*=(list.size()-node_sum);
		fitness = (fitness + 2*utility_src)/list.size();

		//System.out.println("waring......"+fitness + " " +utility_src);
		return (float)fitness;
	}
	private double ss (){
	
		int node_sum = 0;
		double avg =0.;
		double s=0.;
		for (Node i:list){
			if (!i.issrc()){
				//ss.append(i.getCoop()+" ");
				avg+=i.getCoop();
				node_sum ++;
			}
		}
		avg/=node_sum;
		//ss.append(avg+" ");
		for (Node i:list){
			if (!i.issrc()){
				s+=Math.pow((i.getCoop()-avg), 2);
			}
		}
		s/=node_sum;
		//ss.append(s+" ");
		if (Math.abs(s-0)<1e-7)
			System.out.println("ss ����Ϊ0 "+s+" "+avg);
		double tt = avg/(1+s);
		//ss.append(tt+"\r\n");
		//ss=null;
		
		return tt;
	}
	public float utility_src(double x){
		double utility=0.;
		double m =0;
		double a1 =0.1;
		double a2=5;
		if (Math.abs(x-1)<1e-7){
			System.out.println("utility_src ����Ϊ0 "+x);
			return (float)0.5;
		}
		if (Math.abs(x-m)<1e-7)
			return 0;
		else {
			utility = 1./(1+a1*Math.pow(x, -a2));
		}
		//src.append(utility);
		//src.append("\r\n");
		return (float)utility*0.2f;
				
	}
	
	
	public Node getNode(int i){
		return list.get(i);
	}
	
	public static void main(String [] args){
		//Pso pso= new Pso(200,20,4);
	float [] array={0,0,0,0,0,0};
		Node test = new Node(array);
	Nodelist ns = new Nodelist();
	//������
	StringBuffer str = new StringBuffer();
	double utility=0;
		float coop = (float)0;
		float diff =0;
		for (int i=1;i<100;i++){
			test.setCoop(coop);
			utility=ns.utility_coop(test);
			str.append(coop);
			str.append(' ');
			str.append(utility+" "+(utility-diff));
			str.append("\r\n");
			coop+=0.01;
			diff =(float)utility;
		}
		RandWfile.writefile("datatest\\coop.txt", str.toString(),false);	
		//�ڴ�
		str.delete(0, str.length());
		coop =(float)-0.1;
		float data = (float)0;
		diff =0;
//		double utility_cpu=0;
//		double utility_memory=0;
//		double utility_energy=0;
//		StringBuffer sbcpu = new StringBuffer();
//		StringBuffer sbenergy = new StringBuffer();
//		StringBuffer sbmemory = new StringBuffer();
//		for (int i=0;i<=100;i++){
//			test.setMemory(data);
//			test.setCpu(data);
//			test.setEnergy(data);
//			for (int j=1;j<100;j++){
//				test.setCoop(coop);
//				utility_cpu = ns.nutility_cpu(test);
//				utility_energy = ns.utility_energy(test);
//				utility_memory = ns.utility_memory(test);
//				sbcpu.append(data+" "+coop+" "+utility_cpu+"\r\n");
//				sbenergy.append(data+" "+coop+" "+utility_energy+"\r\n");
//				sbmemory.append(data+" "+coop+" "+utility_memory+"\r\n");
//				coop+=0.01;
//			}
//			data+=0.01;
//			coop=0;
//		}
//		RandWfile.writefile("datatest\\memorySanwei.txt", sbmemory.toString(),false);
//		RandWfile.writefile("datatest\\cpuSanwei.txt", sbcpu.toString(),false);
//		RandWfile.writefile("datatest\\energySanwei.txt", sbenergy.toString(),false);

		for (int i=1;i<=10;i++){
			test.setMemory(data);
			for (int j=1;j<120;j++){
				test.setCoop(coop); 
		    utility=ns.nutility_memory(test);
		    str.append(data+" "+coop+" ");
		    str.append(utility+" "+(utility-diff)+"\r\n");
		    coop+=0.01;
		    diff =(float)utility;
			}
		    
		    data+=0.1;
			coop=(float)-0.1;
			RandWfile.writefile("datatest\\memory"+(i)+".txt", str.toString(),false);
			str.delete(0, str.length());
		}
		
		//cpu
		
		coop =(float)-0.1;
		data = (float)0.1;
		diff =0;
		for (int i=1;i<=10;i++){
			 test.setCpu(data);
			for (int j=1;j<120;j++){
				test.setCoop(coop);
		    utility =ns.nutility_cpu(test);
		    str.append(data+" "+coop+" ");
		    str.append(utility+" "+(utility-diff)+"\r\n");
		    diff =(float)utility;
		    coop+=0.01;
			}
			  data+=0.1;
				coop=(float)-0.1;
				RandWfile.writefile("datatest\\cpu"+(i)+".txt", str.toString(),false);
				str.delete(0, str.length());
		}
		
		
		
		coop =(float)-0.1;
		data = (float)0.1;
		diff =0;
		for (int i=1;i<=10;i++){
			 test.setEnergy(data);
			for (int j=1;j<120;j++){
		   
		    test.setCoop(coop);
		    utility = ns.nutility_energy(test);
		    str.append(data+" "+coop+" ");
		    str.append(utility+" "+(utility-diff)+"\r\n");
		    diff =(float)utility;
		    coop+=0.01;
			}
			  data+=0.1;
				coop=(float)-0.1;
				RandWfile.writefile("datatest\\energy"+(i)+".txt", str.toString(),false);
				str.delete(0, str.length());
		}
		
		data =(float)0.1;
		coop =(float)-0.1;
		
		diff =0;
		for (int j=0;j<10;j++)
		{
		 test.setCpu(data);
		 test.setEnergy(data);
		 test.setMemory(data);
		for (int i=1;i<120;i++){
			 test.setCoop(coop);
			 utility = ns.nutility(test);
			 str.append(data+" "+coop+" ");
			 str.append(utility+" "+(utility-diff)+"\r\n");
			 diff = (float)utility;
			 coop+=0.01;
		}
		data+=0.1;
		coop =(float)-0.1;
		RandWfile.writefile("datatest\\utility"+(j+1)+".txt", str.toString(),false);
		str.delete(0, str.length());
		}

		float [] array2={0,0,0,0,0,0};
		for (int i=1;i<=4;i++) {
			//产生一百次数据，取平均值
			for (int j = 1; j <= 100; j++){
				float [][] tmp=TestData.genedata(3,i);
				array2[1] +=tmp[2][1];//CPU
				array2[2] +=tmp[2][2];//energy
				array2[3] +=tmp[2][3];//memory
			}
			array2[1]/=100;
			array2[2]/=100;
			array2[3]/=100;
			test= new Node(array2);
			System.out.println("status"+i+"cpu "+array2[1]+" energy "+array2[2]+ " memory "+array2[3]);

			coop=(float)0.01;
			//取合作度
			for (int t=0;t<=100;t++){
				test.setCoop(coop);
				utility = ns.nutility(test);
				if (utility>1)
					System.out.println("效用异常"+utility+" coop" +coop);
				str.append("status"+i+" "+coop+" "+utility+"\r\n");
				coop+=0.01;
			}
			RandWfile.writefile("datatest\\utilitystate"+i+".txt", str.toString(),false);
			array2[1]=0;
			array2[2]=0;
			array2[3]=0;
			str.delete(0, str.length());
		}
		
	double data1 =0;
	for (int i=1;i<100;++i){
			 str.append(data1+" ");
		    str.append(ns.utility_src(data1)+"\r\n");

			data1+=0.01;
		}
		RandWfile.writefile("datatest\\srcutility.txt", str.toString(),false);
//	
//	RandWfile.writefile("ss.txt", ss.toString());
//	RandWfile.writefile("src.txt", src.toString());

//		for (int k=4;k<=10;k++)
//				for (int j=0;j<100;++j)
//		TestData.start(k);
		for (int n=1;n<=4;n++)
		for (int k=4;k<=10;k++)
			for (int j=0;j<100;++j)
				TestData.start(k,n);
		
		/*for (int i=1;i<=4;i++)
			for (int j=0;j<500;++j)
			TestData.startNopso(7,i);*/
		
	}

}
