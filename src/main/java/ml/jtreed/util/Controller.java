package ml.jtreed.util;

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
			true,
			System.in,
			System.out);
	}

	public void run() {
		//System.out.print(menu);
		menu.enter();
	}



	private void foo() {
		System.out.println("foo!");
	}

	private void bar() {
		System.out.println("bar!");
	}

}
