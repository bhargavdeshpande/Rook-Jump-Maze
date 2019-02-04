package maze.rook.jump.solutions;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Stack;

public class DFS {

	public void callDFS(String location) {
		int n = 0;
		int i = 0, j = 0;
		try {
			FileReader fr = new FileReader(location);

			BufferedReader br = new BufferedReader(fr);
			String s;
			ArrayList<String> arrList = new ArrayList<String>();
			while ((s = br.readLine()) != null) {
				arrList.add(s);
				n++;
			}
			String matrix[][] = new String[n][n];
			for (String str : arrList) {
				String charArr[] = str.split(",");
				for (String str2 : charArr) {
					matrix[i][j] = str2.trim();
					j++;
				}
				i++;
				j = 0;
			}
		
			Stack<String> stack = new Stack<String>();
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("0,0", "0,0");
			ArrayList<String> path = new ArrayList<String>();
			i=0;j=0;
			stack.push(i+","+j);
			while (!stack.isEmpty()) {
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
					stack.push(up+","+j);
					hm.put(up+","+j, i+","+j);
					i = up;
					continue;
				}
				if (down <n && !hm.containsKey(down+","+j)) {
					stack.push(down+","+j);
					hm.put(down+","+j, i+","+j);
					i = down;
					continue;
				}
				if (left >=0 && !hm.containsKey(i+","+left)) {
					stack.push(i+","+left);
					hm.put(i+","+left, i+","+j);
					j = left;
					continue;
				}
				if (right <n && !hm.containsKey(i+","+right)) {
					stack.push(i+","+right);
					hm.put(i+","+right, i+","+j);
					j = right;
					continue;
				}
				stack.pop();
				String newTop = stack.peek();
				i = Integer.parseInt(newTop.split(",")[0].trim());
				j = Integer.parseInt(newTop.split(",")[1].trim());
			}
			System.out.println("No of states expanded:"+path.size());
			System.out.println("Shortest Path for DFS:");
			ListIterator li = path.listIterator(path.size());
			while (li.hasPrevious()) {
				System.out.println(li.previous().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
