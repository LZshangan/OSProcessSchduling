package process;


import java.util.LinkedList;

//���ƿ������
class PCBsQueue
{
    //��������
    private int priority;
    private LinkedList<PCB> queue = new LinkedList<PCB>();

    //private LinkedList<PCB> pcb = new LinkedList<PCB>();

    public PCBsQueue(int priority)
    {
        this.priority = priority;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public LinkedList<PCB> getQueue()
    {
        return queue;
    }

    public void setQueue(LinkedList<PCB> queue)
    {
        this.queue = queue;
    }
}
