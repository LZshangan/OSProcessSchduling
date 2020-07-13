package process;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;


public class RRWindow {
	public RRWindow() {
	}
    private static JFrame frame = new JFrame("时间片轮转调度算法");
    
    private static JPanel panel = new JPanel();
    private static JPanel panel1 = new JPanel();
    //private static JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    //菜单组件
    private static JMenuBar menuBar = new JMenuBar();
    private static JMenu processSettingsMenu = new JMenu("菜单");
    private static JMenuItem RandomcreateProcessItem = new JMenuItem("随机创建");
    private static JMenuItem startRRItem = new JMenuItem("开始调度");
    private static JMenuItem stopRRItem = new JMenuItem("结束调度");
    private static JMenuItem HandcreateProcessItem = new JMenuItem("手动创建");
    private static JMenuItem exitSystemItem = new JMenuItem("退出");
    private static JMenu helpMenu = new JMenu("帮助");
    private static JMenuItem aboutItem = new JMenuItem("关于");

    //设置优先级最49)的队列的时间片大小默认单位：秒
    public static double timeSlice = 1;

    public static double PCBsQueuesTimeSlice[] = new double[5];

    //多级反馈队列
    public static PCBsQueue[] PCBsQueues = new PCBsQueue[5];

    //记录已经使用的pid
    public static int[] pidsUsed = new int[101];

    //当前内存中的进程
    public static int currentPCBsNum = 0;

    //内存中能够容纳的大进程数（这里取决于可分配的pid的个数）
    public static final int PCBS_MAX_NUM = 100;

    //是否停止调度
    public static boolean isStopScheduling;
    private final JButton btnNewButton = new JButton("返回");
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private static JTextArea textArea;
    public static  float nowTime=0;
    private final JLabel lblNewLabel_4 = new JLabel("  时钟");
    //private JEditorPane lblNewLabe_l,lblNewLabe_2,lblNewLabel_3;

	

    
    
    //很短的main函数
    public static void main(String[] args)
    {
        new RRWindow().initWindow();
    }



    //执行窗口初始
    public void initWindow()
    {
    	
    	
        //设置窗口风格为Windows风格
        setWindowsStyle();
        processSettingsMenu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));

        //创建菜单
        processSettingsMenu.add(RandomcreateProcessItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(startRRItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(stopRRItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(HandcreateProcessItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(exitSystemItem);
        helpMenu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));

        helpMenu.add(aboutItem);

        menuBar.add(processSettingsMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        initMemory();

        //panel.setSize(1500,700);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,panel1,panel); //这里第一个参数是控制分割线竖直，第二个参数是当你拖曳切割面版的分隔线时，窗口内的组件是否会随着分隔线的拖曳而动态改变大小，最后两个参数就是我分割完成后分割线两边各添加哪个容器。
        jSplitPane.setDividerLocation(300); //分割线的位置  也就是初始位置
        jSplitPane.setOneTouchExpandable(false); //是否可展开或收起，在这里没用
        jSplitPane.setDividerSize(2);//设置分割线的宽度 像素为单位
        jSplitPane.setEnabled(false); //设置分割线不可拖动！！
       
        frame.getContentPane().add(jSplitPane);  //加入到面板中就好了
        panel1.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("手动新建进程：");
        lblNewLabel.setBounds(79, 6, 140, 24);
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("1.请输入进程ID(1~100):");
        lblNewLabel_1.setBounds(38, 71, 220, 24);
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(89, 108, 106, 30);
        textField.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(textField);
        textField.setColumns(10);
        textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped(e);
				
			}
		});
        
       
        
        JLabel lblNewLabel_3 = new JLabel("3.请输入进程服务时间:");
        lblNewLabel_3.setBounds(37, 245, 210, 24);
        lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(lblNewLabel_3);
        
        textField_2 = new JTextField();
        textField_2.setBounds(89, 282, 106, 30);
        textField_2.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(textField_2);
        textField_2.setColumns(10);
        textField_2.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped2(e);
				
			}
		});
        
        JButton btnNewButton_1 = new JButton("添加");
        btnNewButton_1.setBounds(89, 468, 106, 52);
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		toAdd();
        	}
        });
        
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(btnNewButton_1);
        btnNewButton.setBounds(164, 533, 114, 65);
        
        btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}       	       
        });
        
        panel1.add(btnNewButton);
        
        JLabel lblNewLabel_2 = new JLabel("2.请输入进程到达时间：");
        lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 20));
        lblNewLabel_2.setBounds(38, 149, 227, 42);
        panel1.add(lblNewLabel_2);
        
        textField_3 = new JTextField();
        textField_3.setFont(new Font("宋体", Font.PLAIN, 20));
        textField_3.setBounds(89, 194, 106, 30);
        panel1.add(textField_3);
        textField_3.setColumns(10);
        lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 25));
        lblNewLabel_4.setBounds(89, 342, 93, 42);
        
        panel1.add(lblNewLabel_4);
        
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 25));
        textArea.setBounds(99, 398, 83, 42);
        panel1.add(textArea);
        textField_3.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped1(e);
				
			}
		});

        //frame.setContentPane(scrollPane);
        frame.setSize(2000, 700);
        frame.setBounds(100,100,1900,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        
        
        frame.setLocationRelativeTo(null);
      
        frame.setVisible(true);

        
        
        //为控件绑定监听器
        setComponentsListeners();
    }
    
    

   

	protected void keyTyped2(KeyEvent e) {
		// TODO Auto-generated method stub
		String input=textField_2.getText();
		if (!input.equals("")) {
			int num=Integer.parseInt(input);
			textField_2.setText(String.valueOf(num));
		}
	}



	protected void keyTyped1(KeyEvent e) {
		// TODO Auto-generated method stub
		String input=textField_3.getText();
		if (!input.equals("")) {
			int num=Integer.parseInt(input);
			textField_3.setText(String.valueOf(num));
		}
	}



	protected void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		String input=textField.getText();
		if (!input.equals("")) {
			int num=Integer.parseInt(input);
			textField.setText(String.valueOf(num));
		}
	}



	protected void toAdd() {
		// TODO Auto-generated method stub
		String input1=textField.getText();
		String input2=textField_3.getText();//到达时间
		String input3=textField_2.getText();
		int intNum1=0;
		if (!input1.equals("")) intNum1=Integer.parseInt(input1);
		int intNum2=0;
		if (!input2.equals("")) intNum2=Integer.parseInt(input2);
		int intNum3=0;
		if (!input3.equals("")) intNum3=Integer.parseInt(input3);
		
		if(currentPCBsNum == PCBS_MAX_NUM)
        {
            JOptionPane.showMessageDialog(frame,"The current memory space is full and cannot create a new process");
        }
        else if(pidsUsed[intNum1]==1)
        {
        	JOptionPane.showMessageDialog(null, "换个别的，搞快点！", "提示",JOptionPane.ERROR_MESSAGE);
        }
        else if(intNum3<0){
        	JOptionPane.showMessageDialog(null, "服务时间为0，运行个der？", "提示",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
        	currentPCBsNum++;

           

            pidsUsed[intNum1] = 1;

          
            float serveTime=intNum3;
            float SurplusServeTime=serveTime;

            PCB pcb = new PCB(intNum1, "UnArrive",intNum2, SurplusServeTime);

            pcb.setarriveTime(intNum2);
            pcb.setSurplusServeTime(SurplusServeTime);
            LinkedList<PCB> queue = PCBsQueues[3].getQueue();
            queue.offer(pcb);
            PCBsQueues[3].setQueue(queue);

            showPCBQueues(PCBsQueues);
        }
	}



	



	//设置Swing的控件显示风格为Windows风格
    public static void setWindowsStyle()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

    }

    //初始化相关内存参
    public static void initMemory()
    {
        currentPCBsNum = 0;

        Arrays.fill(pidsUsed, 1, 101, 0);

        for(int i = 0; i < PCBsQueues.length; i++)
        {
            PCBsQueues[i] = new PCBsQueue(i);
        }

        for(int i = PCBsQueuesTimeSlice.length - 1; i >= 0; i--)
        {
            //队列优先级每降一级，时间片加倍
            PCBsQueuesTimeSlice[i] = 1;
           // timeSlice = timeSlice+1;
        }
    }

    //给窗口中有控件绑定监听器
    public static void setComponentsListeners()
    {
        RandomcreateProcessItem.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
        RandomcreateProcessItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createProcess();
            }
        });


        startRRItem.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
        startRRItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startRRSimulation();
            }
        });

        stopRRItem.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK));
        stopRRItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                stopRR();
            }
        });

        HandcreateProcessItem.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));
        HandcreateProcessItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	HandcreateProcessItem();
            }
        });


        exitSystemItem.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK));
        exitSystemItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        aboutItem.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
        aboutItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame, "Operation System application (1.0 version)\n\nCopyright © 2020, LZ, All Rights Reserved.");
            }
        });

    }

    //创建新进
    public static void createProcess()
    {
        if(currentPCBsNum == PCBS_MAX_NUM)
        {
            JOptionPane.showMessageDialog(frame,"The current memory space is full and cannot create a new process");
        }
        else
        {
            currentPCBsNum++;

            int randomPid = 1 + (int)(Math.random() * ((100 - 1) + 1));

            while(pidsUsed[randomPid] == 1)
            {
                randomPid = 1 + (int)(Math.random() * ((100 - 1) + 1));
            }

            pidsUsed[randomPid] = 1;

            int randomPriority = 4;
            int randomArriveTime = 1 + (int)(Math.random() * ((10 - 1) + 1));
            float serveTime=1+(int)(Math.random()*10);
            float SurplusServeTime=serveTime;

            PCB pcb = new PCB(randomPid, "UnArrive",randomArriveTime , SurplusServeTime);

            pcb.setSurplusServeTime(SurplusServeTime);
            pcb.setarriveTime(randomArriveTime);
            LinkedList<PCB> queue = PCBsQueues[3].getQueue();
            queue.offer(pcb);
            PCBsQueues[3].setQueue(queue);

            showPCBQueues(PCBsQueues);
        }
    }

    //始调
    public static void startRRSimulation()
    {
        isStopScheduling = false;

        //更新界面操作必须借助多线程来实现
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //当前内存中还留有进程未执
                while(currentPCBsNum!=0 && !isStopScheduling)
                {                	                	                                                                                                                                      	
                	
                    LinkedList<PCB> queue1 = PCBsQueues[3].getQueue();//UnArrive队列
                                                           
                    LinkedList<PCB> queue3 = PCBsQueues[4].getQueue();//Running队列
                    
                    Collections.sort(queue1, new compareArrive());//对UnArrive队列按照到达时间升序排队
                                                                                               
                    textArea.setText(String.valueOf(nowTime));//用于在时钟对应位置展示此时的nowTime，即当前时间
                    boolean flag = true;//标志
                    while((queue1.size()>0)&&(flag)) {//UnArrive队列非空且flag为true
                    	
                    	flag=false;//设置flag为false
                    	  PCB pcb1 = queue1.element();//获取UnArrive队列第一个进程
                    	  if(pcb1.getarriveTime()<=nowTime) {//判断进程是否已经到达                      		 
                    		  queue1.poll();                 //若到达，则将进程从UnArrive队列删除      	
                    		  pcb1.setStatus("Ready");    //设置进程状态为Ready                    		 
                    		  queue3.offer(pcb1);            //将进程添加到Ready队列            		 
                    		  flag=true;                     //flag设为true，表示进程进入过该if选择语句
                    		 
                    	  }  //因为UnArrive队列已经按照到达时间排序，所以一旦UnArrive队列队列首个元素都未到达，后边进程不必再判断，无法进入if选择语句，flag无法变为true，while循环结束                                    	
                    }
                    PCBsQueues[3].setQueue(queue1);//将UnArrive队列放回队列组
                    PCBsQueues[4].setQueue(queue3);//将Running队列放回队列组
                    showPCBQueues(PCBsQueues);//展示进程剩余服务时间变化
                                                                                                                     	
                    if(queue3.size()>0) //Running队列不为空
                    {
                	                   	                   	   
                	    PCB pcb3=queue3.element();//获取Running队列第一个进程               	                  	    
                	    pcb3.setStatus("Running");
                	    PCBsQueues[3].setQueue(queue1);//将UnArrive队列放回队列组
     	                PCBsQueues[4].setQueue(queue3);//将Running队列放回队列组
     	                showPCBQueues(PCBsQueues);//展示进程剩余服务时间变化
     	                    
     	                int pid=pcb3.getPid();//获取进程ID
     	                   	     	                   	
     	                float temp3=pcb3.getSurplusServeTime()-1;//再减去等待时间，即为该进程的已运行时间     	                   	
     	                   	
     	                pcb3.setSurplusServeTime(temp3);//设置进程的剩余服务时间
     	                    
    	                try
    	                {   	                	
    	                    Thread.sleep((int)(1 * 5000));//每次循环sleep一个时间片的时间
    	                }
    	                catch (InterruptedException e)
    	                {
    	                    e.printStackTrace();
    	                }
     	                   	
     	                if(temp3<=0) {//剩余服务时间小于0，即运行结束
     	                   		
     	                   	queue3.poll();//删除该进程
     	                    pidsUsed[pid] = 0;//设置该进程ID未使用
     	                    currentPCBsNum--;//进程数减1
     	                               	                           
                	    }else {
                	    	pcb3.setStatus("Ready");
                	    	queue3.poll();//删除Running队列第一个进程
                	    	queue3.offer(pcb3);//添加至队尾
                	    	showPCBQueues(PCBsQueues);//展示进程剩余服务时间变化
                	    }
     	                    
                	   
                    }else {
                	   try
	                    {
	                        Thread.sleep((int)(1 * 5000));//每次循环sleep一个时间片的时间
	                    }
	                    catch (InterruptedException e)
	                    {
	                        e.printStackTrace();
	                    }
                   }
                   
                    PCBsQueues[3].setQueue(queue1);//将UnArrive队列放回队列组
                    PCBsQueues[4].setQueue(queue3);//将Running队列放回队列组
                    showPCBQueues(PCBsQueues);//展示进程剩余服务时间变化
                	   
                	
                    
                    nowTime=nowTime+1;//当前时间加1，表示时钟走过一秒
                    textArea.setText(String.valueOf(nowTime));//用于在时钟对应位置展示此时的nowTime，即当前时间
                                                                                                                                                                                 
                }

                
                initMemory();//初始化内存
                showPCBQueues(PCBsQueues);
                //有进程均执行完成，进程调度完
                JOptionPane.showMessageDialog(frame, "进程调度完成!");
                //frame.dispose();
            }
        }).start();

    }

    //强制结束进程调度
    public static void stopRR()
    {
        isStopScheduling = true;
        nowTime=0;
        textArea.setText(String.valueOf(nowTime));//用于在时钟对应位置展示此时的nowTime，即当前时间
        initMemory();
    }

    
    public static void HandcreateProcessItem()
    {
    	Object[] options ={ "拜拜！"};  
    	int m = JOptionPane.showOptionDialog(null, "想不到吧，这个我不会！", "哈哈",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);  
       
    }

    //显示内存中的多级反馈队列
    public static void showPCBQueues(PCBsQueue[] PCBsQueues)
    {
        int queueLocationY = 0;
        JPanel queuesPanel = new JPanel();
        

        for(int i = PCBsQueues.length - 1; i >= 3; i--) {
        
            LinkedList<PCB> queue = PCBsQueues[i].getQueue();

            if (queue.size() > 0)
            {
                //创建个PCB队列
                JPanel PCBsQueue = new JPanel();
                PCBsQueue.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                PCBsQueue.setLayout(new FlowLayout(FlowLayout.LEFT));
                PCBsQueue.setBounds(0, queueLocationY, 800, 700);

                queueLocationY += 50;

                
               if(i==4) {
            	   JLabel PCBsQueuePriorityLabel = new JLabel("Running: ");
                   PCBsQueuePriorityLabel.setOpaque(true);
                   PCBsQueuePriorityLabel.setBackground(Color.YELLOW);
                   PCBsQueuePriorityLabel.setForeground(Color.BLACK);

                   JPanel PCBsQueuePriorityBlock = new JPanel();
                   PCBsQueuePriorityBlock.add(PCBsQueuePriorityLabel);

                   PCBsQueue.add(PCBsQueuePriorityBlock);
               }
               else if(i==3) {
            	   JLabel PCBsQueuePriorityLabel = new JLabel("UnArrive: ");
                   PCBsQueuePriorityLabel.setOpaque(true);
                   PCBsQueuePriorityLabel.setBackground(Color.YELLOW);
                   PCBsQueuePriorityLabel.setForeground(Color.BLACK);

                   JPanel PCBsQueuePriorityBlock = new JPanel();
                   PCBsQueuePriorityBlock.add(PCBsQueuePriorityLabel);

                   PCBsQueue.add(PCBsQueuePriorityBlock);
               }

                for (PCB pcb : queue)
                {

                    //JLabel默认情况下是透明的所以直接设置背景颜色是无法显示的，必须将其设置为不透明才能显示背景

                    //设置pid标签
                    JLabel pidLabel = new JLabel("Pid: " + String.valueOf(pcb.getPid()));
                    pidLabel.setOpaque(true);
                    pidLabel.setBackground(Color.GREEN);
                    pidLabel.setForeground(Color.RED);
                    pidLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //设置status标签
                    JLabel statusLabel = new JLabel("Status: " + pcb.getStatus());
                    statusLabel.setOpaque(true);
                    statusLabel.setBackground(Color.GREEN);
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //设置priority标签
                    JLabel priorityLabel = new JLabel("ArrTime: " + String.valueOf(pcb.getarriveTime()));
                    priorityLabel.setOpaque(true);
                    priorityLabel.setBackground(Color.GREEN);
                    priorityLabel.setForeground(Color.RED);
                    priorityLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //设置life标签
                    JLabel lifeLabel = new JLabel("SSTime: " + String.valueOf(pcb.getSurplusServeTime()));
                    lifeLabel.setOpaque(true);
                    lifeLabel.setBackground(Color.GREEN);
                    lifeLabel.setForeground(Color.RED);
                    lifeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //绘制个PCB
                    JPanel PCBPanel = new JPanel();
                    PCBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    PCBPanel.setBackground(Color.BLUE);
                    PCBPanel.add(pidLabel);
                    PCBPanel.add(statusLabel);
                    PCBPanel.add(priorityLabel);
                    PCBPanel.add(lifeLabel);

                    //将PCB加入队列
                    PCBsQueue.add(new DrawLinePanel());
                    PCBsQueue.add(PCBPanel);
                }

                queuesPanel.add(PCBsQueue);
            }
        


        //设置queuesPanel中的有PCB队列（PCBsQueue组件）按垂直方向排列
        BoxLayout boxLayout = new BoxLayout(queuesPanel, BoxLayout.Y_AXIS);
        queuesPanel.setLayout(boxLayout);

        queuesPanel.setSize(800, 700);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.removeAll();
        panel.add(queuesPanel);
        panel.updateUI();
        panel.repaint();
      }
    }
}



