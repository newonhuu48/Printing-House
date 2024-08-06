package employee;

public class Employee {
	private String name;
	protected static int salary = 30;
	

	public Employee() {
			this.name = "Unnamed Employee";
	}
	
	public Employee(String name) {
		this.name = name;
	}
	
	
	public static int getSalary() {
		return Employee.salary;
	}

	
	
	@Override
	public String toString() {
		return "Employee name: " + this.name + 
				" Employee salary: " + Employee.salary;
	}
}
