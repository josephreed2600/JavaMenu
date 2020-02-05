package ml.jtreed.util;

import java.util.function.Function;
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

	private BufferedReader cin;// = new BufferedReader(new InputStreamReader(System.in));
	private PrintStream cout;

	public Menu(String preamble, String[] options, String prompt, Runnable[] functions, boolean exit, InputStream cinCore, PrintStream cout) {
		this.preamble = preamble;
		this.options = options;
		this.prompt = prompt;
		this.functions = functions;
		this.exit = exit;

		this.cin = new BufferedReader(new InputStreamReader(System.in));
		this.cout = cout;
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

	public void enter() {
		Integer selection = null;
		boolean showFullMenu = true;
		do {
			System.out.print(showFullMenu?this:this.getPrompt());
			try {
				cout.println("[DEBUG ]\tRequesting input");
				String input = cin.readLine();
				cout.println("[DEBUG ]\tGot input: " + input);
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
			if(selection > functions.length) {
				cout.println("Selection out of range");
				showFullMenu = false;
				continue;
			}
			functions[selection-1].run();
			showFullMenu = true;
		} while(selection == null || selection > 0);
	}

}
