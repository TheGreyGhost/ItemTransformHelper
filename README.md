# Item Transform Helper

When you create a custom item, you need to provide ItemCameraTransforms for it. This project is an interactive
  tool to help you get the transforms right by interactively adjusting them.

How to use:<br>
1) Place the Camera item somewhere in the player's hotbar<br>
2) Hold the Item you want to modify in the player's hand<br>
3) Use the up/down keys to move up and down the menu.<br>
4) Use the left/right keys to edit the value (e.g. scaleX)<br>
5) To select a different view (e.g. first person, third person) use left/right to change the VIEW menu item.
6) To modify the transform for an item on the ground:
   * Throw your Item onto the ground, and then put another copy of your Item into the player's hand
   * Use left/right to change the VIEW menu item to grnd<br>
   Use the same technique for items in the gui, on your head (eg pumpkin), fixed (in picture frames), or in the
   player's left hand.
7) To reset the parameters for the current view, select the RESET menu item and press left or right. Use RESET ALL (RSTALL) to reset the parameters for all views.<br>
  You can also copy parameters from a vanilla item to your custom item:
   * Hold the vanilla item in your hand
   * Select RESET or RSTALL, press left or right.
   * Hold your custom item in your hand.
8) When your item looks right, select PRINT and press left or right to print the current parameters to the console
9) Copy the appropriate lines from the console to your item model json file

The item will appear in the Helpers tab in the creative inventory.

For background information on<br>
- Items: see here https://greyminecraftcoder.blogspot.com/2013/12/items.html<br>
- Rendering items: see here https://greyminecraftcoder.blogspot.com/2014/12/item-rendering-18.html<br>
- This link describes the structure of the JSON Item Model file (see the Item Models section halfway down):<br>
-   https://minecraft.fandom.com/wiki/Model#Item_models

As an alternative to the Item Transform Helper, you can also use BlockBench (https://blockbench.net/) to determine the correct transforms for your model, i.e. either<br>
1) Create a new model in BlockBench; or<br>
2) Import an existing model (either the json model file, or the item "generated" texture)<br>

Thanks to ThexXTURBOXx for updating to 1.11.2 and Architectury (Forge + Fabric) 1.18.1, Romejanic for updating to 1.12.2, and to lehrj for updating to 1.16.1!

## Licence Info

See [here](https://github.com/TheGreyGhost/ItemTransformHelper/blob/master/LICENSE).
