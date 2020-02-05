package ml.jtreed.util;

import java.util.Scanner;
import java.io.*;

public class Controller {
	Menu menu;
	public Controller() {
		menu = new Menu(
			"",
			new String[]{
				"print foo",
				"print bar",
				"exit"
				},
			"> ",
			new Runnable[]{
				this::foo,
				this::bar
				},
			true);
	}

	public void run() {
		menu.enter(new BufferedReader(new InputStreamReader(System.in)),
				System.out);
	}



	private void foo() {
		System.out.println("foo!");
	}

	private void bar() {
		System.out.println("bar!");
	}

}
