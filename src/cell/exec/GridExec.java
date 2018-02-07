package cell.exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cell.data.Grid;

public class GridExec implements Runnable{
	
	private ExecutorService e;
	private Grid g;
	
	public GridExec(int threads, Grid g) {
		this.g = g;
		e = Executors.newFixedThreadPool(threads);
	
	}

	@Override
	public void run() {
		
	}

}
