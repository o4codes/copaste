package com.o4codes.copaste.utils;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Pattern;

import static com.o4codes.copaste.utils.Helper.checkStringForLetters;

public class NetworkUtils {
    public static int getFreePort(){
        return 0;
    }

    // check if server is possible to connect
    public static boolean isServerReachable(String ipAddress, int portNumber) {

        Socket socket = new Socket();
        try {
            socket.connect( new InetSocketAddress( ipAddress, portNumber ) );
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    //checks if ip address is valid
    public static boolean isValidIPAddress(String ipAddress){
        final String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
        final String IP_REGEXP = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
        final Pattern IP_PATTERN = Pattern.compile( IP_REGEXP );
        return IP_PATTERN.matcher( ipAddress ).matches();
    }

    // filter through network interface to get connected Inet Address
    public static InetAddress getSystemNetworkConfig() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        ArrayList<InetAddress> inet = new ArrayList<>();
        ArrayList<NetworkInterface> workingInterfaces = new ArrayList<>();
        for (NetworkInterface networkInterface : Collections.list( nets )) {
            if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                workingInterfaces.add( networkInterface );
            }
        }
        for (NetworkInterface networkInterface : workingInterfaces) {
            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list( inetAddressEnumeration )) {
                System.out.println( "InetAddress: " + inetAddress );
                if (!checkStringForLetters( inetAddress.getHostAddress() ) && !inetAddress.getHostAddress().equals( "192.168.137.1" )) {
                    inet.add( inetAddress );
                    break;
                }
            }
            if (!inet.isEmpty())
                break;
        }

        if (inet.isEmpty())
            return null;
        else
            return inet.get( 0 );
    }


    public static String getDeviceName() {
        String systemName = null;

        try {
            systemName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // SystemName stores the name of the system
        System.out.println("System Name : "+ systemName);
        return systemName;
    }
}
