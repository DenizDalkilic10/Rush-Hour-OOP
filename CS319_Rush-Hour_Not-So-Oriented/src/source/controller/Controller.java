package source.controller;

/**
 * Controller class is the base class that every manager or controller is derived from this class.
 */
abstract class Controller
{
   /**
    * It is a function to start.
    * Called when the object is created before update methods. It is only called once.
    */
   void start(){

   }

   /**
    * It is a function to update.
    * Called every frame from the Game Engine.
    */
   void update(){

   }

}
