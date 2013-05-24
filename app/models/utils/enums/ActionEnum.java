package models.utils.enums;

public enum ActionEnum {
	NEW_WORKSHOP("action.add.new.workshop"),
	DELETE_WORKSHOP("action.delete.new.workshop"),
	MODIFY_WORKSHOP("action.modify.new.workshop"),
	ADD_SESSION("action.add.session"),
	MODIFY_SESSION("action.modify.session"),
	COMMENT("action.new.comment"),
	ADD_SUPPORT("action.new.support");
	
	private String message;
	
	private ActionEnum( String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}