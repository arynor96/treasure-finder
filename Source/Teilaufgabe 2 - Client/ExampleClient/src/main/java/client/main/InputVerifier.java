package client.main;

import exceptions.ArgsNumberException;

public class InputVerifier {

	static void checkArgs(String[] args) throws ArgsNumberException {
		if (args.length < 3)
			throw new ArgsNumberException("Mode, URL or GameID is missing from the input parameters! ");
	}

}
