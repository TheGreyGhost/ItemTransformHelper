Item Transform Helper
==================
When you create a custom item, you need to provide ItemCameraTransforms for it.  This project is an interactive
  tool to help you get the transforms right by interactively adjusting them.

How to use:
1) Place the ItemCamera somewhere in the player's hotbar
2) Hold the Item you want to modify in the player's hand
3) Use the up/down keys to move up and down the menu.
4) Use the left/right keys to edit the value (eg scaleX)
5) To select a different view (first person, third person, gui, head (for helmet)) change the VIEW menu item
6) To reset the parameters for the current view, select the RESET menu item and press left or right.
  You can also copy parameters from a vanilla item to your custom item:
  a) Hold the vanilla item in your hand
  b) Select RESET, press left or right.
  c) Hold your custom item in your hand.
7) When your item looks right, select PRINT and press left or right to print the current parameters to the console
8) Copy the appropriate lines from the console to your item model json file

The item will appear in the Helpers tab in the creative inventory.

For background information on
- items: see here http://greyminecraftcoder.blogspot.com/2013/12/items.html
- rendering items: see here http://greyminecraftcoder.blogspot.com.au/2014/12/item-rendering-18.html
- This link describes the structure of the JSON Item Model file (see the Item Models section halfway down):
   http://minecraft.gamepedia.com/Block_models

Thanks to Romejanic for updating to 1.12.2!

--------------
Licence Info
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
