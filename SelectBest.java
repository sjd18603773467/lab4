package lab1;

import java.util.*;
import java.io.*;

public class SelectBest {
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		
		File file_process = new File("D:\\PROCESS.txt");
		File file_req = new File("D:\\REQ.txt");
		File file_result = new File("D:\\RESULT.txt");
		Scanner scp = new Scanner(file_process);
		Scanner scr = new Scanner(file_req);
		PrintStream ps = new PrintStream(file_result);
		Set<String> ss = new HashSet<String>();
		LinkedList ll = new LinkedList();
		while(scp.hasNext()) {
			String sp = scp.next();
			String[] asr = scr.next().split("\\(");
			asr = asr[1].split("\\)");
			asr = asr[0].split(",");
			float min_reliability = Float.parseFloat(asr[0]);
			float min_cost = Float.parseFloat(asr[1]);
			
			for(int i=0; i<sp.length(); i++)
				if(Character.isLetter(sp.charAt(i)) && ss.add("" + sp.charAt(i)))
					ll.add(FileHandle.readService(sp.charAt(i), min_cost));
			
			LinkedList llr = meet_demand(ll, min_reliability, min_cost);
			Iterator it = llr.iterator();
			while(it.hasNext())
				((ASolution) it.next()).cal_q();
			Collections.sort((List) llr);
			//print_LinkedList(llr);
			ASolution as_result = (ASolution) llr.getFirst();
			//System.out.println(as_result);
			
			String s_result = new String(sp);
			for(int i=sp.length()-1; i>=0; i--)
				if(Character.isLetter(sp.charAt(i))) {
					LinkedList ll_result = as_result.ll;
					Iterator it_result = ll_result.iterator();
					while(it_result.hasNext()) {
						ServerData sd = (ServerData) it_result.next();
						if(sd.name.charAt(0) == sp.charAt(i)) {
							s_result = s_result.substring(0, i) + sd.name + s_result.substring(i+1);
							break;
						}
					}
				}
			s_result += " Reliability=" + as_result.all_reliability + ",Cost=" + as_result.all_cost + ",Q=" + as_result.q;
			ps.println(s_result);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("It costs time " + (endTime - startTime) + "ms.");
	}
	
	private static LinkedList meet_demand(LinkedList ll, float min_reliability, float min_cost) {
		LinkedList llr = new LinkedList();
		llr.add(new ASolution());
		Iterator it_ll = ll.iterator();
		while(it_ll.hasNext()) {
			LinkedList sll = (LinkedList) it_ll.next();
			Iterator it_llr = llr.iterator();
			LinkedList tllr = new LinkedList();
			while(it_llr.hasNext()) {
				ASolution as = (ASolution) it_llr.next();
				Iterator it_sll = sll.iterator();
				while(it_sll.hasNext()) {
					ServerData sd = (ServerData) it_sll.next();
					float new_reliability = as.all_reliability * sd.reliability;
					float new_cost = as.all_cost + sd.cost;
					if(new_cost <= min_cost && new_reliability >= min_reliability) {
						ASolution tas = new ASolution(new_reliability, new_cost, as.ll);
						tas.ll.add(sd);
						tllr.add(tas);
					}
				}
			}
			improve(tllr, min_reliability, min_cost);
			llr = tllr;
		}
		return llr;
	}
	
	private static void improve(LinkedList ll, float min_reliability, float min_cost) {
		Collections.sort((List)ll);
		Iterator it = ll.iterator();
		float mc = min_cost;
		while(it.hasNext()) {
			ASolution llr = (ASolution) it.next();
			if(llr.all_cost >= mc || llr.all_reliability < min_reliability)
				it.remove();
			else
				mc = llr.all_cost;
		}
	}
	
	//for test
	private static void print_LinkedList(LinkedList ll) {
		Iterator it = ll.iterator();
		while(it.hasNext()) {
			//LinkedList lr = (LinkedList) it.next();
			ASolution as = (ASolution) it.next();
			Iterator itr = as.ll.iterator();
			while(itr.hasNext())
				System.out.print(itr.next());
			System.out.println(as.all_reliability);
			System.out.println(as.all_cost);
			System.out.println(as.q);
			System.out.println();
		}
	}
}

class FileHandle {
	public static LinkedList readService(char c, float min_cost) throws IOException {
		LinkedList ll = new LinkedList();
		File file = new File("D:\\SERVICE.txt");
		Scanner sc = new Scanner(file);
		while(sc.hasNext()) {
			String s = sc.next();
			if(s.charAt(0) == c) {
				ServerData sd = new ServerData();
				sd.name = s;
				sc.next();
				sd.reliability = sc.nextFloat();
				sc.next();
				sd.cost = sc.nextFloat();
				ll.add(sd);
			}
		}
		improve(ll, min_cost);
		return ll;
	}
	
	private static void improve(LinkedList ll, float min_cost) {
		Collections.sort((List)ll);
		Iterator it = ll.iterator();
		float mc = min_cost;
		while(it.hasNext()) {
			ServerData llr = (ServerData) it.next();
			if(llr.cost >= mc)
				it.remove();
			else
				mc = llr.cost;
		}
	}
}

class ASolution implements Comparable {
	public float all_reliability = 1;
	public float all_cost = 0;
	public float q = 0;
	public LinkedList ll = new LinkedList();
	
	ASolution() {
		;
	}
	ASolution(float reliability, float cost, LinkedList ll) {
		this.all_reliability = reliability;
		this.all_cost = cost;
		Iterator it = ll.iterator();
		while(it.hasNext())
			this.ll.add(it.next());
	}
	
	public void cal_q() {
		q = all_reliability - all_cost / 100;
	}
	
	public int compareTo(Object as) {
		if(this.q > ((ASolution)as).q)
			return -1;
		else if(this.q <((ASolution)as).q)
			return 1;
		
		if(this.all_reliability > ((ASolution)as).all_reliability)
			return -1;
		else if(this.all_reliability < ((ASolution)as).all_reliability)
			return 1;

		if(this.all_cost < ((ASolution)as).all_cost)
			return -1;
		else
			return 1;
	}
	public String toString() {
		String s = new String();
		Iterator it = this.ll.iterator();
		while(it.hasNext())
			s += (ServerData) it.next();
		s += "reliability: " + this.all_reliability + "\n";
		s += "cost: " + this.all_cost + "\n";
		s += "quality: " + this.q + "\n";
		return s;
	}
}

class ServerData implements Comparable {
	public String name = null;
	public float reliability = 0;
	public float cost = 0;
	
	public int compareTo(Object sd) {
		if(this.reliability > ((ServerData)sd).reliability)
			return -1;
		else if(this.reliability < ((ServerData)sd).reliability)
			return 1;
		
		if(this.cost < ((ServerData)sd).cost)
			return -1;
		else
			return 1;
	}
	
	//for test
	public String toString() {
		return this.name + " " + this.reliability + " " + this.cost + "\n";
	}
}