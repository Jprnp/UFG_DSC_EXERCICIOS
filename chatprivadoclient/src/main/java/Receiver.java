package com.github.jprnp.dsc20191.chatprivado.client;

public class Receiver implements Runnable {
	
	private Socket client;
	
	private Scanner entrada;
	
	public Receiver(Socket client) throws IOException {
		this.client = client;
		this.entrada = new Scanner(this.client.getInputStream());
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println(this.entrada.nextLine());
		}
	}
}