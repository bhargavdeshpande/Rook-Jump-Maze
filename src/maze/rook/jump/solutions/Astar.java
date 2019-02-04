package maze.rook.jump.solutions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class Astar {

	public void callAstar(String location) {
		int n = 0;
		int i = 0, j = 0;
		
		FileReader fr;
		try {
			fr = new FileReader(location);
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
			
			HashMap<String, Integer> heuristicArr = new HashMap<String, Integer>();
			ArrayList<String> heuristicElements = new ArrayList<String>();
			HashMap<String,String> path = new HashMap<String,String>();
			Astar astar = new Astar();
			heuristicElements.add("0,0");
			String parent= "";
			ArrayList<String> visited = new ArrayList<String>();
			i=0; j=0;
			HashMap<String, Integer> startDist = new HashMap<String, Integer>();
			startDist.put("0,0", 0);
			while (!"G".equalsIgnoreCase(matrix[i][j])) {
				int jump = Integer.parseInt(matrix[i][j]);
				int minH = Integer.MAX_VALUE;
				int tempH = Integer.MAX_VALUE;
				String near="";
				if (i-jump>=0 && !visited.contains((i-jump)+","+j)) {
					if (startDist.containsKey((i-jump)+","+j)) {
						startDist.put((i-jump)+","+j, Math.min(startDist.get((i-jump)+","+j), 1+startDist.get(i+","+j)));
					} else {
						startDist.put((i-jump)+","+j,1+startDist.get(i+","+j));
					}
					visited.add((i-jump)+","+j);
					if (!path.containsValue((i-jump)+","+j)) {
						heuristicElements.add((i-jump)+","+j);
						tempH = astar.heuristic(heuristicArr, i-jump, j, n, matrix, heuristicElements);	
					} else {
						tempH = heuristicArr.get((i-jump)+","+j);
					}
					if (tempH != Integer.MAX_VALUE && startDist.get((i-jump)+","+j)+tempH < minH) {
						minH = startDist.get((i-jump)+","+j)+tempH;
						near=(i-jump)+","+j;
					} 
				}
				if (i+jump<n && !visited.contains((i+jump)+","+j)) {
					if (startDist.containsKey((i+jump)+","+j)) {
						startDist.put((i+jump)+","+j, Math.min(startDist.get((i+jump)+","+j), 1+startDist.get(i+","+j)));
					} else {
						startDist.put((i+jump)+","+j,1+startDist.get(i+","+j));
					}
					visited.add((i+jump)+","+j);
					if (!path.containsValue((i+jump)+","+j)) {
						heuristicElements.add((i+jump)+","+j);
						tempH = astar.heuristic(heuristicArr, i+jump, j, n, matrix, heuristicElements);
					} else {
						tempH = heuristicArr.get((i+jump)+","+j);
					}
					if (tempH != Integer.MAX_VALUE && startDist.get((i+jump)+","+j)+tempH < minH) {
						minH = startDist.get((i+jump)+","+j)+tempH;
						near=(i+jump)+","+j;
					} 
				}
				if (j-jump>=0 && !visited.contains(i+","+(j-jump))) {
					if (startDist.containsKey(i+","+(j-jump))) {
						startDist.put(i+","+(j-jump), Math.min(startDist.get(i+","+(j-jump)), 1+startDist.get(i+","+j)));
					} else {
						startDist.put(i+","+(j-jump),1+startDist.get(i+","+j));
					}
					visited.add(i+","+(j-jump));
					if (!path.containsValue(i+","+(j-jump))) {
						heuristicElements.add(i+","+(j-jump));
						tempH = astar.heuristic(heuristicArr, i, j-jump, n, matrix, heuristicElements);
					} else {
						tempH = heuristicArr.get(i+","+(j-jump));
					}
					if (tempH != Integer.MAX_VALUE && startDist.get(i+","+(j-jump))+tempH < minH) {
						minH = startDist.get(i+","+(j-jump))+tempH;
						near=i+","+(j-jump);
					} 
				}
				if (j+jump<n && !visited.contains(i+","+(j+jump))) {
					if (startDist.containsKey(i+","+(j+jump))) {
						startDist.put(i+","+(j+jump), Math.min(startDist.get(i+","+(j+jump)), 1+startDist.get(i+","+j)));
					} else {
						startDist.put(i+","+(j+jump),1+startDist.get(i+","+j));
					}
					visited.add(i+","+(j+jump));
					if (!path.containsValue(i+","+(j+jump))) {
						heuristicElements.add(i+","+(j+jump));
						tempH = astar.heuristic(heuristicArr, i, j+jump, n, matrix, heuristicElements);
					} else {
						tempH = heuristicArr.get(i+","+(j+jump));
					}
					if (tempH != Integer.MAX_VALUE && startDist.get(i+","+(j+jump))+tempH < minH) {
						minH = startDist.get(i+","+(j+jump))+tempH;
						near=i+","+(j+jump);
					} 
				}
				if(!"".equals(near)) {
					path.put(near,i+","+j);
					heuristicArr.put(i+","+j, 1+minH);
					i = Integer.parseInt(near.split(",")[0]);
					j = Integer.parseInt(near.split(",")[1]);
				} else {
					parent = path.get(i+","+j);
					visited.remove(i+","+j);
					path.remove(i+","+j);
					heuristicArr.put(i+","+j,Integer.MAX_VALUE);
					i = Integer.parseInt(parent.split(",")[0]);
					j = Integer.parseInt(parent.split(",")[1]);
				}
				
			}
			
			ArrayList<String> pathList = new ArrayList<String>();
			
			parent = i+","+j;
			String child ="";
			while (parent !=null && !parent.equalsIgnoreCase("0,0")) {
				pathList.add(parent);
				child = path.get(parent);
				path.remove(parent);
				parent = child;	
			}
			pathList.add("0,0");
			System.out.println("No of states expanded:"+pathList.size());
			System.out.println("Shortest Path for AStar:");
			ListIterator li = pathList.listIterator(pathList.size());
			while (li.hasPrevious()) {
				System.out.println(li.previous().toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int heuristic(HashMap<String, Integer> heuristicArr, int i, int j, int n, String matrix[][], ArrayList<String> heuristicElements) {
		if ("G".equalsIgnoreCase(matrix[i][j])) {
			heuristicElements.remove(i+","+j);
			heuristicArr.put(i+","+j, 0);
			return 0;
		}
		if (heuristicArr.containsKey(i+","+j)) {
			return heuristicArr.get(i+","+j);
		}
		int jump = Integer.parseInt(matrix[i][j]);
		int minHeuristic = Integer.MAX_VALUE;
		
		if (i+jump <n) {
			if (heuristicArr.containsKey((i+jump)+","+j)) {return 1+heuristicArr.get((i+jump)+","+j);}
			if (!heuristicElements.contains((i+jump)+","+j)) {
				heuristicElements.add((i+jump)+","+j);
				int tempHeuristic = heuristic(heuristicArr,i+jump, j, n, matrix,heuristicElements);
				if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
				if (tempHeuristic < minHeuristic) {
					minHeuristic = tempHeuristic;
					heuristicArr.put(i+","+j, minHeuristic);
				}
			}
		}
		if (i+jump<n && j+jump<n ) {
			if (heuristicArr.containsKey((i+jump)+","+(j+jump))) {return 1+heuristicArr.get((i+jump)+","+(j+jump));}
			if(!heuristicElements.contains((i+jump)+","+(j+jump))) {
			heuristicElements.add((i+jump)+","+(j+jump));
			int tempHeuristic = heuristic(heuristicArr,i+jump, (j+jump), n, matrix,heuristicElements);
			if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
			if (tempHeuristic < minHeuristic) {
				minHeuristic = tempHeuristic;
				heuristicArr.put(i+","+j, minHeuristic);
			}
			}
		}
		if (j+jump <n) {
			if (heuristicArr.containsKey(i+","+(j+jump))) {return 1+heuristicArr.get(i+","+(j+jump));}
			if (!heuristicElements.contains(i+","+(j+jump))) {
				heuristicElements.add(i+","+(j+jump));
				int tempHeuristic = heuristic(heuristicArr,i, j+jump, n, matrix,heuristicElements);
				if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
				if (tempHeuristic < minHeuristic) {
					minHeuristic = tempHeuristic;
					heuristicArr.put(i+","+j, minHeuristic);
				}
			}
		}
		if (i-jump>=0 && j+jump<n) {
			if (heuristicArr.containsKey((i-jump)+","+(j+jump))) {return 1+heuristicArr.get((i-jump)+","+(j+jump));}
			if (!heuristicElements.contains((i-jump)+","+(j+jump))) {
			heuristicElements.add((i-jump)+","+(j+jump));
			int tempHeuristic = heuristic(heuristicArr,i-jump, (j+jump), n, matrix,heuristicElements);
			if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
			if (tempHeuristic < minHeuristic) {
				minHeuristic = tempHeuristic;
				heuristicArr.put(i+","+j, minHeuristic);
			}
		}}
		if (i-jump>=0) {
			if (heuristicArr.containsKey((i-jump)+","+j)) {return 1+heuristicArr.get((i-jump)+","+j);}
			if (!heuristicElements.contains((i-jump)+","+j)) {
			heuristicElements.add((i-jump)+","+j);
			int tempHeuristic = heuristic(heuristicArr,i-jump, j, n, matrix,heuristicElements);
			if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
			if (tempHeuristic < minHeuristic) {
				minHeuristic = tempHeuristic;
				heuristicArr.put(i+","+j, minHeuristic);
			}
		}}
		if (i-jump>=0 && j-jump>=0) {
			if (heuristicArr.containsKey((i-jump)+","+(j-jump))) {return 1+heuristicArr.get((i-jump)+","+(j-jump));}
			if (!heuristicElements.contains((i-jump)+","+(j-jump))) {
			heuristicElements.add((i-jump)+","+(j-jump));
			int tempHeuristic = heuristic(heuristicArr,i-jump, (j-jump), n, matrix,heuristicElements);
			if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
			if (tempHeuristic < minHeuristic) {
				minHeuristic = tempHeuristic;
				heuristicArr.put(i+","+j, minHeuristic);
			}
		}}
		if (j-jump >=0) {
			if (heuristicArr.containsKey(i+","+(j-jump))) {return 1+heuristicArr.get(i+","+(j-jump));}
			if(!heuristicElements.contains(i+","+(j-jump))) {
				heuristicElements.add(i+","+(j-jump));
				int tempHeuristic = heuristic(heuristicArr,i, j-jump, n, matrix,heuristicElements);
				if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
				if (tempHeuristic < minHeuristic) {
					minHeuristic = tempHeuristic;
					heuristicArr.put(i+","+j, minHeuristic);
				}
			}
		}
		if (i+jump<n && j-jump>=0) {
			if (heuristicArr.containsKey((i+jump)+","+(j-jump))) {return 1+heuristicArr.get((i+jump)+","+(j-jump));}
			if (!heuristicElements.contains((i+jump)+","+(j-jump))) {
			heuristicElements.add((i+jump)+","+(j-jump));
			int tempHeuristic = heuristic(heuristicArr,i+jump, (j-jump), n, matrix,heuristicElements);
			if (tempHeuristic != Integer.MAX_VALUE) {tempHeuristic++;}
			if (tempHeuristic < minHeuristic) {
				minHeuristic = tempHeuristic;
				heuristicArr.put(i+","+j, minHeuristic);
			}
		}}
		heuristicElements.remove(i+","+j);
		return minHeuristic;
	}

}
