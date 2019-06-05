
public class Role {

	private int career = -1; //-1: 무직, 0: 창업, 1: 취직
	private int client;
	private int player; //1 , 2
	

	public Role(int clientID,int player){
		super();		
		this.client = clientID;
		this.player = player;

	}
	
		public int getClient() {
		return client;
	}

	public int getPlayer() {
		return player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}


	
	public void setCareer(int career) {
		this.career = career;
	}
	
	public int getCareer() {
		return career;
	}
	
}
