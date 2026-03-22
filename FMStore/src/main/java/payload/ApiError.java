package payload;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {
	private LocalDateTime timestamp;
	private String message; 
	private int status;
	
	
	public ApiError(LocalDateTime timestamp,int status, String message ) {
		this.message=message;
		this.status=status;
		this.timestamp=timestamp;
		
	}
/*
*	{
*		  "timestamp": "2026-03-19T12:00:00",
*		  "status": 404,
*		  "message": "Food not found"
*		}
*/
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	
	
}
