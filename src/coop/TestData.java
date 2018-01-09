package coop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class TestData {
	private static float [][] data;
	private int node_num;
	private static int [][] p= new int  [9][2];
	static {
	p[0][0]=0;
	p[0][1]=20;
	p[1][0]=20;
	p[1][1]=80;
	p[2][0]=80;
	p[2][1]=100;
	p[3][0]=80;
	p[3][1]=100;
	p[4][0]=20;
	p[4][1]=80;
	p[5][0]=0;
	p[5][1]=20;
	p[6][0]=50;
	p[6][1]=100;
	p[7][0]=20;
	p[7][1]=50;
	p[8][0]=0;
	p[8][1]=20;
	}
	
	public static void start(int n){
		genedata(n,5);
		Nodelist.init(data);
		for (int i=0;i<10;i++){
		Pso pso= new Pso(1000,20,n,data);
		data = null;
		}
		
	}

	/**
	 *
	 * @param n 节点个数
	 * @param state 节点状态
	 */
	public static void start(int n,int state){
		genedata(n,state);
		Nodelist.init(data);
		Pso pso= new Pso(1000,20,n,data);
		//RandWfile.writefile("data\\result"+state+".txt", pso.str.toString(),true);
		RandWfile.writefile("data\\"+n+"test"+state+".txt", pso.st.toString(),true);
		//Nopso

		for (int i=50;i<=100;i+=10) {
			NoPso no = new NoPso(n, i);
			RandWfile.writefile("datanopso\\" + n + "nopsotest" +i+"_"+state + ".txt", no.st.toString(), true);
			no.st.delete(0, no.st.length());
		}



		pso.str.delete(0,pso.str.length());
		pso.st.delete(0,pso.st.length());
		data = null;
		pso=null;
		
	}


	public static void mixstart(int n){

		genedata(n);
		Nodelist.init(data);

		Pso pso= new Pso(1000,20,n,data);
		//RandWfile.writefile("data\\result"+state+".txt", pso.str.toString(),true);
		RandWfile.writefile("data\\"+n+"mixtest.txt", pso.st.toString(),true);
		//Nopso

		for (int i=50;i<=90;i+=10) {
			NoPso no = new NoPso(n, i);
			RandWfile.writefile("datanopso\\" + n + "mixnopsotest" +i+".txt", no.st.toString(), true);
			no.st.delete(0, no.st.length());
		}



		pso.str.delete(0,pso.str.length());
		pso.st.delete(0,pso.st.length());
		data = null;
		pso=null;

	}

	public static void startNopso(int n,int state,int co){
		genedata(n,state);
		Nodelist.init(data);
		NoPso no = new NoPso(n,co);
		RandWfile.writefile("data\\"+n+"nopsotest"+state+".txt", no.st.toString(),true);
		no.st.delete(0,no.st.length());
		data = null;
		no = null;
		
		
	}
	
//	public static void testdata(int times){
//		for (int i=0;i<times;++i){
//			cpurate();
//			memoryrate();
//			energyrate();
//		}
//		
//	}
//	private static float cpurate(){
//		float cpurate=0;
//		return cpurate;
//	}
//	private static float memoryrate(){
//		float memoryrate=0;
//		return memoryrate;
//	}
//	private static float energyrate(){
//		float energyrate=0;
//		return energyrate;
//			}
	public static float [][]genedata(int n,int state){
		data = new float[n][6];
		data[0][5] = 0;
		data[1][5] = 0;
		Random r= new Random();
		for (int i=2;i<n;i++){
			
			data[i][4]=r.nextInt(3);
			data[i][5]=1;
		}
		for (int i=2;i<n;++i){
			String str = state(state);
			System.out.println(str);
			int energy =str.charAt(0)-'0';
			int cpu =str.charAt(1)-'0';
			int memory=str.charAt(2)-'0';
			data [i][2]=randnum(p[energy-1][0],p[energy-1][1]);
			data [i][1]=randnum(p[cpu-1][0],p[cpu-1][1]);
			data [i][3]=randnum(p[memory-1][0],p[memory-1][1]);
		}
		return data;
	}



	public static float [][]genedata(int n){
		data = new float[n][6];
		data[0][5] = 0;
		data[1][5] = 0;
		Random r= new Random();
		for (int i=2;i<n;i++){

			data[i][4]=r.nextInt(3);
			data[i][5]=1;
		}
		for (int i=2;i<n;++i){
			Random rand = new Random();

			String str = state(rand.nextInt(4)+1);
			System.out.println(str);
			int energy =str.charAt(0)-'0';
			int cpu =str.charAt(1)-'0';
			int memory=str.charAt(2)-'0';
			data [i][2]=randnum(p[energy-1][0],p[energy-1][1]);
			data [i][1]=randnum(p[cpu-1][0],p[cpu-1][1]);
			data [i][3]=randnum(p[memory-1][0],p[memory-1][1]);
		}
		return data;
	}
	
	public static String  state(int n){
		
		
		String [] str1={"147","148","149","157","158","159","167","168","169"};
		String [] str2={"267","268","269","249","259"};
		String [] str3={"367","368","369","349","359","247","257","248","258"};
		String [] str4={"347","348","357","358"};
	
		Random random = new Random();
		
		switch(n){
		case 1:
			return str1[random.nextInt(9)];
			
		
		case 2:
			return str2[random.nextInt(5)];
		case 3:
			return str3[random.nextInt(9)];
		case 4:
			return str4[random.nextInt(4)];
		default:
			System.out.println("state error!");
			return "345";
		}
		
		
		
	}
	public static String state27(int n){
		if (n<0 || n>26){
			System.out.println("invalid argument "+n);
			System.exit(0);
		}
		String [] str5 ={"147","148","149","157","158","159","167","168","169","267","268","269","249","259","367","368","369","349","359","247","257","248","258","347","348","357","358"};
		return str5[n];
		
	}
	public static float randnum(int min ,int max){
		float num =0;
		
		Random random = new Random();
		  num= random.nextInt(max)%(max-min+1) + min;
		 num/=100;
		return num;
	}
}
