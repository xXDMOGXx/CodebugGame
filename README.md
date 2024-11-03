# This repo is for the development of the game about Ellie_Minibot's codebugs
The game is a resource collection and base expansion topdown that has a big focus on emergent behaviours by allowing players to program simple instructions into the codebugs.

# Setup Guide (For Developers)
## Setting up the IDE
The main game code is entirely in java. you can use any IDE, but I would highly suggest IntelliJ Community. It's purpose built with Java compatability in mind, feels modern, and has much better Intellisense than VSCode.  
You can download IntelliJ Community [Here](https://www.jetbrains.com/idea/download/?section=windows) (Make sure to scroll down past IntelliJ Ultimate)  
I would create an association for .java when installing, but it's not required.
### Reccomended Plugins
I would suggest these three plugins. None of them are required, but they have some QOL features that are useful. You can install plugins in the side bar to the left on the main IntelliJ window.  
- **Wavefront OBJ**  
  This plugin adds file extension support, and a button to preview .obj files. We use .obj files for all of the models, so this is pretty handy to have.  
- **GLSL**  
  GLSL is the shader programming language we use. This plugin adds file extension and intellisense support for it. It is pretty buggy though, so I would suggest trying it out, and getting rid of it if it's too annoying.
- **Discord Integration V2**
  This plugin adds Discord activity integration with IntelliJ. It allows you to show off all the hard work you do working on this.

### Pulling the repo
You can pull the repo by going to your Projects tab and clicking the "Clone Repo" button in the top right. I would suggest linking your Github account in IntelliJ. Go ahead and open the project for the next steps.
### Download java 20!
When you open the project, it will most likely warn you about missing the required version of java. Just click the "Install" button in the notification to have it automatically downloaded.
## Setting up the libraries
### Windows
If you are using Windows, there isn't any special library setup that you have to do. You can just continue on.
### Linux/MacOS
MacOS doesn't particularly like integrating with openGL, but the code should be set up with all the compatability requirements to support it.  
For both platforms, setting up the OpenGL library follows the same steps. You need to download the required natives for you platform by going [Here](https://www.lwjgl.org/customize).  
Make sure you are on Stable Build 3.3.4. Select the appopriate platform in the Natives option, then unselect sources, javadoc, and build config. Download as a zip.  
Unpack the zip and drag all of the files that include the word native into the `lwjgl-release-3.3.4-custom` folder in the root of the repo. You can now go to your Project Structure to add those natives.  
You can do this by going to the three bars in the top left then File->Project Structure...->libraries.  
Next, select all the natives for Windows and remove them with the `-` button, then click the `+` button and select all of your platform specific natives then click "ok".
## Creating a Run Configuration
Creating a run configuration is quite simple, and the final step required to run the code. Look at the top of IntelliJ towards the right for the button that says "Current File v", and click it, then click "Edit Configurations...".  
Click the `+` button or "Add new", then click Application. Name the run configuration whatever you want (I named mine "run"). Now click the "Browse" button on the right side of the red box for "Main class". Select "Launcher", then click "ok".  
You can now click the green sideways triangle to run the project.
# Contribution Guide
This section is a WIP. For now, just contact me on discord @xXDMOGXx. I'll go and fill this out eventually
