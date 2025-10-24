package protocol;

import java.util.List;

public final class Response {

    private final int code;
    private final String firstLine;
    private final List<String> contentLines;
    private final String lastLine;
    private final boolean multiline;

    Response(int code, String firstLine, List<String> contentLines, String lastLine, boolean multiline) {
        this.code = code;
        this.firstLine = firstLine;
        this.contentLines = contentLines == null ? List.of() : List.copyOf(contentLines);
        this.lastLine = lastLine;
        this.multiline = multiline;
    }

    public int code() {
        return code;
    }

    public boolean isMultiline() {
        return multiline;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!multiline) {
            sb.append(code).append(' ').append(firstLine != null ? firstLine : "");
            return sb.toString();
        }
        
        sb.append(code).append('-').append(firstLine != null ? firstLine : "").append("\r\n");
        for (int i = 0; i < contentLines.size(); i++) {
            if (i > 0) {
                sb.append("\r\n");
            }
            sb.append(contentLines.get(i));
        }
        sb.append("\r\n").append(code).append(' ').append(lastLine != null ? lastLine : "");
        return sb.toString();
    }

    public static Response single(int code, String message) {
        return new Response(code, message, List.of(), null, false);
    }

    public static Response multi(int code, String header, List<String> body, String footer) {
        return new Response(code, header, body, footer, true);
    }
}
