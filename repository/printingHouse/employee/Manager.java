package employee;


public class Manager extends Employee {
	public final static double managerBonus = 1.25;
	
	public Manager() {
		super("Unnamed Manager");
}
	
	public Manager(String name) {
		super(name);
	}
	
	
	public static void declareSalaryIncrease() {
		Manager.salary *= managerBonus;
	}
}