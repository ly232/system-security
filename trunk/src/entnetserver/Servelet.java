package entnetserver;

import XML.*;

public class Servelet{
	ThreadedHandler handle;
	XMLRequest xmlRequest;
	public Servelet(XMLRequest rq,ThreadedHandler th) {
		xmlRequest = rq;
		this.handle = th;
	}
	
}
