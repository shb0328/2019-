
public class Role extends Thread{
	private int[] score = new int[3]; //0: 베프, 1: 절교, 2: 비지니스파트너
	private int career = -1; //-1: 무직, 0: 창업, 1: 취직
	private String client;
	private int player; //1 , 2
	
	public String getClient() {
		return client;
	}

	public Role(String client/*client정보*/,int player){
		super();		
		this.client = client;
		this.player = player;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}

	public int getEnding() {
		int res =0;
		for(int i = 0 ; i<score.length; ++i) {
			if(score[res]<score[i+1])
				res = i;
		}
		return res;
	}
	
	public void setCareer(int career) {
		this.career = career;
	}
	
	public int getCareer() {
		return career;
	}
	
	public void run() {
		
	}
}
