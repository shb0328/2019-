
public class Resource {
	private int[][] score; //0: 1p, 1: 2p // 0: 베프, 1: 절교, 2: 비지니스파트너

	public Resource() {
		super();
		this.score = new int[2][3];
		for (int i = 0; i < this.score.length; ++i) {
			for(int j = 0; j<this.score[i].length; ++j)
				this.score[i][j] = 0;
		}
	}

	private void setScore(int player, int endingnum, int value) {
		this.score[player][endingnum] += value;
	}
	
	public void choose(Role role, int num) {
		
		if(role.getPlayer() == 1) { //1p
			
		}else { //2p
			
		}
		
		
	}

}
