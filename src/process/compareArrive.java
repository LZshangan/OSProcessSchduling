package process;

import java.util.Comparator;

public class compareArrive implements Comparator<PCB>{//������ʱ����������

	public int compare(PCB p1, PCB p2) {
		
		double r1=(double)(p1.getarriveTime());
		double r2=(double)(p2.getarriveTime());
		return r1>=r2?1:-1;
	}
	
}