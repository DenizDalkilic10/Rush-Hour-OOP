//package source.test;
//
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import source.controller.*;
//
//import source.model.GameObject;
//
//public class MapExtractorTest {
//
//	public static void main(String[] args) throws FileNotFoundException {
//		MapExtractor mapExtracter = new MapExtractor(2);
//		VehicleController vc = new VehicleController(mapExtracter.getMap());
//		mapExtracter.printVehicleArray();
//		System.out.println(mapExtracter.getMap().getVehicleArray().get(0).getOccupiedCells()[1]);
//		mapExtracter.getMap().printMap();
//		vc.setSelectedVehicle(mapExtracter.getMap().getVehicleArray().get(1));
//		vc.tryMove("Right");
//
//		//mapExtracter.getMap().getVehicleArray().get(1).move(-1);
//		mapExtracter.getMap().updateMap(mapExtracter.getMap().getVehicleArray());
//		//System.out.println("\n");
//		mapExtracter.getMap().printMap();
//	}
//}
//