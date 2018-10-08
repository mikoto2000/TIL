package echo;

/**
 * EchoResponse
 */
public class EchoResponse {

    private String id;
    private String message;

    public EchoResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

