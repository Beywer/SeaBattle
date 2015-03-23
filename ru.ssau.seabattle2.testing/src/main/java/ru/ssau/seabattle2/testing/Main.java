package ru.ssau.seabattle2.testing;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import ru.ssau.seabattle2.client.ServerFinder;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		try {
			System.out.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		ServerFinder finder = new ServerFinder();
		Thread thr = new Thread(finder);
		thr.start();
		Set<InetAddress> servers = finder.getServers();
		while(true){
			System.out.println(servers);
			Thread.sleep(500);
		}
	}

}
