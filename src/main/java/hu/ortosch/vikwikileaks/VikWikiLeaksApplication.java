package hu.ortosch.vikwikileaks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VikWikiLeaksApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(VikWikiLeaksApplication.class, args);
		System.out.println(
		        " __   __  ___   _  __ __      __  ___   _  __  ___     _      ___     _     _  __  ___ \n" +
		        " \\ \\ / / |_ _| | |/ / \\ \\    / / |_ _| | |/ / |_ _|   | |    | __|   /_\\   | |/ / / __|\n" + 
		        "  \\ V /   | |  | ' <   \\ \\/\\/ /   | |  | ' <   | |    | |__  | _|   / _ \\  | ' <  \\__ \\\n" + 
		        "   \\_/   |___| |_|\\_\\   \\_/\\_/   |___| |_|\\_\\ |___|   |____| |___| /_/ \\_\\ |_|\\_\\ |___/\n" +
		        " =====[ Init completed ]===============================================================\n" +
		        "  GitHub: https://github.com/Gerviba/vikwikileaks\n");
	}
}
