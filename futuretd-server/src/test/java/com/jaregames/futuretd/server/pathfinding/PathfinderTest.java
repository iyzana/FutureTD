package com.jaregames.futuretd.server.pathfinding;

import org.junit.Test;

import java.util.List;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 23:54
 *
 * @author Jannis
 */
public class PathfinderTest {
    
    @Test
    public void findPath() throws Exception {
        double duration = 0;
        for (int i = 0; i < 100; i++) {
            System.out.println("run " + i);
            int w = 600;
            int h = 600;
            Node[][] nodeArray = new Node[w][h];
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    boolean traversable = Math.random() < 0.8;
                    nodeArray[x][y] = new Node(x, y, traversable);
                }
            }
            TiledMap map = new TiledMap() {
                @Override
                public Node getNodeAt(int x, int y) {
                    return nodeArray[x][y];
                }
                
                @Override
                public int getWidth() {
                    return w;
                }
                
                @Override
                public int getHeight() {
                    return h;
                }
            };
            Node start = new Node(0, 0);
            Node end = new Node(map.getWidth() - 1, map.getHeight() - 1);
            
            long startTime = System.nanoTime();
            try {
//                System.out.println("pathfinding");
                List<Node> nodes = Pathfinder.findPath(map, start, end);
                duration += (System.nanoTime() - startTime) / 1000000000.0;
                
                //            String path = nodes.stream().map(Object::toString).collect(Collectors.joining(" -> "));
                //            System.out.println("found path " + path);
                //            
                //            System.out.println("map");
                //            for (int y = 0; y < map.getHeight(); y++) {
                //                for (int x = 0; x < map.getWidth(); x++) {
                //                    Node node = new Node(x, y);
                //                    boolean inPath = nodes.contains(node);
                //                    if (map.getNodeAt(x, y).isTraversable()) System.out.print(inPath ? "<" : " ");
                //                    else System.out.print(inPath ? "?" : "#");
                //                }
                //                System.out.println();
                //            }
            } catch (NoPathFoundException e) {
                duration += (System.nanoTime() - startTime) / 1000000000.0;
                //                System.out.println("time " + duration + " s");
                System.out.println(e.getMessage());
                
                //            System.out.println("map");
                //            for (int y = 0; y < map.getHeight(); y++) {
                //                for (int x = 0; x < map.getWidth(); x++) {
                //                    System.out.print(map.getNodeAt(x, y).isTraversable() ? " " : "#");
                //                }
                //                System.out.println();
                //            }
            }
        }
        System.out.println("time " + duration + " s");
    }
}