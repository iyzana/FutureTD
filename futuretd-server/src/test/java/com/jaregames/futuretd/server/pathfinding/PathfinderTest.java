package com.jaregames.futuretd.server.pathfinding;

import org.junit.Test;

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
        //        Thread.sleep(10000);
        double duration = 0;
        double durationGeneration = 0;
        int validPaths = 0;
        
        for (int i = 0; i < 100; i++) {
            long startGeneration = System.nanoTime();
            System.out.println("run " + i);
            int w = 300;
            int h = 500;
            TraversableNode[][] nodeArray = new TraversableNode[w][h];
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    boolean traversable = Math.random() < 0.8;
                    int finalX = x;
                    int finalY = y;
                    nodeArray[x][y] = new TraversableNode() {
                        @Override
                        public boolean isTraversable() {
                            return traversable;
                        }
                        
                        @Override
                        public int getX() {
                            return finalX;
                        }
                        
                        @Override
                        public int getY() {
                            return finalY;
                        }
                    };
                }
            }
            TiledMap map = new TiledMap() {
                @Override
                public TraversableNode getNodeAt(int x, int y) {
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
            Node start = SimpleNode.of(0, 0);
            Node end = SimpleNode.of(map.getWidth() - 1, map.getHeight() - 1);
            durationGeneration += System.nanoTime() - startGeneration;
            
            try {
                //                System.out.println("pathfinding");
                long startTime = System.nanoTime();
                Pathfinder.findPath(map, start, end);
                duration += System.nanoTime() - startTime;
                validPaths++;
                
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
                // duration += (System.nanoTime() - startTime) / 1000000000.0;
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
        System.out.println("valid paths found " + validPaths);
        System.out.println("time " + (duration / 1000000000.0) + " s");
        System.out.println("generation " + (durationGeneration / 1000000000.0) + " s");
    }
}