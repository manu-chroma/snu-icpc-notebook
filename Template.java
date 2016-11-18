import java.util.*;
import java.io.*;

public class Template {

/*
	public static void main(String[] args) throws IOException {
		new Thread(null, new Runnable() {
			public void run() {
				new <ClassName>().run();
			}
		}, "Increase Stack", 1 << 25).start();

	}

	void run(){	
		// Code here
	}

*/

	public static void main(String[] args) throws IOException {
		FastScanner s1  = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false);
		
		
		
		in.close();
		out.close();
	}

	static class FastScanner {
		BufferedReader reader;
		StringTokenizer st;

		FastScanner(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream));
			st = null;
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					String line = reader.readLine();
					if (line == null) {
						return null;
					}
					st = new StringTokenizer(line);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
			return st.nextToken();
		}

		String nextLine() {
			String s = null;
			try {
				s = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return s;
		}

		int nextInt()  {return Integer.parseInt(next());}
		long nextLong(){return Long.parseLong(next());}
		double nextDouble() {return Double.parseDouble(next());}
	}

