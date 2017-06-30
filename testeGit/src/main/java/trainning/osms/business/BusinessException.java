package trainning.osms.business;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -3603233273223742679L;

	public BusinessException(String message) {
		super(message);
	}
}