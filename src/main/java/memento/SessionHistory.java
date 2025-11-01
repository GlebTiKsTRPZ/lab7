package memento;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SessionHistory {
    private final Deque<SessionMemento> stack = new ArrayDeque<>();
    private final int cap;

    public SessionHistory() {
        this(32);
    }

    public SessionHistory(int cap) {
        this.cap = Math.max(1, cap);
    }

    public void push(SessionMemento m) {
        if (m == null)
            return;
        if (stack.size() >= cap) {
            while (stack.size() >= cap)
                stack.removeLast();
        }
        stack.push(m);
    }

    public SessionMemento pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }

    public int size() {
        return stack.size();
    }
}