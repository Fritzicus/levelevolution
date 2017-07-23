package com.levo.game;

// Entry class, contains only main method
public class Main {

	public static void main(String[] args) {
		// Start game in a new Thread
		new Thread(new Game()).start(); 
	}

}  
