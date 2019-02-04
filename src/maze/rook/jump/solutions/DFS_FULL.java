package maze.rook.jump.solutions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Stack;

public class DFS_FULL {
	static int count =0;
	static int states =0;
	HashSet<String> hs = new HashSet<String>();
	public void callDFSFull(String location) {
		try {
			int n = 0;
			int i = 0, j = 0;
			
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
			new DFS_FULL().findUniquePathCount(i, j, n, stack, matrix, hm);

			System.out.println("No of Unique paths:"+count);
			System.out.println("No of states expanded:"+states);
	} catch (Exception e) {
		}
	}
	
	public void findUniquePathCount(int i, int j, int n, Stack<String> stack, String matrix[][], HashMap<String,String> hm) {
		if (!stack.isEmpty()) {
			if (matrix[i][j].equalsIgnoreCase("G")) {
				ArrayList<String> path = new ArrayList<String>();
				int m =i; n =j;
				path.add(m+","+n);
				while (!hm.get(m+","+n).equalsIgnoreCase("0,0")) {
					String parent = hm.get(m+","+n);
					path.add(parent);
					hs.add(parent);
					m =Integer.parseInt(parent.split(",")[0].trim());
					n =Integer.parseInt(parent.split(",")[1].trim());
				}
				hs.addAll(path);
				states = hs.size();
				hm.remove(i+","+j);
				stack.pop();
				count++;
				return;
			}
			int jump = Integer.parseInt(matrix[i][j]);
			int up = i-jump;
			int down = i+jump;
			int left = j-jump;
			int right = j+jump;
			if (up>=0 && !hm.containsKey(up+","+j)) {
				stack.push(up+","+j);
				hm.put(up+","+j, i+","+j);
				findUniquePathCount(up, j, n, stack, matrix, hm);
			}
			if (down <n && !hm.containsKey(down+","+j)) {
				stack.push(down+","+j);
				hm.put(down+","+j, i+","+j);
				findUniquePathCount(down, j, n, stack, matrix, hm);
			}
			if (left >=0 && !hm.containsKey(i+","+left)) {
				stack.push(i+","+left);
				hm.put(i+","+left, i+","+j);
				findUniquePathCount(i, left, n, stack, matrix, hm);
			}
			if (right <n && !hm.containsKey(i+","+right)) {
				stack.push(i+","+right);
				hm.put(i+","+right, i+","+j);
				findUniquePathCount(i, right, n, stack, matrix, hm);
			}
			hm.remove(i+","+j);
			stack.pop();
			if (!stack.isEmpty()) {
				String newTop = stack.peek();
				i = Integer.parseInt(newTop.split(",")[0].trim());
				j = Integer.parseInt(newTop.split(",")[1].trim());
			}
		}
	}

}
