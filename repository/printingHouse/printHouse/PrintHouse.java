package printHouse;


import java.io.*;
import java.util.EnumMap;
import java.util.ArrayList;
import paper.*;
import employee.*;
import printer.*;
import printer.exception.CannotColorPrintException;
import printer.exception.IncompatiblePaperException;
import printer.exception.InsufficientPaperException;
import printer.exception.PaperCapacityOverloadException;


public class PrintHouse {
	private String name;
	
	//Demn inflation :)
	private double costToPriceRatio = 4.25;
	private double copiesDiscount = 0.8;
	private int copiesForDiscount = 10;

	
	//managerBonus
	//This variable is also contained in Manager class
	//I couldn't figure out a good way to link
	//So they are both UNSYNCHRONIZED in PrintHouse and Manager classes
	private double managerBonus = 1.25;
	private double incomeForManagerBonus = 150;
	
	
	private double income = 0.0;
	private double expenses = 0.0;
	

	//AVAILABLE PAPER MULTIKEY MAP
	private EnumMap<paperType, EnumMap<paperSize, Integer> > availablePaper =
			new EnumMap<>(paperType.class);

	//NEVER use this
	//It is just used to initialize availablePaper Nested EnumMap
	private EnumMap<paperSize, Integer> innerMap = new EnumMap<>(paperSize.class);
	
	
	
	//LISTS OF EMPLOYEES AND PRINTERS
	private ArrayList<Employee> employees = new ArrayList<Employee>();
	private ArrayList<Manager> managers = new ArrayList<Manager>();
	private ArrayList<Printer> printers = new ArrayList<Printer>();
	
	
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


	//===================
	//GETTERS AND SETTERS
	//===================
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getCostToPriceRatio() {
		return costToPriceRatio;
	}
	public void setCostToPriceRatio(double costToPriceRatio) {
		this.costToPriceRatio = costToPriceRatio;
	}

	public double getCopiesDiscount() {
		return copiesDiscount;
	}
	public void setCopiesDiscount(double copiesDiscount) {
		this.copiesDiscount = copiesDiscount;
	}

	public int getCopiesForDiscount() {
		return copiesForDiscount;
	}
	public void setCopiesForDiscount(int copiesForDiscount) {
		this.copiesForDiscount = copiesForDiscount;
	}

	public double getManagerBonus() {
		return managerBonus;
	}
	public void setManagerBonus(double managerBonus) {
		this.managerBonus = managerBonus;
	}

	public double getIncomeForManagerBonus() {
		return incomeForManagerBonus;
	}
	public void setIncomeForManagerBonus(double incomeForManagerBonus) {
		this.incomeForManagerBonus = incomeForManagerBonus;
	}

	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpenses() {
		return expenses;
	}
	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}

	public EnumMap<paperType, EnumMap<paperSize, Integer>> getAvailablePaper() {
		return availablePaper;
	}
	public void setAvailablePaper(EnumMap<paperType, EnumMap<paperSize, Integer>> availablePaper) {
		this.availablePaper = availablePaper;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	public ArrayList<Manager> getManagers() {
		return managers;
	}
	public void setManagers(ArrayList<Manager> managers) {
		this.managers = managers;
	}

	public ArrayList<Printer> getPrinters() {
		return printers;
	}
	public void setPrinters(ArrayList<Printer> printers) {
		this.printers = printers;
	}

	public EnumMap<paperType, Double> getTypeCost() {
		return typeCost;
	}
	public void setTypeCost(EnumMap<paperType, Double> typeCost) {
		this.typeCost = typeCost;
	}

	public EnumMap<paperSize, Double> getSizeCost() {
		return sizeCost;
	}
	public void setSizeCost(EnumMap<paperSize, Double> sizeCost) {
		this.sizeCost = sizeCost;
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
			throws PaperCapacityOverloadException, IncompatiblePaperException {
		
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
	
	
	
	
	public void publish(Publication publication, Printer printer, int copies, boolean isColorPrint)
			throws CannotColorPrintException, IncompatiblePaperException, InsufficientPaperException {
		
		paperType type = publication.getType();
		paperSize size = publication.getSize();
		int pages = publication.getPages();
		
		
		printer.print(publication, copies, isColorPrint);
		
		
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
	

	//SERIALIZATION
	//DESERIALIZATION
	public void serialize(String name) {
		String filename = name + ".ser";
		
		try(
				FileOutputStream file = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(file)
		)
		{
			
			out.writeObject(this);
			
			System.out.println("File serialization successful.");
			
		} catch(IOException ex) {
			System.out.println("IOException: " + ex);
		}
		
	}
	
	
	public PrintHouse deserialize(String name) {
		String filename = name + ".ser";
		
		try(FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file)
		)
		{

			PrintHouse ph1 = (PrintHouse) in.readObject();

			return ph1;
			
		} catch(IOException ex) {
			System.out.println("IOException: " + ex);
		} catch(ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException : " + ex);
		}
		
		
		return null;
		
	}
	
	
}










