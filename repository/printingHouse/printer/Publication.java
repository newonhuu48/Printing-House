package printer;

import paper.*;

public class Publication {
	private String name;
	private paperType type;
	private paperSize size;
	private int pages;
	
	
	public Publication(String name, paperType type, paperSize size, int pages) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.pages = pages;
	}
	
	
	public String getName() {
		return this.name;
	}
	public paperType getType() {
		return this.type;
	}
	public paperSize getSize() {
		return this.size;
	}
	public int getPages() {
		return this.pages;
	}
}
