package ml.jtreed.util;

import java.util.function.Function;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.IOException;

public class Menu {
	private String preamble;
	private String[] options;
	private String prompt;
	private boolean exit;
	private Runnable[] functions;

	public Menu(String preamble, String[] options, String prompt, Runnable[] functions, boolean exit) {
		if(exit) {
			if(options.length-1 != functions.length) throw new IllegalArgumentException("Menu requires the same number of option names as methods (excluding exit option)");
		} else {
			if(options.length != functions.length) throw new IllegalArgumentException("Menu requires the same number of option names as methods");
		}
		this.preamble = preamble;
		this.options = options;
		this.prompt = prompt;
		this.functions = functions;
		this.exit = exit;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder(preamble).append('\n');
		for(int i = 0; i < options.length - (exit?1:0); i++) {
			output.append("  ").append(i+1).append(" \t").append(options[i]).append('\n');
		}
		if(exit) {
			output.append("\n  0 \t").append(options[options.length-1]).append('\n');
		}
		output.append(prompt);
		return output.toString();
	}

	public String getPrompt() { return prompt; }

	public void enter(BufferedReader cin, PrintStream cout) {
		Integer selection = null;
		boolean showFullMenu = true;
		do {
			System.out.print(showFullMenu?this:this.getPrompt());
			try {
				String input = cin.readLine();
				selection = Integer.parseInt(input);
			} catch(IOException ioe) {
				cout.println("Something sad happened :(");
				cout.println("Details:");
				cout.println(ioe);
				showFullMenu = false;
				continue;
			} catch(NumberFormatException nfe) {
				cout.println("Not a number");
				showFullMenu = false;
				continue;
			}
			if(selection == 0 && exit) {
				selection = null;
				continue;
			}
			if(selection > functions.length || selection < 1) {
				cout.println("Selection out of range");
				showFullMenu = false;
				continue;
			}
			cout.print('\n');
			functions[selection-1].run();
			showFullMenu = true;
		} while(selection != null);
	}
}
