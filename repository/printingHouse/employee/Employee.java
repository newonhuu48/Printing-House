package employee;

public class Employee {
	protected String name;
	protected static int salary = 30;


	public Employee() {
			this.name = "Unnamed Employee";
	}
	
	public Employee(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int getSalary() {
		return salary;
	}

	public static void setSalary(int salary) {
		Employee.salary = salary;
	}


	@Override
	public String toString() {
		return "Employee name: " + this.name + 
				" Employee salary: " + this.salary;
	}
}
