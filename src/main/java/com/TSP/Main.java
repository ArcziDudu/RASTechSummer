package com.TSP;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.*;
import java.util.*;

public class Main {
    static Graph<String, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    static Map<Pair<String, String>, Integer> connections = new HashMap<>();
    static String firstCity = "";
    static Set<String> uniqueCities = new HashSet<>();

    public static void main(String[] args) {
        findBestWay();
    }

    private static void findBestWay() {
        firstCity = getCitiesFromFile();
        uniqueCities.remove(firstCity);
        List<String> cities = new ArrayList<>(uniqueCities.stream().sorted().toList());
        cities.add(0, firstCity);

        System.out.println("Miasta do obliczenia: " + cities);
        System.out.println("Ilość miast: " + cities.size());
        System.out.println("Miasto startowe: " + firstCity);
        System.out.println();

        for (String city : cities) {
            graph.addVertex(city);
        }

        addEdgesToGraph(connections);
        HeldKarpTSP<String, DefaultWeightedEdge> tspSolver = new HeldKarpTSP<>();
        GraphPath<String, DefaultWeightedEdge> path = tspSolver.getTour(graph);

        if (path != null) {
            System.out.println("Trasa: " + path.getVertexList());
            System.out.println("Całkowity czas podróży: " + path.getWeight());

        } else {
            System.out.println("Nie znaleziono trasy.");
        }
    }

    public static void addEdgesToGraph(Map<Pair<String, String>, Integer> connections) {
        for (Map.Entry<Pair<String, String>, Integer> entry : connections.entrySet()) {
            addEdgeWithWeight(graph, entry.getKey().getFirst(), entry.getKey().getSecond(), entry.getValue());
            addEdgeWithWeight(graph, entry.getKey().getSecond(), entry.getKey().getFirst(), entry.getValue());
        }
    }

    private static String getCitiesFromFile() {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("Cities.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
            String line;
            String firstLine = reader.readLine();
            if (firstLine != null) {
                firstCity = resolveFirstCity(firstCity, firstLine);

            }
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                uniqueCities.add(parts[0]);
                uniqueCities.add(parts[1]);
                connections.put(new Pair<>(parts[0], parts[1]), Integer.valueOf(parts[2]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return firstCity;
    }

    private static String resolveFirstCity(String firstCity, String firstLine) {

        String[] firstLineParts = firstLine.split("\\s+");
        if (firstLineParts.length >= 2) {
            firstCity = firstLineParts[1];
        }
        return firstCity;

    }

    private static void addEdgeWithWeight(Graph<String,
            DefaultWeightedEdge> graph,
                                          String source,
                                          String target,
                                          double weight) {
        DefaultWeightedEdge edge = graph.addEdge(source, target);
        if (edge != null) {
            graph.setEdgeWeight(edge, weight);
        }
    }
}