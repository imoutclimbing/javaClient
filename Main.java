
import java.io.*;
import java.lang.*;
import java.net.*;

/**
*		
*
* <b><big>General Info</big></b><br>
* Author: Douglas Dunlap<br>
* Email: ddunlap3@mscd.edu<br>
* Program: #3 - Client<br>
* Version: 3<br>
* Written For: MSCD CSS4727 / Summer 2004<br>
* Written With: J2SE 1.4.0_01<br>
* Development Environment: jGrasp 1.5.0<br>
* Compiler: javac included with J2SE 1.4.0_01<br>
* Platform: IBM 600e (Intel PII-400Mhz), Microsoft Windows XP Professional<br>
* Original Write: 7/30/2004<br>
* Last Mod: 8/3/04<br><br><br>
*
* <b><big>Statement of Purpose</big></b><br> 
* This program creates a text-only web client that takes a URL on the<br>
* command-line and displays the object on the screen.<br><br><br>
*
* <b><big>Description of Input and Output</big></b><br>
* <i>Input</i>: A valid URL.<br>
* <i>Output</i>: The specified object on the specified web server.<br><br><br>
* 
* <b><big>How to Use</big></b><br>
* 1) Save this file as 'Main.java'.<br>
* 2) Compile the source code with 'javac Main.java'.<br>
* 3) Run by typing 'java Main (URL)'.<br><br><br>
* 
* <b><big>Assumptions on Expected Data</big></b><br>
* The URL must be valid, all else will throw an exception.<br>
* A protocol is required.<br><br><br>
*
* <b><big>Exceptions</big></b><br>
*
* <i>MalformedURLException</i> 
* <ul>
* <li>URL Class</li>
* 	<ul>
* 	<li>URL (String spec) - If the string specifies an unknown protocol.</li>
* 	</ul>
* </ul>
*
* <i>UnknownHostException</i>
* <ul>
* <li>URL Class</li>
* 	<ul>
* 	<li>Socket (String host, int port) - If the IP address of the host could not be determined.</li>
* 	</ul>
* </ul>
*
* <i>SecurityException</i>
* <ul>
* <li>URL Class</li>
* 	<ul>
* 	<li>Socket (String host, int port) - If a security manager exists and its checkConnect method doesn't allow the operation.</li>
* 	</ul>
* </ul>
*
* <i>IOException</i>
* <ul>
* <li>URL Class</li>
* 	<ul>
* 	<li>Socket (String host, int port) - If an I/O error occurs when creating the socket.</li>
* 	<li>close() - If an I/O error occurs when closing this socket.</li>
* 	</ul>
* <li>BufferedWriter Class</li>
* 	<ul>
* 	<li>write() - If an I/O error occurs.</li>
* 	<li>flush() - If an I/O error occurs.</li>
* 	</ul>
* <li>BufferedReader Class</li>
* 	<ul>
* 	<li>BufferedReader (Reader in) - If an I/O error occurs.</li>
* 	<li>readLine() - If an I/O error occurs.</li>
* 	</ul>
* </ul><br>
* 
* <b><big>Test Design</big></b><br>
* <small><table border="border">
* <tr><th>Test Case</th>
* <th>Expected Result</th>
* <th>Misc</th></tr>
*
* <tr><td>Full URL</td>	
* <td>ok</td>
* <td>worked as expected</td></tr>
*
* <tr><td>No port</td>
* <td>ok</td>
* <td>worked as expected</td></tr>	 
*
* <tr><td>No path</td>
* <td>ok</td>
* <td>worked as expected</td></tr>
*
* <tr><td>No protocol</td>
* <td>exception</td>
* <td>protocol is required</td></tr>
*
* <tr><td>Malformed host</td>
* <td>exception</td>
* <td>valid host required</td></tr>
*
* <tr><td>Malformed port</td>
* <td>exception</td>
* <td>no port or valid port required</td></tr></table></small><br><br><br>
*
* <b><big>Method Descriptions</big></b><br>
* Main - operation core of program.<br><br><br>
* 
* <b><big>Bibliography</big></b><br>
* Java Software Solutions - 3rd, Lewis and Loftus, Addison Wesley, 2003.<br>
* HTML & XHTML: The Definitive Guide - 5th, Musciano & Kennedy, O'Reilly, 2002.<br>
* Java Network Programming - 2nd, Harold, O'Reilly, 2000.<br>
*
*
*/ 

public class Main
{
		// Purpose: This clas creates a text-only web server.
		// Author: Douglas Dunlap
		// Email: ddunlap3@mscd.edu
		// Date Written: 7/30/04
		// Last Mod: 8/3/04
		// Version:
		// Exceptions: 

	public static void main (String args[])
	{
			// Purpose: the Main method is the operational core of the program
			// Author: Douglas Dunlap
			// Email: ddunlap3@mscd.edu
			// Date Written: 7/30/04
			// Last Mod: 8/3/04
			// Version:
			// Exceptions:

		// create objects and variables
		String hostName = args[0];  // make string from URL eneterd at command line
		URL URLentered = null;
		String host = "";
		String path = "";
		int port = 0;
		Socket theSocket = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String readInfo;

		try 
		{
			URLentered = new URL(hostName);  // make URL object from the string that was created
		}
		catch (MalformedURLException e)
		{
			System.out.println(e);
			System.exit(1);
		}

		try
		{
			host = URLentered.getHost();  // get host name from URL object
			path = URLentered.getPath();  // get the path for the file
			port = URLentered.getPort();  // get port number from URL object
				
			if (port == -1)  // if no port was present on the command line, get default port
			{
				port = URLentered.getDefaultPort();
			}

			theSocket = new Socket (host, port);  // make the socket
			br = new BufferedReader (new InputStreamReader(theSocket.getInputStream()));
			bw = new BufferedWriter (new OutputStreamWriter(theSocket.getOutputStream()));
		}
		catch (UnknownHostException e)
		{
			System.out.println(e);
			System.exit(1);
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.exit(1);
		}
				
		// send the command for the file
		try
		{
			bw.write("GET " + path + " HTTP/1.0 \r\n\r\n");
			bw.flush();
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.exit(1);
		}
			
		// read the lines from the requested file
		while (true)
		{
			try
			{
				readInfo = br.readLine();  // read the line
				
				if (readInfo == null)  // test for nothingness
				{
					break;
				}

				System.out.println(readInfo);	// print the line to screen
			}
			catch (IOException e)
			{
				System.out.println(e);
				System.exit(1);
			}
		}

		// close the connection
		try
		{
			theSocket.close();
			System.exit(0);
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.exit(1);
		}
	}  // method end
}  // class end