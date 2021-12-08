package com.o4codes.copaste.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Pattern;

import static com.o4codes.copaste.utils.Helper.checkStringForLetters;

public class NetworkUtils {
    public static int getFreePort(){
        return 0;
    }

    public static boolean isServerReachable(){
        return false;
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
                System.out.println( "Display Name: " + networkInterface.getDisplayName() );
                System.out.println( "Name: " + networkInterface.getName() );
                workingInterfaces.add( networkInterface );
            }
        }
        for (NetworkInterface networkInterface : workingInterfaces) {
            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list( inetAddressEnumeration )) {
                System.out.println( "InetAddress: " + inetAddress );
                if (!checkStringForLetters( inetAddress.getHostAddress() ) && !inetAddress.getHostAddress().equals( "192.168.137.1" )) {
                    System.out.println( "Ip address found: " + inetAddress.getHostAddress() );
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


}
