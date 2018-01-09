package coop;

public class Node {
	private float N_coop;
	private float N_cpu;
	private float N_energy;
	private float N_memory;
	
	private float N_friend;

	private boolean src;
	
	public float cpu_utility;
	public float memory_utility;
	public float energy_utility;
	public float coop_utility;
	
	private float N_utility;
	
	public Node(float[] arg ){
		this.N_coop=arg[0];
		this.N_cpu=arg[1];
		this.N_energy=arg[2];
		this.N_memory=arg[3];
		if (Math.abs(arg[4]-0)<1e-7)
			this.N_friend=1;
		else if (Math.abs(arg[4]-1)<1e-7)
			this.N_friend=(float)0.8;
		else
			this.N_friend=(float)0.6;
		
		if  (Math.abs(arg[5]-0) <1e-7)
			this.src = true;
		else
			this.src =false;
		
	}
	
	public float getCpu(){		
		return N_cpu;
	}
	public void  setCpu(float cpu){
		this.N_cpu=cpu;
		
	}
	public float getEnergy(){
		return N_energy;
	}
	public void setEnergy(float energy){
		this.N_energy=energy;
	}
	public float getMemory(){ return N_memory;}
	public void setMemory(float memory){ this.N_memory=memory;}
	
	public float getCoop(){ return N_coop;}
	public void setCoop(float coop){ this.N_coop=coop;}
	
	public float getFriend(){ return N_friend;}
	public void setFriend(byte friend){ this.N_friend=friend;}
	
	public float getUtility(){ return N_utility;}
	public void setUtility(float utility){ this.N_utility = utility;}
	public boolean issrc(){
		return src;
	}
	
	

}
