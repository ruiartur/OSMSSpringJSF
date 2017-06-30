package info.cinematica.framework.presentation;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ExceptionHandlerFactoryImpl extends ExceptionHandlerFactory {
	private ExceptionHandlerFactory defaultFactory;
	
	public ExceptionHandlerFactoryImpl(ExceptionHandlerFactory defaultFactory) {
		if (defaultFactory == null) {
			throw new IllegalArgumentException("defaultFactory == null");
		}
		this.defaultFactory = defaultFactory;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler defaultHandler = defaultFactory.getExceptionHandler();
		ExceptionHandler handler = new ExceptionHandlerImpl(defaultHandler);		
		return handler;
	}
}