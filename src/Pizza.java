public class Pizza {

	int w;
	int h;
	byte toppings[][];
	byte removedCells[][];
	int min_ing;
	int max_cell;
	
	public String toString() {
		String ret = "";
		for (byte[] row : toppings) {
			for (byte cell : row) {
				ret += cell == 1 ? 'T' : 'M';
			}
			ret += '\n';
		}
		
		ret += "\n";
		for (byte[] row : removedCells) {
			for (byte cell : row) {
				ret += cell == 1 ? 'X' : ' ';
			}
			ret += '\n';
		}
		return ret;
	}

	public boolean canSlice(int testX, int testY, int testW, int testH) {
		for (int x = testX; x < testX + testW; x++) {
			for (int y = testY; y < testY + testH; y++) {
				if (x >= w || y >= h) return false;
				if (removedCells[y][x] == 1) return false;
			}
		}
		
		return true;
	}

	public void slice(int testX, int testY, int testW, int testH) {
		for (int x = testX; x < testX + testW; x++) {
			for (int y = testY; y < testY + testH; y++) {
				removedCells[y][x] = 1;
			}
		}
	}

	public boolean hasBothToppings(int testX, int testY, int testW, int testH) {
		int mushrooms = 0;

		for (int x = testX; x < testX + testW; x++) {
			for (int y = testY; y < testY + testH; y++) {
				if (toppings[y][x] == 1) mushrooms++;
			}
		}
		
		// Has minimum number of mushroom and of not mushrooms
		return (mushrooms >= min_ing && (testW * testH - mushrooms) >= min_ing);
	}

}