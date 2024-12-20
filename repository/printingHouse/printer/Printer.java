package printer;

import java.util.ArrayList;
import java.util.HashMap;
import paper.*;
import printer.exception.CannotColorPrintException;
import printer.exception.IncompatiblePaperException;
import printer.exception.InsufficientPaperException;

//COMPLETE IT
public class Printer {
	private String name;
	private boolean canColorPrint;
	private int totalPagesPrinted;
	
	private paperType loadedType = null;
	private paperSize loadedSize = null;
	
	private int loadedPaper = 0;
	public static final int maxLoadedPaper = 700;
	
	
	
	//TODO
	//Publications ArrayList
	public ArrayList<Publication> publications = new ArrayList<>();
	public HashMap<String, Integer> publicationCopies = new HashMap<>();

	
	public Printer() {
		this.name = "Unnamed Printer";
		this.canColorPrint = false;
		
		this.totalPagesPrinted = 0;
	}
	public Printer(String name) {
		this.name = name;
		this.canColorPrint = false;
		
		this.totalPagesPrinted = 0;
	}
	public Printer(String name, boolean canColorPrint) {
		this.name = name;
		this.canColorPrint = canColorPrint;
		
		this.totalPagesPrinted = 0;
	}
	
	
	
	public void setName(String name) {
		this.name = name;
	}	
	public String getName() {
		return this.name;
	}
	
	public void setLoadedType(paperType type) {
		this.loadedType = type;
	}
	public paperType getLoadedType() {
		return this.loadedType;
	}
	
	public void setLoadedSize(paperSize size) {
		this.loadedSize = size;
	}
	public paperSize getLoadedSize() {
		return this.loadedSize;
	}
	
	/*
	public void addPaperAmount(int amount) 
			throws PaperCapacityOverloadException{
		
		if( (this.loadedPaper + amount) > maxLoadedPaper) {
			throw new PaperCapacityOverloadException("Printer paper capacity overload.");
		} else {
			this.loadedPaper += amount;
		}
	}
	*/
	
	public int getPaperAmount() {
		return this.loadedPaper;
	}
	public void addPaperAmount(int amount) {
		loadedPaper += amount;
	}
	
	public boolean getCanColorPrint() {
		return canColorPrint;
	}

	public int getTotalPagesPrinted() {
		return totalPagesPrinted;
	}
	
	
	
	
	
	
	public void print(Publication pub, int copies, boolean isColorPrint) 
			throws InsufficientPaperException, IncompatiblePaperException, CannotColorPrintException {
		
		
		if( (isColorPrint) && (!canColorPrint) ) {
			throw new CannotColorPrintException("The select printer cannot color print");
		}
		
		
		
		if(this.loadedType == pub.getType() && this.loadedSize == pub.getSize()) {
			
			
			if( (pub.getPages() * copies) <= loadedPaper) {
				int pagesPrinted = pub.getPages() * copies;
				
				loadedPaper -= pagesPrinted;
				totalPagesPrinted += pagesPrinted;
				
				
				publications.add(pub);
				publicationCopies.put(pub.getName(), copies);
				
			} else {
				throw new InsufficientPaperException("The printer does not have enough paper loaded.");
			}
			
			
		} else {
			throw new IncompatiblePaperException("The paper type and/or size of printer and publication differ.");
		}
		
	}
	
	
}
