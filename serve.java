package lugang_lab1;

import java.io.*;
import java.util.*;

public class serve {
	public static void main(String[] args) throws IOException {

		File file1 = new File("D:\\PROCESS.txt");
		File file2 = new File("D:\\REQ.txt");
		File file3 = new File("D:\\Service.txt");
		getins(file3);
		getinp(file1);
		getinr(file2);
		result();
	}

	public static final class type {
		public String name = new String();
		public double re = 1;
		public double pr = 0;
		public double q = 0;
	}

	public static type[][] st = new type[14][500];
	static {
		for (int i = 0; i < 14; i++)
			for (int j = 0; j < 500; j++)
				st[i][j] = new type();
	}

	public static String[] ss = new String[4];
	static {
		for (int i = 0; i < 4; i++)
			ss[i] = new String();
	}
	
	public static String[] sss = new String[4];
	static {
		for(int i=0; i<4; i++)
			sss[i] = new String();
	}

	public static void getins(File file) throws IOException {
		Vector<String> v = new Vector<String>();
		FileInputStream fin = new FileInputStream(file);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));

		String line = null;
		while ((line = buffReader.readLine()) != null)
			v.add(line);
		//System.out.println(v);

		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 500; j++) {
				String[] str = v.elementAt(i*500+j).split(" ");
				st[i][j].name = str[0];
				st[i][j].re = Double.parseDouble(str[2]);
				st[i][j].pr = Double.parseDouble(str[4]);
				st[i][j].q = st[i][j].re - st[i][j].pr / 100;
			}
		}
	}

	public static void getinp(File file) throws IOException {
		FileInputStream fin = new FileInputStream(file);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		
		String s;
		for (int i = 0; i < 4; i++) {
			s = buffReader.readLine();
			sss[i] = s;
			List<String> data = new ArrayList<String>();
			for (int j = 0; j < s.length(); j++) {
				String t = s.substring(j, j + 1);
				if ((!data.contains(t)) && (!t.equals(",")) && (!t.equals("(")) && (!t.equals(")"))) {
					data.add(t);
				}
			}
			for (String t : data) {
				ss[i] += t;
			}
			//System.out.println(ss[i]);
		}
	}

	public static class treq {
		public double mlre;
		public double hpr;
	}

	static treq[] t = new treq[4];
	static {
		for(int i=0; i<4; i++)
			t[i] = new treq();
	}
	
	public static void getinr(File file) throws IOException {
		FileInputStream fin = new FileInputStream(file);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));

		for (int i = 0; i < 4; i++) {
			String s = buffReader.readLine();
			t[i].mlre = Double.parseDouble(s.substring(1, 4));
			t[i].hpr = Double.parseDouble(s.substring(5, 7));
			//System.out.println(t[i].mlre + " " + t[i].hpr);
		}
	}

	public static void result() throws IOException {
		type[] result = new type[14];
		for (int i = 0; i < 14; i++) {
			result[i] = new type();
			for (int j = 0; j < 500; j++)
				if (st[i][j].q > result[i].q)
					result[i] = st[i][j];
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\11.txt"));
		for(int i=0; i<4; i++) {
			for(int j=0; j<ss[i].length(); j++) {
				for(int k=0; k<14; k++) {
					if(ss[i].charAt(j) == result[k].name.charAt(0))
						sss[i] = sss[i].replaceAll("" + result[k].name.charAt(0), result[k].name);
				}
			}
		}
		
		for(int i=0; i<4; i++) {
			double ar = 1, ac = 0, aq = 0;
			for(int j=0; j<ss[i].length(); j++) {
				int index = ss[i].charAt(j) - 'A';
				ar *= result[index].re;
				ac += result[index].pr;
			}
			aq = ar - ac / 100; 
			bw.write(sss[i] + " " + "Reliability=" + ar + ",Cost=" + ac + ",Q=" + aq);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}