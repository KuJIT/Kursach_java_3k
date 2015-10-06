package auction;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import auction.First.person;

public class Second extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2661813323188343104L;
	ArrayList<First.person> sellers;
	ArrayList<First.person> buyers;
	First parant;
	int fMinNotMore;
	int fMaxNotMore;
	int fMinPrice;
	int fMaxPrice;
	
	class MyColumns
	{
		ArrayList<person> arr;
		boolean falls; //если тру, то снижение
		
		
		MyColumns(ArrayList<person> arr, boolean falls)
		{
			this.arr = arr;
			this.falls = falls;
			Collections.sort(arr);
			doNormal();
		}
		
		void doNormal()
		{
			if (this.falls)
			{
				for(int i = 0; i < arr.size(); i++)
				{
					arr.get(i).price *= -1;
				}
				
				Collections.sort(arr);
			}
			
			for (int i = 0; i < arr.size() - 1; i++)
			{
				arr.get(i + 1).notMore += arr.get(i).notMore;
			}
			

			if(this.falls)
			{
				for(int i = 0; i < arr.size(); i++)
				{
					arr.get(i).price *= -1;
				}
				Collections.sort(arr);
			}
			
			boolean flag = true;
			
			while (flag)
			{
				for(int i = 0; i < arr.size(); i++)
				{
					flag = false;
					for(int j = i; j < arr.size(); j++)
					{
						if(arr.get(i).price == arr.get(j).price)
							if(!(arr.get(i).notMore == arr.get(j).notMore))
							{
								flag = true;
								int max = (arr.get(i).notMore > arr.get(j).notMore) ? arr.get(i).notMore : arr.get(j).notMore;
								arr.get(i).notMore = max;
								arr.get(j).notMore = max;
							}
					}
				}
			}
		}
		
		int getMaxPrice()
		{
			int res = this.arr.get(this.arr.size() - 1).price;		
			return res;
		}
		
		int getMinPrice()
		{
			int res = this.arr.get(0).price;
			return res;
		}
		
		int getMaxNotMore()
		{
			int res = this.arr.get(this.falls ? 0 : this.arr.size() - 1).notMore;		
			return res;
		}
		
		int getMinNotMore()
		{
			int res = this.arr.get((!this.falls) ? 0 : this.arr.size() - 1).notMore;		
			return res;
		}
		
		int getSize()
		{
			return arr.size();
		}
		
		person get(int i)
		{
			return this.arr.get(i);
		}
	}
	
	class Solut
	{
		MyColumns cSellers;
		MyColumns cBuyers;
		int[] Dc;
		MyResult res;
		
		
		class MyResult
		{
			int c1;
			int c2;
			int notMore;
			
			public MyResult(int c1, int c2, int notMore) 
			{
				this.c1 = c1;
				this.c2 = c2;
				this.notMore = notMore;
			}
			
			
		}

		Solut(MyColumns cSellers, MyColumns cBuyers)
		{
			this.cSellers = cSellers;
			this.cBuyers = cBuyers;
			this.Dc = new int[this.cSellers.getSize() + this.cBuyers.getSize()];
			setDC();
			this.res = this.getResult();
		}


		void setDC()
		{
			int i;
			for(i = 0; i < this.cSellers.getSize(); i++)
				this.Dc[i] = this.cSellers.get(i).price;
			for(int j = 0; j < this.cBuyers.getSize(); j++)
				this.Dc[i + j] = this.cBuyers.get(j).price;
			
			boolean flag = true;
			
			while(flag)
			{
				flag = false;
				for(i = 1; i < this.Dc.length; i++)
					if(this.Dc[i] < this.Dc[i - 1])
					{
						int buf = this.Dc[i];
						this.Dc[i] = this.Dc[i - 1];
						this.Dc[i - 1] = buf;
						flag = true;
					}
			}
			
		}
		
		int P(int c)
		{
			int res = 0;
			for(int i = 0; i < cSellers.getSize(); i++)
				if(cSellers.get(i).price <= c)
				{
					res = cSellers.get(i).notMore;
					//break;
				}
			return res;
		}
		
		int S(int c)
		{
			int res = 0;
			for(int i = 0; i < cBuyers.getSize() ; i++)
				if(cBuyers.get(i).price  >= c )
				{
					res = cBuyers.get(i).notMore;
					break;
				}
			return res;
		}
		
		int cP(int P)
		{
			int res = -1;
			for(int i = 0; i < cSellers.getSize(); i ++)
				if(cSellers.get(i).notMore == P)
					res = cSellers.get(i).price;
			return res;
		}
		
		int cS(int S)
		{
			int res = -1;
			for(int i = 0; i < cBuyers.getSize(); i++)
				if(cBuyers.get(i).notMore == S)
					res = cBuyers.get(i).price;
			return res;
		}
		
		int cSm(int S)
		{
			int res = -1;
			for(int i = 0; i < this.cBuyers.getSize(); i++)
				if(cBuyers.get(i).notMore == S)
					res = cBuyers.get(i - 1).price;
			return res;
		}
		
		int cPp(int P)
		{
			int res = -1;
			for(int i = 0; i < this.cSellers.getSize(); i ++)
				if(this.cSellers.get(i).notMore == P)
					res = cSellers.get(i + 1).price;
			return res;
		}
		
		MyResult getResult()
		{
			int c1 = -1;
			int c2 = -1;
			int notMore = -1;
			
			int PP = -1;
			int SS = -1;
			for(int i = 0; i < this.Dc.length; i++)
			{
				int c = this.Dc[i];
				if(P(c) <= S(c))
					if(PP < P(c))
						PP = P(c);
				if(S(c) <= P(c))
					if(SS < S(c))
						SS = S(c);				
			}
			
			if (!(SS == -1 || PP == -1))
			{			
				if(PP > SS)
				{
					c1 = cP(PP);
					c2 = cSm(SS);
					notMore = PP;
				}
				if(PP < SS)
				{
					c1 = cPp(PP);
					c2 = cS(SS);
					notMore = SS;
				}
				if (PP == SS)
				{
					c1 = cP(PP);
					c2 = cS(SS);
					notMore = SS;
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Ошибка алгоритма");
			}
			MyResult res = new MyResult(c1, c2, notMore);
			return res;
		}		
	}
	
	class GrPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -7080342822021453683L;
		MyColumns cSellers;
		MyColumns cBuyers;
		Solut res;
		
		final static int ots = 20;
		
		public GrPanel() 
		{
			this.cSellers = new MyColumns(Second.this.sellers, false);
			this.cBuyers = new MyColumns(Second.this.buyers, true);
			res = new Solut(cSellers, cBuyers);
			System.out.println("c1 = " + res.res.c1 + " c2 = " + res.res.c2 + " notMore = " + res.res.notMore);
		}


		
		@Override
		public void paintComponent(Graphics g)
		{
			Graphics2D gr = (Graphics2D)g;
			super.paintComponent(gr);
			setBackground(Color.WHITE);
			setOpacity(1);
			
			drowMyColumns(gr);
			drowDatumLines(gr);
		}
		
		int revY(int y_old)
		{
			int y_new = this.getHeight() - y_old;
			return y_new;
		}
		
		
		void drowMyColumns(Graphics2D g)
		{
			int MaxPrice = (this.cSellers.getMaxPrice() > this.cBuyers.getMaxPrice()) ? this.cSellers.getMaxPrice() : this.cBuyers.getMaxPrice();
			int MaxNotMore = (this.cSellers.getMaxNotMore() > this.cBuyers.getMaxNotMore()) ? this.cSellers.getMaxNotMore() : this.cBuyers.getMaxNotMore();
			int MinPrice = (this.cSellers.getMinPrice() < this.cBuyers.getMinPrice()) ? this.cSellers.getMinPrice() : this.cBuyers.getMinPrice();
			int MinNotMore = (this.cSellers.getMinNotMore() < this.cBuyers.getMinNotMore()) ? this.cSellers.getMinNotMore() : this.cBuyers.getMinNotMore();

			Second.this.fMaxNotMore = MaxNotMore;
			Second.this.fMinNotMore = MinNotMore;
			Second.this.fMinPrice = MinPrice;
			Second.this.fMaxPrice = MaxPrice;

			int rc1 = this.res.res.c1;
			int rc2 = this.res.res.c2;
			int rnotMore = this.res.res.notMore;
			
			int h = this.getHeight();
			int w = this.getWidth();
			
			double kh = (h - 4 * ots) / (MaxNotMore - MinNotMore);
			double kw = (w - 4 * ots) / (MaxPrice - MinPrice);
			
			g.setPaint(Color.BLACK);
			
			String family = "Lucida Sans Typewriter";
			int style = Font.PLAIN;
	        int size = 10;
	        Font font = new Font(family, style, size);
	        g.setFont(font);
	        FontMetrics fontMetrics = g.getFontMetrics();
	        
	        rnotMore  = Integer.valueOf(((Long)Math.round(kh * (rnotMore - MinNotMore) + ots)).intValue()) + ots;
			rc1 = Integer.valueOf(((Long)Math.round(kw * (rc1 - MinPrice) + ots)).intValue()) + ots;
			rc2 = Integer.valueOf(((Long)Math.round(kw * (rc2 - MinPrice) + ots)).intValue()) + ots;
			rnotMore = revY(rnotMore);
			
			g.setPaint(Color.GRAY);
			g.fillRect(rc1, rnotMore, rc2 - rc1, -rnotMore + revY(ots));
			g.setPaint(Color.BLACK);

			g.setColor(Color.RED);
			for(int i = 1; i < cBuyers.getSize(); i ++)
			{

				
				int v1 = Integer.valueOf(((Long)Math.round(kh * (cBuyers.get(i-1).notMore - MinNotMore) + ots)).intValue()) + ots;
				int v2 = Integer.valueOf(((Long)Math.round(kh * (cBuyers.get(i).notMore - MinNotMore) + ots)).intValue()) + ots;
				int c1 = Integer.valueOf(((Long)Math.round(kw * (cBuyers.get(i-1).price - MinPrice) + ots)).intValue()) + ots;
				int c2 = Integer.valueOf(((Long)Math.round(kw * (cBuyers.get(i).price - MinPrice) + ots)).intValue()) + ots;
				
				v1 = revY(v1);
				v2 = revY(v2);
				
				if(i == 1)
				{
					g.drawLine(ots, v1, c1, v1);
					g.drawString("" + cBuyers.get(i - 1).price, c1 + 2, revY(ots - 2) + fontMetrics.getAscent());
				}
				if(i == cBuyers.getSize() - 1)
					g.drawLine(c2, v2, c2, revY(ots));
				
				g.drawLine(c1, v1, c1, v2);
				g.drawLine(c1, v2, c2, v2);
				float[] dashl = {5,5};
				BasicStroke pen = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL,10,dashl,0);
				g.setStroke(pen);
				g.drawLine(c1, v2, c1, revY(ots));
				g.setStroke(new BasicStroke());	
				
				g.drawString("" + cBuyers.get(i).price, c2 + 2, revY(ots - 2) + fontMetrics.getAscent());
			}


			g.setColor(Color.BLUE);
	        for(int i = cSellers.getSize() - 2; i >= 0 ; i --)
			{	
				
				int v1 = Integer.valueOf(((Long)Math.round(kh * (cSellers.get(i+1).notMore - MinNotMore) + ots)).intValue()) + ots;
				int v2 = Integer.valueOf(((Long)Math.round(kh * (cSellers.get(i).notMore - MinNotMore) + ots)).intValue()) + ots;
				int c1 = Integer.valueOf(((Long)Math.round(kw * (cSellers.get(i+1).price - MinPrice) + ots)).intValue()) + ots;
				int c2 = Integer.valueOf(((Long)Math.round(kw * (cSellers.get(i).price - MinPrice) + ots)).intValue()) + ots;
				
				v1 = revY(v1);
				v2 = revY(v2);
				
				if(i == 0)
				{
					g.drawLine(c2, v2, c2, revY(ots));
				}
				if(i == cSellers.getSize() - 2)
				{
					g.drawLine(this.getWidth() - ots, v1, c1, v1);
					g.drawString("" + cSellers.get(i + 1).price, c1 + 2, revY(ots + 2));
				}
					
				
				g.drawLine(c1, v1, c1, v2);
				g.drawLine(c1, v2, c2, v2);
				float[] dashl = {5,5};
				BasicStroke pen = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL,10,dashl,0);
				g.setStroke(pen);
				g.drawLine(c1, v2, c1, revY(ots));
				g.setStroke(new BasicStroke());	
				
				g.drawString("" + cSellers.get(i).price, c2 + 2, revY(ots + 2));
			}

			int v1 = Integer.valueOf(((Long)Math.round(kh * (res.res.notMore - MinNotMore) + ots)).intValue()) + ots;
			//int v2 = Integer.valueOf(((Long)Math.round(kh * (cSellers.get(i).notMore - MinNotMore) + ots)).intValue()) + ots;
			int c1 = ots;
			int c2 = w - ots;
			v1 = revY(v1);






			float[] dashl = {5,5};
			BasicStroke pen = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL,10,dashl,0);
			g.setStroke(pen);
			g.setColor(Color.GREEN);
			g.drawLine(c1, v1, c2, v1 );

			g.setStroke(new BasicStroke());
			g.drawString("" + res.res.notMore, c1 + 2, v1 + 2);
			g.setColor(Color.BLACK);
			
		}
		
		void drowDatumLines(Graphics2D g)
		{
			//int ots = 20;
			int h = this.getHeight();
			int w = this.getWidth();
			g.setPaint(Color.BLACK);
			g.drawLine(0, revY(ots), w, revY(ots));
			g.drawLine(ots, revY(0), ots, revY(h));
			
			g.setColor(Color.BLACK);
			Polygon arrow = new Polygon();
			arrow.addPoint(w - ots/2, revY(ots*3/4));
			arrow.addPoint(w - ots/2, revY(ots + ots/4));
			arrow.addPoint(w, revY(ots));
			g.fillPolygon(arrow); 
			
			arrow = new Polygon();
			arrow.addPoint(ots*3/4, revY(h - ots/2));
			arrow.addPoint(ots + ots/4, revY(h - ots/2));
			arrow.addPoint(ots, revY(h));
			g.fillPolygon(arrow);		
		}
	}
	
	public Second(ArrayList<person> sellers, ArrayList<person> buyers, First parant)
	{
		super("Figure");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);
		
		this.sellers = sellers;
		this.buyers = buyers;
		this.parant = parant;
		
		this.initComponents();
	}

	void PrintResult(Solut res)
	{
		String StringMessage = "Результат: \n" +
				"Цена аукциона: ";
		if(res.res.c1 != res.res.c2)
			StringMessage += "[" + res.res.c1 + "; " + res.res.c2 + "]\n";
		else
			StringMessage += res.res.c1 + "\n";

		StringMessage += "Всего куплено/продано " + res.res.notMore + " шт. \n";
		StringMessage += "Покупатели: \n";

		for (person vv : res.cBuyers.arr)
		{
			if (vv.price >= res.res.c1)
			{
				int value = 0;
				if(vv.notMore > res.res.notMore)
					value = vv.offer - vv.notMore + res.res.notMore;
				else
					value = vv.offer;
				if (value > 0)
					StringMessage += vv.id + ": купил " + value + " шт. \n";
			}
		}

		StringMessage += "\nПродавцы: \n";
		for (person vv: res.cSellers.arr)
		{
			if (vv.price <= res.res.c2)
			{
				int value = 0;
				if (vv.notMore > res.res.notMore)
					value = vv.offer - vv.notMore + res.res.notMore;
				else
					value = vv.offer;
				if (value > 0)
					StringMessage += vv.id + ": продал " + value + " шт. \n";
			}
		}


		JOptionPane.showMessageDialog(null, StringMessage);
	}
	
	void initComponents()
	{
		//Первый уровень
		JPanel firstL = new JPanel();
		firstL.setLayout(new BorderLayout());
		
		//Второй уровень
		JPanel bufpanB = new JPanel();
		bufpanB.setLayout(new FlowLayout());
		GrPanel grPanel = new GrPanel();
		Solut res1 = grPanel.res;
		
		//Третий уровень
		JButton bufBut = new JButton("Просмотр изменений");
		bufBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Second.this.showChanges();
				super.mouseClicked(e);
			}
		});
		JButton bufBut1 = new JButton("Просмотр решения");
		bufBut1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Second.this.PrintResult(res1);
				super.mouseClicked(e);
			}
		});
			
		//Компоновка второй уровень
		bufpanB.add(bufBut);
		bufpanB.add(bufBut1);
		
		//Компоновка первый уровень
		firstL.add(grPanel, BorderLayout.CENTER);
		firstL.add(bufpanB, BorderLayout.SOUTH);
			
		getContentPane().add(firstL);
	
	}

	void changeStateCombo(JRadioButton rButtonS, JComboBox combo)
	{
		int value = (rButtonS.isSelected())?(sellers.size()):(buyers.size());
		combo.removeAllItems();
		for(int i = 1; i <= value; i++)
			combo.addItem("" + i);
	}

	void finishit(String str, Boolean isSeller)
	{

		int num = Integer.parseInt(str);

		for (int i = 0; i < 2 * fMaxPrice; i++)
		{
			//Solut bufSolut = new Solut()
		}

	}

	public void showChanges()
	{
		JFrame qvest = new JFrame();
		qvest.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel pan = new JPanel();
	//	pan.setLayout(new GridLayout(3,1,5,10));
		JButton bufBut = new JButton("Ok");

	//	JTextField bufTextField = new JTextField(4);
	//	pan.add(new JLabel("Введите \"sx\" или \"bx\" для выбора x-того продавца или покупателя соответственно"));
	//	pan.add(bufTextField);
	//	pan.add(bufBut);


		JComboBox combo = new JComboBox();

		Box box1 = Box.createVerticalBox();
		JRadioButton rButonS = new JRadioButton("Продавец");
		rButonS.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Second.this.changeStateCombo(rButonS, combo);
			}
		});
		JRadioButton rButonB = new JRadioButton("Покупатель");
		rButonB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Second.this.changeStateCombo(rButonS, combo);
			}
		});
		bufBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Second.this.finishit((String)combo.getSelectedItem(), rButonS.isSelected());
				super.mouseClicked(e);
			}
		});
		ButtonGroup bg = new ButtonGroup();
		bg.add(rButonB);
		bg.add(rButonS);
		box1.add(rButonB);
		box1.add(rButonS);



		pan.setLayout(new FlowLayout());

		pan.add(box1);
		pan.add(combo);
		pan.add(bufBut);

		qvest.setContentPane(pan);
		qvest.pack();
		qvest.setVisible(true);


	}
}
