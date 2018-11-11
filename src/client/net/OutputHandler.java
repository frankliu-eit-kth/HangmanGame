package client.net;

/*
 * a single interface to connect the connect the low level net layer to the upper view layer
 * without which the client will have to create a view object to pass the new message to which will bring chaos into the top-down structure
 * therefore we create this third party agent to connect the two layers
 */

public interface OutputHandler {
	public void handleMsg(String msg);
}
