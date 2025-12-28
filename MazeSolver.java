/* Intro to Data Structures : Program #3
 * Maze Solver using Graphs, BFS, and DFS
*/

// Line 248 - Change file name if neccessary or use maze.txt as input file
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

class Graph {
    // Hashmap to store the vertices and their adjacent edges
    private HashMap<String, ArrayList<String>> adjacencyList = new HashMap<>();

    // Visited list to store vertices already visited
    private ArrayList<String> visited;

    // Hashmap to track the path taken from start to end along with an arraylist for easier printing
    private HashMap<String, String> path;
    private ArrayList<String> pathList;

    // Maze characters
    private final String[][] maze;
    private final String wall = "#";
    private final String vertex = "O";
    private final String start = "S";
    private final String end = "E";
    private final String verticalEdge = "|";
    private final String horizontalEdge = "-";

    // Length variables for the maze
    private int xLength;
    private int yLength;

    // Construct the adjacency hashmap and initialize variables
    public Graph(String[][] maze) {
        this.maze = maze;
        this.xLength = maze[0].length;
        this.yLength = maze.length;
        
        // Create vertices for all nodes including start & end
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (isNode(maze[i][j])) {
                    addVertex(maze[i][j]);
                }
            }
        }

        // Create edges for all vertices in N,E,S,W order
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                // North
                if (i > 1 && isNode(maze[i][j]) && isNode(maze[i-2][j])) {
                    // Check to make sure there is an edge and not a wall between the two vertices
                    if (isEdge(maze[i-1][j])) {
                        addEdge(maze[i][j], maze[i-2][j]);
                    }
                }
                // East
                if (j < xLength - 1 && isNode(maze[i][j]) && isNode(maze[i][j+2])) {
                    // Check to make sure there is an edge and not a wall between the two vertices
                    if (isEdge(maze[i][j+1])) {
                        addEdge(maze[i][j], maze[i][j+2]);
                    }
                }
                // South
                if (i < yLength - 1 && isNode(maze[i][j]) && isNode(maze[i+2][j])) {
                    // Check to make sure there is an edge and not a wall between the two vertices
                    if (isEdge(maze[i+1][j])) {
                        addEdge(maze[i][j], maze[i+2][j]);
                    }
                }
                // West
                if (j > 1 && isNode(maze[i][j]) && isNode(maze[i][j-2])) {
                    // Check to make sure there is an edge and not a wall between the two vertices
                    if (isEdge(maze[i][j-1])) {
                        addEdge(maze[i][j], maze[i][j-2]);
                    }
                } 
            }
        }
    }

    // Add a new vertex to the graph with adjacent nodes
    public void addVertex(String s) {
        adjacencyList.put(s, new ArrayList<>());
    }

    // This function adds the edge between source to destination
    public void addEdge(String source, String destination) {
        adjacencyList.get(source).add(destination);
    }

    // Check to see if a string is a number, start, or end
    public boolean isNode(String str) {
        try {Double.parseDouble(str); return true; } 
        catch (NumberFormatException e) {
            if (str.equals(start) || str.equals(end)) {return true;}
            else {return false;}
        }
    }

    // Check to see if a string is an edge
    public boolean isEdge(String str) {
        if (str.equals(verticalEdge) || str.equals(horizontalEdge)) {return true;} 
        else {return false;}
    }

    // Depth First Search
    public ArrayList<String> DFS() {
        visited = new ArrayList<>();
        path = new HashMap<>();
        pathList = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        String key = start;
        String value;

        try {
            // Start with S and add/push to visited and stack
            stack.push(key);
            visited.add(key);
            
            // Keep adding to the stack and popping until E is found
            while (!key.equals(end)) {
                key = stack.pop();
                for (String neighbor : adjacencyList.get(key)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        visited.add(neighbor);
                        path.put(neighbor, key);
                    }
                }
            }
                    
            // Create the arraylist from the path hashmap
            pathList.add(end);
            value = path.get(end);

            while (!value.equals(start)) {
                pathList.add(value);
                value = path.get(value);
            }
            pathList.add(start);

            // Reverse the arraylist so its from S -> E
            Collections.reverse(pathList);
        }
        catch (Exception e) {
            System.out.println("Maze is unsolvable.");
            pathList = null;
        }
        return pathList;
    }

    // Breadth First Search
    public ArrayList<String> BFS() {
        visited = new ArrayList<>();
        path = new HashMap<>();
        pathList = new ArrayList<>();
        Queue<String> queue = new ArrayDeque<>();
        String key = start;
        String value;

        try {
            // Add S to the visited list
            visited.add(key); 

            // Keep adding the queue and pulling off the front until E is found
            while (!key.equals(end)) {
                for (String str : adjacencyList.get(key)) {
                    value = str;
                    if (!visited.contains(value)) {
                        queue.add(value);
                        visited.add(value);
                        path.put(value, key);
                    }
                }
                key = queue.poll();
            }

            // Create the arraylist from the path hashmap
            pathList.add(end);
            value = path.get(end);

            while (!value.equals(start)) {
                pathList.add(value);
                value = path.get(value);
            }
            pathList.add(start);

            // Reverse the arraylist so its from S -> E
            Collections.reverse(pathList);
        }
        catch (Exception e) {
            System.out.println("Maze is unsolvable.");
            pathList = null;
        }
        return pathList;
    }

    // Print out the solved maze
    public void printSolvedMaze(ArrayList<String> pathList) {
        String[][] solvedMaze = new String[yLength][xLength];

        // Copy the original maze to a new one that will be edited with the final path taken
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                solvedMaze[i][j] = maze[i][j];
            }
        }

        // Loop through the 2D array reverting the numbers back to O or X depending on if it's in the pathList array
        for (int i = 0; i < solvedMaze.length; i++) {
            for (int j = 0; j < solvedMaze[i].length; j++) {
                if (pathList.contains(solvedMaze[i][j]) && !solvedMaze[i][j].equals(start) && !solvedMaze[i][j].equals(end)) {
                    solvedMaze[i][j] = "X";
                }
                else if (!pathList.contains(solvedMaze[i][j]) && !solvedMaze[i][j].equals(wall) && !solvedMaze[i][j].equals(horizontalEdge) && !solvedMaze[i][j].equals(verticalEdge)) {
                    solvedMaze[i][j] = vertex;
                }
            }
        }

        // Print out the solved maze
        for (int i = 0; i < solvedMaze.length; i++) {
            for (int j = 0; j < solvedMaze[i].length; j++) {
                System.out.print(solvedMaze[i][j] + " ");
            }
            System.out.println(); // New line after each row
        }
        System.out.println();
    }
}

public class MazeSolver {
    public static void main(String[] args) throws IOException {
        // Change file name if necessary
        String filename = "maze.txt";

        // Check to make sure the file exists and is a text file
        File file = new File(filename);
        if (!file.exists() || !file.isFile() || !filename.contains(".txt")) {
            System.out.println("Invalid File.");
            System.exit(0);
        }
        
        // Create necessary variables   
        int xLength = 0;
        int yLength = 1;
        String[][] maze;

        // Read from text file
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();

        // Count the horizontal length of maze
        for (int i = 0; i < line.length(); i++) {
            xLength++;
        }
        // Count the vertical length of maze
        while ((line = reader.readLine()) != null) {
            yLength++;
        }
        // Close & reset the reader
        reader.close();

        // Create a 2D array of the maze (excluding outside borders)
        maze = new String[yLength - 2][xLength - 2];
        reader = new BufferedReader(new FileReader(filename));
        line = reader.readLine(); // Remove the first row of borders

        for (int i = 0; i < yLength - 2; i++) {
            line = reader.readLine();
            line = line.substring(1, xLength - 1);

            for (int j = 0; j < line.length(); j++) {
                maze[i][j] = String.valueOf(line.charAt(j));
            } 
        }
        reader.close();

        // Print starting maze
        System.out.println();
        System.out.println("Maze to Solve:");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println(); // New line after each row
        }
        System.out.println();

        // Check to make sure there is a Start and End
        // Convert vertices into numbers so that they can be more easily distinguished
        int vCounter = 1;
        boolean start = false;
        boolean end = false;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                // Node
                if (maze[i][j].equals("O")) {
                    maze[i][j] = Integer.toString(vCounter);
                    vCounter++;
                }
                // Start Node
                if (maze[i][j].equals("S")) {
                    start = true;
                }
                // End Node
                if (maze[i][j].equals("E")) {
                    end = true;
                }
            }
        }
        if (!start) {System.out.println("Maze does not contain a Start."); System.exit(0);}
        if (!end) {System.out.println("Maze does not contain an End."); System.exit(0);}

        // Create the graph from the 2D array
        Graph graph = new Graph(maze);

        // Print solved maze using BFS
        System.out.println("Maze Solved using BFS:");
        if (graph.BFS() != null) {
            graph.printSolvedMaze(graph.BFS());
        }
        else {System.out.println();}

        // Print solved maze using DFS
        System.out.println("Maze Solved using DFS:");
        if (graph.DFS() != null) {
            graph.printSolvedMaze(graph.DFS());
        }
        else {System.out.println();}
    }
}