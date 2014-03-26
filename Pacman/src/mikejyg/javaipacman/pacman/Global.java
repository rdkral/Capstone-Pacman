package mikejyg.javaipacman.pacman;

public class Global {
	/*Table for Affective State
	 * 1 = engagement
	 * 2 = boredom
	 * 3 = frustration
	 * 4 = meditation
	 * 5 = agreement
	 * 6 = concentrating
	 * 7 = disagreement
	 * 8 = interested
	 */
	public static boolean changedState = true;
	public static int affectiveState = 0;
	public static int p = 0;
	public static int a = 0;
	public static int d = 0;
	
	public static String affectiveStateToString() {
		String state = new String();
		switch (affectiveState) {
	        case 1:  state = "Engagement";
	                 break;
	        case 2:  state = "Boredom";
	                 break;
	        case 3:  state = "Frustration";
	                 break;
	        case 4:  state = "Mediation";
	                 break;
	        case 5:  state = "Agreement";
	                 break;
	        case 6:  state = "Concentration";
	                 break;
	        case 7:  state = "Disagreement";
	                 break;
	        case 8:  state = "Interested";
	                 break;
	        default: state = "Unknown";
	                 break;
		}
		return state;
	}
}
