package info.cinematica.framework.presentation;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import trainning.osms.business.BusinessException;




class ExceptionHandlerImpl extends ExceptionHandlerWrapper {
	private ExceptionHandler defaultHandler;

	ExceptionHandlerImpl(ExceptionHandler defaultHandler) {
		this.defaultHandler = defaultHandler;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return defaultHandler;
	}

	@Override
	public void handle() throws FacesException {
		Iterable<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents();
		for (Iterator<ExceptionQueuedEvent> i = events.iterator(); i.hasNext(); ) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = event.getContext();
			Throwable exception = context.getException();
			BusinessException businessException = searchBusinessException(exception);
			if (businessException != null) {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary(businessException.getMessage());
				message.setDetail(businessException.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				i.remove();
			}
		}
		super.handle();
	}
	
	private BusinessException searchBusinessException(Throwable exception) {
		while (true) {
			if (exception == null) {
				return null;
			} else {
				if (exception instanceof BusinessException) {
					return (BusinessException) exception;
				} else {
					exception = exception.getCause();
				}
			}
		}
	}
}