package com.rboomerang.app.sandbox.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class LongFileWriterSimulator {
	
	private static final Logger logger = Logger.getLogger(LongFileWriterSimulator.class);
	
	
	public void simulate(File file, long durationInMills){
		
		if(file.exists()){
			file.delete();
		}
		
		simulateNewFile(file,durationInMills);
		
	}

	private void simulateNewFile(File file, long durationInMills) {
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(file));
			
			long  duration = 0;
			int counter = 0;
			
			while(duration <= durationInMills){
				counter++;
				System.out.print(".");
				if(counter % 10 == 0)
					System.out.println();
				TimeUnit.SECONDS.sleep(5);
				writer.write("" + counter + " This is another line.....!!!! \n");
				writer.flush();
				duration += TimeUnit.SECONDS.toMillis(5);
			}
			writer.write("I am done!!!!");
			
			
		}catch(Exception ex){
			logger.error("exception ", ex);
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				logger.error("Cant close file ", e);
			}
		}
		 
		
	}
	
	
	public static void main(String[] args){
		File file = Paths.get("/Users/satish/tmp/s3test/test.txt").toFile();
		LongFileWriterSimulator simulator = new LongFileWriterSimulator();
		simulator.simulate(file, TimeUnit.MINUTES.toMillis(2));
	}

}
