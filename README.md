# Simulate Link State Routing Protocol with Java Socket Programming
[![Build Status](https://travis-ci.org/NineKa/COMP535-Simulate-Link-State-Routing-Protocol.svg?branch=master)](https://travis-ci.org/NineKa/COMP535-Simulate-Link-State-Routing-Protocol)

## Goal
In this project, you are supposed to develop a pure user-space program which simulates the major functionalities of a routing device running a simplified Link State Routing protocol.
To simulate the real-world network environment, you have to start multiple instances of the program, each of which connecting with (some of) others via socket. Each program instance represents a router or host in the simulated network space; Correspondingly, the links connecting the routers/hosts and the IP addresses identifying the routers/hosts are simulated by the in-memory data structures.
By defining the format of the messages transmitting between the program instances, as well as the parser and the handlers of these messages, you simulate the routing protocol with the user- space processes.

## Command-line Console
Besides the components introduced above, you have to develop a console for the router. When you start the router program, the terminal interface (command line based) should show to the user, and it allows the user to input following commands:
- attach [Process IP] [Process Port] [IP Address] [Link Weight]
- attach "[Configure File]" [Link Weight]
- start
- connect [Process IP] [Process Port] [IP Address] [Link Weight]
- disconnect [Port Number]
- detect [IP Address]
- neighbors
- quit
