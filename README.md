# Maze Solver using Graph Traversal (Java)
A console-based maze solving program written in Java.  
This project was an academic assignment designed to practice graph representations, Breadth-First Search (BFS), and Depth-First Search (DFS) pathfinding techniques.

## Assignment Overview
The goal of this program is to read a maze from a text file, convert it into a graph structure, and solve it by finding a path from the **Start (S)** to **End (E)**.  
Vertices, edges, and traversal logic are manually implemented without external libraries.

## Features
The program supports the following functionality:
- Reads maze input from a text file (`maze.txt`)
- Parses the maze into a graph using adjacency lists
- Solves the maze using **Breadth-First Search (BFS)** and **Depth-First Search (DFS)**
- Prints both unsolved and solved maze states to the console
- Shows the path found using `X` markers
- Validates input to ensure Start and End exist

## How It Works
- Maze is loaded into a 2D array of characters
- Open tiles become graph nodes, and connecting paths become edges
- BFS finds the shortest path  
- DFS finds a depth-based path (not guaranteed to be the shortest)
- The final maze is printed with the discovered route highlighted
