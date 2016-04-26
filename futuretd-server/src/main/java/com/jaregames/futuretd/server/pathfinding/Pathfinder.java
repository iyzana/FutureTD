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
    public static List<Node> findPath(TiledMap map, Node start, Node end) {
        return findPath(map, start, Collections.singletonList(end));
    }
    
    public static List<Node> findPath(TiledMap map, Node start, List<Node> ends) {
        Queue<PathNode> open = new PriorityQueue<>(100, comparatorFor(ends));
        Set<Node> closed = new HashSet<>();
        
        open.add(PathNode.of(start));
        
        int maxDistance = map.getWidth() + map.getHeight();
        int minDistance = maxDistance;
        
        while (!open.isEmpty()) {
            PathNode current = open.poll();
            if (!closed.add(current)) continue;
            
            if (ends.contains(current)) return backtrack(current);
            
            int distance = distanceToAny(current, ends);
            if(distance < minDistance) minDistance = distance;
            if(distance > minDistance * (maxDistance / 10))
                throw new NoPathFoundException("No easy path between start and end");
            
            List<PathNode> neighbors = neighbors(map, current)
                    .parallelStream()
                    .filter(node -> !closed.contains(node))
                    .map(node -> PathNode.of(node, current))
                    .collect(Collectors.toList());
            
            open.addAll(neighbors);
        }
        
        throw new NoPathFoundException("No path between start and end");
    }
    
    private static float heuristic(PathNode node, List<Node> ends) {
        return distanceToAny(node, ends) + node.getSteps();
    }
    
    private static ThreadLocal<Map<Node, Integer>> cachedDistance = new ThreadLocal<>();
    
    private static int distanceToAny(Node node, List<Node> ends) {
        Map<Node, Integer> cache = cachedDistance.get();
        if (cache == null) cachedDistance.set(cache = new HashMap<>());
        if (!cache.containsKey(node)) {
            cache.put(node, ends.parallelStream()
                    .mapToInt(n -> distance(n, node))
                    .min()
                    .orElseThrow(() -> new IllegalArgumentException("No end points supplied")));
        }
        return cache.get(node);
    }
    
    private static int distance(Node node1, Node node2) {
        return Math.abs(node1.getX() - node2.getX()) + Math.abs(node1.getY() - node2.getY());
    }
    
    private static Comparator<PathNode> comparatorFor(List<Node> ends) {
        return (n1, n2) -> Float.compare(heuristic(n1, ends), heuristic(n2, ends));
    }
    
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
                if (dx != 0 && dy != 0) {
                    if (!map.getNodeAt(cx, y).isTraversable()) continue;
                    if (!map.getNodeAt(x, cy).isTraversable()) continue;
                }
                if (map.getNodeAt(cx, cy).isTraversable())
                    neighbors.add(new Node(cx, cy));
            }
        }
        
        return neighbors;
    }
    
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
