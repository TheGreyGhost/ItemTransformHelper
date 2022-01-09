Item Transform Helper
==================
When you create a custom item, you need to provide ItemCameraTransforms for it.  This project is an interactive
  tool to help you get the transforms right by interactively adjusting them.

How to use:<br>
1) Place the ItemCamera somewhere in the player's hotbar<br>
2) Hold the Item you want to modify in the player's hand<br>
3) Use the up/down keys to move up and down the menu.<br>
4) Use the left/right keys to edit the value (eg scaleX)<br>
5) To select a different view (eg first person, third person) use left/right to change the VIEW menu item.
6) To modify the transform for an item on the ground:
   * throw your Item onto the ground, and then put another copy of your Item into the player's hand
   * Use left/right to change the VIEW menu item to grnd<br>
   Use the same technique for items in the gui, on your head (eg pumpkin), fixed (in picture frames), or in the
   player's left hand.
7) To reset the parameters for the current view, select the RESET menu item and press left or right.  Use RESET ALL (RSTALL) to reset the parameters for all views. <br>
  You can also copy parameters from a vanilla item to your custom item:
   * Hold the vanilla item in your hand
   * Select RESET or RSTALL, press left or right.
   * Hold your custom item in your hand.
8) When your item looks right, select PRINT and press left or right to print the current parameters to the console
9) Copy the appropriate lines from the console to your item model json file

The item will appear in the Helpers tab in the creative inventory.

For background information on<br>
- items: see here http://greyminecraftcoder.blogspot.com/2013/12/items.html<br>
- rendering items: see here http://greyminecraftcoder.blogspot.com.au/2014/12/item-rendering-18.html<br>
- This link describes the structure of the JSON Item Model file (see the Item Models section halfway down):<br>
-   http://minecraft.gamepedia.com/Block_models

As an alternative to the Item Transform Helper, you can also use BlockBench (https://blockbench.net/) to determine the correct transforms for your model, i.e. either<br>
1) Create a new model in BlockBench; or<br>
2) Import an existing model (either the json model file, or the item "generated" texture)<br>

Thanks to Romejanic for updating to 1.12.2, and to lehrj for updating to 1.16.1!

## Versions after 1.16.1
At the moment I haven't updated beyond 1.16.1 but ThexXTURBOXx has created versions for 1.16.5, 1.17 and 1.18, and also added support for Fabric (a simpler alternative to Forge).  You can find his repo and the releases here:  https://github.com/ThexXTURBOXx/ItemTransformHelper


## Licence Info:
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
