package main;

import printHouse.*;
import printer.*;
import paper.*;
import printer.exception.CannotColorPrintException;
import printer.exception.IncompatiblePaperException;
import printer.exception.InsufficientPaperException;
import printer.exception.PaperCapacityOverloadException;


public class Main {
	
	public static void main(String args[]) 
			throws PaperCapacityOverloadException, IncompatiblePaperException,
            CannotColorPrintException, InsufficientPaperException {
		
		
		/*
		 * Трябва принтерите да се заредят с подходяща хартия,
		 * която преди това да се закупи от печатницата
		 * 
		 * Щом принтер сеза реди с един вид хартия трябва да работи само с нея
		 * докато се затвори приложението
		 * Възможно беше да реазлизирам unload-ване на хартия, но смятам,
		 * че и така е добре
		 * 
		 * 
		 * Друго нещо, което не разбрах е това с принтирането на определен
		 * брой страници на минута от принтера, затова просто го направих
		 * като печата издание да трябва да има достатъчно хартия
		 * 
		 * Знам че не е логично принтер да може да държи 700 листа хартия
		 * А вместо това само около 150
		 * 
		 * 
		 * Видовете и размери хартия се съхраняват с enum-и, например:
		 * PaperType.GLOSSY
		 * PaperSize.A3
		 * 
		 * 
		 * 
		 * Стойности по подразбиране
		 * Вид хартия цена:
		 * Вестникарска хартия	- 	0.02
		 * Обикновена хартия 	- 	0.06
		 * Гланцирана хартия 	- 	0.12
		 * 
		 * Размер хартия модификатор на цена:
		 * А5	-	1.00
		 * А4	-	1.11
		 * А3	-	1.22
		 * А2	-	1.33
		 * А1	-	1.44
		 * 
		 * Копия, за да се направи отсъпка - 10
		 * Отстъпка - 20% (Умножение по 0.8)
		 * 
		 * 
		 * TODO 
		 * Приходи на печатницата, за да получат мениджърите бонуси - 150
		 * Бонус на мениджърите - 20% (умножение по 1.2)
		 * 
		 * TODO
		 * След всичкото принтиране трябва да се извика функция endOfShift(), за да се пресметнат
		 * заплатите на служителите и потенциалните бонуси за мениджърите
		 * 
		 * 
		 * Функцията getInfo() принтира приходите и разходите на печатницата(без подробности)
		 * и принтерите - име, публикации, копия на всяка публикация
		 * 
		 * Функцията serialize() извършва сериализация на печатницата
		 * Функцията deserialize() обратното
		 * 
		 * 
		 * На двете горни функции се подава само име на файли БЕЗ РАЗШИРЕНИЕ
		 * 
		 * 
		 * 
		 * Единственото нещо, което ЛИПСВА е UNIT TEST-ове, 
		 * но дебъгвам много добре наум ;)
		 * 
		 * */
		
		
		PrintHouse ph1 = new PrintHouse("Print House 1");
		
		
		//Публикации
		//new Publication(String name, paperType, paperSize, pages)
		Publication pub1 = new Publication(
				"News 24-04-2034", paperType.NEWSPRINT, paperSize.A3, 30);
		
		Publication pub2 = new Publication(
				"Circus Advertisement", paperType.GLOSSY, paperSize.A1, 1);
		
		Publication pub3 = new Publication(
				"Crime of The Silent Librarian", paperType.COMMON, paperSize.A5, 444);
		
		Publication pub4 = new Publication(
				"Star Wars: Heir to the Empire", paperType.COMMON, paperSize.A5, 528);
		
		Publication pub5 = new Publication(
				"News 17-03-2031", paperType.NEWSPRINT, paperSize.A3, 29);
		
		Publication pub6 = new Publication(
				"Dance club advertisement", paperType.GLOSSY, paperSize.A1, 1);
		
		
		//Назначаваме служители
		//ph1.addEmployee(String name)
		ph1.addEmployee("Gosho");
		ph1.addEmployee("Pesho");
		ph1.addManager("Kiro");
		
		
		ph1.addPrinter("Black-White Printer 1", false);
		ph1.addPrinter("Color Printer 1", true);
		ph1.addPrinter("Black-White Printer 2", false);
		ph1.addPrinter("Color Printer 2", true);
		ph1.addPrinter("Black-White Printer 3", false);
		ph1.addPrinter("Color Printer 3", true);
		
		
		//Записваме референции към принтерите
		//ph1.getPrinter()
		Printer pr1 = ph1.getPrinter(0);
		Printer pr2 = ph1.getPrinter(1);
		Printer pr3 = ph1.getPrinter(2);
		Printer pr4 = ph1.getPrinter(3);
		Printer pr5 = ph1.getPrinter(4);
		Printer pr6 = ph1.getPrinter(5);
		
		
		//Печатницата купува хартия тук
		//ph1.addPaper(paperType, paperSize, int amount);
		ph1.addPaper(paperType.NEWSPRINT, paperSize.A3, 600);//17 копия
		ph1.addPaper(paperType.GLOSSY, paperSize.A1, 100);//80 копия
		ph1.addPaper(paperType.COMMON, paperSize.A5, 500);//1 копие, естествено
		ph1.addPaper(paperType.COMMON, paperSize.A5, 600);//1 копие, естествено
		ph1.addPaper(paperType.NEWSPRINT, paperSize.A3, 500);//17 копия
		ph1.addPaper(paperType.GLOSSY, paperSize.A1, 100);//80 копия като цирка
		
		
		//Зареди принтерите със съответната хартия
		//ph1.loadPaper(Printer pr, paperType type, paperSize size, int amount)
		ph1.loadPaper(pr1, paperType.NEWSPRINT, paperSize.A3, 600);
		ph1.loadPaper(pr2, paperType.GLOSSY, paperSize.A1, 100);
		ph1.loadPaper(pr3, paperType.COMMON, paperSize.A5, 500);
		ph1.loadPaper(pr4, paperType.COMMON, paperSize.A5, 600);
		ph1.loadPaper(pr5, paperType.NEWSPRINT, paperSize.A3, 500);
		ph1.loadPaper(pr6, paperType.GLOSSY, paperSize.A1, 100);
		
		
		
		//Публикувай чрез PrintHouse
		//Публикуването извиква print метода на принтера
		//ph1.publish(Publication pub, Printer pr, int copies, boolean isColorPrint)
		ph1.publish(pub1, pr1, 17, false);
		ph1.publish(pub2, pr2, 80, true);
		ph1.publish(pub3, pr3, 1, false);
		ph1.publish(pub4, pr4, 1, true);
		ph1.publish(pub5, pr5, 17, false);
		ph1.publish(pub6, pr6, 80, true);
		
		
		//След всичко ТРЯБВА да се изпълни фукнция EndOfDay(), за да се калкулират
		//Заплатите на служителите
		//ТРЯБВА!!!!!!
		ph1.endOfDay();
		
		ph1.printInfo();
	}

	
}
