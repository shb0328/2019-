
public class Resource {
	private int[] score = new int[3]; //0: 베프, 1: 절교, 2: 비지니스파트너
	
	public Resource() {
		super();
		for(int i =0;i<score.length;++i)
			score[i] = 0;
		
	}

	private int getEnding() {
		int res =0;
		for(int i = 0 ; i<score.length; ++i) {
			if(score[res]<score[i+1])
				res = i;
		}
		return res;
	}
	
	public synchronized void choose(Role role, int num) {
		
		if(role.getPlayer() == 1) { //1p
			
		}else { //2p
			
		}
		
	}

}
