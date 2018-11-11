About:
	@Author: Liming(Frank) Liu
	@contact: limingl@kth.se
	@purpose of the project:
		This Hangman Game project is the 1st homework of ID1212 Network Programming course, from which I learned how to use TCP sockets to build connection between the client to the server
	@Code source & Copyright:
		I learned part of the structure and code from code example provided by the course: https://github.com/KTH-ID1212/nio
		Here is the license of the code example:
			/*
			 * The MIT License
			 *
			 * Copyright 2017 Leif Lindbäck <leifl@kth.se>.
			 *
			 * Permission is hereby granted, free of charge, to any person obtaining a copy
			 * of this software and associated documentation files (the "Software"), to deal
			 * in the Software without restriction, including without limitation the rights
			 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
			 * copies of the Software, and to permit persons to whom the Software is
			 * furnished to do so, subject to the following conditions:
			 *
			 * The above copyright notice and this permission notice shall be included in
			 * all copies or substantial portions of the Software.
			 *
			 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
			 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
			 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
			 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
			 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
			 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
			 * THE SOFTWARE.
			 */
	@structure:
		there are three packages in this project:
		1.client: 
			implements MVC structure:
			-model: in client.net package: to handle all the communication between the client and the server
			-controller: provides functions needed by the view to manage connection and send message to the server
			-view: in client.view package  handles the console interaction
			-an extra layer: a output handler interface in .net package
						  It is a practice to pass the data from net layer to view layer since the net layer should not visit the view layer
			
		2. common:
			mainly set common global variables and define the message object
		
		3. server:
			implements MVC structure to server the client:
			-model: realize the game logic, totally independent from the upper level
			-controller: a game controller provides methods operated by the server's network layer
			-view: in server.net package, handles the network communication and also executes the game logic to serve the client
		
		4. appending materials:
			words.txt: the dictionary from which the game logic reads word
			
	@What I learned:
		1. server communication:
			listen to client connection request:
				by opening a server socket( default TCP), and constantly listen to new request and pass the client socket to the handler( which will run serving logic)
			communicate with client:
				in client handler open the io stream by the client socket and then use read and write functions to communicate
				and of course by different handlers for different clients there will be multithreads
			run service logic:
				in handler, use controller to run the service logic
		
		2. client:
			connect to the server:
				by create a new socket( by which the local machine will automatically assign the local address and a port number) and connect the new socket to server
				if the connection is successful, use the socket to open io streams
			communicate with the server:
				open io streams to the server by using the socket
			UI:
				command: by using a interpreter which reads client's command and interprete the command into acutal operations, then operates by using the controller
				output: in this project I implemented a thread safe output console which means that the console will be locked by a singe thread once in a time,
				if not two messages from two output threads may cross into each other
				 