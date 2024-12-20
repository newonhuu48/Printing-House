package employee;

public class Manager extends Employee {
	private static double managerBonus = 1.25;
	
	public Manager() {
		super("Unnamed Manager");
}
	
	public Manager(String name) {
		super(name);
	}


	public double getManagerBonus() {
		return managerBonus;
	}

	public void setManagerBonus(double managerBonus) {
		this.managerBonus = managerBonus;
	}


	public static void declareSalaryIncrease() {
		Manager.salary *= managerBonus;
	}
}