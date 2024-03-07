# RAS Tech Summer 3.0

This repository contains a solution to a traveling salesman type programming problem.

The main task is to choose the most optimal route and provide the shortest time required to collect all the ducks from the given cities and return to the starting city. 
<br>
Each city is connected to all other cities by a direct two-way road, and each city can be visited only once.

Input:

The first line of the standard input contains one integer N (where, 1 N 16) representing the number of cities on the map and the starting city from which the route should start and end. The number of cities and the starting city are separated by a single space.

Each subsequent line contains, separated by single spaces, in turn: cityX, cityY, the time it takes to travel from cityX to cityY (and also from cityY to cityX) given in minutes.
<br>
Example:
Warsaw Cracow 209
means that the travel time from Warsaw to Cracow and from Cracow to Warsaw is 209 minutes.

Output:
The program should write out to the standard output the smallest possible time to visit all cities given in minutes, where the start will be from the city given in the input.

The data for the program can be found in the Cities.txt file.

Solution:
The program uses the jgrapht-core library, which includes an implementation of the Held-Karp algorithm that returns the optimal, minimum-cost Hamiltonian tour.

The lowest possible time to visit all the cities from the file is: 1945 minutes