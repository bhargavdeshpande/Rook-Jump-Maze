package maze.rook.jump.solutions;

public class MyMainClass {

	public static void main(String[] args) {
		
		String location = args[1];
		String algo = args[0];
		String paths = null;
		if(args.length ==3) {
			paths = "all";
		}
		
		Astar astar = new Astar();
		Best_First bf = new Best_First();
		BFS bfs = new BFS();
		DFS dfs = new DFS();
		DFS_FULL dfs_full = new DFS_FULL();  
		
		if (algo.equalsIgnoreCase("AStar")) {
			astar.callAstar(location);
		}
		if (algo.equalsIgnoreCase("DFS")) {
			if (paths == null) {
				dfs.callDFS(location);
			} 
			else if (paths.equalsIgnoreCase("all")) {
				dfs_full.callDFSFull(location);
			}
		}
		
		if (algo.equalsIgnoreCase("BFS")) {
			bfs.callBFS(location);
		}
		if (algo.equalsIgnoreCase("BestFirst")) {
				bf.callBestFirst(location);
		}
			
	}

}
