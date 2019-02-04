package maze.rook.jump.solutions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

public class BFS {

	public void callBFS(String location) {
		try {
			
			int n=0;
			int i=0,j=0;
			FileReader fr = new FileReader(location);
			BufferedReader br = new BufferedReader(fr);
			String s;
			ArrayList<String> arrList = new ArrayList<String>();
			while ((s = br.readLine()) != null) {
				arrList.add(s);
				n++;
			}
			String matrix[][] = new String[n][n];
			for (String str: arrList) {
				String charArr[] = str.split(",");
				for (String str2: charArr) {
					matrix[i][j] = str2.trim();
					j++;
				}
				i++;j=0;
			}
			Queue<String> queue = new LinkedList();
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("0,0", "0,0");
			i=0;j=0;
			queue.add(i+","+j);
			ArrayList<String> path = new ArrayList<String>();
			while (!queue.isEmpty()) {
				if (matrix[i][j].equalsIgnoreCase("G")) {
					path.add(i+","+j);
					while (!hm.get(i+","+j).equalsIgnoreCase("0,0")) {
						String parent = hm.get(i+","+j);
						path.add(parent);
						i =Integer.parseInt(parent.split(",")[0].trim());
						j =Integer.parseInt(parent.split(",")[1].trim());
					}
					path.add("0,0");
					break;
				}
				int jump = Integer.parseInt(matrix[i][j]);
				int up = i-jump;
				int down = i+jump;
				int left = j-jump;
				int right = j+jump;
				if (up>=0 && !hm.containsKey(up+","+j)) {
					queue.add(up+","+j);
					hm.put(up+","+j, i+","+j);
				}
				if (down <n && !hm.containsKey(down+","+j)) {
					queue.add(down+","+j);
					hm.put(down+","+j, i+","+j);
				}
				if (left >=0 && !hm.containsKey(i+","+left)) {
					queue.add(i+","+left);
					hm.put(i+","+left, i+","+j);
				}
				if (right <n && !hm.containsKey(i+","+right)) {
					queue.add(i+","+right);
					hm.put(i+","+right, i+","+j);
				}
				queue.remove();
				String newHead = queue.peek();
				i = Integer.parseInt(newHead.split(",")[0].trim());
				j = Integer.parseInt(newHead.split(",")[1].trim());
			}
			System.out.println("No of states expanded:"+path.size());
			System.out.println("Shortest Path for BFS:");
			ListIterator li = path.listIterator(path.size());
			while (li.hasPrevious()) {
				System.out.println(li.previous().toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
