Item Transform Helper
==================
When you create a custom item, you need to provide ItemCameraTransforms for it.  This project is an interactive
  tool to help you get the transforms right by interactively adjusting them.

How to use:<br>
1) Place the ItemCamera somewhere in the player's hotbar<br>
2) Hold the Item you want to modify in the player's hand<br>
3) Use the up/down keys to move up and down the menu.<br>
4) Use the left/right keys to edit the value (eg scaleX)<br>
5) To select a different view (first person, third person, gui, head (for helmet)) change the VIEW menu item<br>
6) To reset the parameters for the current view, select the RESET menu item and press left or right.<br>
-  You can also copy parameters from a vanilla item to your custom item:<br>
-  a) Hold the vanilla item in your hand<br>
-  b) Select RESET, press left or right.<br>
-  c) Hold your custom item in your hand.<br>
7) When your item looks right, select PRINT and press left or right to print the current parameters to the console<br>
8) Copy the appropriate lines from the console to your item model json file<br>

The item will appear in the Helpers tab in the creative inventory.

For background information on<br>
- items: see here http://greyminecraftcoder.blogspot.com/2013/12/items.html<br>
- rendering items: see here http://greyminecraftcoder.blogspot.com.au/2014/12/item-rendering-18.html<br>
- This link describes the structure of the JSON Item Model file (see the Item Models section halfway down):<br>
-   http://minecraft.gamepedia.com/Block_models