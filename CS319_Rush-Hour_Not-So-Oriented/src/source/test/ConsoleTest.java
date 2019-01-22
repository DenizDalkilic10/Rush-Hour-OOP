//package source.test;
//
//import java.util.Scanner;
//import source.controller.*;
//import source.model.*;
//import source.view.GuiPanelManager;
//
//import java.io.FileNotFoundException;
//
//public class ConsoleTest {
//
//	public static void main(String[] args) throws FileNotFoundException {
//		Scanner scan = new Scanner(System.in);
//		MapExtractor me = new MapExtractor(2);
//		Map map = me.getMap();
//		GuiPanelManager guiManager = new GuiPanelManager();
//		int x, y, option;
//		Vehicle selectedVehicle;
//		VehicleController vc = new VehicleController(map);
//		char direction;
//		boolean successfulMove, mapFinished;
//
//		do
//		{
//			mapFinished = false;
//			successfulMove = false;
//			map.printMap();
//
//			//System.out.println("");
//			System.out.println("\nEnter 1 to select vehicle");
//			System.out.println("Enter 0 to exit");
//			option = scan.nextInt();
//
//			if(option == 1)
//			{
//				System.out.println("Enter the X and Y locations of any part of the vehicle you want to select");
//				System.out.println("(i.e. 2nd row , 3rd column --> X: 2 , Y: 1)");
//				System.out.print("X position: ");
//				x = scan.nextInt();
//				System.out.print("\nY position: ");
//				y = scan.nextInt();
//				System.out.println("");
//
//				selectedVehicle = map.getVehicleBySelectedCell(x, y);
//				if (x < 0 || x >= map.getMapSize() || y < 0 || y >= map.getMapSize())
//				{
//					System.out.println("Your input is invalid");
//				}
//				if (selectedVehicle != null)
//				{
//					vc.setSelectedVehicle(selectedVehicle);
//					System.out.println("Enter direction (R, L, U, D):");
//					direction = scan.next().charAt(0);
//
//					if (direction == 'R')
//					{
//						if (selectedVehicle.isPlayer() && map.isPlayerAtLast())
//						{
//							mapFinished = true;
//						}
//						else
//						{
//							successfulMove = vc.tryMove("Right");
//						}
//
//					}
//
//					else if (direction == 'L')
//					{
//						successfulMove = vc.tryMove("Left");
//					}
//
//					else if (direction == 'U')
//					{
//						successfulMove = vc.tryMove("Upwards");
//					}
//
//					else if (direction == 'D')
//					{
//						successfulMove = vc.tryMove("Downwards");
//					}
//
//				}
//				else
//				{
//					System.out.println("You chose a space cell!");
//				}
//				if (successfulMove)
//				{
//					map.updateMap(map.getVehicleArray());
//					//guiManager.getGamePanel().updatePanel(map.getVehicleArray());
//				}
//			}
//		} while (option != 0 && !mapFinished);
//
//		if (mapFinished)
//		{
//			System.out.println("You finished the map!");
//		}
//	}
//
//}
