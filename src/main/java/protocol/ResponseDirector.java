package protocol;

import java.util.List;

public final class ResponseDirector {

    private ResponseDirector() {
    }

    public static Response singleOK(int code, String message) {
        return Response.single(code, message);
    }

    public static Response listing(int code, String header, List<String> lines, String footer) {
        return new FtpResponseBuilder()
                .code(code)
                .header(header)
                .lines(lines)
                .footer(footer)
                .build();
    }
}
