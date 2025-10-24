package protocol;

import java.util.ArrayList;
import java.util.List;

public class FtpResponseBuilder implements ResponseBuilder {

    private int code = 200;
    private String header = "";
    private final List<String> body = new ArrayList<>();
    private String footer = "OK";

    @Override
    public ResponseBuilder code(int c) {
        this.code = c;
        return this;
    }

    @Override
    public ResponseBuilder header(String text) {
        this.header = text == null ? "" : text;
        return this;
    }

    @Override
    public ResponseBuilder addLine(String l) {
        if (l != null) {
            body.add(l);

        }
        return this;
    }

    @Override
    public ResponseBuilder lines(Iterable<String> lines) {
        if (lines != null) {
            for (String s : lines) {
                if (s != null) {
                    body.add(s);
                }
            }
        }
        return this;
    }

    @Override
    public ResponseBuilder footer(String text) {
        this.footer = text == null ? "" : text;
        return this;
    }

    @Override
    public Response buildSingle(String message) {
        return Response.single(code, message);
    }

    @Override
    public Response build() {
        return Response.multi(code, header, List.copyOf(body), footer);
    }

    public FtpResponseBuilder reset() {
        this.code = 200;
        this.header = "";
        this.body.clear();
        this.footer = "OK";
        return this;
    }
}
