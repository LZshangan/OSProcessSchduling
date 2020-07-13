package process;

import java.util.Comparator;

public class compareHRR implements Comparator<PCB>{//∞¥œÏ”¶±»Ωµ–Ú≈≈–Ú

	float nowTime=HRRNWindow.nowTime;
	//float nowTime;
	public int compare(PCB o1, PCB o2) {
		
		//float nowTime = 0;
		double r1=(double)(nowTime-o1.getarriveTime()+o1.getserveTime())/o1.getserveTime();
		double r2=(double)(nowTime-o2.getarriveTime()+o2.getserveTime())/o2.getserveTime();
		return r1<r2?1:-1;
	}
	
}
