package process;



//���̿��ƿ���
public class PCB
{
    //���̱�ʶ
    private int pid;

    //����״
    private String status;

    //��������
    private int priority;

    //������������
    //private int life;
    
   
    private float serveTime;

    private float arriveTime;
    
    private float waitTime;
    
    private float SurplusServeTime;
    public PCB()
    {
    }

    public PCB(int pid, String status, int priority, float serveTime)
    {
        this.pid = pid;
        this.status = status;
        this.priority = priority;
        this.serveTime = serveTime;
        this.SurplusServeTime=SurplusServeTime;
        this.arriveTime=arriveTime;
        this.waitTime=waitTime;
        //this.life = life;
    }
    
   
    public float getSurplusServeTime(){
    	return SurplusServeTime;
    }

    public void setSurplusServeTime(float SurplusServeTime)
    {
        this.SurplusServeTime = SurplusServeTime;
    }
    
    public int getPid()
    {
        return pid;
    }

    public void setPid(int pid)
    {
        this.pid = pid;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    
    
    public float getwaitTime()
    {
        return waitTime;
    }
    
    public void setwaitTime(float temp)
    {
        this.waitTime = temp;
    }
    
    public float getarriveTime()
    {
        return arriveTime;
    }
    
    public void setarriveTime(float arriveTime2)
    {
        this.arriveTime = arriveTime2;
    }
    
    public float getserveTime()
    {
        return serveTime;
    }
    
    public void setserveTime(float serveTime)
    {
        this.serveTime = serveTime;
    }
    
}

