package protocol;

public interface ResponseBuilder {
    ResponseBuilder code(int c);
    ResponseBuilder header(String text);
    ResponseBuilder addLine(String line);
    ResponseBuilder lines(Iterable<String> lines);
    ResponseBuilder footer(String text);
    Response buildSingle(String message);
    Response build();
}