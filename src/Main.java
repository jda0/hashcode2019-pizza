import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.err.println("Input filename parameter expected!");
			return;
		}

		File file = new File(args[0]);
		if (!file.exists()) {
			System.err.println("Specified file does not exist!");
			return;
		}

		// Parse input file
		System.out.println("Parsing input...");
		InputStream iis = new FileInputStream(file);
		Pizza pizza;
		try (DataInputStream is = new DataInputStream(iis)) {
			Pattern regex = Pattern.compile("(\\d+) (\\d+) (\\d+) (\\d+)");
			Matcher m = regex.matcher(is.readLine());
			m.find();

			pizza = new Pizza();
			pizza.h = Integer.parseInt(m.group(1));
			pizza.w = Integer.parseInt(m.group(2));
			pizza.min_ing = Integer.parseInt(m.group(3));
			pizza.max_cell = Integer.parseInt(m.group(4));

			pizza.toppings = new byte[pizza.h][];
			pizza.removedCells = new byte[pizza.h][];
			
			for (int i = 0; i < pizza.h; i++) {
				byte[] line = is.readLine().getBytes();
				pizza.toppings[i] = new byte[pizza.w];
				pizza.removedCells[i] = new byte[pizza.w];
				for (int j = 0; j < pizza.w; j++) {
					if (line[j] == 'T') {
						pizza.toppings[i][j] = 1;
					}
				}
			}
		}
		
		// Get the possible slice sizes in order of size
		System.out.println("Finding possible pizza slice sizes...");
		ConcurrentLinkedQueue<Pair> pairs = new ConcurrentLinkedQueue<>();
		for (int w = 1; w < pizza.max_cell; w++) {
			for (int h = (pizza.max_cell / w); h >= 1; h--) {
				pairs.add(new Pair(w, h));
			}
		}
// 		Collections.sort(pairs, (p1, p2) -> p2.w * p2.h - p1.w * p1.h);

		// Find where each slice size can be cut in pizza, starting with largest
		System.out.println("Locating pizza slices...");
		ConcurrentLinkedQueue<String> results = new ConcurrentLinkedQueue<>();
		long start = System.currentTimeMillis();

		pairs.forEach((pair) -> {
			for (int x = 0; x + pair.w < pizza.toppings[0].length; x++) {
				for (int y = 0; y + pair.h < pizza.toppings.length; y++) {
					if (pizza.canSlice(x, y, pair.w, pair.h) && pizza.hasBothToppings(x, y, pair.w, pair.h)) {
						pizza.slice(x, y, pair.w, pair.h);
						results.add(String.format("%d %d %d %d", y, x, y+pair.h-1, x+pair.w-1));
					}
				}
			}
		});

		// Output results
		System.out.println("Found a result in " + (System.currentTimeMillis() - start) + "ms");
		System.out.println("Result has " + results.size() + " slices! Saving to \"output.txt\"");

		file = new File("output.txt");
		file.delete();
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
		dos.write((results.size() + "\n").getBytes());
		for (String str : results) dos.write((str + "\n").getBytes());
		dos.flush();
		dos.close();
	}

}
