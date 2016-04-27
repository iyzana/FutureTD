package com.jaregames.futuretd.server.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:01
 *
 * @author Jannis
 */
public class Pathfinder {
    
    private static ThreadLocal<Map<Node, Integer>> cachedDistances = new ThreadLocal<>();
    
    /**
     * Finds a path on the map from the node start to node end.
     * The returned path may not always be optimal in favour for faster computation speed.
     *
     * @param map   The map to find a path on
     * @param start The start node to start searching from
     * @param end   The end (goal) node to reach
     * @return A path from start to end, as a list of every node on the path. Starting with the node start and ending with the node end.
     * @throws NoPathFoundException If a path cannot or cannot be found in a reasonable amount of time
     */
    public static List<Node> findPath(TiledMap map, Node start, Node end) throws NoPathFoundException {
        return findPath(map, start, Collections.singletonList(end));
    }
    
    /**
     * Finds a path on the map from the node start to any of the nodes in ends.
     * The returned path may not always be optimal in favour for faster computation speed.
     *
     * @param map   The map to find a path on
     * @param start The start node to start searching from
     * @param ends  The ending (goal) nodes to reach
     * @return A path from start to any end, as a list of every node on the path. Starting with the node start and ending with any of the nodes in ends.
     * @throws NoPathFoundException If a path cannot or cannot be found in a reasonable amount of time
     */
    public static List<Node> findPath(TiledMap map, Node start, List<Node> ends) throws NoPathFoundException {
        cachedDistances.set(null); // Reset the cache from last pathfinding
        
        if (ends.isEmpty()) throw new NoPathFoundException("No end points supplied");
        if (!(ends.get(0) instanceof SimpleNode))
            ends = ends.parallelStream().map(SimpleNode::of).collect(Collectors.toList());
        
        Queue<PathNode> open = new PriorityQueue<>(100, comparatorFor(ends)); // Get a queue sorted by heuristic
        Set<Node> closed = new HashSet<>();
        
        open.add(PathNode.of(start)); // Start searching from the start ode
        
        int minDistance = (map.getWidth() + map.getHeight()) * 2; // Save the nearest we've come to the end so far
        int maxDistance = minDistance / 20;
        
        while (!open.isEmpty()) { // While nodes to check are available
            PathNode current = open.poll();
            // if (!closed.add(current)) continue; // Skip nodes already in the closed set
            
            if (ends.contains(current)) return backtrack(current);
            
            int distance = distanceToAny(current, ends); // Get the distance to the current node
            if (distance < minDistance) minDistance = distance; // Alter min distance if necessary
            else if (distance > minDistance * maxDistance) // If distance is far greater stop searching
                throw new NoPathFoundException("No easy path between start and end");
            
            List<PathNode> neighbors = neighbors(map, current) // Build the neighbors
                    .stream()
                    .filter(node -> !closed.contains(node)) // Filter already visited nodes
                    .map(node -> PathNode.of(node, current)) // Map them to PathNodes
                    .collect(Collectors.toList());
            
            closed.addAll(neighbors); // Add new nodes to closed and opened list
            open.addAll(neighbors);
        }
        
        throw new NoPathFoundException("No path between start and end");
    }
    
    /**
     * Calculates how good the node is for reaching any node in ends
     *
     * @param node The node to calculate the score fr
     * @param ends The ends
     * @return The score of the given node in range 0 to map size
     */
    private static float heuristic(PathNode node, List<Node> ends) {
        return distanceToAny(node, ends) + node.getSteps();
    }
    
    /**
     * Calculates the shortest distance from a node to any of the given ends
     *
     * @param node The node to calculate the distance for
     * @param ends The ends
     * @return The shortest distance to the nearest end from ends
     */
    private static int distanceToAny(Node node, List<Node> ends) {
        Map<Node, Integer> cache = cachedDistances.get(); // Get cache for current thread
        if (cache == null) cachedDistances.set(cache = new HashMap<>());
        if (!cache.containsKey(node)) {
            cache.put(node, ends.parallelStream()
                    .mapToInt(n -> manhattanDistance(n, node))
                    .min()
                    .orElseThrow(() -> new IllegalArgumentException("No end points supplied")));
        }
        return cache.get(node);
    }
    
    /**
     * Calculates the distance from node1 to node2 by manhattan distance.
     * Distance is calculated by adding x difference and y difference of the nodes
     *
     * @param node1 Node to measure distance from
     * @param node2 Node to measure distance to
     * @return The distance from node1 to node2
     */
    private static int manhattanDistance(Node node1, Node node2) {
        return Math.abs(node1.getX() - node2.getX()) + Math.abs(node1.getY() - node2.getY());
    }
    
    /**
     * Creates a comparator for comparing nodes by best heuristic value using {@link Pathfinder#heuristic(PathNode, List)}
     *
     * @param ends The list of endpoints for calculating heuristics
     * @return A heuristic comparator
     */
    private static Comparator<PathNode> comparatorFor(List<Node> ends) {
        return (n1, n2) -> Float.compare(heuristic(n1, ends), heuristic(n2, ends));
    }
    
    /**
     * Builds all 8 neighboring nodes for a given node matching the given conditions:
     * <ul>
     * <li>The node is traversable</li>
     * <li>The node is either
     * <ul>
     * <li>directly adjacent</li>
     * <li>or not blocked by non transversable nodes</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param map  The map to check for traversibility
     * @param node The node to build neighbors for
     * @return The nodes 8 filtered neighbors
     */
    private static List<Node> neighbors(TiledMap map, Node node) {
        List<Node> neighbors = new ArrayList<>(9);
        
        int x = node.getX();
        int y = node.getY();
        int w = map.getWidth();
        int h = map.getHeight();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int cx = x + dx;
                int cy = y + dy;
                if (cx < 0 || cy < 0 || cx >= w || cy >= h) continue;
                if (map.getNodeAt(cx, cy).isTraversable()) {
                    if (dx != 0 && dy != 0) {
                        if (!map.getNodeAt(cx, y).isTraversable()) continue;
                        if (!map.getNodeAt(x, cy).isTraversable()) continue;
                    }
                    neighbors.add(SimpleNode.of(cx, cy));
                }
            }
        }
        
        return neighbors;
    }
    
    /**
     * Backtracks the best path from the goal node to the starting node
     *
     * @param node The goal node to backtrack from
     * @return The path from start node to given goal node
     */
    private static List<Node> backtrack(PathNode node) {
        List<Node> path = new ArrayList<>(50);
        
        PathNode current = node;
        path.add(current);
        while (current.getParent() != null) {
            current = current.getParent();
            path.add(current);
        }
        
        Collections.reverse(path);
        return path;
    }
}
