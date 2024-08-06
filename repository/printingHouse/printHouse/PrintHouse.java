package printHouse;


import java.io.*;
import java.util.EnumMap;
import java.util.ArrayList;
import paper.*;
import employee.*;
import printer.*;



public class PrintHouse {
	private String name;
	
	//Demn inflation :)
	public final static double costToPriceRatio = 4.25;
	public final static double copiesDiscount = 0.8;
	public final static int copiesForDiscount = 10;
	
	
	//managerBonus
	//This variable is also contained in Manager class
	//I couldn't figure out a good way to link
	//So they are both UNSYNCHRONIZED in PrintHouse and Manager classes
	public final static double managerBonus = 1.25;
	public final static double incomeForManagerBonus = 150;
	
	
	double income = 0.0;
	double expenses = 0.0;
	
	
	
	//AVAILABLE PAPER MULTIKEY MAP
	EnumMap<paperType, EnumMap<paperSize, Integer> > availablePaper = 
			new EnumMap<>(paperType.class);
	
	
	//NEVER use this
	//It is just used to initialize availablePaper Nested EnumMap
	private EnumMap<paperSize, Integer> innerMap = new EnumMap<>(paperSize.class);
	
	
	
	//LISTS OF EMPLOYEES AND PRINTERS
	ArrayList<Employee> employees = new ArrayList<Employee>();
	ArrayList<Manager> managers = new ArrayList<Manager>();
	ArrayList<Printer> printers = new ArrayList<Printer>();
	
	
	//PAPER COSTS BY TYPE AND SIZE
	EnumMap<paperType, Double> typeCost = 
			new EnumMap<paperType, Double>(paperType.class);
	EnumMap<paperSize, Double> sizeCost = 
			new EnumMap<paperSize, Double>(paperSize.class);

	
	//THREE CONSTRUCTORS
	public PrintHouse() {
		this.name = "Unnamed Print House";
		
		typeCost.put(paperType.NEWSPRINT,	0.02);
		typeCost.put(paperType.COMMON,		0.06);
		typeCost.put(paperType.GLOSSY,		0.12);
		
		
		fillSizeCost();
		fillAvailablePaper();
	}
	public PrintHouse(String name) {
		this.name = name;
		
		typeCost.put(paperType.NEWSPRINT,	0.02);
		typeCost.put(paperType.COMMON,		0.06);
		typeCost.put(paperType.GLOSSY,		0.12);
		

		fillSizeCost();
		fillAvailablePaper();
	}
	public PrintHouse(String name, double newsprintCost, 
			double commonCost, double glossyCost) {
		
		this.name = name;
		
		typeCost.put(paperType.NEWSPRINT, newsprintCost);
		typeCost.put(paperType.COMMON, commonCost);
		typeCost.put(paperType.GLOSSY, glossyCost);
		
		
		fillSizeCost();
		fillAvailablePaper();
	}

	//Size cost is always the same
	//So fill it to avoid writing duplicate code
	private void fillSizeCost() {
		sizeCost.put(paperSize.A5, 1.00);
		sizeCost.put(paperSize.A4, 1.11);
		sizeCost.put(paperSize.A3, 1.22);
		sizeCost.put(paperSize.A2, 1.33);
		sizeCost.put(paperSize.A1, 1.44);
	}
	private void fillAvailablePaper() {
		
		//Fill Inner Map first
		for(paperSize size : paperSize.values()) {
			innerMap.put(size, 0);
		}
		
		
		//Attach Inner Map to Outer Map
		for(paperType type : paperType.values() ) {
			availablePaper.put(type, innerMap);
		}
	}
	
	//Buy paper
	public void addPaper(paperType type, paperSize size, int amount) {
		
		int newAmount = availablePaper.get(type).get(size);
		availablePaper.get(type).put(size, newAmount);
		
		
		expenses += typeCost.get(type) * sizeCost.get(size) * amount;
	}
	
	
	//Load paper into printer
	public void loadPaper(Printer pr, paperType type, paperSize size, int amount)
			throws PaperCapacityOverloadException, IncompatiblePaperException  {
		
		if(pr.getLoadedType() == null && pr.getLoadedSize() == null ) {
			pr.setLoadedType(type);
			pr.setLoadedSize(size);
			
			pr.addPaperAmount(amount);
			
		} else {
			if( (pr.getLoadedType() == type) && (pr.getLoadedSize() == size) ) {
				
				if( (pr.getPaperAmount() + amount) <= Printer.maxLoadedPaper) {
					pr.addPaperAmount(amount);
				} else {
					throw new PaperCapacityOverloadException("Printer paper capacity overflow");
				}
				
			} else {
				throw new IncompatiblePaperException("The type/size of the paper to load differ from the already loaded.");
			}
		}
		
	}
	
	
	
	public void addEmployee(String name) {
		Employee thisEmployee = new Employee(name);
		
		employees.add(thisEmployee);
	}
	public void addManager(String name) {
		Manager thisManager = new Manager(name);
		
		managers.add(thisManager);
	}
	public void addPrinter(String name, boolean canColorPrint) {
		Printer thisPrinter = new Printer(name, canColorPrint);
		
		printers.add(thisPrinter);
	}
	
	//Access printer for publish() function
	public Printer getPrinter(int index) {
		return printers.get(index);
	}
	
	
	
	
	public void publish(Publication pub, Printer pr, int copies, boolean isColorPrint) 
			throws CannotColorPrintException, IncompatiblePaperException, InsufficientPaperException {
		
		paperType type = pub.getType();
		paperSize size = pub.getSize();
		int pages = pub.getPages();
		
		
		pr.print(pub, copies, isColorPrint);
		
		
		//Discount check
		if(copies >= copiesForDiscount) {
			income += typeCost.get(type) * sizeCost.get(size) * pages * copies * costToPriceRatio;
			//Discount
			income *= copiesDiscount;
			
		} else {
			income += typeCost.get(type) * sizeCost.get(size) * pages * copies * costToPriceRatio;
		}
	}
	
	
	
	
	//Always call after done publishing
	//To give salaries
	public void endOfDay() {
		if( (income > incomeForManagerBonus) ) {
			Manager.declareSalaryIncrease();
		}
		
		
		expenses += employees.size() * Employee.getSalary();
		
		expenses += managers.size() * Manager.getSalary();
	}
	
	//Print whole information
	public void printInfo() {
		System.out.println("Print house name: " + this.name);
		
		System.out.println("\n");
		
		System.out.println("Income: " + this.income);
		System.out.println("Expenses: " + this.expenses);
		
		System.out.println("\n");
		
		
		System.out.println("Difference: " + (this.income - this.expenses) );
		
		System.out.println("\n\n");
		
		
		for(Printer pr : printers) {
			System.out.println("Printer name: " + pr.getName() );
			
			System.out.println("\n");
			
			
			for(Publication pub : pr.publications) {
				System.out.println("Publication: " + pub.getName() );
				System.out.println("Copies: " + pr.publicationCopies.get(pub.getName() ));
				
				System.out.println("\n");
			}
			
			
		}
		
	}
	
	
	//TODO
	//Implement writing to a file
	//Serialization
	//And the opposite
	//Deserialization
	public void serialize(String name) {
		String filename = name + ".ser";
		
		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(this);
			
			out.close();
			file.close();
			
			System.out.println("File serialization successful.");
			
		} catch(IOException ex) {
			System.out.println("IOException: " + ex);
		}
		
	}
	
	
	public PrintHouse deserialize(String name) {
		String filename = name + ".ser";
		
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			
			PrintHouse ph1 = (PrintHouse) in.readObject();
			
			
			in.close();
			file.close();
			
			
			return ph1;
			
		} catch(IOException ex) {
			System.out.println("IOException: " + ex);
		} catch(ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException : " + ex);
		}
		
		
		return null;
		
	}
	
	
}










