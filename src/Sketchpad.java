// chap_10\Ex_7.java
// drawing program
// program to demonstrate a graphical user interface for drawing shapes

import java.awt.*;
import java.awt.event.*;

class Sketchpad extends Frame implements 
		ActionListener,		// menu item
		ItemListener,		// radio buttons
		MouseListener,		// pressing/releasing a mouse button
		MouseMotionListener,	// dragging or moving a mouse
		WindowListener		// closing a window
{
	// size of Sketchpad area
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
		
	// coordinate of upper-left hand corner of a rectangle
	int upperLeftX, upperLeftY;
	// size of surrounding rectangle
	int width, height;
	// coordinates of two selected points
	int x1,y1,x2,y2;

	// set/reset flags to indicate filling and erasing
	boolean fill = false;
	boolean erasure = false;
		
	// chosen color and shape - initialized to default values
	String drawColor = new String("black");
	String drawShape = new String("line");
	
	// text fields for displaying the chosen color, shape
	// and the coordinate position of the mouse
	TextField color = new TextField();
	TextField shape = new TextField();
	TextField position = new TextField();

	CheckboxGroup fillOutline = new CheckboxGroup();
	
	// contents of the drop-down menus stored in arrays
	String[] colorNames = 
	{"black","blue","cyan","gray","green","magenta","red","white","yellow"};
	String[] shapeNames = {"line","square","rectangle","circle","ellipse"};
	String[] toolNames  = {"erase","clearpad"};
	String[] helpNames  = {"information","about"};

	// information about using Sketchpad
	String helpText =
	"Sketchpad allows you to draw different plane shapes over a predefined area.\n"+
	"A shape may be either filled or in outline, and in one of eight different colors.\n\n"+
	"The position of the mouse on the screen is recorded in the bottom left-hand \n"+
	"corner of the sketchpad. The choice of color and shape are displayed also in \n"+
	"the left-hand corner of the sketchpad\n\n"+
	"The size of a shape is determined by the position of the mouse when the mouse \n"+
	"button is first pressed, followed by the mouse being dragged to the final position\n"+
	"and released. The first press of the mouse button will generate a reference dot \n"+
	"on the screen. This dot will disappear after the mouse button is released\n\n"+
	"Both the square and circle only use the distance measured along the horizontal \n"+
	"axis when determining the size of the shape.\n\n"+
	"Upon selecting erase, press the mouse button, and move the mouse over the area\n"+
	"to be erased. Releasing the mouse button will deactivate erasure\n\n"+
	"To erase this text area choose clearpad from the TOOLS menu\n\n";

	// componet for displaying information
	TextArea info = new 
	TextArea(helpText,0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
	// component for displaying information about product
	Frame about = new Frame("About Sketchpad");

	//---------------------------------------------------------------------
	//helper methods
	private void initializeTextFields()
	{
		// add textfield to show color of figure
		color.setLocation(5,525);
		color.setSize(70,20);
		color.setBackground(Color.white);
		color.setText(drawColor);
		add(color);		
	
		// add textfield to show shape of figure
		shape.setLocation(5,550);
		shape.setSize(70,20);
		shape.setBackground(Color.white);
		shape.setText(drawShape);
		add(shape);

		// add textfield to show position of mouse
		position.setLocation(5,575);
		position.setSize(70,20);
		position.setBackground(Color.white);
		add(position);

		// set up textfield for information
		info.setLocation(150,250);
		info.setSize(500,100);
		info.setBackground(Color.white);
		info.setEditable(false);
	}

	private void initializeMenuComponents()
	{
		// add pull-down menu to menu bar
		MenuBar bar = new MenuBar();

		// add colours menu		
		Menu colors = new Menu("COLORS");
		for (int index=0; index != colorNames.length; index++)
			colors.add(colorNames[index]);
		bar.add(colors);
		colors.addActionListener(this);

		// add shapes menu
		Menu shapes = new Menu("SHAPES");
		for (int index=0; index != shapeNames.length; index++)
			shapes.add(shapeNames[index]);
		bar.add(shapes);
		shapes.addActionListener(this);		

		// add tools menu
		Menu tools = new Menu("TOOLS");
		for (int index=0; index != toolNames.length; index++)
			tools.add(toolNames[index]);
		bar.add(tools);
		tools.addActionListener(this);
	
		// add help menu
		Menu help = new Menu("HELP");
		for (int index=0; index != helpNames.length; index++)
			help.add(helpNames[index]);
		bar.add(help);
		help.addActionListener(this);

		setMenuBar(bar);
	}

	//-----------------------------------------------------------------


	private void initializeRadioButtons()
	{
		// add radio buttons
		Checkbox fill = new Checkbox("fill", fillOutline, false);
		Checkbox outline = new Checkbox("outline",fillOutline,true);

		fill.setLocation(5,500);
		fill.setSize(70,20);
		add(fill);
		fill.addItemListener(this);

		outline.setLocation(5,475);
		outline.setSize(70,20);
		add(outline);
		outline.addItemListener(this);
	}	

	
	// constructor
	public Sketchpad(String s)
	{
		super(s);
		setBackground(Color.yellow);	
                	setLayout(null);

		initializeTextFields();
		initializeMenuComponents();
		initializeRadioButtons();		
		
		// set up remaining listeners
		addMouseMotionListener(this);
		addMouseListener(this);
		addWindowListener(this);
	}

	//-----------------------------------------------------------------

	public void actionPerformed(ActionEvent event)
	// method to detect which item is chosen from a menu
	{
		Graphics g = getGraphics();
		Object source = event.getActionCommand();

		// check for color chosen
		for (int index=0; index != colorNames.length; index++)
			if (source.equals(colorNames[index]))
			{
				drawColor =colorNames[index];
				color.setText(drawColor);
				return;
			}

		// check for shape chosen
		for (int index=0; index != shapeNames.length; index++)
			if (source.equals(shapeNames[index]))
			{
				drawShape = shapeNames[index];
				shape.setText(drawShape);
				return;
			}

		// check for tools chosen
		if (source.equals("erase"))
		{
			erasure = true;
			return;
		}
		else if (source.equals("clearpad"))
		{
			remove(info);
			g.clearRect(0,0,800,600);
			return;
		}


		// check for help chosen
		if (source.equals("information"))
		{
			add(info);
			return;
		}
		else if (source.equals("about"))
		{
			displayAboutWindow(about);
		}
	}

	//-----------------------------------------------------------------

	public void itemStateChanged(ItemEvent event)
	// method to detect which radio button was pressed
	{
		if (event.getItem() == "fill")
			fill = true;
		else if (event.getItem() == "outline")
			fill = false;
	}

	//-----------------------------------------------------------------

	protected void displayAboutWindow(Frame about)
	// method to display information about Sketchpad
	// in new window
	{
		about.setLocation(300,300);
		about.setSize(200,150);
		about.setBackground(Color.cyan);
		about.setFont(new Font("Serif",Font.ITALIC,14));
		about.setLayout(new FlowLayout(FlowLayout.LEFT));

		about.add(new Label("Author: Barry Holmes"));
		about.add(new Label("Title: Sketchpad"));
		about.add(new Label("Date: 1998"));
		about.add(new Label("Use: Demonstration only"));
		about.setVisible(true);
		about.addWindowListener(this);
	}

	//-----------------------------------------------------------------

	protected void selectColor(Graphics g)
	// method to change color of graphic to correspond
	// with chosen menu item
	{
		for (int index=0; index != colorNames.length; index++)
		{
			if (drawColor.equals(colorNames[index]))
			{
				switch (index)
				{
					case 0: g.setColor(Color.black);break;
					case 1: g.setColor(Color.blue);break;
					case 2: g.setColor(Color.cyan);break;
					case 3: g.setColor(Color.gray);break;
					case 4: g.setColor(Color.green);break;
					case 5: g.setColor(Color.magenta);break;
					case 6: g.setColor(Color.red);break;
					case 7: g.setColor(Color.white);break;
					case 8: g.setColor(Color.yellow);
				}
			}
		}	
	}

	//------------------------------------------------------------------

	protected void closedShapes(Graphics g, String shape, boolean fill)
	// method to draw a closed shape with the correct orientation
	{
		// calculate correct parameters for shape
		upperLeftX = Math.min(x1,x2);
		upperLeftY = Math.min(y1,y2);
		width = Math.abs(x1-x2);
		height = Math.abs(y1-y2);

		// draw appropraite shape
		if (shape.equals("square") && fill)
			g.fillRect(upperLeftX,upperLeftY,width,width);
		else if (shape.equals("square") && !fill)
			g.drawRect(upperLeftX,upperLeftY,width,width);

		else if (shape.equals("rectangle") && fill)
			g.fillRect(upperLeftX,upperLeftY,width,height);
		else if (shape.equals("rectangle") && !fill)
			g.drawRect(upperLeftX,upperLeftY,width,height);

		else if (shape.equals("circle") && fill)
			g.fillOval(upperLeftX,upperLeftY,width,width);
		else if (shape.equals("circle") && !fill)
			g.drawOval(upperLeftX,upperLeftY,width,width);

		else if (shape.equals("ellipse") && fill)
			g.fillOval(upperLeftX,upperLeftY,width,height);
		else if (shape.equals("ellipse") && !fill)
			g.drawOval(upperLeftX,upperLeftY,width,height);	
	}	

	//------------------------------------------------------------------

	protected void displayMouseCoordinates(int X, int Y)
	// method to display the coordinates of the mouse
	{
		position.setText("["+String.valueOf(X)+","+String.valueOf(Y)+"]");
	}

	



	public void mouseDragged(MouseEvent event)
	// capture coordinates of new mouse position as it is 
	// dragged across the screen
	{
		Graphics g = getGraphics();
		
		x2=event.getX();
		y2=event.getY();		
		
		// erase a small rectangular area of the window
		// if erase was selected
		if (erasure)
		{
			g.setColor(Color.yellow);
			g.fillRect(x2,y2,5,5);
		}

		displayMouseCoordinates(event.getX(), event.getY());		
	}

	//----------------------------------------------------------------

	public void mouseMoved(MouseEvent event)
	{
		displayMouseCoordinates(event.getX(), event.getY());
	}

	//----------------------------------------------------------------
	// implemented blank methods
	public void mouseClicked(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseExited(MouseEvent event){}	

	//----------------------------------------------------------------
	public void mousePressed(MouseEvent event)
	// capture initial coordinates of mouse
	{
		if (erasure) return;

		upperLeftX=0; upperLeftY=0; width=0; height=0;
		
		x1=event.getX(); 
		y1=event.getY();

		Graphics g = getGraphics();

		// display reference point of coordinates (x1,y1)
		g.drawString(".",x1,y1);
		displayMouseCoordinates(event.getX(), event.getY());
	}







	public void mouseReleased(MouseEvent event)
	// draw the appropriate shape when mouse button released;
	// shape will be drawn between the coordinates (x1,y1) and (x2,y2)
	{
		Graphics g = getGraphics();	
		displayMouseCoordinates(event.getX(), event.getY());
		if (erasure)
		{
			erasure = false;
			return;
		}
		selectColor(g);
		x2=event.getX();
		y2=event.getY();		
		
		if (drawShape.equals("line"))
		{
			g.drawLine(x1,y1,x2,y2);
		}		
		else if (drawShape.equals("circle"))
		{
			closedShapes(g,"circle",fill);		
		}
		else if (drawShape.equals("ellipse"))
		{
			closedShapes(g,"ellipse",fill);
		}
		else if (drawShape.equals("square"))
		{
			closedShapes(g,"square",fill);
		}
		else if (drawShape.equals("rectangle"))
		{
			closedShapes(g,"rectangle",fill);
		}		

		// erase reference point at coordinates (x1,y1)
		g.setColor(Color.yellow);
		g.drawString(".",x1,y1);
		g.setColor(Color.black);
	}

	//------------------------------------------------------------------	
	//	implemented blank methods
	public void windowClosed(WindowEvent event){}
	public void windowDeiconified(WindowEvent event){}
	public void windowIconified(WindowEvent event){}
	public void windowActivated(WindowEvent event){}
	public void windowDeactivated(WindowEvent event){}
	public void windowOpened(WindowEvent event){}

	//------------------------------------------------------------------

	public void windowClosing(WindowEvent event)
	// method to check which window was closing
	{
		if (event.getWindow() == about)
		{
			about.dispose();
			return;
		}
		else
		{	
			System.exit(0);
		}
	}
}


class Sketch
{
	public static void main(String[] args)
	{
		Sketchpad screen = new Sketchpad("Sketchpad");
		screen.setSize(Sketchpad.WIDTH, Sketchpad.HEIGHT);
		screen.setVisible(true);
	}
}
